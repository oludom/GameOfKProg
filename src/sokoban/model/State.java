package sokoban.model;

import sokoban.model.levelobjects.LevelObject;

/**
 * @author Micha Heiß
 */
public class State {

    private LevelObject[][] levelObjects;

    public State(LevelObject[][] levelObjects) {
        this.levelObjects = levelObjects;
    }

    public LevelObject[][] getLevelObjects() {
        return levelObjects;
    }
}
