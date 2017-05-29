package gol;

import java.awt.*;
import java.util.Observable;

/**
 * 27.04.2017
 *
 * @author SWirries
 *         <p>
 *         This code is
 *         documentation enough
 */
public class Cell extends Observable{

    private boolean alive;
    private World world;
    private final int x,y;

    public Cell(World world, int x, int y, boolean alive){
        this.alive = alive;
        this.world = world;
        this.x = x;
        this.y = y;
    }

    public Cell(World world, int x, int y){
        this.alive = false;
        this.world = world;
        this.x = x;
        this.y = y;
    }


    public boolean isAlive() {
        return alive;
    }

    public int countNeighboursAlive(){
        int out = 0;
        for(int i = -1; i<2;i++)
            for(int j = -1; j<2; j++){
                    Cell n = world.getCell(this.x+i, this.y+j);
                    if(n.isAlive())
                        out++;

            }
            if(this.isAlive())
                out--;

        return out;
    }

//    public void step(){
//        if(alive){
//            if(this.countNeighboursAlive()<2 || this.countNeighboursAlive() > 3)
//               alive = false;
//        }else {
//            if(this.countNeighboursAlive() == 3)
//                alive = true;
//        }
//    }

    public void setAlive(boolean alive) {

        this.alive = alive;
        notifyObservers();
        setChanged();
    }


    public String toString(){
        return this.isAlive() ? "1" : "0";
    }
}
