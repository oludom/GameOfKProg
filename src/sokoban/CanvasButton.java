package sokoban;

import javafx.scene.image.Image;

/**
 * @author MHeiß SWirries
 *
 */
public class CanvasButton {

    private Image image;
    private Image hover;
    private double x,y,width,height;
    String name;

    public CanvasButton(double x, double y, double w, double h, String image, String hover, String name){
        this.image = new Image(image);
        this.hover = new Image(hover);
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.name = name;
    }

    public boolean isInside(double x, double y){
        if(x > this.x && x < (this.x + this.width) && y > this.y && y < (this.y + this.width)){
            return true;
        }else{
            return false;
        }
    }

    public Image getImage() {
        return image;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public String getName() {
        return name;
    }

    public Image getHover() {
        return hover;
    }
}
