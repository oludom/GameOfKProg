package sokoban;

import br.com.supremeforever.mdi.MDIWindow;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sokoban.model.Level;
import sokoban.model.levelobjects.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

/**
 * @author MHeiß SWirries
 *
 */
public class Sokoban extends AnchorPane{

    private Canvas canvas;
    private GraphicsContext c;
    private MDIWindow mdiWindow;
    private ArrayList<CanvasButton> canvasButtons = new ArrayList<>();

    private double boxSize;
    private double levelXPos, levelYPos;

    private ArrayList<Level> levels = new ArrayList<>();
    private Level currentLevel;
    private String currentLevelFile;
    private Stage primaryStage;
    private CustomDialog customDialog;
    private SelectStateDialog selectStateDialog;

    public static final String StandardDirectory = "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\GOK_Sokoban";

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

        // create controll buttons
        // positions: y: 10 x: 20+70
        // size: 50*50
        // level select
        canvasButtons.add(
                new CanvasButton(
                        20,20,50,32,
                        "sokoban/images/buttons/Level.png",
                        "sokoban/images/buttons/Level_hover.png",
                        "levelselect"));
        // open level file
        canvasButtons.add(
                new CanvasButton(
                        90, 10, 50, 50,
                        "sokoban/images/buttons/open.png",
                        "sokoban/images/buttons/open_hover.png",
                        "open"
                )
        );
        // load saved game state
        canvasButtons.add(
                new CanvasButton(
                        160,10,50,50,
                        "sokoban/images/buttons/upload.png",
                        "sokoban/images/buttons/upload_hover.png",
                        "loadstate"
                )
        );
        // save game state
        canvasButtons.add(
                new CanvasButton(
                        230,10,50,50,
                        "sokoban/images/buttons/download.png",
                        "sokoban/images/buttons/download_hover.png",
                        "savestate"
                )
        );
        // undo
        canvasButtons.add(
                new CanvasButton(
                        300, 10,50,50,
                        "sokoban/images/buttons/undo.png",
                        "sokoban/images/buttons/undo_hover.png",
                        "undo"
                )
        );

