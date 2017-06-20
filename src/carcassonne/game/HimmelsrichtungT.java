package carcassonne.game;

/**
 * 07.05.2017
 *
 * @author SWirries
 */
public enum HimmelsrichtungT {
    NORD,
    OST,
    SUED,
    WEST,
    STOP;

    public HimmelsrichtungT next(){
        HimmelsrichtungT himmelsrichtungT = this;
        if(himmelsrichtungT != STOP){
            himmelsrichtungT = HimmelsrichtungT.values()[himmelsrichtungT.ordinal() +1];
            if(himmelsrichtungT == STOP){
                himmelsrichtungT = HimmelsrichtungT.values()[0];
            }
        }
        return himmelsrichtungT;
    }

    public HimmelsrichtungT prev(){
        HimmelsrichtungT himmelsrichtungT = this;
        if(himmelsrichtungT != STOP){
            if(himmelsrichtungT == NORD) himmelsrichtungT = HimmelsrichtungT.values()[3];
            else himmelsrichtungT = HimmelsrichtungT.values()[himmelsrichtungT.ordinal() -1];

            if(himmelsrichtungT == STOP){
                himmelsrichtungT = HimmelsrichtungT.values()[3];
            }
        }
        return himmelsrichtungT;
    }
}


