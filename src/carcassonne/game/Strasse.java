package carcassonne.game;

import java.util.*;

/**
 * 07.05.2017
 *
 * @author SWirries MHeiß
 */
public class Strasse {

    ArrayList<Strassenabschnitt> strassenabschnitte = new ArrayList<>();
    boolean abgeschlossen = false;
    boolean besetzt = false;

    public Strasse() {
        //nur zur Vermeidung von NullPointerEx
    }

    public Strasse(ArrayList<Strassenabschnitt> strassenabschnitte) {
        this.strassenabschnitte = strassenabschnitte;
    }

    public Strasse(Strassenabschnitt strassenabschnitt) {
        strassenabschnitte.add(strassenabschnitt);
    }

    /**
     * Fügt eine Strassenabschnitt der Straße hinzu
     * @param strassenabschnitt
     */
    public void addStrassenabschnitt(Strassenabschnitt strassenabschnitt){
        strassenabschnitte.add(strassenabschnitt);
        checkAbgeschlossen();
    }

    public boolean removeStrassenabschnitt(Strassenabschnitt strassenabschnitt){
        try {
            strassenabschnitte.remove(strassenabschnitt);
            return true;
        }catch (Exception e){

        }
        return false;
    }

    public int getAnzahlStrassenabschnitte(){
        return strassenabschnitte.size();
    }

    /**
     * Berechnet den Wert der Straße
     * @return
     */
    public int getTotalWert(){
        int totalWert = 0;
        Set<Landschaftskarte> landschaftskarteSet = new HashSet<>();
        for(Strassenabschnitt s: strassenabschnitte){
            if(!landschaftskarteSet.contains(s.getLandschaftskarte())) {
                landschaftskarteSet.add(s.getLandschaftskarte());
                totalWert += s.getWert();
            }
        }
        return totalWert;
    }

    /**
     * Prüft ob die Straße besetzt ist
     * @return
     */
    public boolean isBesetzt() {
        if(!besetzt){
            for(Strassenabschnitt s: strassenabschnitte){
                boolean tempBesetzt = s.isBesetzt();
                if(tempBesetzt){
                    besetzt = true;
                    break;
                }
            }
        }
        return besetzt;
    }

    /**
     * Prüft ob die Straße abgschlossen ist
     */
    public void checkAbgeschlossen(){
        int stopCount = 0;
        for(Strassenabschnitt str : strassenabschnitte){
            boolean hasStop =  Arrays.asList(str.getHimmelsrichtungenT()).indexOf(HimmelsrichtungT.STOP) >=0;
            if (hasStop) stopCount++;
            if (stopCount >= 2) {
                abgeschlossen = true;
                setPlayerPoints();
                break;
            }
        }

    }

    /**
     * Gibt die Spieler die Punkte für die Straße
     */
    public void setPlayerPoints(){
            HashMap<Spieler, Integer> gefolgsmannAnzahl = new HashMap<>();
            ArrayList<Gefolgsmann> besetzer = new ArrayList<>();
            int maxCount = 0;
            for(Strassenabschnitt str: strassenabschnitte){
                if(str.getBesetzer() != null){
                    Spieler spieler = str.getBesetzer().getSpieler();
                    besetzer.add(str.getBesetzer());
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

    public ArrayList<Strassenabschnitt> getStrassenabschnitte() {
        return strassenabschnitte;
    }

    public boolean contains(Strassenabschnitt strassenabschnitt){
        return strassenabschnitte.contains(strassenabschnitt);
    }

    public boolean isAbgeschlossen() {
        return abgeschlossen;
    }
}
