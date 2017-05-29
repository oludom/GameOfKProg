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
public class World extends Observable implements Cloneable {

    private final Cell[][] cells;
    private int dimX;
    private int dimY;
    int threadTime = 500;
    int viewCount = 0;
    boolean runChangeWorld = false;
    int worldNumber = 1;

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


    public Cell getCell(int x, int y){
        // Torrus
        if(x<0)
            x = cells.length+x;
        if(y<0)
            y = cells.length +y;
        if(x>cells.length-1)
            x -= cells.length;
        if(y>cells.length-1)
            y -= cells.length;
        return cells[x][y];
    }

    public Cell[][] getCells(){
        return cells;
    }

    public void setAlive(int x, int y, boolean alive){
        cells[x][y].setAlive(alive);
    }

    public void nextStep(){
        World clone = this.clone();
        for(int i = 0; i<dimX; i++)
            for(int j = 0;j<dimY;j++){
//
                if(clone.getCell(i,j).countNeighboursAlive() == 3){
                    this.getCell(i,j).setAlive(true);
                }else if(clone.getCell(i,j).countNeighboursAlive()<2 || clone.getCell(i,j).countNeighboursAlive() > 3){
                    this.getCell(i,j).setAlive(false);
                }

            }
    }

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

    public void addView(){
        viewCount++;
    }

    public void removeView(){
        viewCount--;
        if(viewCount <= 0){
            stopThread();
        }
    }

    public void clear(){
        for(Cell[] cll : cells){
            for(Cell c : cll){
                c.setAlive(false);
            }
        }
    }
}
