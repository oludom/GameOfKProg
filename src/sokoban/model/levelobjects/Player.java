package sokoban.model.levelobjects;

import javafx.scene.image.Image;
import sokoban.Vector;

import java.io.Serializable;

/**
 *  @author MHeiß SWirries
 *
 */
public class Player implements LevelObject, Serializable {

    private static Image image = new Image("sokoban/images/trump.png");
    private Vector position;

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    @Override
    public char getChar() {
        return '@';
    }

    @Override
    public Image toImage() {
        return image;
    }
}
