package sokoban;

import br.com.supremeforever.mdi.MDIWindow;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sokoban.model.Level;
import sokoban.model.levelobjects.LevelObject;
import sokoban.model.levelobjects.Player;
import sokoban.model.levelobjects.Space;
import sokoban.model.levelobjects.Stone;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Optional;

/**
 * @author Micha Heiß
 */
public class Sokoban extends AnchorPane{

    private Canvas canvas;
    private GraphicsContext c;
    private MDIWindow mdiWindow;
    private ArrayList<CanvasButton> canvasButtons = new ArrayList<>();

    private ArrayList<Level> levels = new ArrayList<>();
    private Level currentLevel;
    private Stage primaryStage;

    public Sokoban(Stage primaryStage){
        this.primaryStage = primaryStage;
        readLevel();

        this.setMinSize(800,600);

        CanvasPane canvasPane = new CanvasPane(800,600);
        canvas = canvasPane.getCanvas();
        c = canvas.getGraphicsContext2D();
        this.getChildren().add(canvasPane);

        this.widthProperty().addListener(e -> render());
        this.heightProperty().addListener(e -> render());

        AnchorPane.setBottomAnchor(canvasPane, 0d);
        AnchorPane.setLeftAnchor(canvasPane, 0d);
        AnchorPane.setTopAnchor(canvasPane, 0d);
        AnchorPane.setRightAnchor(canvasPane, 0d);

        bindListener();

        // TODO remove?
        // start with first Level
        currentLevel = levels.get(0);

//        render();

        canvasButtons.add(new CanvasButton(50,50,50,32,"sokoban/images/Level.png", "sokoban/images/Level_hover.png", "levelselect"));

        canvas.setOnMouseClicked(event -> {
            // compute canvasButtons
            for(CanvasButton cb : canvasButtons){
                if(cb.isInside(event.getX(), event.getY())){ // TODO if not drag detected
                    switch (cb.getName()){
                        case "levelselect":

                            ArrayList<String> choices = new ArrayList<>();
                            for(Level level : levels){
                                choices.add(level.getTitle());
                            }
                            String res = getChoice("Starte Level", null, "Wählen Sie ein Level: ", choices, 0, 0);
                            if(!res.equals("")){
                                currentLevel = getLevelByTitle(res);
                            }
                            break;
                    }
                    break;
                }
            }
        });
        canvas.setOnMouseMoved(event -> {
            render();

            for (CanvasButton cb : canvasButtons){
                if(cb.isInside(event.getX(), event.getY()))
                    c.drawImage(cb.getHover(), cb.getX(), cb.getY(), cb.getWidth(), cb.getHeight());
            }
        });

    }

    private Level getLevelByTitle(String title) {
        Level out = levels.get(0);
        for(Level level : levels){
            if(level.getTitle().equals(title)){
                out = level;
            }
        }
        return out;
    }

    public void setMdiWindow(MDIWindow mdiWindow) {
        this.mdiWindow = mdiWindow;
    }

    /**
     * update canvas, redraw all images contained in current level
     */
    private void render(){

        double width, height, levelSize;
        LevelObject[][] lo = currentLevel.getLevelObjects();
        int lWidth = lo[0].length; // Anzahl Boxen in der Breite
        int lHeight = lo.length;   // Anzahl Boxen in der Höhe
        int divider;

        if(mdiWindow != null) {
            width = mdiWindow.getWidth();
            height = mdiWindow.getHeight();
            canvas.setWidth(width);
            canvas.setHeight(height);
            // set title to level name
            mdiWindow.setMdiTitle(currentLevel.getTitle());
        }else {
            width = canvas.getWidth();
            height = canvas.getHeight();
        }

        if(width<height){
            levelSize = width;
        }else {
            levelSize = height;
        }
        if(lWidth>lHeight){
            divider = lWidth;
        }else {
            divider = lHeight;
        }

        divider++;
        double boxSize = levelSize/divider;

        //                  zentrieren          unterschied im verhältnis zwischen Breite und Höhe, daher nochmal zentrieren
        double levelXPos = (width-levelSize)/2+(levelSize-lWidth*boxSize)/2;
        double levelYPos = (height-levelSize)/2+(levelSize-lHeight*boxSize)/4;

        // Black background
//        c.setFill(Color.BLACK);
//        c.fillRect(0,0,width,height);

        // calculate minimum size of image to fill canvas completely
        double minsize = width>height ? width : height;
        c.drawImage(new Stone().toImage(), 0,0,minsize,minsize);


        // render currentLevel

        for(int i = 0; i<lo.length;i++){
            for(int j = 0; j<lo[0].length; j++){
                Image tmp;
                if(lo[i][j] != null){

                    // check whether sand in background needs to represent end position
                    if(!lo[i][j].getClass().equals(Stone.class)) {
                        if (currentLevel.isEndposition(new Vector(j, i))) {
                            tmp = Space.toImage(true);
                        } else {
                            tmp = Space.toImage(false);
                        }
                        // draw sand as background
                        c.drawImage(tmp, levelXPos + j * boxSize, levelYPos + i * boxSize, boxSize, boxSize);
                    }

                    // set Player position for key movement
                    if(lo[i][j].getClass().equals(Player.class)) lo[i][j].setPosition(new Vector(j,i));

                    // if not Space, draw object
                    if(!lo[i][j].getClass().equals(Space.class)){
                        tmp =  lo[i][j].toImage(); // new Image("sokoban/images/wood.png");//
                        if(!lo[i][j].getClass().equals(Stone.class)) c.drawImage(tmp,levelXPos+j*boxSize, levelYPos+i*boxSize, boxSize, boxSize );
                    }
                }
            }
        }


        // add canvas Buttons
        for (CanvasButton cb : canvasButtons){
            c.drawImage(cb.getImage(), cb.getX(), cb.getY(), cb.getWidth(), cb.getHeight());
        }

    }

