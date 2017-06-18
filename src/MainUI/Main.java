package MainUI;

import br.com.supremeforever.mdi.MDICanvas;
import br.com.supremeforever.mdi.MDIWindow;
import connect6.ui.BasicUIX;
import connect6.ui.GameModusT;
import drehsafe.DrehSafe;
import gol.GameOfLife;
import gol.World;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mvcexample.MVCpolynom;
import regenbogen.Regenbogen;
import siebenspaltenprime.SiebenSpaltenPrimezahl;
import snake.Snake;
import sokoban.Sokoban;

/**
 * Created by brisatc171.minto on 12/11/2015.
 */
public class Main extends Application {
    static int count = 0;
    public static HostServices hostServices;
    static MDICanvas mdiCanvas;
    private Scene scene;
    private static Stage primaryStage;


    @Override
    public void start(Stage primaryStage) throws Exception {
        hostServices = getHostServices();
        //Creat main Pane Layout
        AnchorPane root = new AnchorPane();
        root.setPrefSize(800, 600);

        Main.primaryStage = primaryStage;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MainUI/Main.fxml"));
        Node node = fxmlLoader.load();
        root.getChildren().add(node);

        // controller class containing UI elements
        Controller controller = (Controller) fxmlLoader.getController();
        AnchorPane MDIAnchor = controller.getMDIAnchor(); // MDI frame

        //Creat MDI Canvas Container
        mdiCanvas = new MDICanvas(MDICanvas.Theme.DEFAULT);
        //Fit it to the mdi Pane
        MDIAnchor.setBottomAnchor(mdiCanvas, 0d);
        MDIAnchor.setLeftAnchor(mdiCanvas, 0d);
        MDIAnchor.setTopAnchor(mdiCanvas, 0d);//Button place
        MDIAnchor.setRightAnchor(mdiCanvas, 0d);
        //Put the container Into the mdi pane
        MDIAnchor.getChildren().add(mdiCanvas);

        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }
    public Scene getScene(){
        return scene;
    }

    public static void addContent(GameTypT gameTypT, Object gameValue){
        Node content = null;
        MDIWindow mdiWindow;
        String titel = "Titel";
        switch (gameTypT){
            case MVCexample:
                try {
                    content = new MVCpolynom();
                    titel = "MVCexample";
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case Sokoban:
                try {
                    content = new Sokoban(primaryStage);
                    titel = "Sokoban";
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case Connect6:
                try {
                    if(gameValue != null){
                        if(gameValue.getClass().equals(GameModusT.class))
                            content = new BasicUIX((GameModusT) gameValue);
                    }else{
                        content = new BasicUIX();//FXMLLoader.load(Main.class.getResource("/MainUI/proginwork.fxml"));
                    }
                    titel = "Connect 6";
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case DrehSafe:
                try {
                    content = new DrehSafe();
                    titel = "DrehSafe";
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case GameOfLife:
                try {
                    if(gameValue != null){
                        if(gameValue.getClass().equals(World.class))
                            content = new GameOfLife((World) gameValue);
                    }else{
                        content = new GameOfLife();
                        System.out.println("New GOL");
                    }
                    titel = "Game Of Life";
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case Regenbogen:
                try {
                    content = new Regenbogen();//FXMLLoader.load(Main.class.getResource("/MainUI/proginwork.fxml"));
                    titel = "Regenbogen";
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case SiebenSpaltenPrimzahlen:
                try {
                    content = new SiebenSpaltenPrimezahl(); //FXMLLoader.load(Main.class.getResource("/MainUI/proginwork.fxml"));
                    titel = "Sieben Spalten Primzahl";
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case Snake:
                try {
                    content = new Snake(primaryStage);
                    titel = "Snake";
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
        if(content != null){
            count++;
            //Create a Default MDI Withou Icon
            mdiWindow = new MDIWindow("UniqueID" + count,
                    new ImageView("/assets/WindowIcon.png"),
                    titel + " " + count,
                    content,gameTypT);
            //Set MDI Size
            //Add it to the container
            mdiCanvas.addMDIWindow(mdiWindow);
            if(gameTypT.equals(GameTypT.Sokoban)) ((Sokoban) content).setMdiWindow(mdiWindow);
            if(gameTypT.equals(GameTypT.Snake)) ((Snake) content).setMdiWindow(mdiWindow);
        }

    }


}
