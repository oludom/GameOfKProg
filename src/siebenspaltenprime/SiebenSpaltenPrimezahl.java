/**
 * 
 */
package siebenspaltenprime;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
/**
 * @author SWirries MHeiß
 *
 * UI der Siebenspaltenprimezahl
 */
public class SiebenSpaltenPrimezahl extends AnchorPane{

	private AnchorPane anchorPane = this;
	private ObservableList<String> primeList = FXCollections.observableArrayList();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Primer[] aPrimer = new Primer[7];
		for(int k = 0;k < aPrimer.length; k++){
			
			aPrimer[k] = new Primer(2,k);
		}
		for (int i=3; i <= 6000; i++){
			for(Primer element : aPrimer){
				element.send(i);
			}
		} // weitere
		for(Primer element : aPrimer){
			element.send(0);
		}
	}

	public SiebenSpaltenPrimezahl() {
		ListView listView = new ListView();
		listView.setItems(primeList);
		anchorPane.getChildren().add(listView);

		Primer[] aPrimer = new Primer[7];
		for(int k = 0;k < aPrimer.length; k++){

			aPrimer[k] = new Primer(2,k, this);
		}
		for (int i=3; i <= 6000; i++){
			for(Primer element : aPrimer){
				element.send(i);
			}
		} // weitere
		for(Primer element : aPrimer){
			element.send(0);
		}
	}

	public void addPrime(String item){
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				primeList.add(item);
			}
		});
	}


}
