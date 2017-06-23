package carcassonne.game;

/**
 * 07.05.2017
 *
 * @author SWirries MHeiß
 * Superklasse aller Wiesenstücke, Kloster, Strassenabschnitte, Stadtteile
 */
public abstract class Landschaftsteil {

    int wert = 1;
    Gefolgsmann besetzer;
    boolean besetzt = false;
    Landschaftskarte landschaftskarte;
    public int getWert() {
        return wert;
    }

    public Gefolgsmann getBesetzer() {
        return besetzer;
    }

    public void setBesetzer(Gefolgsmann besetzer) {
        this.besetzer = besetzer;
        besetzer.setGebiet(this);
        besetzt = true;
    }

    public Landschaftskarte getLandschaftskarte(){
        return landschaftskarte;
    }

    public void setLandschaftskarte(Landschaftskarte landschaftskarte){
        this.landschaftskarte = landschaftskarte;
    }

    public boolean isBesetzt() {
        return besetzt;
    }

    public abstract void sammlePoints();
}
