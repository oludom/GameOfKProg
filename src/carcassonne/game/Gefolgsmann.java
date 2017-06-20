package carcassonne.game;

import javafx.geometry.Point2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.*;


/**
 * 07.05.2017
 *
 * @author SWirries
 */
public class Gefolgsmann {
    int id;
    FarbeT farbe;
    RolleT rolle;
    AusrichtungT ausrichtung;
    Spieler spieler;
    Landschaftsteil gebiet;
    Point2D absolutePosition;
    private Image image ;//= new Image("images/Mann_Blau.png",false);
    private static final int TOLERANCE_THRESHOLD = 0xCF;

    public Gefolgsmann(int id, FarbeT farbe, Spieler spieler){
        this.id = id;
        this.farbe = farbe;
        this.spieler = spieler;

        rolle = RolleT.FREI;
        ausrichtung = AusrichtungT.STEHEND;
        init();
    }

    public Gefolgsmann(int id, FarbeT farbe, Spieler spieler, RolleT rolle, AusrichtungT ausrichtung){
        this.id = id;
        this.farbe = farbe;
        this.spieler = spieler;

        this.rolle = rolle;
        this.ausrichtung = ausrichtung;
        init();
    }

    private void init(){
        switch (farbe){
            case ROT:
                image = Stapel.mannRot;
                break;
            case BLAU:
                image = Stapel.mannBlau;
                break;
            case GELB:
                image = Stapel.mannGelb;
                break;
            case GRUEN:
                image = Stapel.mannGruen;
                break;
            case SCHWARZ:
                image = Stapel.mannSchwarz;
                break;
        }
    }

    public int getId() {
        return id;
    }

    public FarbeT getFarbe() {
        return farbe;
    }

    public Spieler getSpieler() {
        return spieler;
    }

    public RolleT getRolle() {
        return rolle;
    }

    public void setRolle(RolleT rolle) {
        this.rolle = rolle;
        if(rolle == RolleT.FREI){
            gebiet = null;
            ausrichtung = AusrichtungT.STEHEND;
        }
        if(rolle == RolleT.BAUER){
            ausrichtung = AusrichtungT.LIEGEND;
        }
    }

    public void setAbsolutePosition(Point2D absolutePosition) {
        this.absolutePosition = absolutePosition;
    }

    public Point2D getAbsolutePosition() {
        return absolutePosition;
    }

    public Image getImage() {
        int rotationDegree = 0;
        ImageView imageView = new ImageView(image);
        if(ausrichtung == AusrichtungT.LIEGEND){
            rotationDegree = 90;
        }else{
            rotationDegree = 0;
        }
        imageView.setRotate(rotationDegree);


        return makeTransparent(imageView.snapshot(new SnapshotParameters(),null));
    }

    public AusrichtungT getAusrichtung() {
        return ausrichtung;
    }

    public void setAusrichtung(AusrichtungT ausrichtung) {
        this.ausrichtung = ausrichtung;
    }

    public Landschaftsteil getGebiet() {
        return gebiet;
    }

    public void setGebiet(Landschaftsteil gebiet) {
        this.gebiet = gebiet;
//        gebiet.setBesetzer(this);
    }

    private Image makeTransparent(Image inputImage) {
        int W = (int) inputImage.getWidth();
        int H = (int) inputImage.getHeight();
        WritableImage outputImage = new WritableImage(W, H);
        PixelReader reader = inputImage.getPixelReader();
        PixelWriter writer = outputImage.getPixelWriter();
        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {
                int argb = reader.getArgb(x, y);

                int r = (argb >> 16) & 0xFF;
                int g = (argb >> 8) & 0xFF;
                int b = argb & 0xFF;

                if (r >= TOLERANCE_THRESHOLD
                        && g >= TOLERANCE_THRESHOLD
                        && b >= TOLERANCE_THRESHOLD) {
                    argb &= 0x00FFFFFF;
                }

                writer.setArgb(x, y, argb);
            }
        }

        return outputImage;
    }
}
