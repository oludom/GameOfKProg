package mvcexample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * 11.05.2017
 *
 * @author SWirries
 *         <p>
 *         This code is
 *         documentation enough
 */
public class MVCpolynom extends AnchorPane {

    private AnchorPane borderPane = this;

    public MVCpolynom() {

        GridPane gridPane = new GridPane();
        GridPane viewGridPane = new GridPane();
        GridPane controlGridPane = new GridPane();

        /**
         * Erzeugen der Steuerungselemente
         */
        Slider constantSlider = new Slider();
        Slider linearSlider = new Slider();
        Slider quadSlider = new Slider();
        Slider cubicSlider = new Slider();
        TitledBorderPane constantTbPane = new TitledBorderPane("Konstante",constantSlider);
        TitledBorderPane linearTbPane = new TitledBorderPane("Linearer Koeffizient",linearSlider);
        TitledBorderPane quadTbPane = new TitledBorderPane("Quadratischer Koeffizient",quadSlider);
        TitledBorderPane cubicTbPane = new TitledBorderPane("Kubischer Koeffizient",cubicSlider);

        final PolynomModel polynomModel = new PolynomModel(1,2,3,4);

//        TextView textView1 = new TextView(polynomModel);
//        TextView textView2 = new TextView(polynomModel);


        constantTbPane.setMinSize(400, 10);

        gridPane.add(viewGridPane, 1, 1);
        gridPane.add(controlGridPane, 2,1);
        gridPane.setVgap(10);

        borderPane.getChildren().add(gridPane);

        controlGridPane.add(constantTbPane, 1, 1);
        controlGridPane.add(linearTbPane, 1,2);
        controlGridPane.add(quadTbPane, 1,3);
        controlGridPane.add(cubicTbPane, 1,4);

        double freeWidth = borderPane.getWidth() - controlGridPane.getWidth();

//        textView1.setMaxWidth(freeWidth/2);

        GraphView graphView1 = new GraphView(polynomModel,borderPane.getHeight(), freeWidth/2 -100);
        GraphView graphView2 = new GraphView(polynomModel,borderPane.getHeight(), freeWidth/2 -100);

        /**
         * Hinzufügen der Observer
         */
        polynomModel.addObserver(graphView1);
        polynomModel.addObserver(graphView2);

        /**
         * Views der Layout hinzufügen
         */
        viewGridPane.add(graphView1,1,1);
        viewGridPane.add(graphView2,2,1);
        viewGridPane.setHgap(5);

        /**
         * Events und Konfiguration der Steuerungselemente
         */
        constantSlider.setValue(1);
        constantSlider.setMin(-10);
        constantSlider.setMax(10);
        constantSlider.setShowTickLabels(true);
        constantSlider.setMajorTickUnit(1);
        constantSlider.setSnapToTicks(true);
        constantSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                if (!constantSlider.isValueChanging()){
                    constantSlider.setValue(Math.round((Double) newValue));
                    polynomModel.setConstant((int)constantSlider.getValue());
                }

            }
        });


        linearSlider.setValue(2);
        linearSlider.setMin(-10);
        linearSlider.setMax(10);
        linearSlider.setShowTickLabels(true);
        linearSlider.setMajorTickUnit(1);
        linearSlider.setSnapToTicks(true);
        linearSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (!linearSlider.isValueChanging()){
                    linearSlider.setValue(Math.round((Double) newValue));
                    polynomModel.setLinear((int)linearSlider.getValue());
                }

            }
        });

        quadSlider.setValue(3);
        quadSlider.setMin(-10);
        quadSlider.setMax(10);
        quadSlider.setShowTickLabels(true);
        quadSlider.setMajorTickUnit(1);
        quadSlider.setSnapToTicks(true);
        quadSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                if (!quadSlider.isValueChanging()){
                    quadSlider.setValue(Math.round((Double) newValue));
                    polynomModel.setQuadratic((int)quadSlider.getValue());
                }

            }
        });

        cubicSlider.setValue(4);
        cubicSlider.setMin(-10);
        cubicSlider.setMax(10);
        cubicSlider.setShowTickLabels(true);
        cubicSlider.setMajorTickUnit(1);
        cubicSlider.setSnapToTicks(true);
        cubicSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                if (!cubicSlider.isValueChanging()){
                    cubicSlider.setValue(Math.round((Double) newValue));
                    polynomModel.setCubic((int)cubicSlider.getValue());
                }

            }
        });







    }
}
