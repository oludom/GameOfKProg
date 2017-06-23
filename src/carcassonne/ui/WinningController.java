package carcassonne.ui;


import carcassonne.game.Spieler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * 09.06.2017
 *
 * @author SWirries MHeiß
 *
 * Gibt die Endpunkte im aus die Spiel an
 */
public class WinningController extends Stage implements Initializable {


    @FXML
    private Label platz1;

    @FXML
    private Label punkte1;

    @FXML
    private Label platz2;

    @FXML
    private Label platz3;

    @FXML
    private Label platz4;

    @FXML
    private Label platz5;

    private Carcassonne carcassonne;

    private Spieler[] spielers;

    public WinningController(Parent parent, Carcassonne carcassonne, Spieler[] spielers) {
        this.spielers = spielers;
        setTitle("Spiel beendet");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/carcassonne/ui/WinningMessage.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(parent);
        this.carcassonne = carcassonne;
        try {
            setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        punkte1.setText("");
        platz1.setText("");
        platz2.setText("");
        platz3.setText("");
        platz4.setText("");
        platz5.setText("");

        HashMap<Spieler, Integer> sorter = new HashMap<>();
        for(Spieler s: spielers){
            sorter.put(s, new Integer(s.getPunkte()));
        }

        /**
         * Sortiert die Spieler entsprechent ihrer Punkte
         */
        Spieler max = Collections.max(sorter.entrySet(), (entry1, entry2) -> entry1.getValue() - entry2.getValue()).getKey();
        sorter.remove(max);
        punkte1.setText("Punkte: "+max.getPunkte());
        platz1.setText(max.getName() + " hat gewonnen!");
        platz1.setStyle("-fx-text-fill: \"" + max.getFarbe().getColor() + "\";-fx-stroke: black; -fx-stroke-width: 2px;");

        try {
            max = sorter.size() == 1 ? sorter.entrySet().iterator().next().getKey() : Collections.max(sorter.entrySet(), (entry1, entry2) -> entry1.getValue() - entry2.getValue()).getKey();
            sorter.remove(max);
            platz2.setText("Platz 2: " + max.getName() + " - Punkte: " + max.getPunkte());
            platz2.setStyle("-fx-text-fill: \"" + max.getFarbe().getColor() + "\";-fx-stroke: black; -fx-stroke-width: 2px;");
            if(sorter.size()<1) return;
            max = sorter.size() == 1 ? sorter.entrySet().iterator().next().getKey() : Collections.max(sorter.entrySet(), (entry1, entry2) -> entry1.getValue() - entry2.getValue()).getKey();
            sorter.remove(max);
            platz3.setText("Platz 3: " + max.getName() + " - Punkte: " + max.getPunkte());
            platz3.setStyle("-fx-text-fill: \"" + max.getFarbe().getColor() + "\";-fx-stroke: black; -fx-stroke-width: 2px;");
            if(sorter.size()<1) return;
            max = sorter.size() == 1 ? sorter.entrySet().iterator().next().getKey() : Collections.max(sorter.entrySet(), (entry1, entry2) -> entry1.getValue() - entry2.getValue()).getKey();
            sorter.remove(max);
            platz4.setText("Platz 4: " + max.getName() + " - Punkte: " + max.getPunkte());
            platz4.setStyle("-fx-text-fill: \"" + max.getFarbe().getColor() + "\";-fx-stroke: black; -fx-stroke-width: 2px;");
            if(sorter.size()<1) return;
            max = sorter.size() == 1 ? sorter.entrySet().iterator().next().getKey() : Collections.max(sorter.entrySet(), (entry1, entry2) -> entry1.getValue() - entry2.getValue()).getKey();
            sorter.remove(max);
            platz5.setText("Platz 5: " + max.getName() + " - Punkte: " + max.getPunkte());
            platz5.setStyle("-fx-text-fill: \"" + max.getFarbe().getColor() + "\";-fx-stroke: black; -fx-stroke-width: 2px;");
        } catch (Exception e) {}

    }
}
