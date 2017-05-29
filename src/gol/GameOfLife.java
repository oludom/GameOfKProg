package gol;

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

import java.io.IOException;
import java.util.ArrayList;

/**
 * 16.05.2017
 *
 * @author SWirries
 */
public class GameOfLife extends Pane {

    Node content;
    static World world;
    GoLController golController;
    int worldHeight = 0;
    int worldWidth = 0;
    private ArrayList<CellPane> cellPaneList = new ArrayList<>();
    private boolean enableDrawing = false;
    private String colorDead = "000000";
    private String colorAlive = "FFFFFF";

    public GameOfLife(){
        this.setMinSize(500,500);
//        new WorldDimDialog(this);
        worldHeight = 10;
        worldWidth = 10;
        init();
    }

    private void init(){

        Color c = Color.WHITE;
        colorAlive = String.format("#%02X%02X%02X", (int)(c.getRed()*255),
                (int)(c.getGreen()*255), (int)(c.getBlue()*255));
        c = Color.BLACK;
        colorDead = String.format("#%02X%02X%02X", (int)(c.getRed()*255),
                (int)(c.getGreen()*255), (int)(c.getBlue()*255));

        Platform.runLater(() -> {
            MDIWindow mdiWindow;
            do{
                mdiWindow = (MDIWindow) this.getParent().getParent();

            }while (mdiWindow == null);

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gol/gameoflife.fxml"));
                content = fxmlLoader.load();
                mdiWindow.setContent(content,this);
                golController = fxmlLoader.getController();
                world = new World(worldWidth, worldHeight);
                world.addView();
                GridPane contentGridPane = golController.getGridPane();

                Cell[][] cells = world.getCells();
                int i = 0,j = 0;
                System.out.println(cells.length);
                for(Cell[] clls : cells){
                    j=0;
                    for(Cell cell : clls){
                        CellPane cellPane = new CellPane(cell, this);
                        contentGridPane.add(cellPane,i,j);
                        cellPaneList.add(cellPane);
                        j++;
                    }
                    i++;
                }

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


            } catch (Exception e) {
                e.printStackTrace();
            }



        });
    }

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

    public void setColorDead(String colorDead) {
        this.colorDead = colorDead;
    }

    public String getColorAlive() {
        return colorAlive;
    }

    public void setColorAlive(String colorAlive) {
        this.colorAlive = colorAlive;
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

    public void close(){
        world.stopThread();
    }

    //    public static class ContentGridPane extends GridPane {
////        private final GridPane gridPane;
//
//        public ContentGridPane(double width, double height){
//
////            this.setPrefSize(width, height);
////            this.setStyle("-fx-background-color: green");
//
//
//        }
//
////        public GridPane getGridPane(){return gridPane;}
//
//        @Override
//        protected void layoutChildren() {
//            final double x = snappedLeftInset();
//            final double y = snappedTopInset();
//            final double w = snapSize(getWidth()) - x - snappedRightInset();
//            final double h = snapSize(getHeight()) - y - snappedBottomInset();
//
//            this.setLayoutX(x);
//            this.setLayoutY(y);
//            this.setPrefWidth(w);
//            this.setPrefHeight(h);
//
////            System.out.println("Grid W:"+w);
////            System.out.println("Grid H"+h);
//        }
//    }
}
