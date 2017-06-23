package sokoban;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * @author MHeiß SWirries
 *
 */
public class SelectStateDialog extends Application {

    ObservableList<String> titles = FXCollections.observableArrayList();
    Sokoban parent;
    public static final String NOSTATESELECTED = "Keine Spielstand gefunden!";

    public SelectStateDialog(ArrayList<String> titles, Sokoban parent){

        this.titles.addAll(titles);
        this.parent = parent;

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Spielstand auswählen");
        StackPane root  = new StackPane();
        Scene scene = new Scene(root, 600, 700);

        ListView listView = new ListView();
        listView.setItems(titles);

        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(listView);
        borderPane.setMargin(listView, new Insets(10,10,10,10));
        Button cancel = new Button("schließen");
        borderPane.setBottom(cancel);

        cancel.setOnAction(event -> primaryStage.close());

        root.getChildren().add(borderPane);

        primaryStage.setScene(scene);
        primaryStage.show();

        listView.setOnMouseClicked(e -> {
            String clickedName = (String) listView.getSelectionModel().getSelectedItem();

            if(clickedName != null)
                if(!clickedName.equals(NOSTATESELECTED)){

                    parent.readSerialLevel(clickedName + ".ssf");
                    primaryStage.close();
                }

        });

//        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//            @Override
//            public void handle(WindowEvent event) {
//                parentRmiClient.stopLog();
//            }
//        });
    }


    public void addLogItem(String item){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(titles.get(0).equals(NOSTATESELECTED)){
                    titles.remove(0);
                }
                titles.add(item);
            }
        });
    }
}

