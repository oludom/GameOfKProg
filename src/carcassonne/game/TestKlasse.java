package carcassonne.game;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * 25.04.2017
 *
 * @author SWirries
 *         <p>
 *         This code is
 *         documentation enough
 */
public class TestKlasse extends Application{

//    public static void main(String[] args) {
//
//        /**
//         * Test der Logik ohne GUI
//         */
//
////        game.Spieler spieler1 = new game.Spieler("game.Spieler 1", game.FarbeT.BLAU);
////        game.Spieler spieler2 = new game.Spieler("game.Spieler 2", game.FarbeT.ROT);
//
////        System.out.println(spieler1);
////        System.out.println(spieler2);
//
////        ArrayList<game.Landschaftskarte> gelegtekarten = new ArrayList<>();
//
//        game.Stapel stapel = new game.Stapel();
//
////        game.Landschaftskarte start = stapel.generateLandschaftA();
////        game.Landschaftskarte la1 = stapel.generateLandschaftX();
////        System.out.println(la1);
////        game.Landschaftskarte la2 = generateLandschaftA();
//
////        start.addNeighbor(la1,game.HimmelsrichtungT.OST);
////        la2.addNeighbor(start, game.HimmelsrichtungT.SUED);
////        System.out.println("----------------------");
////
////        game.Landschaftskarte la3 = generateLandschaftI();
////        game.Landschaftskarte la4 = generateLandschaftG();
////        game.Landschaftskarte la5 = generateLandschaftN();
////        la3.addNeighbor(la4,game.HimmelsrichtungT.SUED);
////        la4.addNeighbor(la2,game.HimmelsrichtungT.SUED);
////        la4.addNeighbor(la5,game.HimmelsrichtungT.WEST);
//
//        /**
//         * Test Rotation
//         */
////        game.Landschaftskarte start = stapel.generateLandschaftD();
////        start.rotate();
////        start.rotate();
////        start.rotate();
////        start.rotate();
//
////        game.Landschaftskarte e = stapel.generateLandschaftE();
////        start.addNeighbor(e, game.HimmelsrichtungT.OST);
////        e.rotate(false,1);
////        System.out.println("-------------");
////        start.addNeighbor(e, game.HimmelsrichtungT.OST);
//
//        /**
//         * Test Strassen / game.Stadt abgeschlossen
//         */
//        //game.Strasse
////        game.Landschaftskarte start = stapel.generateLandschaftD();
////        game.Landschaftskarte a = stapel.generateLandschaftA();
////        game.Landschaftskarte l = stapel.generateLandschaftL();
////
////        start.addNeighbor(a, game.HimmelsrichtungT.NORD);
////        start.addNeighbor(l,game.HimmelsrichtungT.SUED);
////
////        start.getInformation();
////        a.getInformation();
////        l.getInformation();
//
//        //game.Stadt
////        game.Landschaftskarte start = stapel.generateLandschaftD();
////        game.Landschaftskarte c = stapel.generateLandschaftC();
////        game.Landschaftskarte h = stapel.generateLandschaftH();
////        game.Landschaftskarte e1 = stapel.generateLandschaftE();
////        game.Landschaftskarte e2 = stapel.generateLandschaftE();
////        game.Landschaftskarte e3 = stapel.generateLandschaftE();
////        game.Landschaftskarte f = stapel.generateLandschaftF();
////        game.Landschaftskarte n1 = stapel.generateLandschaftN();
////        game.Landschaftskarte n2 = stapel.generateLandschaftN();
////
////        start.addNeighbor(c, game.HimmelsrichtungT.OST);
////        e1.rotate(true, 2);
////        c.addNeighbor(e1, game.HimmelsrichtungT.NORD);//Oben
////        c.addNeighbor(e3, game.HimmelsrichtungT.SUED);//unten
////        c.addNeighbor(f, game.HimmelsrichtungT.OST);
//////
////        f.addNeighbor(n1, game.HimmelsrichtungT.OST);
//////        n1.getInformation();
//////        System.out.println("---");
////        n2.rotate(true, 2);
////        n2.addNeighbor(h, game.HimmelsrichtungT.OST);
//////        n2.getInformation();
////        n1.addNeighbor(n2, game.HimmelsrichtungT.NORD);
////        n2.getInformation();
//
//
//        /**
//         * Test game.Gefolgsmann setzen / brechnen
//         */
//        game.Spieler spieler1 = new game.Spieler("game.Spieler 1", game.FarbeT.BLAU);
//        game.Spieler spieler2 = new game.Spieler("game.Spieler 2", game.FarbeT.ROT);
//
//        //game.Stadt
////        game.Landschaftskarte start = stapel.generateLandschaftD();
////        game.Landschaftskarte c = stapel.generateLandschaftC();
////        game.Landschaftskarte h = stapel.generateLandschaftH();
////        game.Landschaftskarte e1 = stapel.generateLandschaftE();
////        game.Landschaftskarte e2 = stapel.generateLandschaftE();
////        game.Landschaftskarte e3 = stapel.generateLandschaftE();
////        game.Landschaftskarte f = stapel.generateLandschaftF();
////        game.Landschaftskarte n1 = stapel.generateLandschaftN();
////        game.Landschaftskarte n2 = stapel.generateLandschaftN();
////
////        //Mehre Gefolgsleute in einer game.Stadt
////        c.setGefolgsmann(spieler1.getFreienGeflogsmann());
////        n1.setGefolgsmann(spieler1.getFreienGeflogsmann());
////        n2.setGefolgsmann(spieler2.getFreienGeflogsmann());
////
////        start.addNeighbor(c, game.HimmelsrichtungT.OST);
////        e1.rotate(true, 2);
////        c.addNeighbor(e1, game.HimmelsrichtungT.NORD);//Oben
////        c.addNeighbor(e3, game.HimmelsrichtungT.SUED);//unten
////        c.addNeighbor(f, game.HimmelsrichtungT.OST);
//////
////        f.addNeighbor(n1, game.HimmelsrichtungT.OST);
////        n2.rotate(true, 2);
////        n2.addNeighbor(h, game.HimmelsrichtungT.OST);
////        n1.addNeighbor(n2, game.HimmelsrichtungT.NORD);
////        System.out.println(spieler1);
////        System.out.println(spieler2);
//
//        //game.Strasse
//        game.Landschaftskarte start = stapel.generateLandschaftD();
//        game.Landschaftskarte d = stapel.generateLandschaftD();
//        game.Landschaftskarte a1 = stapel.generateLandschaftA();
//        game.Landschaftskarte a2 = stapel.generateLandschaftA();
//        game.Landschaftskarte l = stapel.generateLandschaftL();
//
//        start.setGefolgsmann(spieler1.getFreienGeflogsmann());
//        start.addNeighbor(a1, game.HimmelsrichtungT.NORD);
////        a1.setGefolgsmann(spieler2.getFreienGeflogsmann());
//        start.addNeighbor(d,game.HimmelsrichtungT.SUED);
////        d.setGefolgsmann(spieler1.getFreienGeflogsmann());
//        a2.rotate(true,2);
//        d.addNeighbor(a2,game.HimmelsrichtungT.SUED);

//    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        /**
         * Test der Logik
         */

