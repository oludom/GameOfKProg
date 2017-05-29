package sokoban.model.levelobjects;

import javafx.scene.image.Image;
import sokoban.Vector;

import java.io.Serializable;

/**
 * @author Micha Heiß
 */
public class Space implements LevelObject, Serializable {

    private static Image image = new Image("sokoban/images/sand.png");
    private static Image cross = new Image("sokoban/images/sand_red.png");
    private Vector position;

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    @Override
    public char getChar() {
        return ' ';
    }

    @Override
    public Image toImage() {
        return image;
    }
    public static Image toImage(boolean red){
        return red ? cross : image;
    }
}
