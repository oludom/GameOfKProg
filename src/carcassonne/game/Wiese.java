package carcassonne.game;

import java.util.*;

/**
 * 07.05.2017
 *
 * @author SWirries MHeiß
 */
public class Wiese {

    ArrayList<Wiesenstueck> wiesenstuecke = new ArrayList<>();

    boolean abgeschlossen = false;
    boolean besetzt = false;

    public Wiese() {
        //nur zur Vermeidung von NullPointerEx
    }

    public Wiese(ArrayList<Wiesenstueck> wiesenstuecke) {
        this.wiesenstuecke = wiesenstuecke;
    }

    public Wiese(Wiesenstueck wiesenstueck) {
        wiesenstuecke.add(wiesenstueck);
    }

    public void addWiesenstueck(Wiesenstueck wiesenstueck){
        wiesenstuecke.add(wiesenstueck);
    }

    public boolean removeWiesenstuecke(Wiesenstueck wiesenstueck){
        try {
            wiesenstuecke.remove(wiesenstueck);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean contains(Wiesenstueck wiesenstueck){
        return wiesenstuecke.contains(wiesenstueck);
    }

    public int getAnzahlWiesenstuecke(){
        return wiesenstuecke.size();
    }

    /**
     * Prüft ob die Wiese bestzt ist
     * @return
     */
    public boolean isBesetzt() {
        if(!besetzt){
            for(Wiesenstueck w : wiesenstuecke){
                boolean tempBesetzt = w.isBesetzt();
                if(tempBesetzt){
                    besetzt = true;
                    break;
                }

            }
        }
        return besetzt;
    }

    /**
     * Berechnet den Wert der Wiese anhande der abgeschlossenen Städte
     * @return
     */
    public int getTotalWert(){
        int totalWert = 0;
        Set<Stadt> staete = new HashSet<>();
        for(Wiesenstueck stueck : wiesenstuecke){
            Stadtteil[] teile = stueck.getAngrenzendeStadteile();
            if(teile != null){
                for(Stadtteil std : teile){
                    if(std.getStadt().isAbgeschlossen()) staete.add(std.getStadt());
                }
            }
        }
        totalWert = staete.size()*3;


        return totalWert;
    }

    public ArrayList<Wiesenstueck> getWiesenstuecke() {
        return wiesenstuecke;
    }

    /**
     * Gibt die Spiel die Punkte für die Wiese
     */
    public void setPlayerPoints(){
        /**
         * in der Endwertung aufrufen
         * für alle game.Spieler und Gefolgsleute
         * game.Spieler.getAllGefolgsleute = game.RolleT.BAUER
         * game.Gefolgsmann.getGebiet().setPlayerPoints <- alle Bauern auf der gleichen game.Wiese werden entfern bzw FREI
         */

        HashMap<Spieler, Integer> gefolgsmannAnzahl = new HashMap<>();
        ArrayList<Gefolgsmann> besetzer = new ArrayList<>();
        int maxCount = 0;
        for(Wiesenstueck stueck: wiesenstuecke){
            if(stueck.getBesetzer() != null){
                Spieler spieler = stueck.getBesetzer().getSpieler();
                besetzer.add(stueck.getBesetzer());
                int count = 0;

                if(gefolgsmannAnzahl.containsKey(spieler)){
                    count = gefolgsmannAnzahl.get(spieler);
                    count++;
                    gefolgsmannAnzahl.replace(spieler,count);
                }else{
                    count = 1;
                    gefolgsmannAnzahl.put(spieler,count);
                }
                if(maxCount < count) maxCount = count;
            }
        }
        int points = getTotalWert();
        for(Map.Entry entry : gefolgsmannAnzahl.entrySet()){
            if((int)entry.getValue() == maxCount) ((Spieler)entry.getKey()).addPunkte(points);
        }

        for (Gefolgsmann g : besetzer){
            g.setRolle(RolleT.FREI);
        }
    }

    public void setBesetzt(boolean besetzt) {
        this.besetzt = besetzt;
    }
}
