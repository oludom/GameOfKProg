package gol;

import java.util.Observable;

import static java.lang.Thread.sleep;

/**
 * 27.04.2017
 *
 * @author SWirries
 *         <p>
 *         This code is
 *         documentation enough
 */

/**
 * Stellt die Welt (Sammlung von Zellen) dar
 */
public class World extends Observable implements Cloneable {

    private Cell[][] cells;
    private int dimX;
    private int dimY;
    int threadTime = 500;
    int viewCount = 0;
    boolean runChangeWorld = false;
    int worldNumber = 1;

    /**
     * Erzeugt eine Welt mit gleicher Höhe und Breite
     * @param dim
     */
    public World(int dim){
        this.cells = new Cell[dim][dim];

        // init
        for(int i = 0; i<cells.length; i++){
            for(int j = 0;j<cells.length;j++){
                this.cells[i][j] = new Cell(this, i, j);
            }
        }

        this.dimX = dim;
        this.dimY = dim;
    }

    /**
     * Erzeugt eine Welt mit unterschiedlicher Höhe und Breite
     * @param dimX
     * @param dimY
     */
    public World(int dimX, int dimY){
        this.cells = new Cell[dimX][dimY];

        // init
        for(int i = 0; i<dimX; i++){
            for(int j = 0;j<dimY;j++){
                this.cells[i][j] = new Cell(this, i, j);
            }
        }

        this.dimX = dimX;
        this.dimY = dimY;
    }


    /**
     * Gibt die Zelle an der Position zurück
     * @param x
     * @param y
     * @return
     */
    public Cell getCell(int x, int y){
        // Torrus
        if(x<0)
            x = dimX+x;
        if(y<0)
            y = dimY +y;
        if(x>dimX-1)
            x -= dimX;
        if(y>dimY-1)
            y -= dimY;
        return cells[x][y];
    }

    /**
     * Gibt alle Zellen zurück
     * @return
     */
    public Cell[][] getCells(){
        return cells;
    }

    /**
     * setzt eine Zelle an der Position lebend oder Tod
     * @param x
     * @param y
     * @param alive
     */
    public void setAlive(int x, int y, boolean alive){
        cells[x][y].setAlive(alive);
    }

    /**
     * Lässt die Welt um eine Generation altern
     */
    public void nextStep(){
        World clone = this.clone();
        for(int i = 0; i<dimX; i++)
            for(int j = 0;j<dimY;j++){
                if(clone.getCell(i,j).countNeighboursAlive() == 3){
                    this.getCell(i,j).setAlive(true);
                }else if(clone.getCell(i,j).countNeighboursAlive()<2 || clone.getCell(i,j).countNeighboursAlive() > 3){
                    this.getCell(i,j).setAlive(false);
                }

            }
    }

    /**
     * Erzeugt eine Clone-Objekt der Welt
     * @return
     */
    @Override
    public World clone(){
        World clone = new World(dimX, dimY);
        for(int i = 0; i<dimX; i++)
            for(int j = 0;j<dimY;j++){
                if(this.cells[i][j].isAlive())
                    clone.setAlive(i,j,true);
            }
        return clone;
    }

    public int getDimX(){
        return dimX;
    }
    public int getDimY(){ return dimY;}

    /**
     * Runnable für die Thread
     */
    Runnable changeWorldRunnable = new Runnable() {
        @Override
        public void run() {
            while (runChangeWorld) {
                try{
                    nextStep();
                    sleep(threadTime);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }
    };

    /**
     * Startet den Gernerationswechsel
     */
    public void startThread(){
        if(!runChangeWorld){
            runChangeWorld = true;
            new Thread(changeWorldRunnable).start();
        }
    }

    public void stopThread(){
        runChangeWorld = false;
    }

    public void setThreadTime(int i){
        threadTime = i;
    }

    public int getThreadTime(){
        return threadTime;
    }

    /**
     * Fügte eine "View" hinzu
     */
    public void addView(){
        viewCount++;
    }

    public int getViewCount() {
        return viewCount;
    }

    /**
     * Löscht eine View
     * wenn kleiner gelich 0 dann wird der Generationswechsel gestoppt
     */
    public void removeView(){
        viewCount--;
        if(viewCount <= 0){
            stopThread();
        }
    }

    /**
     * Setzt alle Zellen auf tot
     */
    public void clear(){
        for(Cell[] cll : cells){
            for(Cell c : cll){
                c.setAlive(false);
            }
        }
    }
}
