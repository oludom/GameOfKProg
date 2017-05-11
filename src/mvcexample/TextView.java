package mvcexample;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import java.util.Observable;
import java.util.Observer;

/**
 * 20.04.2017
 *
 * @author SWirries
 *         <p>
 *         This code is
 *         documentation enough
 */
public class TextView extends Pane implements Observer {
    PolynomModel polynomModel;
    TextField constantField = new TextField();
    TextField linearField = new TextField();
    TextField quadField = new TextField();
    TextField cubicField = new TextField();

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Update");
        if(o == polynomModel) fillText();
    }

    public TextView(PolynomModel polynomModel){
        this.polynomModel = polynomModel;

        GridPane gridPane = new GridPane();

        constantField.setEditable(false);
        linearField.setEditable(false);
        quadField.setEditable(false);
        cubicField.setEditable(false);

        gridPane.add(new Label("Konstante: "), 1, 1);
        gridPane.add(constantField, 2, 1);

        gridPane.add(new Label("Linearer Koeffizient: "), 1, 2);
        gridPane.add(linearField, 2, 2);

        gridPane.add(new Label("Quadratischer Koeffizient: "), 1, 3);
        gridPane.add(quadField, 2, 3);

        gridPane.add(new Label("Kubischer Koeffizient: "), 1, 4);
        gridPane.add(cubicField, 2, 4);

        this.getChildren().add(gridPane);
        fillText();
    }

    private void fillText(){
        constantField.setText(""+polynomModel.getConstant());
        linearField.setText(""+polynomModel.getLinear());
        quadField.setText(""+polynomModel.getQuadratic());
        cubicField.setText(""+polynomModel.getCubic());
    }

    
}
