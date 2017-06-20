package carcassonne.ui;

import carcassonne.game.FarbeT;
import carcassonne.game.Spieler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * 09.06.2017
 *
 * @author SWirries
 */
public class PlayerInputController extends Stage implements Initializable{
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField textFieldPlayer1;

    @FXML
    private TextField textFieldPlayer2;

    @FXML
    private TextField textFieldPlayer3;

    @FXML
    private TextField textFieldPlayer4;

    @FXML
    private TextField textFieldPlayer5;

    @FXML
    private Button buttonStart;

    private Carcassonne carcassonne;

    public PlayerInputController(Parent parent, Carcassonne carcassonne) {
        setTitle("Spieler festlegen");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/carcassonne/ui/PlayerInput.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(parent);
        this.carcassonne = carcassonne;
        try {
            setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //    @FXML
//    void initialize() {
//        assert textFieldPlayer1 != null : "fx:id=\"textFieldPlayer1\" was not injected: check your FXML file 'PlayerInput.fxml'.";
//        assert textFieldPlayer2 != null : "fx:id=\"textFieldPlayer2\" was not injected: check your FXML file 'PlayerInput.fxml'.";
//        assert textFieldPlayer3 != null : "fx:id=\"textFieldPlayer3\" was not injected: check your FXML file 'PlayerInput.fxml'.";
//        assert textFieldPlayer4 != null : "fx:id=\"textFieldPlayer4\" was not injected: check your FXML file 'PlayerInput.fxml'.";
//        assert textFieldPlayer5 != null : "fx:id=\"textFieldPlayer5\" was not injected: check your FXML file 'PlayerInput.fxml'.";
//        assert buttonStart != null : "fx:id=\"buttonStart\" was not injected: check your FXML file 'PlayerInput.fxml'.";
//
//    }

    private Spieler[] getAllPlayer(){
        ArrayList<Spieler> spieler = new ArrayList<>();
        if(!textFieldPlayer1.getText().equals("")) spieler.add(new Spieler(textFieldPlayer1.getText(), FarbeT.BLAU));
        if(!textFieldPlayer2.getText().equals("")) spieler.add(new Spieler(textFieldPlayer2.getText(), FarbeT.GELB));
        if(!textFieldPlayer3.getText().equals("")) spieler.add(new Spieler(textFieldPlayer3.getText(), FarbeT.GRUEN));
        if(!textFieldPlayer4.getText().equals("")) spieler.add(new Spieler(textFieldPlayer4.getText(), FarbeT.ROT));
        if(!textFieldPlayer5.getText().equals("")) spieler.add(new Spieler(textFieldPlayer5.getText(), FarbeT.SCHWARZ));

        Spieler[] spielers = new Spieler[spieler.size()];
        for(int i=0; i< spielers.length;i++){
            spielers[i] = spieler.get(i);
        }
        return spielers;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textFieldPlayer1.setText("");
        textFieldPlayer2.setText("");
        textFieldPlayer3.setText("");
        textFieldPlayer4.setText("");
        textFieldPlayer5.setText("");
        buttonStart.setOnAction(event -> {
            carcassonne.setSpielers(getAllPlayer());
            this.close();
        });
    }
}
