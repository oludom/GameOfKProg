package gol;

import java.awt.*;
import java.util.Observable;

/**
 * 27.04.2017
 *
 * @author SWirries MHeiß
 *
 */

/**
 * Stellt eine Zelle im Game Of Life da
 */
public class Cell extends Observable{

    private boolean alive; //gibt an ob Zelle lebt oder tod ist
    private World world; // Verknüpfung zur Welt
    private final int x,y; //Position in der Welt

    /**
     * Erzeugt Zelle tote oder lebnede Zelle
     * @param world
     * @param x
     * @param y
     * @param alive
     */
    public Cell(World world, int x, int y, boolean alive){
        this.alive = alive;
        this.world = world;
        this.x = x;
        this.y = y;
    }

    /**
     * Erzeugt tote Zelle
     * @param world
     * @param x
     * @param y
     */
    public Cell(World world, int x, int y){
        this.alive = false;
        this.world = world;
        this.x = x;
        this.y = y;
    }


    public boolean isAlive() {
        return alive;
    }

    /**
     * Zählt die lebenden Nachbarn
     * @return
     */
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

    /**
     * Setzt die Zelle lebend oder Tod
     * @param alive
     */
    public void setAlive(boolean alive) {

        this.alive = alive;
        notifyObservers();
        setChanged();
    }


    public String toString(){
        return this.isAlive() ? "1" : "0";
    }
}