        primaryStage.show();

        Spieler spieler1 = new Spieler("game.Spieler 1", FarbeT.BLAU);
        Spieler spieler2 = new Spieler("game.Spieler 2", FarbeT.ROT);

//        System.out.println(spieler1);
//        System.out.println(spieler2);

//        ArrayList<game.Landschaftskarte> gelegtekarten = new ArrayList<>();

        Stapel stapel = new Stapel();

        /**
         * Welt
         */

        Landschaftskarte d = stapel.generateLandschaftD();
        Landschaftskarte e1 = stapel.generateLandschaftE();
        Landschaftskarte e2 = stapel.generateLandschaftE();
        Landschaftskarte e3 = stapel.generateLandschaftE();
        Landschaftskarte e4 = stapel.generateLandschaftE();
        Landschaftskarte n = stapel.generateLandschaftN();
        Landschaftskarte c = stapel.generateLandschaftC();
        Landschaftskarte f = stapel.generateLandschaftF();
        Landschaftskarte p = stapel.generateLandschaftP();
        Landschaftskarte w = stapel.generateLandschaftW();
        Landschaftskarte u1 = stapel.generateLandschaftU();
        Landschaftskarte u2 = stapel.generateLandschaftU();
        Landschaftskarte u3 = stapel.generateLandschaftU();
        Landschaftskarte a = stapel.generateLandschaftA();
        Landschaftskarte v1 = stapel.generateLandschaftV();
        Landschaftskarte v2 = stapel.generateLandschaftV();
        Landschaftskarte l = stapel.generateLandschaftL();
        Landschaftskarte i = stapel.generateLandschaftI();
        Landschaftskarte k = stapel.generateLandschaftK();

//        System.out.println("game.Stadt 1");
//        //game.Stadt 1
//        d.addNeighbor(c,HimmelsrichtungT.OST, true);
//        e1.rotate(true,2);
//        c.addNeighbor(e1,HimmelsrichtungT.NORD, true);
//        c.addNeighbor(e2, HimmelsrichtungT.SUED, true);
//        c.addNeighbor(f,HimmelsrichtungT.OST, true);
//        f.addNeighbor(p,HimmelsrichtungT.OST, true);
//        n.rotate(true,2);
//        p.addNeighbor(n, HimmelsrichtungT.NORD, true);
//        e3.rotate(false,1);
//        n.addNeighbor(e3,HimmelsrichtungT.OST, true);
//
//        System.out.println("\nStraße 1");
//        //Straße 1
//        d.addNeighbor(u1,HimmelsrichtungT.SUED, true);
//        u1.addNeighbor(l,HimmelsrichtungT.SUED, true);
//        u1.addNeighbor(e2,HimmelsrichtungT.OST, true);
//
//        System.out.println("\ngame.Kloster und Straße 2");
//        //game.Kloster und Straße 2
//        a.rotate(false,1);
//        e2.addNeighbor(a,HimmelsrichtungT.OST, true);
//        v1.rotate(true,1);
//        a.addNeighbor(v1,HimmelsrichtungT.OST, true);
//        v1.addNeighbor(p,HimmelsrichtungT.NORD, true);
//        //TODO P und v1 koennen nicht verbunden werden
//        p.getInformation();
//        v1.getInformation();
//        p.addNeighbor(w, HimmelsrichtungT.OST, true);
//        w.addNeighbor(e3, HimmelsrichtungT.NORD, true);
//
//        System.out.println("\nStraße 3");
//        //Straße 3
//        w.addNeighbor(u2, HimmelsrichtungT.SUED, true);
//        u2.addNeighbor(v1,HimmelsrichtungT.WEST, true);
//        u2.addNeighbor(k,HimmelsrichtungT.SUED, true);
//        u3.rotate(true,1);
//        k.addNeighbor(u3,HimmelsrichtungT.WEST, true);
//        v2.rotate(false,1);
//        u3.addNeighbor(v2,HimmelsrichtungT.WEST, true);
//        i.rotate(true,1);
//        v2.addNeighbor(i, HimmelsrichtungT.WEST, true);
//
//        System.out.println("game.Stadt 2");
//        //game.Stadt 2
//        l.addNeighbor(i,HimmelsrichtungT.OST, true);
//
//        System.out.println("game.Stadt 3");
//        //game.Stadt 3
//        e4.rotate(false,1);
//        k.addNeighbor(e4,HimmelsrichtungT.OST, true);



//        game.Landschaftskarte start = stapel.generateLandschaftA();
//        game.Landschaftskarte la1 = stapel.generateLandschaftX();
//        System.out.println(la1);
//        game.Landschaftskarte la2 = generateLandschaftA();

//        start.addNeighbor(la1,game.HimmelsrichtungT.OST);
//        la2.addNeighbor(start, game.HimmelsrichtungT.SUED);
//        System.out.println("----------------------");
//
//        game.Landschaftskarte la3 = generateLandschaftI();
//        game.Landschaftskarte la4 = generateLandschaftG();
//        game.Landschaftskarte la5 = generateLandschaftN();
//        la3.addNeighbor(la4,game.HimmelsrichtungT.SUED);
//        la4.addNeighbor(la2,game.HimmelsrichtungT.SUED);
//        la4.addNeighbor(la5,game.HimmelsrichtungT.WEST);

