package MainUI;

import br.com.supremeforever.mdi.MDICanvas;
import br.com.supremeforever.mdi.MDIWindow;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Created by brisatc171.minto on 12/11/2015.
 */
public class Main extends Application {
    int count = 0;
    public static HostServices hostServices;

    @Override
    public void start(Stage primaryStage) throws Exception {
        hostServices = getHostServices();
        //Creat main Pane Layout
        AnchorPane root = new AnchorPane();
        root.setPrefSize(800, 600);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MainUI/Main.fxml"));
        Node node = fxmlLoader.load();
        root.getChildren().add(node);

        // controller class containing UI elements
        Controller controller = (Controller) fxmlLoader.getController();
        AnchorPane MDIAnchor = controller.getMDIAnchor(); // MDI frame

        //Creat MDI Canvas Container
        MDICanvas mdiCanvas = new MDICanvas(MDICanvas.Theme.DEFAULT);
        //Fit it to the mdi Pane
        MDIAnchor.setBottomAnchor(mdiCanvas, 0d);
        MDIAnchor.setLeftAnchor(mdiCanvas, 0d);
        MDIAnchor.setTopAnchor(mdiCanvas, 25d);//Button place
        MDIAnchor.setRightAnchor(mdiCanvas, 0d);
        //Put the container Into the mdi pane
        MDIAnchor.getChildren().add(mdiCanvas);


        // TODO DELETE AS EXAMPLE
        //Create a 'New MDI Window' Button
        Button btnDefaultMdi = new Button("New Window");
        //set the button action

        btnDefaultMdi.setOnAction(event -> {
            Node content = null;
            try {
                content = FXMLLoader.load(getClass().getResource("/gol/gameoflife.fxml"));
            } catch (Exception e) {
            }
            count++;
            //Create a Default MDI Withou Icon
            MDIWindow mdiWindow = new MDIWindow("UniqueID" + count,
                    new ImageView("/assets/WindowIcon.png"),
                    "Title " + count,
                    content);
            //Set MDI Size
            //Add it to the container
            mdiCanvas.addMDIWindow(mdiWindow);
        });
        //Put it into the main pane
        MDIAnchor.getChildren().add(btnDefaultMdi);

        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }


}
