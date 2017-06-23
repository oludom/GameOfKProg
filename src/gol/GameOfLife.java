package gol;

import MainUI.GameTypT;
import MainUI.Main;
import br.com.supremeforever.mdi.MDIWindow;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.ArrayList;

/**
 * 16.05.2017
 *
 * @author SWirries MHeiß
 *
 */
public class GameOfLife extends Pane {

    Node content;
    World world; //Welt
    GoLController golController; //JFX UI Controller
    int worldHeight = 0;
    int worldWidth = 0;
    private ArrayList<CellPane> cellPaneList = new ArrayList<>();
    private boolean enableDrawing = false;//ob Malen aktiv ist
    private String colorDead = "000000";//Anfangsfarbe tote Zellen
    private String colorAlive = "FFFFFF";//Anfangsfarbe lebende Zellen
    private MDIWindow mdiWindow; //Anzeigefenster

    /**
     * Erzeugt neues Fenster mit neur Welt
     */
    public GameOfLife(){
        this.setMinSize(500,500);
        new WorldDimDialog(this);
//        worldHeight = 20;
//        worldWidth = 20;
        world = new World(worldWidth, worldHeight);
        init();
    }

    /**
     * Erzeugt ein neues Fenster mit vorhandender Welt
     * @param world
     */
    public GameOfLife(World world){
        this.setMinSize(500,500);
        this.world = world;
        init();
    }

