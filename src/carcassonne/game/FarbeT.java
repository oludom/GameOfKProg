package carcassonne.game;

/**
 * 07.05.2017
 *
 * @author SWirries
 */
public enum FarbeT {
    BLAU("036BB6"),
    GELB("FFD800"),
    GRUEN("07A458"),
    ROT("E7172B"),
    SCHWARZ("24373B");

    private String color;

    FarbeT(String color){
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}