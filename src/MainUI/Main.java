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
        AnchorPane mainPane = new AnchorPane();
        mainPane.setPrefSize(800, 600);
        //Creat MDI Canvas Container
        MDICanvas mdiCanvas = new MDICanvas(MDICanvas.Theme.DEFAULT);
        //Fit it to the main Pane
        AnchorPane.setBottomAnchor(mdiCanvas, 0d);
        AnchorPane.setLeftAnchor(mdiCanvas, 0d);
        AnchorPane.setTopAnchor(mdiCanvas, 25d);//Button place
        AnchorPane.setRightAnchor(mdiCanvas, 0d);
        //Put the container Into the main pane
        mainPane.getChildren().add(mdiCanvas);
        //Create a 'New MDI Window' Button
        Button btnDefaultMdi = new Button("New Window");
        //set the button action

        btnDefaultMdi.setOnAction(event -> {
            Node content = null;
            try {
                content = FXMLLoader.load(getClass().getResource("/MainUI/Main.fxml"));
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
        mainPane.getChildren().add(btnDefaultMdi);

        primaryStage.setScene(new Scene(mainPane));
        primaryStage.show();



    }


}
