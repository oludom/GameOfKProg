package sokoban.model;

import sokoban.Sokoban;
import sokoban.Vector;
import sokoban.model.levelobjects.*;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Micha Heiß
 */
public class Level {

    private LevelObject[][] levelObjects;
    private Vector[] endpositions;
    private Player player;
    private Sokoban parent;
    private String title;
    private ArrayList<State> moves = new ArrayList<>();

    public Level(ArrayList<String> leveldata, Sokoban parent, String title){

        this.parent = parent;
        this.title = title;

        sokoban.Vector dim = countLevelSize(leveldata);
        int x = dim.getX();
        int y = dim.getY();
        levelObjects = new LevelObject[y][x];

        String[] levelarray = new String[leveldata.size()];
        levelarray = leveldata.toArray(levelarray);

        ArrayList<Vector> endP = new ArrayList<>();

        // initialize Object Array
        for(int i = 0; i<y;i++) {
            for (int j = 0; j < x; j++) {
                if(levelarray[i].toCharArray().length>j){
                    if(levelarray[i].charAt(j) == '.'){
                        endP.add(new Vector(j,i));
                    }
                    levelObjects[i][j] = createLevelObject(levelarray[i].charAt(j));
                }else{
                    levelObjects[i][j] = createLevelObject('#');
                }
            }
        }
        endpositions = new Vector[endP.size()];
        for(int i = 0; i <endP.size();i++){
            endpositions[i] = endP.get(i);
        }

        // fill outside level with stone
        int left = 0;
        int right = 0;
        LevelObject lo = createLevelObject('#');
        for(int i = 0; i<levelObjects.length;i++){

            left = getLeftHash(levelObjects[i]);
//            right = getRightHash(levelObjects[i]);

            for(int j = 0; j<left;j++){
                levelObjects[i][j] = lo;
            }
        }
    }


    /**
     * get max level size for array initialization
     * @param data String ArrayList representing Level
     * @return Vector, contains width + height (x,y)
     */
    private sokoban.Vector countLevelSize(ArrayList<String> data){

        int x = 0;
        int y = 0;

        for(String element : data){
            y++;
            if(element.toCharArray().length>x) x = element.toCharArray().length;
        }
        return new sokoban.Vector(x,y);
    }

    /**
     * get LevelObject according to String parameter
     * @param l text representation of LevelObject
     * @return LevelObject according to String parameter
     */
    private LevelObject createLevelObject(char l){
        switch (l){
            case '@':
                this.player = new Player();
                return this.player;
            case '$':
                return new Box();
            case '#':
                return new Stone();
//            case ' ':
//                return new Stone();
            default:
                return new Space();
        }

    }

    public LevelObject[][] getLevelObjects() {
        return levelObjects;
    }

    public int getLeftHash(LevelObject[] line){

        int out = 0;
        LevelObject lo = createLevelObject('#');
        for(int i = 0; i<line.length;i++){

            if(lo.getClass().equals(line[i].getClass())) {
                break;
            }
            else out++;
        }
        return out;

    }

    public boolean isEndposition(Vector v){
        boolean out = false;
        for (int i = 0; i<endpositions.length;i++){
            if(v.getX() == endpositions[i].getX() && v.getY() == endpositions[i].getY()){
                out = true;
                break;
            }
        }
        return out;
    }

    public void movePlayer(int x, int y){


        int pX = player.getPosition().getX();
        int pY = player.getPosition().getY();

        // move to position is empty (Space.class)
        if(levelObjects[pY+y][pX+x].getClass().equals(Space.class)){
            switchObjects(pX,pY, pX+x,pY+y);
            player.setPosition(new Vector(pX+x,pY+y));
        }else if(levelObjects[pY+y][pX+x].getClass().equals(Box.class)){
            if(levelObjects[pY+y*2][pX+x*2].getClass().equals(Space.class)){
                // box can be moved
                // save last position for undo
                System.out.println("moves.add()");
                moves.add(new State(cloneLevel()));
                switchObjects(pX+x,pY+y,pX+2*x, pY+2*y);  // move box
                switchObjects(pX,pY, pX+x,pY+y);                // move player
                player.setPosition(new Vector(pX+x,pY+y));
                if(levelDone()){
                    // start next Level if possible
                    parent.startNextLevel();
                }
            }
        }

    }

    private void switchObjects(int x, int y, int tx, int ty){

        LevelObject tmp = levelObjects[y][x];
        levelObjects[y][x] = levelObjects[ty][tx];
        levelObjects[ty][tx] = tmp;

    }

    private boolean levelDone(){

        boolean out = true;

        for(Vector v : endpositions){
            if(!levelObjects[v.getY()][v.getX()].getClass().equals(Box.class))
                out = false;
        }

        return out;

    }

    private LevelObject[][] cloneLevel(){
        LevelObject[][] clone = new LevelObject[levelObjects.length][levelObjects[0].length];

        for(int i = 0; i<levelObjects.length;i++){
            for(int j = 0; j<levelObjects[0].length;j++){
                clone[i][j] = createLevelObject(levelObjects[i][j].getChar());
            }
        }
        return clone;

    }


    public String getTitle() {
        return title;
    }

    public void undo(){
        System.out.println("undo" + moves.size());
        if(moves.size()>0) {
            System.out.println(Arrays.toString(levelObjects[2]));
            levelObjects = moves.remove(moves.size() - 1).getLevelObjects();
            System.out.println(Arrays.toString(levelObjects[2]));
        }
    }
}