    /**
     * reads level data from file, parses it and fills level array with levels from file
     */
    public void readLevel(){

        // TODO debug
        // TODO maybe create it first
        String dir = "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\GOK_Sokoban";
        String filename = "minicosmos.txt";
        ArrayList<String> fileAsString = new ArrayList<>();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(dir+"\\"+filename));
            String line = bufferedReader.readLine();
            while (line != null){

                fileAsString.add(line);
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // create each level
        ArrayList<String> levellines = new ArrayList<>();
        int levelindex = -1;
        String leveltitle = "";

        for(String element : fileAsString){
            if(element.matches("(Level )([0-9]+)")){ // level nummer
                if(levelindex != -1){
                    levels.add(new Level(levellines, this, leveltitle));
                }
                levellines = new ArrayList<>();
                String[] parts = element.split(" ");
                levelindex = Integer.valueOf(parts[1]);
            }else if (element.matches("(')(.)+(')")) { // titel umringt von '
                String[] parts = element.split("'");
                leveltitle = parts[1];
            }else if(element.matches("( )*(#)(.)+(#)")){
                levellines.add(element);
            }
        }
        levels.add(new Level(levellines, this, leveltitle));

    }


    /**
     * scalable canvas element to fit resizeable window
     */
    public static class CanvasPane extends Pane {

        private final javafx.scene.canvas.Canvas canvas;

        public CanvasPane(double width, double height) {
            canvas = new javafx.scene.canvas.Canvas(width, height);
            getChildren().add(canvas);
        }

        public javafx.scene.canvas.Canvas getCanvas() {
            return canvas;
        }

        @Override
        protected void layoutChildren() {
            final double x = snappedLeftInset();
            final double y = snappedTopInset();
            final double w = snapSize(getWidth()) - x - snappedRightInset();
            final double h = snapSize(getHeight()) - y - snappedBottomInset();
            canvas.setLayoutX(x);
            canvas.setLayoutY(y);
            canvas.setWidth(w);
            canvas.setHeight(h);
        }
    }

    /**
     * binds key controls to active internal frame
     */
    public void bindListener(){
        primaryStage.getScene().setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    currentLevel.movePlayer(0,-1);
                    break;
                case DOWN:
                    currentLevel.movePlayer(0, 1);
                    break;
                case LEFT:
                    currentLevel.movePlayer(-1,0);
                    break;
                case RIGHT:
                    currentLevel.movePlayer(1,0);
                    break;
                case U:
                    currentLevel.undo();
                    render();
                    break;
            }
            render();
        });
    }

    /**
     * starts the next level contained in levels ArrayList if possible
     */
    public void startNextLevel(){

        int current = levels.indexOf(currentLevel);

        if(levels.size()>current+1){
            currentLevel = levels.get(current+1);
            if(mdiWindow != null) mdiWindow.setMdiTitle(currentLevel.getTitle());
            render();
        } // TODO winning message after last level solved

    }

    /**
     * easy to use method for choice dialogs
     * @param title title of dialog
     * @param header header text
     * @param content information for user
     * @param choices ArrayList containing choices to chose from
     * @param xPosition may contain x position information
     * @param yPosition may contain x position information
     * @return returns empty string or selected item from Array
     */
    private String getChoice(String title, String header, String content, ArrayList<String> choices, double xPosition, double yPosition){

        ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(content);
        if((xPosition != 0) && (yPosition != 0)){
            dialog.setX(xPosition);
            dialog.setY(yPosition);
        }

        Optional<String> result = dialog.showAndWait();
        if(result.isPresent())return result.get();
        return "";
    }

}
