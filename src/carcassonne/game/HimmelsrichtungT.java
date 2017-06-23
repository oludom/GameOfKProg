package carcassonne.game;

/**
 * 07.05.2017
 *
 * @author MHeiß SWirries
 * Ausrichtung/Position der Straßen und Städte
 */
public enum HimmelsrichtungT {
    NORD,
    OST,
    SUED,
    WEST,
    STOP;

    /**
     * Rotation mit dem Uhrzeigersinn
     * @return
     */
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

    /**
     * Rotation gegen den Uhrzeigersinn
     * @return
     */
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


