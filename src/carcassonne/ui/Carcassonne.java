package carcassonne.ui;

import carcassonne.game.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;
import java.util.Optional;


/**
 * @author Micha Heiß
 */
public class Carcassonne extends Application{

    private Canvas canvas;
    private Boolean canvasAllowUserInput = false;
    private GraphicsContext c;
    private BorderPane root;
    private ArrayList<CanvasButton> canvasButtons = new ArrayList<>();
    private ArrayList<Landschaftskarte> gelegteLandschaftskarten = new ArrayList<>();
    private Landschaftskarte currentLKarte;
    private Spieler currentSpieler;
    private int currentSpielerIndex;
    private double currentLKartX, currentLKartY;
    private Stapel stapel;
    private Spieler[] spielers;
    private PlayerStatusBar playerStatusBar = new PlayerStatusBar(new AnchorPane());
    private boolean gameFinished = false;

    private double dragStartX = 0, dragStartY = 0;
    private int planWidth = 1000;
    private int planHeight = 1000;
    private boolean closeApplication = false;

    private double originX = 0, originY = 0;
    Carcassonne me = this;
    Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Carcassonne");
        stage = primaryStage;
        CanvasPane canvasPane = new CanvasPane(1920, 1080);
        canvas = canvasPane.getCanvas();
        c = canvas.getGraphicsContext2D();

        root = new BorderPane(canvasPane);
        root.setBottom(playerStatusBar.getNode());
        Scene scene = new Scene(root, 1200,800);
        primaryStage.setScene(scene);
        scene.getStylesheets().add("carcassonne/css/styles.css");