        /**
         * Test Rotation
         */
//        game.Landschaftskarte start = stapel.generateLandschaftD();
//        start.rotate();
//        start.rotate();
//        start.rotate();
//        start.rotate();

//        game.Landschaftskarte e = stapel.generateLandschaftE();
//        start.addNeighbor(e, game.HimmelsrichtungT.OST);
//        e.rotate(false,1);
//        System.out.println("-------------");
//        start.addNeighbor(e, game.HimmelsrichtungT.OST);

        /**
         * Test Strassen / game.Stadt abgeschlossen
         */
        //game.Strasse
//        game.Landschaftskarte start = stapel.generateLandschaftD();
//        game.Landschaftskarte a = stapel.generateLandschaftA();
//        game.Landschaftskarte l = stapel.generateLandschaftL();
//
//        start.addNeighbor(a, game.HimmelsrichtungT.NORD);
//        start.addNeighbor(l,game.HimmelsrichtungT.SUED);
//
//        start.getInformation();
//        a.getInformation();
//        l.getInformation();

        //game.Stadt
//        game.Landschaftskarte start = stapel.generateLandschaftD();
//        game.Landschaftskarte c = stapel.generateLandschaftC();
//        game.Landschaftskarte h = stapel.generateLandschaftH();
//        game.Landschaftskarte e1 = stapel.generateLandschaftE();
//        game.Landschaftskarte e2 = stapel.generateLandschaftE();
//        game.Landschaftskarte e3 = stapel.generateLandschaftE();
//        game.Landschaftskarte f = stapel.generateLandschaftF();
//        game.Landschaftskarte n1 = stapel.generateLandschaftN();
//        game.Landschaftskarte n2 = stapel.generateLandschaftN();
//
//        start.addNeighbor(c, game.HimmelsrichtungT.OST);
//        e1.rotate(true, 2);
//        c.addNeighbor(e1, game.HimmelsrichtungT.NORD);//Oben
//        c.addNeighbor(e3, game.HimmelsrichtungT.SUED);//unten
//        c.addNeighbor(f, game.HimmelsrichtungT.OST);
////
//        f.addNeighbor(n1, game.HimmelsrichtungT.OST);
////        n1.getInformation();
////        System.out.println("---");
//        n2.rotate(true, 2);
//        n2.addNeighbor(h, game.HimmelsrichtungT.OST);
////        n2.getInformation();
//        n1.addNeighbor(n2, game.HimmelsrichtungT.NORD);
//        n2.getInformation();


