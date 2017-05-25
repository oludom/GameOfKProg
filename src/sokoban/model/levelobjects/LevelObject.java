package sokoban.model.levelobjects;

import javafx.scene.image.Image;
import sokoban.Vector;

/**
 * @author Micha Heiß
 */
public interface LevelObject {

    public String getChar();
    public Image toImage();
    public void setPosition(Vector v);
}