        primaryStage.show();




        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                render();
            }
        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                render();
            }
        });
        //Maximiert bei F11
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode()== KeyCode.F11) {
                primaryStage.setFullScreen(true);
                primaryStage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
                primaryStage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
                render();
            }
        });
        primaryStage.maximizedProperty().addListener(event -> render());


        /*
        events für canvas
         */

        canvas.setOnMousePressed(event -> {
            dragStartX = event.getSceneX();
            dragStartY = event.getSceneY();
        });
        canvas.setOnMouseMoved(e -> {
            if(!gameFinished){
                render();
                drawCardHover(e);
            }
        });
        /**
         * Verschiebt die Karten auf der Spielfeld um an einer Seite weiter bauen zu können
         */
        canvas.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                double x = event.getSceneX();
                double y = event.getSceneY();

                double deltaX = x-dragStartX;
                double deltaY = y-dragStartY;

                originX += deltaX;
                originY += deltaY;

                int maxCardRowWidth = gelegteLandschaftskarten.size() * 100;
                if(maxCardRowWidth > planWidth){
                    planWidth += 1000;
                    planHeight += 1000;
                }
                if(originX- planWidth /2 > 50 || (originX+ planWidth /2) < (canvas.getWidth()-50)){
                    // reset
                    originX -= deltaX;
                }
                if(originY- planHeight /2 > 50 || (originY+ planHeight /2) < (canvas.getHeight()-50)){
                    // reset
                    originY -= deltaY;
                }

                dragStartX += deltaX;
                dragStartY += deltaY;

                render();
            }
        });
        /**
         * Click Event
         * entweder auf einen der CanvasButtons oder um eine Karte zu plazieren
         */
        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(gameFinished) return;
                // compute canvasButtons
                boolean foundButton = false;
                for(CanvasButton cb : canvasButtons){
                    if(cb.isInside(event.getSceneX(), event.getSceneY())){
                        switch (cb.getName()){
                            case "Rotate Left":
                                foundButton = true;
                                currentLKarte.rotate(false,1);

                                break;
                            case "Rotate Right":
                                foundButton = true;
                                currentLKarte.rotate(true,1);
                                break;
                            case "Surrender":
                                Alert alertSurrender = new Alert(Alert.AlertType.CONFIRMATION);
                                alertSurrender.setTitle("Spiel beenden");
                                alertSurrender.setHeaderText("Möchten Sie das Spiel wirklich beenden?");
                                Optional<ButtonType> resultSurrender = alertSurrender.showAndWait();
                                if (resultSurrender.get() == ButtonType.OK){
                                    finishGame();
                                }
                                break;
                            case "ThrowCard":
                                Alert alertThrowCard;
                                if(currentLKarte.getName().equalsIgnoreCase("C") || currentLKarte.getName().equalsIgnoreCase("X")){
                                    alertThrowCard = new Alert(Alert.AlertType.CONFIRMATION);
                                    alertThrowCard.setTitle("Karte wegwerfen");
                                    alertThrowCard.setHeaderText("Möchten Sie die Karte wirklich wegwerfen?");
                                    Optional<ButtonType> resultCard = alertThrowCard.showAndWait();
                                    if (resultCard.get() == ButtonType.OK){
                                        //TODO Karte auf anlege Möglichkeit prüfen (alternativ nur bei Karte C und X)
                                        currentLKarte = stapel.drawLandschaftskarte();
                                    }
                                    break;
                                }else {
                                    alertThrowCard = new Alert(Alert.AlertType.INFORMATION);
                                    alertThrowCard.setTitle("Karte wegwerfen");
                                    alertThrowCard.setHeaderText("Das Wegwerfen bei dieser Karte ist nicht möglich!");
                                    alertThrowCard.showAndWait();
                                }
                            case "Manual":{
                                getHostServices()
                                        .showDocument("http://www.brettspiele-report.de/images/carcassonne/Spielanleitung_Carcassonne.pdf");
                            }
                        }
                        render();
                        break;
                    }
                }
                if(!foundButton){
                    if (canvasAllowUserInput) {
                        placeLKarte();
                    }
                }
            }
        });

        // set origin to center
        originX = canvas.getWidth()/2;
        originY = canvas.getHeight()/2;

        /**
         * Erzeugt die Steuerelemete des Spiels
         */
        canvasButtons.add(new CanvasButton(500,20,60,60, Stapel.rotateLeft, "Rotate Left"));
        canvasButtons.add(new CanvasButton(700,20,60,60, Stapel.rotateRight, "Rotate Right"));
        canvasButtons.add(new CanvasButton(100,20,60,60, Stapel.manual, "Manual"));
        canvasButtons.add(new CanvasButton(200,20,60,60, Stapel.surrender, "Surrender"));
        canvasButtons.add(new CanvasButton(300,20,60,60, Stapel.throwCard, "ThrowCard"));

        /**
         * Start Karte und erster Zug
         */
        stapel  = new Stapel();
        Landschaftskarte startKarte = stapel.getStartLandschaftskarte();
        startKarte.setX(0);
        startKarte.setY(0);
        gelegteLandschaftskarten.add(startKarte);
        currentLKarte = stapel.drawLandschaftskarte();

        PlayerInputController playerInputController = new PlayerInputController(new AnchorPane(), this);
        int countPlayerInput = 0;

        while (spielers == null && !closeApplication){
            if(countPlayerInput > 0 && !closeApplication){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Spiel beenden");
                alert.setHeaderText("Möchten Sie das Spiel wirklich beenden?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    //System.exit(0);
                    playerInputController.close();
                    stage.close();
                    closeApplication = true;
                }
            }
            if(!closeApplication)playerInputController.showAndWait();
            countPlayerInput++;
        }

        render();
    }

    /**
     * Beendet das Spiel und berechnet die Punkte der noch plazierten Gefolgsleute
     */
    private void machJetztSofortDieEndwertung() {

        gameFinished = true;

        for(Spieler s : spielers){
            Gefolgsmann[] gefolgsmanns = s.getAllGefolgsleute();
            for(Gefolgsmann g : gefolgsmanns){

                if(g.getRolle() != RolleT.FREI){
                    Landschaftsteil parent = g.getGebiet();
                    parent.sammlePoints();
                }
            }
        }
        playerStatusBar.setPlayer(spielers, currentSpielerIndex);

    }

    /**
     * Rendert das Spielfeld neu
     */
    public void render(){
            // canvas background
            c.setFill(Color.WHEAT);
            c.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

            //draw landschaftskarten
            for (Landschaftskarte lkarte : gelegteLandschaftskarten) {
                c.drawImage(lkarte.getImage(), lkarte.getX() * lkarte.getWidth() + originX,
                        lkarte.getY() * lkarte.getHeight() + originY);
            }

            //draw Gefolgsmann
            if(spielers != null) {
                for (Spieler spieler : spielers) {
                    for (Gefolgsmann gefolgsmann : spieler.getAllGefolgsleute()) {
                        if (gefolgsmann.getRolle() != RolleT.FREI && gefolgsmann.getAbsolutePosition() != null) {
                            c.drawImage(gefolgsmann.getImage(), gefolgsmann.getAbsolutePosition().getX() + originX,
                                    gefolgsmann.getAbsolutePosition().getY() + originY, 25, 24);
                        }

                    }
                }
            }

            //draw canvas Buttons
            for (CanvasButton cb : canvasButtons) {
                c.drawImage(cb.getImage(), cb.getX(), cb.getY(), cb.getWidth(), cb.getHeight());
            }
            //draw currentKarte
            c.drawImage(currentLKarte.getImage(), 600, 20, 60, 60);

    }

    /**
     * Erzeugt einen Hover-Effekt am Rand der Karten
     * @param e
     */
    public void drawCardHover(MouseEvent e){
        Image rotatedImage = currentLKarte.getImage();
        canvasAllowUserInput = false;
        for(Landschaftskarte landschaftskarte : gelegteLandschaftskarten) {

            double x = e.getSceneX()-originX, y = e.getSceneY()-originY;

            double lx = landschaftskarte.getX()*landschaftskarte.getWidth(), ly = landschaftskarte.getY()*landschaftskarte.getHeight(),
                lw = landschaftskarte.getWidth(), lh = landschaftskarte.getHeight();

            /**
             * Prüft ob die Karte dort angezeigt werden kann
             */
            if (x > (lx - lw) && x < (lx + 2 * lw) && y > (ly - lh) && y < (ly + 2 * lh)) {

                boolean showKard = false;
                // karte darüber
                if (squareContains(x, y, lx, ly - lh, lw, lh)) {
                    currentLKartX = landschaftskarte.getX() + 0;
                    currentLKartY = landschaftskarte.getY() - 1;
                    showKard = true;
                    canvasAllowUserInput = true;
                } else if (squareContains(x, y, lx + lw, ly, lw, lh)) { // karte rechts
                    currentLKartX = landschaftskarte.getX() + 1;
                    currentLKartY = landschaftskarte.getY() - 0;
                    showKard = true;
                    canvasAllowUserInput = true;
                } else if (squareContains(x, y, lx, ly + lh, lw, lh)) { // karte unten
                    currentLKartX = landschaftskarte.getX() + 0;
                    currentLKartY = landschaftskarte.getY() + 1;
                    showKard = true;
                    canvasAllowUserInput = true;
                } else if (squareContains(x, y, lx - lw, ly, lw, lh)) { // karte links
                    currentLKartX = landschaftskarte.getX() - 1;
                    currentLKartY = landschaftskarte.getY() - 0;
                    showKard = true;
                    canvasAllowUserInput = true;
                }
                for(Landschaftskarte landschaftskarte2 : gelegteLandschaftskarten){
                    if (currentLKartX == landschaftskarte2.getX() && currentLKartY == landschaftskarte2.getY()) {
                        showKard = false;
                        canvasAllowUserInput = false;
                    }
                }
                if(spielers == null){
                    showKard =false;
                    canvasAllowUserInput = false;
                }
                if (showKard) c.drawImage(rotatedImage, currentLKartX*currentLKarte.getWidth()+originX,
                        currentLKartY*currentLKarte.getHeight()+originY);

            }
        }
    }

    /**
     * Prüft ob x und y in einem Rechteck aus sx,sy, w und h sind
     * @param x
     * @param y
     * @param sx
     * @param sy
     * @param w
     * @param h
     * @return
     */
    private boolean squareContains(double x, double y, double sx, double sy, double w, double h){
        return x>sx && x<(sx+w) && y>sy && y<(sy+h);
    }

    /**
     * Prüft ob die Karte gelegt werden kann
     * Plaziert die Karte auf dem Spielfeld (CanvasPane)
     * Ziegt einen neue Karte und der nächste Spieler ist an der Reihe
     */
    private void placeLKarte(){
        Landschaftskarte nordKarte = null;
        Landschaftskarte ostKarte = null;
        Landschaftskarte suedKarte = null;
        Landschaftskarte westKarte = null;

        boolean bNord = true;
        boolean bOst = true;
        boolean bSued = true;
        boolean bWest = true;

        /**
         * Sammelt mögliche Nachbar-Karten
         */
        for(Landschaftskarte landschaftskarte : gelegteLandschaftskarten){
            if(landschaftskarte.getX() == currentLKartX && landschaftskarte.getY() == currentLKartY-1)
                nordKarte = landschaftskarte;
            if(landschaftskarte.getX() == currentLKartX+1 && landschaftskarte.getY() == currentLKartY)
                ostKarte = landschaftskarte;
            if(landschaftskarte.getX() == currentLKartX && landschaftskarte.getY() == currentLKartY+1)
                suedKarte = landschaftskarte;
            if(landschaftskarte.getX() == currentLKartX-1 && landschaftskarte.getY() == currentLKartY)
                westKarte = landschaftskarte;
        }
        //Prüft ob Karte gelegt werden kann
        if(nordKarte != null) bNord = currentLKarte.addNeighbor(nordKarte, HimmelsrichtungT.NORD, false);
        if(ostKarte != null) bOst = currentLKarte.addNeighbor(ostKarte, HimmelsrichtungT.OST, false);
        if(suedKarte != null) bSued = currentLKarte.addNeighbor(suedKarte, HimmelsrichtungT.SUED, false);
        if(westKarte != null) bWest = currentLKarte.addNeighbor(westKarte, HimmelsrichtungT.WEST, false);

        if(bNord && bOst && bSued && bWest){ //Wenn ja
            currentLKarte.setX(currentLKartX);
            currentLKarte.setY(currentLKartY);
            gelegteLandschaftskarten.add(currentLKarte);
            //Karte bzw Landschaftsteile miteinander Verbinden
            if(nordKarte != null) currentLKarte.addNeighbor(nordKarte, HimmelsrichtungT.NORD, true);
            if(ostKarte != null) currentLKarte.addNeighbor(ostKarte, HimmelsrichtungT.OST, true);
            if(suedKarte != null) currentLKarte.addNeighbor(suedKarte, HimmelsrichtungT.SUED, true);
            if(westKarte != null) currentLKarte.addNeighbor(westKarte, HimmelsrichtungT.WEST, true);

            //Erhöht den FillField Count wenn Kloster vorhanden
            for(Landschaftskarte landschaftskarte : gelegteLandschaftskarten){
                //oben links
                if(landschaftskarte.getX() == currentLKarte.getX() -1 && landschaftskarte.getY() == currentLKarte.getY()-1){
                    if(currentLKarte.hasKloster()) currentLKarte.getKloster().addFillFreeField();
                    if(landschaftskarte.hasKloster()) landschaftskarte.getKloster().addFillFreeField();
                }
                //oben mitte
                if(landschaftskarte.getX() == currentLKarte.getX() && landschaftskarte.getY() == currentLKarte.getY()-1){
                    if(currentLKarte.hasKloster()) currentLKarte.getKloster().addFillFreeField();
                    if(landschaftskarte.hasKloster()) landschaftskarte.getKloster().addFillFreeField();
                }
                //oben rechts
                if(landschaftskarte.getX() == currentLKarte.getX() +1 && landschaftskarte.getY() == currentLKarte.getY()-1){
                    if(currentLKarte.hasKloster()) currentLKarte.getKloster().addFillFreeField();
                    if(landschaftskarte.hasKloster()) landschaftskarte.getKloster().addFillFreeField();
                }
                // mitte links
                if(landschaftskarte.getX() == currentLKarte.getX() -1 && landschaftskarte.getY() == currentLKarte.getY()){
                    if(currentLKarte.hasKloster()) currentLKarte.getKloster().addFillFreeField();
                    if(landschaftskarte.hasKloster()) landschaftskarte.getKloster().addFillFreeField();
                }
                //mitte rechts
                if(landschaftskarte.getX() == currentLKarte.getX() +1 && landschaftskarte.getY() == currentLKarte.getY()){
                    if(currentLKarte.hasKloster()) currentLKarte.getKloster().addFillFreeField();
                    if(landschaftskarte.hasKloster()) landschaftskarte.getKloster().addFillFreeField();
                }
                //unten links
                if(landschaftskarte.getX() == currentLKarte.getX() -1 && landschaftskarte.getY() == currentLKarte.getY()+1){
                    if(currentLKarte.hasKloster()) currentLKarte.getKloster().addFillFreeField();
                    if(landschaftskarte.hasKloster()) landschaftskarte.getKloster().addFillFreeField();
                }
                //unten mitte
                if(landschaftskarte.getX() == currentLKarte.getX() && landschaftskarte.getY() == currentLKarte.getY()+1) {
                    if(currentLKarte.hasKloster()) currentLKarte.getKloster().addFillFreeField();
                    if(landschaftskarte.hasKloster()) landschaftskarte.getKloster().addFillFreeField();
                }
                //unten rechts
                if(landschaftskarte.getX() == currentLKarte.getX() +1 && landschaftskarte.getY() == currentLKarte.getY()+1){
                    if(currentLKarte.hasKloster()) currentLKarte.getKloster().addFillFreeField();
                    if(landschaftskarte.hasKloster()) landschaftskarte.getKloster().addFillFreeField();
                }
            }
            //Ermögliche dem Spieler eine Gefolgsmann zusetzen, wenn dieser noch Frei besitzt
            if(currentSpieler.countFreieGefolgsleute() != 0){
                currentLKarte.setGefolgsmann(currentSpieler.getFreienGeflogsmann());
            }
            //Neue Karte zeihen
            currentLKarte = stapel.drawLandschaftskarte();
            if(currentLKarte == null){
                finishGame();
                return;
            }
            //nächster Spieler
            int spielerIndex = (++currentSpielerIndex)%spielers.length;

            currentSpieler = spielers[spielerIndex];
            currentSpielerIndex = spielerIndex;
            //Aktuallisiert die Anzige
            playerStatusBar.setPlayer(spielers, currentSpielerIndex);
        }else {
            //Wenn nicht gelegt werden kann
            currentLKarte.getInformation();
            System.out.println(bNord + " " + bOst + " "+ bSued + " " +bWest);
            if(nordKarte != null){
                System.out.println("--NORD--");
                nordKarte.getInformation();
            }
            if(ostKarte != null){
                System.out.println("--OST--");
                ostKarte.getInformation();
            }
            if(suedKarte != null){
                System.out.println("--SUED--");
                suedKarte.getInformation();
            }
            if(westKarte != null){
                System.out.println("--WEST--");
                westKarte.getInformation();
            }
        }

        render();

    }

    /**
     * Setzt die Spieler des Spiels
     * @param spielers
     */
    public void setSpielers(Spieler[] spielers) {
        this.spielers = spielers;
        currentSpieler = spielers[0];
        currentSpielerIndex = 0;
        playerStatusBar.setPlayer(spielers, currentSpielerIndex);
    }

    public void finishGame(){
        machJetztSofortDieEndwertung();

        WinningController winningController = new WinningController(new AnchorPane(), me, spielers);
        winningController.showAndWait();
    }

    public void setCloseApplication(boolean closeApplication) {
        this.closeApplication = closeApplication;
    }
}
