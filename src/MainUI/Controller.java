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

    // TODO DELETE as example
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        help.setOnAction(event -> System.exit(0));
    }

    public AnchorPane getMDIAnchor() {
        return MDIAnchor;
    }
}
