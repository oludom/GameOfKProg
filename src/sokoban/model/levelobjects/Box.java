package sokoban.model.levelobjects;

import javafx.scene.image.Image;
import sokoban.Vector;

/**
 * @author Micha Heiß
 */
public class Box implements LevelObject {

    private static Image image = new Image("sokoban/images/wood.png");
    private Vector position;

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    @Override
    public char getChar() {
        return '$';
    }

    @Override
    public Image toImage() {
        return image;
    }

    public static Image getImage() {
        return image;
    }
}
