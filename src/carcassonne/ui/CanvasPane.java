package carcassonne.ui;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;

/**
 * @author MHeiß SWirries
 * Stellt den Spielplan im Spiel da, auf dem die Karten gelegt und die CanvasButtons erzugt werden
 */
class CanvasPane extends Pane {

    private final Canvas canvas;

    public CanvasPane(double width, double height) {
        canvas = new Canvas(width, height);
        getChildren().add(canvas);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    /**
     * Sorgt für das Ausfüllen des Parentelements
     */
    @Override
    protected void layoutChildren() {
        final double x = snappedLeftInset();
        final double y = snappedTopInset();
        final double w = snapSize(getWidth()) - x - snappedRightInset();
        final double h = snapSize(getHeight()) - y - snappedBottomInset();
        canvas.setLayoutX(x);
        canvas.setLayoutY(y);
        canvas.setWidth(w);
        canvas.setHeight(h);
    }
}