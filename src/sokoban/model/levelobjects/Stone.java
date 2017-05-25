package sokoban.model.levelobjects;

import javafx.scene.image.Image;
import sokoban.Vector;

/**
 * @author Micha Heiß
 */
public class Stone implements LevelObject {

    private static Image image = new Image("sokoban/images/background.jpg");
    private Vector position;

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    @Override
    public String getChar() {
        return "#";
    }

    @Override
    public Image toImage() {
        return image;
    }
}
