package carcassonne.game;

/**
 * 07.05.2017
 *
 * @author SWirries
 */
public class Stadtteil extends Landschaftsteil {
    boolean wappen = false;
    HimmelsrichtungT[] offeneKanten;
    Stadt stadt;

    public Stadtteil(boolean wappen, HimmelsrichtungT[] offeneKanten) {
        this.wappen = wappen;
        this.offeneKanten = offeneKanten;
        stadt = new Stadt(this);
    }

    @Override
    public int getWert() {
        if(wappen){
            return 2;
        }
        return super.getWert();
    }

    public Stadt getStadt() {
        return stadt;
    }

    public void setStadt(Stadt stadt) {
        this.stadt = stadt;
    }

    public HimmelsrichtungT[] getOffeneKanten() {
        return offeneKanten;
    }

    public void rotate(boolean direction){
        for(int i = 0; i<offeneKanten.length;i++){
            offeneKanten[i] = direction ? offeneKanten[i].next() : offeneKanten[i].prev();
        }
        stadt.replaceOffeneKanten(offeneKanten);
    }

    @Override
    public void setBesetzer(Gefolgsmann besetzer) {
        super.setBesetzer(besetzer);
        besetzer.setRolle(RolleT.RITTER);
    }

    @Override
    public void sammlePoints() {
        stadt.setPlayerPoints();
    }

    @Override
    public String toString() {
        String himmelsRichtung = "{";
        for(HimmelsrichtungT himmelsrichtungT : offeneKanten){
            himmelsRichtung += " " + himmelsrichtungT;
        }
        himmelsRichtung += "}";
        return "Stadtteil " + himmelsRichtung;
    }
}
