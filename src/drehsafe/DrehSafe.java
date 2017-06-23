package drehsafe;

import br.com.supremeforever.mdi.MDIWindow;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.util.Duration;


/**
 * 30.03.2017
 *
 * @author SWirries MHeiß
 *
 */
public class DrehSafe extends AnchorPane {

    Button[] btnArray = {new Button(),new Button(), new Button(), new Button(), new Button(), new Button(),
            new Button(), new Button(), new Button(), new Button()};//Array der Buttons
    int[] password = {3,0,0,3,2,0,1,7};//gewuenschtes Password
    int state = -1;//Nummer des Passworts, wird für die ueberpruefung verwendet
    int direction = 1;//Drehrichtung
    AnchorPane anchorPane = this;

    public DrehSafe() {
        GridPane gridPane = new GridPane();//GridLayout
        /**
         * Events für das Resizing des Fensters in Höhe und Breite
         */
        anchorPane.widthProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                changeSize();
            }
        });
        anchorPane.heightProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                changeSize();
            }
        });


       int i = 0;//Laufvariable für Button Text

       for(Button btn : btnArray){

           btn.setText(String.valueOf(i));
           /**
            * Click Event der Buttons
            */
           btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                openSafe(btn.getText());//Methode zum Öffnen des Safes
                }
            });
            i++;
       }

        /**
         * Buttons zum Layout hinzufügen
         */
        gridPane.add(btnArray[0], 2,1);
        gridPane.add(btnArray[1], 1,1);
        gridPane.add(btnArray[2], 1,2);
        gridPane.add(btnArray[3], 1,3);
        gridPane.add(btnArray[4], 1,4);
        gridPane.add(btnArray[5], 2,4);
        gridPane.add(btnArray[6], 3,4);
        gridPane.add(btnArray[7], 3,3);
        gridPane.add(btnArray[8], 3,2);
        gridPane.add(btnArray[9], 3,1);

        gridPane.setHgap(5);//Hozizontale Lücke in der Grid
        gridPane.setVgap(5);//Vertikale Lücke in der Grid
        changeSize();//Anpassen der Buttongröße
        rotate();//Rotation der Nummern
        this.getChildren().add(gridPane);
    }

    /***
     *
     * @param s
     *
     * Öffnen des Safes
     */
    public void openSafe(String s){
        int temp = Integer.parseInt(s);

        if(temp == password[state+1]){
            state++;
            for(Button btn: btnArray) btn.setStyle("-fx-background-color: green");//Setzen der Buttonfarbe
        }else{
            state = -1;
            for(Button btn: btnArray) btn.setStyle("-fx-background-color: red");
            direction *= -1;//Drehrichtung ändern
        }
        if(state == 7){
            state = -1;

            close();
        }

    }

    private void close(){
        MDIWindow window = (MDIWindow) this.getParent().getParent();
        window.closeMdiWindow();
    }

    /***
     * Anpassen der Buttongröße anhand der Fenstergröße
     */
    public void changeSize(){
        double width = anchorPane.getWidth() /3 - 5 - 5/3;// abzüglich der Seitenränder
        double height = anchorPane.getHeight() /4 - 5 - 5/4;
        for(Button btn : btnArray){
            btn.setMinSize(width,height);
        }
    }

    /**
     * Methode zur Rotation der Zahlen
     */
    public void rotate(){
        //Thead der FX-Klassen um auf UI zuzugreifen
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            /**
             *
             * @param event
             * Thread Event
             */
            @Override
            public void handle(ActionEvent event) {
                for(Button btn : btnArray){

                    int actNumber = Integer.parseInt(btn.getText());
                    int newNumber = actNumber + direction;//drehrichtung festlegen
                    newNumber = newNumber == -1 ? 9 : newNumber;//Wenn zahl = -1 dann auf 9 setzen
                    newNumber = newNumber == 10 ? 0 : newNumber;// Wenn zahl = 10 dann auf 0

                    btn.setText(String.valueOf(newNumber));//Button Text setzen
                }
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();


    }
}
