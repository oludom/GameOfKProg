package regenbogen;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

/**
 * 11.05.2017
 *
 * @author SWirries
 */
public class Regenbogen extends AnchorPane{

    //String zur Erstellung der Farben
    public static String[] colors = {"black", "blue", "cyan", "gray", "green", "magenta", "orange", "pink",
            "red", "white", "yellow"};
    public static int colorNumber = 0;

    AnchorPane anchorPane = this;

    public Regenbogen() {
        new Thread(){
            public void run(){
                while(true){
                    try {
                        sleep(1000);//Thread schläft 1000 ms = 1 sek
                    } catch (InterruptedException e) {

                        e.printStackTrace();
                    }
//                    setBackground(ClonesAlt.changeColor(regenbogenColors[++ClonesAlt.colorNumber%regenbogenColors.length]));
                    anchorPane.setStyle("-fx-background-color:"+toHex(changeColor(colors[++colorNumber%colors.length])));
                }

            }
        }.start();
    }

    public static Color changeColor(String color) {

        switch(color){
            case "black":
                return (Color.BLACK);

            case "blue":
                return (Color.BLUE);

            case "cyan":
                return (Color.CYAN);

            case "gray":
                return (Color.GRAY);

            case "green":
                return (Color.GREEN);

            case "magenta":
                return (Color.MAGENTA);

            case "orange":
                return (Color.ORANGE);

            case "pink":
                return (Color.PINK);

            case "red":
                return (Color.RED);

            case "white":
                return (Color.WHITE);

            case "yellow":
                return (Color.YELLOW);

            default:
                return Color.BLACK;

        }

//        pickedColor = String.format("#%02X%02X%02X", (int)(c.getRed()*255),
//                (int)(c.getGreen()*255), (int)(c.getBlue()*255));
//        series.nodeProperty().get().setStyle("-fx-stroke-width: "+strokeWidth+"px; -fx-stroke: "+pickedColor);

    }

    private String toHex(Color c){
        return String.format("#%02X%02X%02X", (int)(c.getRed()*255),
                (int)(c.getGreen()*255), (int)(c.getBlue()*255));
    }
}
