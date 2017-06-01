package gol;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.util.Optional;

/**
 * 16.05.2017
 *
 * @author SWirries
 *         <p>
 *         This code is
 *         documentation enough
 */
public class WorldDimDialog {
    int heightValue;
    int widthValue;

    public WorldDimDialog(GameOfLife gameOfLife) {
        // Create the custom dialog.
        Dialog<Integer> dialog = new Dialog<>();
        dialog.setTitle("Weltgröße wählen");

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Erstellen", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField height = new TextField();
        height.setPromptText("Höhe");
        TextField width = new TextField();
        width.setPromptText("Breite");

        grid.add(new Label("Höhe:"), 0, 0);
        grid.add(height, 1, 0);
        grid.add(new Label("Breite:"), 0, 1);
        grid.add(width, 1, 1);

        // Enable/Disable login button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        // Do some validation (using the Java 8 lambda syntax).
        height.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(() -> height.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                heightValue = Integer.valueOf(height.getText());
                widthValue = Integer.valueOf(width.getText());
            }
            return  1;
        });

        Optional<Integer> result = dialog.showAndWait();

        result.ifPresent(usernamePassword -> {
            System.out.println("Höhe=" + heightValue + ", Breite=" + widthValue);
            gameOfLife.setWorldHeight(heightValue);
            gameOfLife.setWorldWidth(widthValue);
        });
    }

    public int getHeightValue() {
        return heightValue;
    }

    public int getWidthValue() {
        return widthValue;
    }
}
