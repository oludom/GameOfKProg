package MainUI;

import br.com.supremeforever.mdi.MDICanvas;
import br.com.supremeforever.mdi.MDIWindow;
import carcassonne.ui.Carcassonne;
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
 *  @author SWirries MHeiß
 *  Hauptklasse des Programms, hat Auswahl über die Spiele und beinhaltet deren Fenster
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
        primaryStage.setTitle("Game Box");
        Main.primaryStage = primaryStage;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MainUI/Main.fxml"));
        Node node = fxmlLoader.load();
        root.getChildren().add(node);


        Controller controller = (Controller) fxmlLoader.getController();
        AnchorPane MDIAnchor = controller.getMDIAnchor(); // MDI frame


        mdiCanvas = new MDICanvas(MDICanvas.Theme.DEFAULT);

        MDIAnchor.setBottomAnchor(mdiCanvas, 0d);
        MDIAnchor.setLeftAnchor(mdiCanvas, 0d);
        MDIAnchor.setTopAnchor(mdiCanvas, 0d);//Button place
        MDIAnchor.setRightAnchor(mdiCanvas, 0d);

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
                        content = new BasicUIX();
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
                    }
                    titel = "Game Of Life";
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case Regenbogen:
                try {
                    content = new Regenbogen();
                    titel = "Regenbogen";
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case SiebenSpaltenPrimzahlen:
                try {
                    content = new SiebenSpaltenPrimezahl();
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
            case Carcassonne:
                try {
                    Carcassonne c = new Carcassonne();
                    c.start(new Stage());
                    titel = "Carcassonne";
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
            if(gameTypT.equals(GameTypT.Connect6)) mdiWindow.setGameObject(content);
            if(gameTypT.equals(GameTypT.Regenbogen)) mdiWindow.setGameObject(content);
        }

    }


}
