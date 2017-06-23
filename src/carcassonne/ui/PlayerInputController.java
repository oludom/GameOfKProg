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

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * 09.06.2017
 *
 * @author SWirries MHeiß
 *
 * Erstellt eine Dialog mit dem die Spieler festgelegt werden
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

    @FXML
    private Button btnOpenAnleitung;

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

    /**
     * Liest die Spielernamen aus den Textfeldern und gibt die Spieler an Carcassonne weiter
     * @return
     */
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
        btnOpenAnleitung.setOnAction(event -> carcassonne.getHostServices()
                .showDocument("http://www.brettspiele-report.de/images/carcassonne/Spielanleitung_Carcassonne.pdf")
        );
    }
}
