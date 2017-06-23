package sokoban;

import java.io.Serializable;

/**
 * @author MHeiß SWirries
 *
 */
public class Vector implements Serializable{

    private int x,y;

    public Vector(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
