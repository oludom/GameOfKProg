/**
 * 
 */
package regenbogen;

import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author SWirries
 *
 */
public class ClonesAlt extends Frame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//String zur Erstellung der Farben
	public static String[] colors = {"black", "blue", "cyan", "gray", "green", "magenta", "orange", "pink",
			"red", "white", "yellow"};
	
	public static int colorNumber = 0; //Nummer der Farbe
	
	/**
	 * 
	 * @param args
	 * Methode zum Starten des Programms
	 * erstellt eine Fenster in der Mitte des Bildschirms mit der Groesse 640x480
	 */
	public static void main(String[] args) {
		
		Frame frame= new ClonesAlt();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		
		frame.setSize(640, 480);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		
	}
	
	/**
	 * Konstruktor der Klasse ClonesAlt
	 * erzeugt eine FlowLayout mit 2 Buttons NEW und CHANGE
	 * und legt die Hintergrundfarbe fest, je nach uebergebener colorNumber
	 */
	public ClonesAlt(){
		setLayout(new FlowLayout());
		
		Button btn = new Button("CHANGE");
		btn.addActionListener(this);
		btn.setActionCommand("CHANGE");
		add(btn);
		
		btn = new Button("NEW");
		btn.addActionListener(this);
		btn.setActionCommand("NEW");
		add(btn);
		
		setBackground(changeColor(colors[colorNumber%colors.length]));
		
	}
	
	/**
	 * 
	 * @param color
	 * @return Color
	 * Methode zum Umwandeln der Farbennamen in Farben
	 * 
	 */
	public static Color changeColor(String color) {
		
		switch(color){
		case "black":
			return (Color.BLACK);
			
		case "blue":
			return (Color.BLUE);
						
		case "cyan":
			return (Color.CYAN);
						
		case "gray":
			return (Color.GRAY);
		
		case "green":
			return (Color.GREEN);
			
		case "magenta":
			return (Color.MAGENTA);
			
		case "orange":
			return (Color.ORANGE);
			
		case "pink":
			return (Color.PINK);
			
		case "red":
			return (Color.RED);
			
		case "white":
			return (Color.WHITE);
			
		case "yellow":
			return (Color.YELLOW);
			
		default:
			return Color.black;
			
		}
		
		
	}
	
	/**
	 * Methode die Ausgefuehrt wird wenn ein Button benutzt wird.
	 * Beim Button NEW wird die Main-Method erneut ausgeführt
	 * Beim Button CHANGE wird die Hintergrundfarbe geändert
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("NEW")){
			ClonesAlt.main(new String[0]);
		}else{
			setBackground(changeColor(colors[++colorNumber%colors.length]));
		}
		
	}

}
