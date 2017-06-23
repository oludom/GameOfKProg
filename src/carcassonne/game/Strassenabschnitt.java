package carcassonne.game;

/**
 * 07.05.2017
 *
 * @author SWirries MHeiß
 */
public class Strassenabschnitt extends Landschaftsteil {
    private HimmelsrichtungT startRichtung;
    private HimmelsrichtungT endRichtung;
    private Strasse strasse;

    public Strassenabschnitt(HimmelsrichtungT startRichtung, HimmelsrichtungT endRichtung) {
        this.startRichtung = startRichtung;
        this.endRichtung = endRichtung;
        strasse = new Strasse(this);
    }

    public Strasse getStrasse() {
        return strasse;
    }

    public HimmelsrichtungT getStartRichtung() {
        return startRichtung;
    }

    public HimmelsrichtungT getEndRichtung() {
        return endRichtung;
    }

    public HimmelsrichtungT[] getHimmelsrichtungenT(){
        return new HimmelsrichtungT[]{startRichtung,endRichtung};
    }

    public void setStrasse(Strasse strasse) {
        this.strasse = strasse;
    }

    /**
     * Rotiert die Straßenabschnitte
     * @param direction
     */
    public void rotate(boolean direction){
        startRichtung = direction ? startRichtung.next() : startRichtung.prev();
        endRichtung = direction ? endRichtung.next() : endRichtung.prev();
    }

    @Override
    public void setBesetzer(Gefolgsmann besetzer) {
        super.setBesetzer(besetzer);
        besetzer.setRolle(RolleT.WEGELAGERER);
    }

    @Override
    public void sammlePoints() {
        strasse.setPlayerPoints();
    }

    @Override
    public String toString() {
        String himmelsRichtung = "{";
        himmelsRichtung += " " + startRichtung;
        himmelsRichtung += " " + endRichtung;
        himmelsRichtung += "}";

        return "Stassenabschnitt " + himmelsRichtung;
    }

}
