package carcassonne.game;

/**
 * 07.05.2017
 *
 * @author SWirries MHieß
 *
 */
public class Wiesenstueck extends Landschaftsteil {
    HalbKantenT[] offeneHalbKanten;
    Stadtteil[] angrenzendeStadteile;
    Wiese wiese;

    public Wiese getWiese() {
        return wiese;
    }

    public void setWiese(Wiese wiese) {
        this.wiese = wiese;
    }

    public Wiesenstueck(HalbKantenT[] offeneHalbKanten, Stadtteil[] angrenzendeStadteile) {
        this.offeneHalbKanten = offeneHalbKanten;
        this.angrenzendeStadteile = angrenzendeStadteile;
        wiese = new Wiese(this);
    }

    public Stadtteil[] getAngrenzendeStadteile() {
        return angrenzendeStadteile;
    }

    public HalbKantenT[] getOffeneHalbKanten() {
        return offeneHalbKanten;
    }

    /**
     * Rotiert alle Kante des Wiesenstücks
     * @param direction
     */
    public void rotate(boolean direction){
        for(int i = 0; i<offeneHalbKanten.length;i++){
            offeneHalbKanten[i] = direction ? offeneHalbKanten[i].next() : offeneHalbKanten[i].prev();
        }
    }

    @Override
    public void setBesetzer(Gefolgsmann besetzer) {
        super.setBesetzer(besetzer);
        besetzer.setRolle(RolleT.BAUER);
        besetzer.setAusrichtung(AusrichtungT.LIEGEND);
    }

    /**
     * Berechnte die Punkte der Wiese und schreibt sie dem Spieler gut
     */
    @Override
    public void sammlePoints() {
        wiese.setPlayerPoints();
    }

    @Override
    public String toString() {
        String halbKanten = "{";
        for(HalbKantenT halbKantenT : offeneHalbKanten){
            halbKanten += " " + halbKantenT;
        }
        halbKanten += "}";
        return "Wiesenstueck " + halbKanten;
    }
}
