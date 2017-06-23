package carcassonne.game;

/**
 * 07.05.2017
 *
 * @author MHeiß SWirries
 *
 */
public class Kloster extends Landschaftsteil {

    int filledFields = 0;

    /**
     * Gibt den Spieler die Punkt des Kloster
     */
    private void setPlayerPoints(){
            if(besetzer != null){
                besetzer.getSpieler().addPunkte(getWert());
                besetzer.setRolle(RolleT.FREI);
            }
    }

    /**
     * Erhöht die Zahl der belegten Felder um das Kloster
     */
    public void addFillFreeField(){
        if(filledFields <8) filledFields++;
        if (filledFields == 8) setPlayerPoints();
    }

    /**
     * Gibt dem Spieler die Punkte, wenn alle Felder um das Kloster gelegt sind
     */
    public void checkAbgeschlossen(){
        if(filledFields == 8){
            setPlayerPoints();
        }
    }

    /**
     * Gibt den Wert des Kloster mit den aktuellen Felder zurück
     * @return
     */
    @Override
    public int getWert() {
        return wert * filledFields +1;
    }

    @Override
    public String toString() {
        return "Kloster";
    }

    /**
     * Setzt den Gefolgsmann auf das Koster und legt seine Rolle fest
     * @param besetzer
     */
    @Override
    public void setBesetzer(Gefolgsmann besetzer) {
        super.setBesetzer(besetzer);
        besetzer.setRolle(RolleT.MOENCH);
    }

    @Override
    public void sammlePoints() {
        setPlayerPoints();
    }
}
