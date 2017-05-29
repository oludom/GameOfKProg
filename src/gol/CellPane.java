package gol;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.Observable;
import java.util.Observer;


/**
 * 16.05.2017
 *
 * @author SWirries
 */
public class CellPane extends Pane implements Observer{
    private Cell cell;
    private GameOfLife gameOfLife;

    public CellPane(Cell cell, GameOfLife gameOfLife){
        this.gameOfLife = gameOfLife;
        this.cell = cell;
        cell.addObserver(this);
        if (!cell.isAlive()) {
            this.setStyle("-fx-background-color: " + gameOfLife.getColorDead());

        } else {
            this.setStyle("-fx-background-color: " + gameOfLife.getColorAlive());
        }
        setPrefSize(1000,1000);
        setBorder(new Border(new BorderStroke(Color.GRAY,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        this.setOnMouseClicked(event -> {
            if (cell.isAlive()) {
                cell.setAlive(false);
                this.setStyle("-fx-background-color: " + gameOfLife.getColorDead());

            } else {
                this.setStyle("-fx-background-color: " + gameOfLife.getColorAlive());
                cell.setAlive(true);
            }
        });

        this.setOnMouseEntered(event -> {
            if(gameOfLife.isEnableDrawing()){
                if (cell.isAlive()) {
                    cell.setAlive(false);
                    this.setStyle("-fx-background-color: " + gameOfLife.getColorDead());

                } else {
                    this.setStyle("-fx-background-color: " + gameOfLife.getColorAlive());
                    cell.setAlive(true);
                }
            }
        });
    }

    public void refill(){
        if (!cell.isAlive()) {
            this.setStyle("-fx-background-color: " + gameOfLife.getColorDead());

        } else {
            this.setStyle("-fx-background-color: " + gameOfLife.getColorAlive());
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o ==  cell){
            refill();
        }
    }
}
