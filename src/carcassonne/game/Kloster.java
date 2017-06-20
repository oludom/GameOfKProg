package carcassonne.game;

/**
 * 07.05.2017
 *
 * @author SWirries
 */
public class Kloster extends Landschaftsteil {

    int filledFields = 0;

    private void setPlayerPoints(){
            if(besetzer != null){
                besetzer.getSpieler().addPunkte(getWert());
                besetzer.setRolle(RolleT.FREI);
            }
    }

    public void addFillFreeField(){
        if(filledFields <8) filledFields++;
        if (filledFields == 8) setPlayerPoints();
    }

    public void checkAbgeschlossen(){
        if(filledFields == 8){
//                besetzer.getSpieler().addPunkte(getWert());
//                besetzer.setRolle(RolleT.FREI);
            setPlayerPoints();
        }
    }

    @Override
    public int getWert() {
        return wert * filledFields +1;
    }

    @Override
    public String toString() {
        return "Kloster";
    }

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
