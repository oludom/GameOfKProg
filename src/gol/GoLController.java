package gol;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * 16.05.2017
 * Controller für die Steuertung der UI Elemente aus der gameoflife.fxml
 * @author SWirries MHeiß
 *
 */
public class GoLController implements Initializable {

        @FXML
        private Menu colorMenu;

        @FXML
        private Menu modusMenu;

        @FXML
        private RadioMenuItem runItem;

        @FXML
        private RadioMenuItem drawItem;

        @FXML
        private RadioMenuItem placeItem;

        @FXML
        private Menu speedMenu;

        @FXML
        private Menu saveloadMenu;

        @FXML
        private MenuItem loadItem;

        @FXML
        private MenuItem saveItem;

        @FXML
        private Menu windowMenu;

        @FXML
        private MenuItem newItem;

        @FXML
        private MenuItem copyItem;

        @FXML
        private Menu figureItem;

        @FXML
        private MenuItem gliderItem;

        @FXML
        private MenuItem bipolItem;

        @FXML
        private MenuItem tripolItem;

        @FXML
        private MenuItem seglerItem;

        @FXML
        private MenuItem cannonItem;

        @FXML
        private MenuItem clearItem;

        @FXML
        private AnchorPane anchorPane;

        @FXML
        private GridPane gridPane;



        @Override
        public void initialize(URL location, ResourceBundle resources) {

        }


        /**
         * Getter der UI Elemente
         * @return
         */

        public AnchorPane getAnchorPane() {
                return anchorPane;
        }

        public GridPane getGridPane() {
                return gridPane;
        }

        public Menu getColorMenu() {
            return colorMenu;
        }

        public RadioMenuItem getRunItem() {
            return runItem;
        }

        public RadioMenuItem getDrawItem() {
            return drawItem;
        }

        public RadioMenuItem getPlaceItem() {
            return placeItem;
        }

        public MenuItem getLoadItem() {
            return loadItem;
        }

        public MenuItem getSaveItem() {
            return saveItem;
        }

        public MenuItem getNewItem() {
            return newItem;
        }

        public MenuItem getCopyItem() {
            return copyItem;
        }

        public MenuItem getGliderItem() {
            return gliderItem;
        }

        public MenuItem getBipolItem() {
            return bipolItem;
        }

        public MenuItem getTripolItem() {
            return tripolItem;
        }

        public MenuItem getSeglerItem() {
            return seglerItem;
        }

        public MenuItem getCannonItem() {
            return cannonItem;
        }

        public MenuItem getClearItem() {
            return clearItem;
        }

        public Menu getSpeedMenu() {
            return speedMenu;
        }
}
