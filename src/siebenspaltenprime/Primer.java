/**
 * 
 */
package siebenspaltenprime;

/**
 * @author SWirries
 * Ereugt Primezahlen für die Siebenspaltenprimezahl
 */
public class Primer extends Thread{
	
	private int p = 0;
	private Primer nextPrimer = null;
	private String tabChar = "\u0009";
	private String tablePos = "";
	private int tableIndex = 0;
	private SiebenSpaltenPrimezahl siebenSpaltenPrimezahl;
	
	Primer (int prime, int index, SiebenSpaltenPrimezahl siebenSpaltenPrimezahl){
		p = prime;
		tableIndex = index;
		genTab();
		this.siebenSpaltenPrimezahl = siebenSpaltenPrimezahl;
		this.start();
	}

	Primer (int prime, int index){
		p = prime;
		tableIndex = index;
		genTab();
		this.start();
	}
	// \u0009 Unicode Tab

	/**
	 * Erzeugt die Verschiebung
	 */
	private void genTab(){
		for(int i = 1; i <= tableIndex;i++){
			tablePos += tabChar;
		}
	}

	/**
	 * Fügt die Primezahl der UI hinzu
	 */
	protected void print(){
		if(tablePos.length() == 0){
			genTab();
		}
		if(siebenSpaltenPrimezahl != null){
			siebenSpaltenPrimezahl.addPrime(tablePos+p);
		}else {

		}

		
	}

	/**
	 * Run Methode des Threads
	 */
	public void run(){
		print();

		while (true) { // Endlos-Schleife
			int n = receive(); // Lese-Versuch
			if (n == 0) {// wenn n=0: Ende
				if (nextPrimer != null) nextPrimer.send(n);// auch von next
				break; // Ende while loop
			}
			if (n%p != 0) { // vielleicht prim
				if (nextPrimer != null){
					nextPrimer.send(n);// weiter testen
				}
				else{
					if(siebenSpaltenPrimezahl != null){
						nextPrimer = new Primer(n, tableIndex, siebenSpaltenPrimezahl); // Primzahl!
					}else {
						nextPrimer = new Primer(n, tableIndex); // Primzahl!
					}

				}
			} // sonst: n nicht prim
		}
	}
	
	private int buffer = -1; // Puffer zum Senden & Empfangen
	// wenn < 0: leer

	/**
	 * Sendet die Primezahl an die nächste Instanz wieter
	 * @param i
	 */
	synchronized void send(int i){ // Sperre erlangen
		try {
		while (buffer >= 0) wait();// warten bis Puffer frei
		buffer = i; // Puffer füllen
		notify(); // Empfänger benachrichtigen
		} catch (InterruptedException e) {
			
		}
	}

	/**
	 * Empfängt die Primezahl einer Instanz
	 * @return
	 */
	synchronized int receive(){ // Sperre erlangen
		int result = 0;
		try {
		while ((result=buffer)<0) wait();// warten bis Puffer
		// voll
		buffer = -1; // Puffer leeren
		notify(); // Sender
		// benachrichtigen
		} catch (InterruptedException e) {
			
		}
		return result;
	}
	
}
