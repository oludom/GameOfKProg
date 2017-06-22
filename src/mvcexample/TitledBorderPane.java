package mvcexample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 * 20.04.2017
 *
 * @author SWirries
 *         <p>
 *         This code is
 *         documentation enough
 *
 * Customclass für ein UI Element mit Rahmen und Beschriftung
 */
public class TitledBorderPane extends StackPane{

    public TitledBorderPane(String titleString, Node content) {
        Label title = new Label(" " + titleString + " ");
        title.getStyleClass().add("bordered-titled-title");
        StackPane.setMargin(title, new Insets(5, 5, 10, 5));
        StackPane.setAlignment(title, Pos.TOP_LEFT);


        StackPane contentPane = new StackPane();
        contentPane.getChildren().add(content);
//        contentPane.getStyleClass().add("bordered-titled-content");

        contentPane.setMargin(content, new Insets(30, 10, 10, 10));

//        this.getStyleClass().add("bordered-titled-border");
        getChildren().addAll(contentPane, title);
    }

}