        canvas.setOnMouseClicked(event -> {
            double x = event.getX(), y = event.getY();
            // compute canvasButtons
            for(CanvasButton cb : canvasButtons){
                if(cb.isInside(x,y)){ // TODO if not drag detected
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
                        case "open":
                            readLevel();
                            break;
                        case "undo":
                            currentLevel.undo();
                            break;
                        case "loadstate":
                            readSavedState();
                            break;
                        case "savestate":
                            saveState();
                            break;
                    }
                    break;
                }
            }

            if(currentLevel == null) return;
            // mouse control for player
            Player player = currentLevel.searchPlayer();
            double playerX = player.getPosition().getX()*boxSize + levelXPos;
            double playerY = player.getPosition().getY()*boxSize + levelYPos;

            if(x > (x-boxSize) && x < (x+2*boxSize) && y > (y-boxSize) && y < (y + 2*boxSize)){
                // top
                if(squareContains(x,y, playerX,playerY-boxSize,boxSize, boxSize)){
                    currentLevel.movePlayer(0,-1);
                }
                // right
                else if(squareContains(x,y, playerX+boxSize,playerY,boxSize, boxSize)){
                    currentLevel.movePlayer(1,0);
                }
                // bottom
                else if(squareContains(x,y, playerX,playerY+boxSize,boxSize, boxSize)){
                    currentLevel.movePlayer(0, 1);
                }
                // left
                else if(squareContains(x,y, playerX-boxSize,playerY,boxSize, boxSize)){
                    currentLevel.movePlayer(-1,0);
                }
                render();
            }
        });
        canvas.setOnMouseMoved(event -> {
            render();
            if(currentLevel == null) {

                CanvasButton cb = canvasButtons.get(1);

                if(cb.isInside(event.getX(), event.getY()))
                    c.drawImage(cb.getHover(), cb.getX(), cb.getY(), cb.getWidth(), cb.getHeight());

                return;
            }

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
    public void render(){

        if(currentLevel == null) {

            // Black background
            c.setFill(Color.BLACK);
            c.fillRect(0,0,canvas.getWidth(),canvas.getHeight());

            CanvasButton cb = canvasButtons.get(1);
            c.drawImage(cb.getImage(), cb.getX(), cb.getY(), cb.getWidth(), cb.getHeight());
            return;
        }

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
        boxSize = levelSize/divider;

        //                  zentrieren          unterschied im verhältnis zwischen Breite und Höhe, daher nochmal zentrieren
        levelXPos = (width-levelSize)/2+(levelSize-lWidth*boxSize)/2;
        levelYPos = (height-levelSize)/2+(levelSize-lHeight*boxSize)/4;

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
                        // make box look dark if it is on red crossed sand
                        if(currentLevel.isEndposition(new Vector(j, i)) && lo[i][j].getClass().equals(Box.class)){
                            tmp =  Box.toImage(true);
                        }else {
                            tmp =  lo[i][j].toImage(); // new Image("sokoban/images/wood.png");//
                        }
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
    public void readLevel(String file, boolean fromFileDialog){

        String stddir = getDataDirectory(true);

        if(fromFileDialog){
            ArrayList<String> fileAsString = fileToArrayList(file); // selected file
            String selectedFile = file.split(	"\\\\")[file.split("\\\\").length-1];
            copyLevel(fileAsString, stddir + "\\" + selectedFile);
            currentLevelFile = file;
            addLevel(fileAsString, selectedFile);
        }else{
            ArrayList<String> fileAsString = fileToArrayList(stddir + "\\" + file); // selected file
            currentLevelFile = file;
            addLevel(fileAsString, file);

        }
    }

    /**
     * generates ArrayList out of a file
     * @param file
     * @return
     */
    private ArrayList<String> fileToArrayList(String file){
        ArrayList<String> fileAsString = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line = bufferedReader.readLine();
            while (line != null){

                fileAsString.add(line);
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileAsString;
    }

    public void saveState(){

        // show dialog to enter name

        TextInputDialog dialog = new TextInputDialog(currentLevel.getTitle());
        dialog.setTitle("Level speichern");
        dialog.setHeaderText(null);
        dialog.setContentText("Bitte Name für Levelstatus eingeben: ");

        Optional<String> result;
        do {
            result = dialog.showAndWait();
            if(!result.isPresent()) break;

            // show again if file already exists
        }while (new File( getDataDirectory(false) + result.get().replace(' ', '_') + ".ssf").exists());

        if(result.isPresent()){

            // save state serialized as .ssf (SokobanStateFile || SerializedStateFile)
            saveSerialLevel(getDataDirectory(false) + "\\" + result.get().replace(' ', '_') + ".ssf");

        }


    }

    public void readSavedState(){

        String stddir = getDataDirectory(false);

        File folder = new File(stddir);
        File[] listOfFiles = folder.listFiles();
        ArrayList<String> filenames = new ArrayList<>();
        if(listOfFiles.length>0)
            for(File e : listOfFiles) filenames.add(e.getName().substring(0,e.getName().length()-4));
        if(filenames.size()<1) filenames.add(SelectStateDialog.NOSTATESELECTED);

        selectStateDialog = new SelectStateDialog(filenames, this);
        try {
            selectStateDialog.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * reads all level in appdata directory
     */
    public void readLevel(){

        String stddir = getDataDirectory(true);

        File folder = new File(stddir);
        File[] listOfFiles = folder.listFiles();
        ArrayList<String> filenames = new ArrayList<>();
        if(listOfFiles.length>0)
            for(File e : listOfFiles) filenames.add(e.getName());
        if(filenames.size()<1) filenames.add(CustomDialog.NOFILESELECTED);

        customDialog = new CustomDialog(filenames, this);
        try {
            customDialog.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }

//        for (File file : listOfFiles) {
//            if (file.isFile()) {
//                addLevel(fileToArrayList(file.getPath()), file.getName());
//            }
//        }

    }


    /**
     * creates file out of level Strings
     * @param fileAsString
     * @param filePath
     */
    private void copyLevel(ArrayList<String> fileAsString, String filePath){

        try {
            PrintWriter writer = new PrintWriter(filePath, "UTF-8");

            for(String element : fileAsString){
                writer.println(element);
            }

            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    private void addLevel(ArrayList<String> fileAsString, String filename){

        levels.clear();

        // create each level
        ArrayList<String> levellines = new ArrayList<>();
        int levelindex = -1;
        int levelcount = 0;
        String leveltitle = "";

        for(String element : fileAsString){
            if(element.matches("(Level )([0-9]+)")){ // level nummer
                if(levelindex != -1){
                    if(leveltitle.equals("")){
                        leveltitle = filename.substring(0, filename.length()-5).toUpperCase() + " " + levelindex;
                    }
                    levels.add(new Level(levellines, this, leveltitle, levelcount++, currentLevelFile));
                    leveltitle = "";
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
        if(leveltitle.equals("")){
            leveltitle = filename.substring(0, filename.length()-5).toUpperCase() + " " + levelindex;
        }
        levels.add(new Level(levellines, this, leveltitle, levelcount++, currentLevelFile));

        if(levels.size()>0) currentLevel = levels.get(0);
        render();
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

        //int current = levels.indexOf(currentLevel);
        // changed because of serialization
        int current = currentLevel.getLevelNumber();

        if(levels.size()>current+1){
            currentLevel = levels.get(current+1);
            if(mdiWindow != null) mdiWindow.setMdiTitle(currentLevel.getTitle());
            render();
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Finally...");
            alert.setHeaderText(null);
            alert.setContentText("Glückwunsch! Du hast alle Level erfolgreich gelöst!");
            alert.showAndWait();
        }

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

    /**
     * checks if coordinates are inside a given rectangle
     * @param x x coordinate to check
     * @param y y coordinate to check
     * @param sx x coordinate of rectangle
     * @param sy y coordinate of rectangle
     * @param w width of rectangle
     * @param h height of rectangle
     * @return true if x,y inside rectangle (sx,sy,w,h)
     */
    private boolean squareContains(double x, double y, double sx, double sy, double w, double h){
        return x>sx && x<(sx+w) && y>sy && y<(sy+h);
    }


    private void saveSerialLevel(String filename){

        try {
            FileOutputStream fs = new FileOutputStream(filename);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(currentLevel);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readSerialLevel(String filename){

        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(getDataDirectory(false) + "\\" + filename));
            Level tmp = (Level) inputStream.readObject();
            tmp.setParent(this);
            if(new File(getDataDirectory(true) + "\\" + tmp.getLevelFile()).exists()){
                readLevel(currentLevel.getLevelFile(), false);
                currentLevelFile = tmp.getLevelFile();
            }
            currentLevel = tmp;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void showLevelFileDialog(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Lade Level Datei");
//        fileChooser.setInitialFileName("GoL-Exported.txt");
        fileChooser.setInitialDirectory(new File("C:\\Users\\" + System.getProperty("user.name") + "\\Desktop"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Dateien (*.txt)", "*.txt"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Alle Dateien (*.*)", "*"));
        File file = fileChooser.showOpenDialog(null);
        if(file != null){
            readLevel(file.getPath(), true);
            customDialog.addLogItem(file.getName());
        }
    }

    /**
     * get (and create) level or saves directory
     * @param levelorsave true: \level, false: \saves
     * @return
     */
    private String getDataDirectory(boolean levelorsave){

        String levelDirString = StandardDirectory + "\\";
        levelDirString += levelorsave ?  "level" : "save";
        File mainDir = new File(StandardDirectory);
        File levelDir = new File(levelDirString);

        // if the directory does not exist, create it
        if (!mainDir.exists()) {
            boolean result = false;

            try{
                result = mainDir.mkdir();

            }
            catch(SecurityException se){
                se.printStackTrace();
            }
            if(!result) {
                System.out.println("DIR NOT created!");
                return null;
            }
        }
        if(!levelDir.exists()){
            boolean result = false;
            try{
                result = levelDir.mkdir();

            }catch (SecurityException se){
                se.printStackTrace();
            }
            if(!result){
                System.out.println("LevelDir NOT created");
                return null;
            }
        }
        return levelDirString;
    }


}
