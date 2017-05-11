/**
 * 
 */
package regenbogen;

import java.awt.Button;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author SWirries
 *
 */
public class RegenbogenAlt extends Frame implements ActionListener{

	private static final long serialVersionUID = 1L;

	String[] regenbogenColors = {"red", "orange", "yellow", "green", "cyan", "blue", "magenta"};
	/**
	 * 
	 * @param args
	 * Methode zum Starten des Programms
	 * erstellt eine Fenster in der Mitte des Bildschirms mit der Groesse 640x480
	 * Wenn ein Array mit der Laenge 2 uebergeben wird, oeffnet sich ein Fenster, das Automatisch die Farben wechselt
	 */
	public static void main(String[] args) {
		
		Frame frame= new RegenbogenAlt();
		if(args.length == 2){
			frame= new RegenbogenAlt("Thread");//Auomatischer Farbwechsel
		}else{
			frame= new RegenbogenAlt();//Start-Fenster
		}
		
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
	 * 1. Konstuktor der Klasse RegenbogenAlt
	 * fuegt dem Fenster einen Button NEW hinzu
	 */
	public RegenbogenAlt(){
		Button btn = new Button("NEW");
		btn.addActionListener(this);
		btn.setActionCommand("NEW");
		add(btn);
	}
	
	/**
	 * 2. Konstuktor der Klasse RegenbogenAlt
	 * erstellt einen Thread, der die Hintergrundfarbe jede Sekunde aendert
	 */
	public RegenbogenAlt(String s){
		new Thread(){
			public void run(){
				while(true){
					try {
						sleep(1000);//Thread schläft 1000 ms = 1 sek
					} catch (InterruptedException e) {
						
						e.printStackTrace();
					}
					setBackground(ClonesAlt.changeColor(regenbogenColors[++ClonesAlt.colorNumber%regenbogenColors.length]));
				}
				
			}
		}.start();
	}

	/**
	 * Methode die Ausgefuehrt wird wenn ein Button benutzt wird
	 * Ruft die Main-Methode der Klasse mit einem leeren Array der Laenge 2 und erzeugt dadurch ein
	 * Fenster mit wechselnder Farbe
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		RegenbogenAlt.main(new String[2]);
		
	}

}
