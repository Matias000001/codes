package fxkelmienkerho;

import java.util.ArrayList;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Tämä luokka hallinnoi tehtävän lisäys modal controlleria, mutta
 * muokkaaminen ei vielä toimi
 * 
 * @author Matias
 * @version 11.5.2024
 */

public class TehtavanlisaysController implements ModalControllerInterface<Tehtava>{

	@FXML private ChoiceBox<String> choiceBoxJasenTehtava;  
	@FXML private Button idLisaatehtavacancel; 				
	@FXML private Button idLisaatehtavaok; 					
	@FXML private TextField idTextLisaaTehtava; 		
	@FXML private BorderPane idValitsejasen;				
	
	private Tehtava tehtava;
	private Reissu reissuKohdalla;
	private ArrayList<Osallistuminen> o;	
	private Kerho kerho;


	@FXML 
	void handeValitseJasen(MouseEvent event) {
		ObservableList<String> nimi = FXCollections.observableArrayList();
		for (Osallistuminen o : kerho.haeOsallistumisetReissulle(reissuKohdalla.getReissuId())) { 
			Jasen jasen = kerho.haeJasen(o.getOmistajaJasen());
			String rivi = jasen.getNimi();
			nimi.add(rivi);
		}
		choiceBoxJasenTehtava.setItems(nimi);
	}

	@FXML 
	void handleLisaatehtava(ActionEvent event) {
	}
		
	@FXML 
	void handleLisaatehtavaok(ActionEvent event) {
		String teht = idTextLisaaTehtava.getText(); // Otetaan käyttäjän syöttämä tehtävä tekstikentästä
		if (teht.contains("|")) { Dialogs.showMessageDialog("Tehtava ei saa sisältää | -merkkiä !"); return; }
		String valittuJasen = choiceBoxJasenTehtava.getValue(); // Otetaan valittu jäsen valintalaatikosta
	    if ( valittuJasen == null ) {
	    	Dialogs.showMessageDialog("Valitse tehtävälle jäsen !"); // Ratkaisu siksi aikaa kunnen keksitään miten poistaa tehtävän jolla ei jäsentä
			return;
	    }
	   	int i = kerho.annaJasen(valittuJasen);
	   	tehtava.setvalittuJasen(i);	   
	    tehtava.setOmistajaReissu(reissuKohdalla.getReissuId());
	    tehtava.setNimi(teht);
	    
	    ModalController.closeStage(idValitsejasen);
	}

	@FXML 
	void handleLisaatehtevacancel(ActionEvent event) {
		ModalController.closeStage(idValitsejasen);
	}

	@Override
	public Tehtava getResult() {
		return tehtava;
	}
	
	@Override
	public void handleShown() {
	}
	
	@Override
	public void setDefault(Tehtava oletus) {
		this.tehtava = oletus;
		naytaTehtava(tehtava);
	}
	
	/**
	 * Näyttää tehtävät
	 * @param tehtava
	 */
	private void naytaTehtava(Tehtava tehtava) {
		idTextLisaaTehtava.setText(tehtava.getNimi());		
		ObservableList<String> nimi = FXCollections.observableArrayList();		
		choiceBoxJasenTehtava.setItems(nimi);
	}
	

	/**
	 * Asettaan kerhon ja reissun attribuutteihin
	 * @param kerho
	 * @param reissuKohdalla
	 */
	public void setKerho(Kerho oletus, Reissu reissuKohdalla) {
		this.kerho = oletus;
		this.reissuKohdalla = reissuKohdalla;
	}

	/**
	 * Tehtävän muokkaus
	 * @param modalityStage
	 * @param oletus
	 * @param kentta
	 * @return
	 */
	public static Tehtava kysyTehtava(Stage modalityStage, Tehtava oletus, int kentta) {
		return ModalController.<Tehtava, TehtavanlisaysController>showModal(
				TehtavanlisaysController.class.getResource("tehtavan_lisays_ikkuna.fxml"), "Kerho", modalityStage, oletus,
				ctrl -> ctrl.setKentta(kentta));
	}

	Object setKentta(int kentta) {
		return null;
	}
	
}

