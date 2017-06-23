package sokoban.model;

import sokoban.model.levelobjects.LevelObject;

import java.io.Serializable;

/**
 * @author MHeiß SWirries
 *
 */
public class State implements Serializable{

    private LevelObject[][] levelObjects;

    public State(LevelObject[][] levelObjects) {
        this.levelObjects = levelObjects;
    }

    public LevelObject[][] getLevelObjects() {
        return levelObjects;
    }
}