        /**
         * Test game.Gefolgsmann setzen / brechnen
         */


        //game.Stadt
//        game.Landschaftskarte start = stapel.generateLandschaftD();
//        game.Landschaftskarte c = stapel.generateLandschaftC();
//        game.Landschaftskarte h = stapel.generateLandschaftH();
//        game.Landschaftskarte e1 = stapel.generateLandschaftE();
//        game.Landschaftskarte e2 = stapel.generateLandschaftE();
//        game.Landschaftskarte e3 = stapel.generateLandschaftE();
//        game.Landschaftskarte f = stapel.generateLandschaftF();
//        game.Landschaftskarte n1 = stapel.generateLandschaftN();
//        game.Landschaftskarte n2 = stapel.generateLandschaftN();
//
//        //Mehre Gefolgsleute in einer game.Stadt
//        c.setGefolgsmann(spieler1.getFreienGeflogsmann());
//        n1.setGefolgsmann(spieler1.getFreienGeflogsmann());
//        n2.setGefolgsmann(spieler2.getFreienGeflogsmann());
//
//        start.addNeighbor(c, game.HimmelsrichtungT.OST);
//        e1.rotate(true, 2);
//        c.addNeighbor(e1, game.HimmelsrichtungT.NORD);//Oben
//        c.addNeighbor(e3, game.HimmelsrichtungT.SUED);//unten
//        c.addNeighbor(f, game.HimmelsrichtungT.OST);
////
//        f.addNeighbor(n1, game.HimmelsrichtungT.OST);
//        n2.rotate(true, 2);
//        n2.addNeighbor(h, game.HimmelsrichtungT.OST);
//        n1.addNeighbor(n2, game.HimmelsrichtungT.NORD);
//        System.out.println(spieler1);
//        System.out.println(spieler2);

        //game.Strasse
//        game.Landschaftskarte start = stapel.generateLandschaftD();
//        game.Landschaftskarte d = stapel.generateLandschaftD();
//        game.Landschaftskarte a1 = stapel.generateLandschaftA();
//        game.Landschaftskarte a2 = stapel.generateLandschaftA();
//        game.Landschaftskarte l = stapel.generateLandschaftL();
//
//        start.setGefolgsmann(spieler1.getFreienGeflogsmann());
//        start.addNeighbor(a1, game.HimmelsrichtungT.NORD);
//        a1.setGefolgsmann(spieler2.getFreienGeflogsmann());
//        start.addNeighbor(d,game.HimmelsrichtungT.SUED);
//        d.setGefolgsmann(spieler1.getFreienGeflogsmann());
//        a2.rotate(true,2);
//        d.addNeighbor(a2,game.HimmelsrichtungT.SUED);

        Landschaftskarte h = stapel.generateLandschaftH();
        h.addNeighbor(p,HimmelsrichtungT.OST, false);
        h.getInformation();
        p.getInformation();

    }
}