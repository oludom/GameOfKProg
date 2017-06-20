package carcassonne.game;

/**
 * 07.05.2017
 *
 * @author SWirries
 */
public enum HalbKantenT {
    NordWest,
    NordOst,
    OstNord,
    OstSued,
    SuedOst,
    SuedWest,
    WestSued,
    WestNord;

    public HalbKantenT next(){
        HalbKantenT halbKantenT = this;
        halbKantenT = HalbKantenT.values()[(halbKantenT.ordinal() +2)%8];
        return halbKantenT;
    }

    public HalbKantenT prev(){
        HalbKantenT halbKantenT = this;
        if(halbKantenT == NordWest) halbKantenT = HalbKantenT.values()[6];
        else if(halbKantenT == NordOst) halbKantenT = HalbKantenT.values()[7];
        else halbKantenT = HalbKantenT.values()[halbKantenT.ordinal() -2];
        return halbKantenT;
    }
}
