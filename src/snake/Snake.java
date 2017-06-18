package snake;

import br.com.supremeforever.mdi.MDIWindow;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sokoban.Sokoban;
import sokoban.Vector;

import java.util.ArrayList;


/**
 * @author Micha Heiß
 *
 * Code source (JavaScript, siehe Kommentare): https://www.youtube.com/watch?v=xGmXxpIj6vs
 *
 */
public class Snake extends AnchorPane{

    private Canvas canvas;
    private GraphicsContext c;
    private Stage primaryStage;
    private MDIWindow mdiWindow;

    private int px,py,gsw,gsh,tc,ax,ay,xv,yv,tail;
    private ArrayList<Vector> trail;

    public Snake(Stage primaryStage){

        this.primaryStage = primaryStage;
        this.setMinSize(400,400);

        Sokoban.CanvasPane canvasPane = new Sokoban.CanvasPane(800,600);
//                canv=document.getElementById("gc");
        canvas = canvasPane.getCanvas();
//                ctx=canv.getContext("2d");
        c = canvas.getGraphicsContext2D();
        this.getChildren().add(canvasPane);

        this.widthProperty().addListener(e -> render());
        this.heightProperty().addListener(e -> render());

        AnchorPane.setBottomAnchor(canvasPane, 0d);
        AnchorPane.setLeftAnchor(canvasPane, 0d);
        AnchorPane.setTopAnchor(canvasPane, 0d);
        AnchorPane.setRightAnchor(canvasPane, 0d);



//                setInterval(game,1000/10);
        new Thread(){
            public void run(){
                while(true){
                    try {
                        sleep(1000/10);
                    } catch (InterruptedException e) {

                        e.printStackTrace();
                    }
                    update();
                    render();
                }

            }
        }.start();



//                px=py=10;
        // player/snake position
        px = 10;
        py = 10;
//                gs=tc=20;
        // square width/height
        gsw = 20;
        gsh = 20;
        // dimensions
        tc = 20;
//                ax=ay=15;
        // apple position
        ax = 15;
        ay = 15;
//                xv=yv=0;
        // Velocity
        xv = 0;
        yv = 0;
//                trail=[];
        trail = new ArrayList<>();
//                tail = 5;
        tail = 5;


//                document.addEventListener("keydown",keyPush);
        registerKeyPush();

    }

    /**
     * refresh canvas
     */
    private void render(){

        double width, height, levelSize;
        int divider;

        if(mdiWindow != null) {
            width = mdiWindow.getWidth();
            height = mdiWindow.getHeight();
            canvas.setWidth(width);
            canvas.setHeight(height);
            // set title to level name
//            mdiWindow.setMdiTitle("");
        }else {
            width = canvas.getWidth();
            height = canvas.getHeight();
        }


        gsw = (int)width/tc;
        gsh = (int)height/tc;



//                    ctx.fillStyle="black";
        c.setFill(Color.BLACK);
//                    ctx.fillRect(0,0,canv.width,canv.height);
        c.fillRect(0,0, canvas.getWidth(),canvas.getHeight());
//
//                    ctx.fillStyle="lime";
        c.setFill(Color.LIME);
//                    for(var i=0;i<trail.length;i++) {
        for(int i=0;i<trail.size();i++) {
//                        ctx.fillRect(trail[i].x*gs,trail[i].y*gs,gs-2,gs-2);
            c.fillRect(trail.get(i).getX()*gsw,trail.get(i).getY()*gsh,gsw-2,gsh-2);
        }

//                    ctx.fillStyle="red";
        c.setFill(Color.RED);
//                    ctx.fillRect(ax*gs,ay*gs,gs-2,gs-2);
        c.fillRect(ax*gsw,ay*gsh,gsw-2,gsh-2);

    }

    /**
     * calculate changes
     */
    private void update(){


//                    px+=xv;
        px+=xv;
//                    py+=yv;
        py+=yv;

        if(px<0) {
            px= tc-1;
        }
        if(px>tc-1) {
            px= 0;
        }
        if(py<0) {
            py= tc-1;
        }
        if(py>tc-1) {
            py= 0;
        }

        for(int i=0;i<trail.size();i++) {
//                        if(trail[i].x==px && trail[i].y==py) {
//                            tail = 5;
//                        }
            if(trail.get(i).getX()==px && trail.get(i).getY()==py) {
                tail = 5;
            }
        }

//                    trail.push({x:px,y:py});
        trail.add(new Vector(px,py));
//                    while(trail.length>tail) {
//                        trail.shift();
//                    }
        while(trail.size()>tail) {
            trail.remove(0);
        }
//
//                    if(ax==px && ay==py) {
//                        tail++;
//                        ax=Math.floor(Math.random()*tc);
//                        ay=Math.floor(Math.random()*tc);
//                    }
        if(ax==px && ay==py) {
            tail++;
            ax=(int)Math.floor(Math.random()*tc);
            ay=(int)Math.floor(Math.random()*tc);
        }

    }

    /**
     * called if MDIWindow gets focus
     * sets the Key controlls for game
     */
    public void registerKeyPush(){
        primaryStage.getScene().setOnKeyPressed(event -> {
            System.out.println("fired!");
            switch (event.getCode()) {
                case UP:
                    xv=0;
                    yv=-1;
                    break;
                case DOWN:
                    xv=0;
                    yv=1;
                    break;
                case LEFT:
                    xv=-1;
                    yv=0;
                    break;
                case RIGHT:
                    xv=1;
                    yv=0;
                    break;
            }
        });

    }

    public void setMdiWindow(MDIWindow mdiWindow) {
        this.mdiWindow = mdiWindow;
    }
}
