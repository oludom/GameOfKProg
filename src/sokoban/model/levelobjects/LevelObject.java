package sokoban.model.levelobjects;

import javafx.scene.image.Image;
import sokoban.Vector;

/**
 * @author Micha Heiß
 */
public interface LevelObject {

    char getChar();
    Image toImage();
    void setPosition(Vector v);
}
