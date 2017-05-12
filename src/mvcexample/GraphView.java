package mvcexample;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import java.util.Observable;
import java.util.Observer;

/**
 * 20.04.2017
 *
 * @author SWirries
 */
public class GraphView extends Pane implements Observer{


    PolynomModel polynomModel;
    double height;
    double width;
    GridPane gridPane;
    LineChart<Number, Number> lineChart;
    NumberAxis xAxis = new NumberAxis();
    NumberAxis yAxis = new NumberAxis();
    String pickedColor;
    int strokeWidth = 1;

    XYChart.Series<Number, Number> series = new XYChart.Series<>();

    /**
     *
     * @param polynomModel
     * @param height
     * @param width
     * Konstruktor der Klasse
     */
    public GraphView(PolynomModel polynomModel,double height, double width){
        this.polynomModel = polynomModel;
        this.setWidth(width);
        this.setHeight(height);
        this.height = height;
        this.width = width;
        gridPane = new GridPane();

        /**
         * Erstellen der Steuerungselemente
         */
        ColorPicker colorPicker = new ColorPicker(Color.RED);
        Slider rotateSliter = new Slider();
        TitledBorderPane rotatePane = new TitledBorderPane("Rotation:", rotateSliter);
        Slider zoomXSlider = new Slider();
        TitledBorderPane zoomXPane = new TitledBorderPane("x-Achse Skalieren:", zoomXSlider);
        Slider zoomYSlider = new Slider();
        TitledBorderPane zoomYPane = new TitledBorderPane("y-Achse Skalieren:", zoomYSlider);
        Slider strokeSlider = new Slider();
        TitledBorderPane strokePane = new TitledBorderPane("Strichbreite: ", strokeSlider);

        /**
         * Konfigurieren des Koordinaten Systems
         */
        xAxis.setLabel("X");
//        xAxis.setTickMarkVisible(false);
        xAxis.setMinorTickVisible(false);
//        xAxis.setTickLabelsVisible(false);
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(-10);
        xAxis.setUpperBound(10);
        xAxis.setTickUnit(1);

        yAxis.setLabel("Y");
//        yAxis.setTickMarkVisible(false);
        yAxis.setMinorTickVisible(false);
//        yAxis.setTickLabelsVisible(false);
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(-10);
        yAxis.setUpperBound(10);
        yAxis.setTickUnit(1);

        lineChart = new LineChart<Number, Number>(xAxis, yAxis);
        lineChart.setTitle("GraphView");
        lineChart.getData().add(series);
        lineChart.setCreateSymbols(false);
        lineChart.setAnimated(false);
        lineChart.setLegendVisible(false);

        series.nodeProperty().get().setStyle("-fx-stroke-width: 2px; -fx-stroke: red;");

        /**
         * Berechnen der Punkte
         */
        calculatePoints();

        this.getChildren().add(gridPane);
        gridPane.add(lineChart, 1, 1);
        gridPane.add(colorPicker, 1,2);
        gridPane.add(rotatePane, 1, 3);
        gridPane.add(zoomXPane, 1, 4);
        gridPane.add(zoomYPane, 1, 5);
        gridPane.add(strokePane, 1, 6);


        /**
         * Events der Steuerungselemente
         */
        colorPicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Color c = colorPicker.getValue();
                pickedColor = String.format("#%02X%02X%02X", (int)(c.getRed()*255),
                        (int)(c.getGreen()*255), (int)(c.getBlue()*255));
                series.nodeProperty().get().setStyle("-fx-stroke-width: "+strokeWidth+"px; -fx-stroke: "+pickedColor);
            }
        });

        rotateSliter.setValue(0);
        rotateSliter.setMin(-15);
        rotateSliter.setMax(360);
        rotateSliter.setShowTickLabels(true);
        rotateSliter.setMajorTickUnit(15);
        rotateSliter.setSnapToTicks(true);
        rotateSliter.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                rotate((int) rotateSliter.getValue());

            }
        });

        zoomXSlider.setValue(10);
        zoomXSlider.setMin(0);
        zoomXSlider.setMax(50);
        zoomXSlider.setShowTickMarks(true);
        zoomXSlider.setShowTickLabels(true);
        zoomXSlider.setMajorTickUnit(5);
        zoomXSlider.setSnapToTicks(true);
        zoomXSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double zoomFactor = zoomXSlider.getValue();

                xAxis.setAutoRanging(false);
                xAxis.setLowerBound(-zoomFactor);
                xAxis.setUpperBound(zoomFactor);

            }
        });

        zoomYSlider.setValue(10);
        zoomYSlider.setMin(0);
        zoomYSlider.setMax(50);
        zoomYSlider.setShowTickMarks(true);
        zoomYSlider.setShowTickLabels(true);
        zoomYSlider.setMajorTickUnit(5);
        zoomYSlider.setSnapToTicks(true);
        zoomYSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double zoomFactor = zoomYSlider.getValue();

                yAxis.setAutoRanging(false);
                yAxis.setLowerBound(-zoomFactor);
                yAxis.setUpperBound(zoomFactor);

            }
        });

        strokeSlider.setValue(2);
        strokeSlider.setMin(1);
        strokeSlider.setMax(10);
        strokeSlider.setShowTickMarks(true);
        strokeSlider.setShowTickLabels(true);
        strokeSlider.setMajorTickUnit(1);
        strokeSlider.setSnapToTicks(true);
        strokeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                strokeWidth = (int) strokeSlider.getValue();
                series.nodeProperty().get().setStyle("-fx-stroke-width: "+strokeWidth+"px; -fx-stroke: "+pickedColor);
            }
        });

        Color c = colorPicker.getValue();
        pickedColor = String.format("#%02X%02X%02X", (int)(c.getRed()*255),
                (int)(c.getGreen()*255), (int)(c.getBlue()*255));

    }

    /**
     * Rotationsmethode
     * @param value
     */
    private void rotate(int value){
            lineChart.setRotate(value);
    }

    /**
     * Berechnen der Punkte für den Graphen
     */
    private void calculatePoints(){
        int pointsCount = 500;
//        double maxWidth = height/2;
//        double maxHeight = width/2;
//        this.getChildren().removeAll();
//        if (canvas != null) canvas.setRotate(0);canvas = null;
//        canvas = new Canvas(maxWidth,maxHeight);
//        if(graphicsContext != null){
//
//            this.getChildren().add(canvas);
//            graphicsContext.setFill(Color.WHITE);
//            graphicsContext.fillRect(0,0,maxWidth,maxHeight);
//            canvas = new Canvas(maxWidth,maxHeight);
//            canvas.setRotate(0);
//        }
//        graphicsContext = canvas.getGraphicsContext2D();
//
//        graphicsContext.clearRect(0,0,maxWidth,maxHeight);
//
//        graphicsContext.setFill(Color.TRANSPARENT);
//        graphicsContext.fillRect(0,0,maxWidth,maxHeight);

//        int xMax = 100, xMin = -10;

//        double umformFactor = (maxWidth)/ (2*xMax);

//        double hstep = (2d*(double) xMax )/ (double) (pointsCount);

        int a = polynomModel.getCubic();
        int b = polynomModel.getQuadratic();
        int c = polynomModel.getLinear();
        int d = polynomModel.getConstant();

//        double[] xPoints = new double[pointsCount];
//        double[] yPoints = new double[pointsCount];

        series.getData().clear();
        ObservableList<XYChart.Data<Number, Number>> data = series.getData();


        for(double i = -pointsCount/2;i <= pointsCount/2;i+=0.1){
//            xPoints[i] = xMin + (i*hstep);
            double xWert = (i); //xPoints[i]*(-1);
//            yPoints[i] = (a*Math.pow(xWert,3)+b*xWert*xWert+c*xWert+d) + maxHeight/2;

//            xPoints[i] = xPoints[i] * umformFactor + maxWidth/2;
            data.add(new XYChart.Data<Number, Number>( (i), (a*xWert*xWert*xWert+b*xWert*xWert+c*xWert+d)));
//            data.add(new XYChart.Data<Number, Number>( (i), Math.sin(i)));
        }

    }

    /**
     * Methode für den Observer
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {

        if(o == polynomModel) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    calculatePoints();
                }
            });

        }
    }
}
