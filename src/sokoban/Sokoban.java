package sokoban;

import MainUI.Main;
import br.com.supremeforever.mdi.MDIWindow;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
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

/**
 * @author Micha Heiß
 */
public class Sokoban extends AnchorPane{

    private Canvas canvas;
    private GraphicsContext c;
    private MDIWindow mdiWindow;

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


    }

    public void setMdiWindow(MDIWindow mdiWindow) {
        this.mdiWindow = mdiWindow;

    }

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
        double minsize = width>height ? width : height;
        c.drawImage(new Stone().toImage(), 0,0,minsize,minsize);

        // render currentLevel

        for(int i = 0; i<lo.length;i++){
            for(int j = 0; j<lo[0].length; j++){
                Image tmp;
                if(lo[i][j] != null){

                    if(!lo[i][j].getClass().equals(Stone.class)) {
                        if (currentLevel.isEndposition(new Vector(j, i))) {
                            tmp = Space.toImage(true);
                        } else {
                            tmp = Space.toImage(false);
                        }
                        c.drawImage(tmp, levelXPos + j * boxSize, levelYPos + i * boxSize, boxSize, boxSize); // draw sand as background
                    }

                    if(lo[i][j].getClass().equals(Player.class)) lo[i][j].setPosition(new Vector(j,i)); // set Player position for key movement

                    // if not Space, draw object
                    if(!lo[i][j].getClass().equals(Space.class)){
                        tmp =  lo[i][j].toImage(); // new Image("sokoban/images/wood.png");//
                        if(!lo[i][j].getClass().equals(Stone.class)) c.drawImage(tmp,levelXPos+j*boxSize, levelYPos+i*boxSize, boxSize, boxSize );
                    }
                }
            }
        }
    }

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
        int levelcount = 0;
        int levelindex = -1;
        String leveltitle = "";

        for(String element : fileAsString){
            if(element.matches("(Level )([0-9]+)")){ // level nummer
                if(levelindex != -1){
                    levels.add(new Level(levellines, this));
//                    currentLevel = new Level(levellines);

                }
                levellines = new ArrayList<>();
                String[] parts = element.split(" ");
                levelindex = Integer.valueOf(parts[1]);
                levelcount++;
            }else if (element.matches("(')(.)+(')")) { // titel umringt von '
                String[] parts = element.split("'");
                leveltitle = parts[1];
            }else if(element.matches("( )*(#)(.)+(#)")){
                levellines.add(element);
            }
        }
        levels.add(new Level(levellines, this));
//        currentLevel = new Level(levellines);

    }


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
                case SHIFT:
                    break;
            }
            render();
        });
    }

    public void startNextLevel(){

        int current = levels.indexOf(currentLevel);

        if(levels.size()>current+1){
            currentLevel = levels.get(current+1);
            render();
        } // TODO winning message after last level solved

    }

}
