package MainUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *  @author SWirries MHeiß
 *  Controller für die Main.fxml
 *  Haupftfenster von dem Programm
 */

public class Controller implements Initializable {

    @FXML
    private AnchorPane MDIAnchor;

    @FXML
    private MenuItem help;
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
    @FXML
    private MenuItem snake;
    @FXML
    private MenuItem carcassonne;

    // TODO DELETE as example
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        help.setOnAction(event -> System.exit(0));
        /**
         * Event für das Clicken auf eine Menueintrag
         */
        mvcexample.setOnAction(event ->  Main.addContent(GameTypT.MVCexample,null));
        drehsafe.setOnAction(event ->  Main.addContent(GameTypT.DrehSafe,null));
        sokoban.setOnAction(event ->  Main.addContent(GameTypT.Sokoban,null));
        siebenspaltenprimzahlen.setOnAction(event ->  Main.addContent(GameTypT.SiebenSpaltenPrimzahlen,null));
        regenbogen.setOnAction(event ->  Main.addContent(GameTypT.Regenbogen,null));
        connect6.setOnAction(event ->  Main.addContent(GameTypT.Connect6,null));
        gameoflife.setOnAction(event ->  Main.addContent(GameTypT.GameOfLife,null));
        snake.setOnAction(event -> Main.addContent(GameTypT.Snake,null));
        carcassonne.setOnAction(event -> Main.addContent(GameTypT.Carcassonne, null));
    }

    public AnchorPane getMDIAnchor() {
        return MDIAnchor;
    }
}