    /**
     * Inizialisiert die UI
     */
    private void init(){

        Color c = Color.WHITE;
        colorAlive = String.format("#%02X%02X%02X", (int)(c.getRed()*255),
                (int)(c.getGreen()*255), (int)(c.getBlue()*255));
        c = Color.BLACK;
        colorDead = String.format("#%02X%02X%02X", (int)(c.getRed()*255),
                (int)(c.getGreen()*255), (int)(c.getBlue()*255));

        Platform.runLater(() -> {
            do{
                mdiWindow = (MDIWindow) this.getParent().getParent();

            }while (mdiWindow == null);

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gol/gameoflife.fxml"));
                golController = new GoLController();
                fxmlLoader.setController(golController);
                content = fxmlLoader.load();
                mdiWindow.setContent(content,this);
                world.addView();
                generateWorld();

                /*
                Setzt onAction der Menu-Einträge
                 */
                Menu colorMenu = golController.getColorMenu();
                ColorPicker pickerAlive = new ColorPicker(Color.WHITE);
                ColorPicker pickerDead = new ColorPicker(Color.BLACK);

                MenuItem colorAliveItem = new MenuItem("lebende Zellen",pickerAlive);
                colorAliveItem.setOnAction(event -> {
                    Color color = pickerAlive.getValue();
                    refillCellPane(color, true);
                });

                MenuItem colorDeadItem = new MenuItem("tote Zellen",pickerDead);
                colorDeadItem.setOnAction(event -> {
                    Color color = pickerDead.getValue();
                    refillCellPane(color,false);
                });

                colorMenu.getItems().add(colorAliveItem);
                colorMenu.getItems().add(colorDeadItem);

                golController.getDrawItem().setOnAction(event -> drawingItem());
                golController.getPlaceItem().setOnAction(event -> setzenItem());
                golController.getRunItem().setOnAction(event -> laufenItem());
                golController.getBipolItem().setOnAction(event -> addBipol());
                golController.getTripolItem().setOnAction(event -> addTripol());
                golController.getGliderItem().setOnAction(event -> addGleiter());
                golController.getSeglerItem().setOnAction(event -> addSegler());
                golController.getCannonItem().setOnAction(event -> addCannon());
                golController.getClearItem().setOnAction(event -> world.clear());

                Slider speedSlider = new Slider(1d,1000,500);
                speedSlider.valueProperty().addListener(l -> {
                    world.setThreadTime((int) speedSlider.getValue());
                });
                golController.getSpeedMenu().getItems().add(new MenuItem(null,speedSlider));

                golController.getLoadItem().setOnAction(event -> loadWorld());
                golController.getSaveItem().setOnAction(event -> saveWorld());

                golController.getCopyItem().setOnAction(event -> copyWordItem());
                golController.getNewItem().setOnAction(event -> newWindowItem());

            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }

    /**
     * Erzeugt die Welt als UI Element
     * verknüpft Cell mit CellPane
     */
    private void generateWorld(){

        GridPane contentGridPane = golController.getGridPane();
        contentGridPane.getChildren().clear();
        cellPaneList.clear();

        Cell[][] cells = world.getCells();
        int i = 0,j = 0;
        for(Cell[] clls : cells){
            j=0;
            for(Cell cell : clls){
                CellPane cellPane = new CellPane(cell, this);
                contentGridPane.add(cellPane,j,i);
                cellPaneList.add(cellPane);
                j++;
            }
            i++;
        }
    }

    /**
     * Setzt die übergebende Farbe in die Eigenschaften
     * @param color
     * @param cellTyp
     */
    private void refillCellPane(Color color, boolean cellTyp){
        String colorString = String.format("#%02X%02X%02X", (int)(color.getRed()*255),
                (int)(color.getGreen()*255), (int)(color.getBlue()*255));

        if(cellTyp){
            colorAlive = colorString;
        }else {
            colorDead = colorString;
        }

        for(CellPane cellPane : cellPaneList){
            cellPane.refill();
        }
    }

    public void setWorldHeight(int worldHeight) {
        this.worldHeight = worldHeight;
    }

    public void setWorldWidth(int worldWidth) {
        this.worldWidth = worldWidth;
    }

    public String getColorDead() {
        return colorDead;
    }

    public String getColorAlive() {
        return colorAlive;
    }

    public boolean isEnableDrawing() {
        return enableDrawing;
    }

    public void laufenItem(){
        world.startThread();
        enableDrawing = false;
    }

    public void setzenItem(){
        world.stopThread();
        enableDrawing = false;
    }

    public void drawingItem(){
        world.stopThread();
        enableDrawing = true;
    }

    /**
     * Wird beim Schließen des MDIWindow aufgrufen
     */
    public void close(){
        world.stopThread();
    }

    private void addGleiter(){
        world.clear();
        world.getCell(5,5).setAlive(true);
        world.getCell(6,6).setAlive(true);
        world.getCell(6,7).setAlive(true);
        world.getCell(7,5).setAlive(true);
        world.getCell(7,6).setAlive(true);
    }

    private void addBipol(){
        world.clear();
        world.getCell(5,5).setAlive(true);
        world.getCell(6,5).setAlive(true);
        world.getCell(6,6).setAlive(true);
        world.getCell(5,6).setAlive(true);
        world.getCell(7,7).setAlive(true);
        world.getCell(8,7).setAlive(true);
        world.getCell(7,8).setAlive(true);
        world.getCell(8,8).setAlive(true);
    }

    private void addTripol(){
        world.clear();
        world.getCell(5,5).setAlive(true);
        world.getCell(6,5).setAlive(true);
        world.getCell(5,6).setAlive(true);
        world.getCell(7,6).setAlive(true);
        world.getCell(7,8).setAlive(true);
        world.getCell(9,8).setAlive(true);
        world.getCell(8,9).setAlive(true);
        world.getCell(9,9).setAlive(true);
    }

    private void addSegler(){
        world.clear();
        int x = 5;
        int y = 5;
        world.getCell(x,y+1).setAlive(true);
        world.getCell(x,y+2).setAlive(true);
        world.getCell(x,y+3).setAlive(true);
        world.getCell(x,y+4).setAlive(true);
        world.getCell(x,y+5).setAlive(true);
        world.getCell(x,y+6).setAlive(true);
        world.getCell(x+1,y).setAlive(true);
        world.getCell(x+1,y+6).setAlive(true);
        world.getCell(x+2,y+6).setAlive(true);
        world.getCell(x+3,y).setAlive(true);
        world.getCell(x+3,y+5).setAlive(true);
        world.getCell(x+4,y+2).setAlive(true);
        world.getCell(x+4,y+3).setAlive(true);
    }

    private void addCannon(){
        world.clear();
        world.getCell(2,24).setAlive(true);
        world.getCell(2,25).setAlive(true);
        world.getCell(3,24).setAlive(true);
        world.getCell(3,26).setAlive(true);
        world.getCell(4,12).setAlive(true);
        world.getCell(4,25).setAlive(true);
        world.getCell(4,26).setAlive(true);
        world.getCell(4,27).setAlive(true);
        world.getCell(5,9).setAlive(true);
        world.getCell(5,10).setAlive(true);
        world.getCell(5,11).setAlive(true);
        world.getCell(5,12).setAlive(true);
        world.getCell(5,16).setAlive(true);
        world.getCell(5,17).setAlive(true);
        world.getCell(5,26).setAlive(true);
        world.getCell(5,27).setAlive(true);
        world.getCell(5,28).setAlive(true);
        world.getCell(5,35).setAlive(true);
        world.getCell(5,36).setAlive(true);
        world.getCell(6,8).setAlive(true);
        world.getCell(6,9).setAlive(true);
        world.getCell(6,10).setAlive(true);
        world.getCell(6,11).setAlive(true);
        world.getCell(6,15).setAlive(true);
        world.getCell(6,16).setAlive(true);
        world.getCell(6,25).setAlive(true);
        world.getCell(6,26).setAlive(true);
        world.getCell(6,27).setAlive(true);
        world.getCell(6,35).setAlive(true);
        world.getCell(6,36).setAlive(true);
        world.getCell(7,1).setAlive(true);
        world.getCell(7,2).setAlive(true);
        world.getCell(7,8).setAlive(true);
        world.getCell(7,11).setAlive(true);
        world.getCell(7,15).setAlive(true);
        world.getCell(7,16).setAlive(true);
        world.getCell(7,18).setAlive(true);
        world.getCell(7,20).setAlive(true);
        world.getCell(7,21).setAlive(true);
        world.getCell(7,24).setAlive(true);
        world.getCell(7,26).setAlive(true);
        world.getCell(8,1).setAlive(true);
        world.getCell(8,2).setAlive(true);
        world.getCell(8,8).setAlive(true);
        world.getCell(8,9).setAlive(true);
        world.getCell(8,10).setAlive(true);
        world.getCell(8,11).setAlive(true);
        world.getCell(8,16).setAlive(true);
        world.getCell(8,18).setAlive(true);
        world.getCell(8,20).setAlive(true);
        world.getCell(8,21).setAlive(true);
        world.getCell(8,24).setAlive(true);
        world.getCell(8,25).setAlive(true);
        world.getCell(9,9).setAlive(true);
        world.getCell(9,10).setAlive(true);
        world.getCell(9,11).setAlive(true);
        world.getCell(9,12).setAlive(true);
        world.getCell(9,16).setAlive(true);
        world.getCell(9,18).setAlive(true);
        world.getCell(9,19).setAlive(true);
        world.getCell(10,12).setAlive(true);
    }

    /**
     * Öffnet einen File Save Dialog
     */
    private void saveWorld(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Welt als Datei speichern");
        fileChooser.setInitialFileName("GoL-Exported.txt");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Dateien (*.txt)", "*.txt"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Alle Dateien (*.*)", "*"));

        File file = fileChooser.showSaveDialog(null);
        if(file != null){
            saveFile(file.getPath());
        }
    }

