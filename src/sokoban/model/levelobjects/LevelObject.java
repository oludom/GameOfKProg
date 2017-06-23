package sokoban.model.levelobjects;

import javafx.scene.image.Image;
import sokoban.Vector;

import java.io.Serializable;

/**
 * @author MHeiß SWirries
 *
 */
public interface LevelObject {

    char getChar();
    Image toImage();
    void setPosition(Vector v);
}
