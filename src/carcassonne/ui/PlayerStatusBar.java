package carcassonne.ui;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import carcassonne.game.Spieler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * 12.06.2017
 *
 * @author SWirries
 */
public class PlayerStatusBar implements Initializable {

    @FXML
    private Label playerName;

    @FXML
    private Label playerPoints;

    @FXML
    private ImageView pictureGefolgsmann;

    @FXML
    private Label playerFree;

    private Node node;

    @FXML
    private Label playerNameA;

    @FXML
    private Label playerNameB;

    @FXML
    private Label playerNameC;

    @FXML
    private Label playerNameD;

    public PlayerStatusBar(Parent parent) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/carcassonne/ui/PlayerStatusBar.fxml"));
        fxmlLoader.setController(this);
//        fxmlLoader.setRoot(parent);
        try {
           node = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        playerName.setText("");
//        playerPoints.setText("");
//        playerFree.setText("");
    }

    public void setPlayer(Spieler spieler){
        playerName.setText(spieler.getName());
        playerPoints.setText(""+spieler.getPunkte());
        playerFree.setText("x"+spieler.countFreieGefolgsleute());
        pictureGefolgsmann.setImage(spieler.getImage());
    }

    public void setPlayer(Spieler[] spielers, int spielerindex){
        playerName.setText(spielers[spielerindex].getName());
        playerPoints.setText(""+spielers[spielerindex].getPunkte());
        playerFree.setText("x"+spielers[spielerindex].countFreieGefolgsleute());
        pictureGefolgsmann.setImage(spielers[spielerindex].getImage());

        if(spielers.length >= 2) {
            spielerindex = ++spielerindex%spielers.length;
            playerNameA.setText(spielers[spielerindex].getName() + " Punkte: "+
                            spielers[spielerindex].getPunkte());
        }
        if(spielers.length >= 3) {
            spielerindex = ++spielerindex%spielers.length;
            playerNameB.setText(spielers[spielerindex].getName() + " Punkte: "+
                            spielers[spielerindex].getPunkte());
        } else playerNameB.setText("");
        if(spielers.length >= 4) {
            spielerindex = ++spielerindex%spielers.length;
            playerNameC.setText(spielers[spielerindex].getName() + " Punkte: "+
                            spielers[spielerindex].getPunkte());
        } else playerNameC.setText("");
        if(spielers.length >= 5){
            spielerindex = ++spielerindex%spielers.length;
            playerNameD.setText(spielers[spielerindex].getName() + " Punkte: "+
                    spielers[spielerindex].getPunkte());
        }

        else playerNameD.setText("");
    }

    public Node getNode() {
        return node;
    }
}
