package MainUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private AnchorPane MDIAnchor;

    @FXML
    private MenuItem help;
    @FXML
    private Button testbutton;
    @FXML
    private MenuBar menuBar;
    @FXML
    private MenuItem mvcexample;
    @FXML
    private MenuItem drehsafe;
    @FXML
    private MenuItem sokoban;
    @FXML
    private MenuItem siebenspaltenprimzahlen;
    @FXML
    private MenuItem regenbogen;
    @FXML
    private MenuItem connect6;
    @FXML
    private MenuItem gameoflife;

    // TODO DELETE as example
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        help.setOnAction(event -> System.exit(0));
        mvcexample.setOnAction(event ->  Main.addContent(GameTypT.MVCexample));
        drehsafe.setOnAction(event ->  Main.addContent(GameTypT.DrehSafe));
        sokoban.setOnAction(event ->  Main.addContent(GameTypT.Sokoban));
        siebenspaltenprimzahlen.setOnAction(event ->  Main.addContent(GameTypT.SiebenSpaltenPrimzahlen));
        regenbogen.setOnAction(event ->  Main.addContent(GameTypT.Regenbogen));
        connect6.setOnAction(event ->  Main.addContent(GameTypT.Connect6));
        gameoflife.setOnAction(event ->  Main.addContent(GameTypT.GameOfLife));


    }

    public AnchorPane getMDIAnchor() {
        return MDIAnchor;
    }
}
