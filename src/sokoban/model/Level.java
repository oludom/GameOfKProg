package sokoban.model;

import javafx.scene.control.Alert;
import sokoban.Sokoban;
import sokoban.Vector;
import sokoban.model.levelobjects.*;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author MHeiß SWirries
 *
 */
public class Level implements Serializable {

    private static final long serialVersionUID = 9187435L;

    private LevelObject[][] levelObjects;
    private Vector[] endpositions;
    private Player player;
    private transient Sokoban parent;
    private String title;
    private int levelNumber;
    private String levelFile;
    private ArrayList<State> moves = new ArrayList<>();

    /**
     * level constructor
     * @param leveldata strings containing level data for single level
     * @param parent reference to Sokoban parent to be able to start next level
     * @param title name of level as set in levels file
     */
    public Level(ArrayList<String> leveldata, Sokoban parent, String title, int levelNumber, String levelFile){

        this.parent = parent;
        this.title = title;
        this.levelNumber = levelNumber;
        this.levelFile = levelFile;

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
                    if(levelarray[i].charAt(j) == '.' || levelarray[i].charAt(j) == '*' || levelarray[i].charAt(j) == '+'){
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
        LevelObject lo = createLevelObject('#');
        for(int i = 0; i<levelObjects.length;i++){

            left = getLeftHash(levelObjects[i]);

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
            case '+':
                if(this.player == null)
                    this.player = new Player();
                return this.player;
            case '$':
            case '*':
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

    /**
     * counts number of empty fields (spaces) in front of first hash (left wall of level)
     * @param line string containing level chars for level generation
     * @return number of chars to be replaced
     */
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

    /**
     * checks if v is in endpositions array
     * @param v coordinates of some LevelObject in levelobjects array
     * @return true if found in endpositions array
     */
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

    /**
     * moves player in specified direction if possible
     * @param x direction to move on x-axis as delta ( range: -1 to 1 )
     * @param y direction to move on x-axis as delta ( range: -1 to 1 )
     */
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
                moves.add(new State(cloneLevel()));
                switchObjects(pX+x,pY+y,pX+2*x, pY+2*y);  // move box
                switchObjects(pX,pY, pX+x,pY+y);                // move player
                player.setPosition(new Vector(pX+x,pY+y));
                if(levelDone()){
                    parent.render();
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Level erfolgreich!");
                    alert.setHeaderText(null);
                    alert.setContentText("Sie haben das Level Erfolgreich abgeschlossen!");
                    alert.showAndWait();
                    // start next Level if possible
                    parent.startNextLevel();
                }
            }
        }

    }

    /**
     * switches position of two LevelObjects in levelobject array
     * @param x coordinate of first LevelObject
     * @param y coordinate of first LevelObject
     * @param tx coordinate of second LevelObject
     * @param ty coordinate of second LevelObject
     */
    private void switchObjects(int x, int y, int tx, int ty){

        LevelObject tmp = levelObjects[y][x];
        levelObjects[y][x] = levelObjects[ty][tx];
        levelObjects[ty][tx] = tmp;

    }

    /**
     * checks if all Box LevelObjects in levelobjects array are on end positions
     * @return
     */
    private boolean levelDone(){

        boolean out = true;

        for(Vector v : endpositions){
            if(!levelObjects[v.getY()][v.getX()].getClass().equals(Box.class))
                out = false;
        }

        return out;

    }

    /**
     * creates deep copy of levelobjects array for saving level state
     * @return levelobjects clone (deep copy)
     */
    private LevelObject[][] cloneLevel(){
        LevelObject[][] clone = new LevelObject[levelObjects.length][levelObjects[0].length];

        for(int i = 0; i<levelObjects.length;i++){
            for(int j = 0; j<levelObjects[0].length;j++){
                if(levelObjects[i][j].getChar() == '@'){
                    clone[i][j] = new Player();
                    clone[i][j].setPosition(new Vector(j,i));
                }else{
                    clone[i][j] = createLevelObject(levelObjects[i][j].getChar());
                }
            }
        }
        return clone;

    }

    /**
     * search for player object in levelobject array
     * @return player object reference
     */
    public Player searchPlayer(){
        Player player = new Player();
        for(int i = 0; i<levelObjects.length;i++){
            for(int j = 0; j<levelObjects[0].length;j++){
                if(levelObjects[i][j].getChar() == '@'){
                    player = (Player) levelObjects[i][j];
                    player.setPosition(new Vector(j,i));
                }
            }
        }
        return player;
    }


    public String getTitle() {
        return title;
    }

    public String getLevelFile() {
        return levelFile;
    }

    /**
     * reset levelobjects array to last state in moves ArrayList
     */
    public void undo(){
        if(moves.size()>0) {
            levelObjects = moves.remove(moves.size() - 1).getLevelObjects();
            this.player = searchPlayer();
        }
    }

    public void setParent(Sokoban parent) {
        this.parent = parent;
    }

    public int getLevelNumber() {
        return levelNumber;
    }
}