    /**
     * Speichert die Weltdaten in der gewählten Datei
     * @param filename
     */
    private void saveFile(String filename){
        try {
            PrintWriter writer = new PrintWriter(filename, "UTF-8");
            writer.println("GameOfLife - WorldData");
            writer.println("--START--");
            writer.println("worlddimX:"+world.getDimX());
            writer.println("worlddimY:"+world.getDimY());
            writer.println("---------");
            Cell[][] cells = world.getCells();
            for(Cell[] c1: cells){
                String text = "";
                for(Cell c : c1){
                    text += c;
                }
                writer.println(text);
            }
            writer.println("--END--");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Öffen eine File Open Dialog
     */
    private void loadWorld(){

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Welt aus Datei laden");
        File file = fileChooser.showOpenDialog(null);
        if(file != null){
            readFile(file.getPath());
        }

    }

    /**
     * Liest die gewählte Datei ein und erzeugt/überschreibt die aktuelle Welt
     * @param filename
     */
    private void readFile(String filename){
        ArrayList<String> fileAsString = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
            String line = bufferedReader.readLine();
            while (line != null){

                fileAsString.add(line);
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int worlddimX = 0;
        int worlddimY = 0;
        ArrayList<String> worldData  = new ArrayList<>();
        for(String element : fileAsString){
            if(element.matches("(worlddimX:)([0-9]+)")){
                String[] parts = element.split(":");
                worlddimX = Integer.valueOf(parts[1]);
            }else if (element.matches("(worlddimY:)([0-9]+)")) {
                String[] parts = element.split(":");
                worlddimY = Integer.valueOf(parts[1]);
            }else if(element.matches("(worlddim:)([0-9]+)")){
                String[] parts = element.split(":");
                worlddimY = Integer.valueOf(parts[1]);
                worlddimX = Integer.valueOf(parts[1]);
            }else if(element.matches("[0,1]+")){
                worldData.add(element);
            }

        }
        final int worldDimX = worlddimX;
        final int worldDimY = worlddimY;
        Platform.runLater(() -> {
                if(world.getDimX() != worldDimX || world.getDimY() != worldDimY){
                    world.removeView();
                    world = new World(worldDimX, worldDimY);
                    world.addView();
                }
                world.clear();

                try {
                    for(int i = 0;i < worldDimX; i++){
                        String s = worldData.get(i);
                        char[] chars = s.toCharArray();
                        for(int j = 0; j < worldDimY; j++){
                            String sdata = String.valueOf(chars[j]);
                            if (sdata.contains("1")) {
                                world.getCell(i, j).setAlive(true);
                            }
                            if (sdata.contains("0")) {
                                world.getCell(i, j).setAlive(false);
                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            generateWorld();

        });


    }

    /**
     * Erzeugt eine Clone-Objekt der Welt und erzeugt neues Fenster
     */
    private void copyWordItem(){
        Main.addContent(GameTypT.GameOfLife,world.clone());
    }

    /**
     * Erzeugt ein neues Fenster mit der gelichen Welt
     */
    private void newWindowItem(){
        Main.addContent(GameTypT.GameOfLife,world);
    }

}
