package fxkelmienkerho;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import fxkelmienkerho.Reissu;

/**
 * Tämä luokka hallinnoi reissujen lisäys controlleria
 * 
 * @author Matias
 * @version 11.5.2024
 */

public class ReissunlisaysController implements ModalControllerInterface<Kerho>,Initializable {

    @FXML private Button idCancel;
    @FXML private Button idLisaaOsallistuja;
    @FXML private ChoiceBox<String> idOsallistujat;
    @FXML private Button idReissunlisaysok;
    @FXML private TextField idAjankohta;
    @FXML private TextField idKuvat;
    @FXML private TextField idReissunNimi;
    @FXML private TextField idSijainti;
    private String reissunNimi2 = "-";
    
    private Reissu reissu;
    private Kerho kerho;

    
    @FXML 
    void handleCancel(ActionEvent event) {
    	ModalController.closeStage(idCancel);
    }
    
    /**
     * Laittaa kerhon jäsenet ChoiceBoxiin
     * @param event
     */
    @FXML
    void handleOsallistuja(MouseEvent event) {
    	ObservableList<String> nimi = FXCollections.observableArrayList();
    	for ( int i = 0; i < kerho.annaJasenPituus();i++ ) {
    		String apu = kerho.annaJasen(i).getNimi(); 
    		nimi.add(apu);
    	}
    	idOsallistujat.setItems(nimi);
    }
    
	/**
	 * Lisää osallistujan reissulle
	 * @param event
	 */
	@FXML 
	void handleLisaaOsallistuja(ActionEvent event) {
		String valittu = idOsallistujat.getSelectionModel().getSelectedItem(); 
		if (valittu == null) {
			Dialogs.showMessageDialog("Ei valittua jäsentä, yritä uudelleen");
			return;
		}
		int id = kerho.annaJasen(valittu);
		this.reissu = kerho.getReissuKohdalla(); 
		int reissuId = reissu.getReissuId(); 
		Boolean onko = kerho.onkoJoReissulla(reissuId, id);
		if (onko)  {
			Dialogs.showMessageDialog("Valittu jäsen on jo reissulla!");
			return;
		}
		
		Osallistuminen o = new Osallistuminen(reissu.getReissuId(),id);
		kerho.lisaaOsallistuminen(o);
		o.rekisteroi();
    }

	/**
	 * Hyväksyy reissun
	 * @param event
	 */
	@FXML 
	void handleReissunlisaysok(ActionEvent event) {
    	lisaaUusiReissu(); 
    	ModalController.closeStage(idCancel);
    }
	
    private void lisaaUusiReissu() {
        String reissunNimi = idReissunNimi.getText();
        if (reissunNimi == null) { Dialogs.showMessageDialog("Reissun nimi ei voi olla tyhjä"); return; }
    	if (reissunNimi.contains("|")) { Dialogs.showMessageDialog("Nimi ei saa sisältää | -merkkiä"); return; }
    	
    	
    	reissu.setReissunNimi(reissunNimi);
    	
    	String ajankohta = idAjankohta.getText();
    	if (ajankohta.contains("|")) { Dialogs.showMessageDialog("Ajankohta ei saa sisältää | -merkkiä"); return; }
    	reissu.setAjankohta(ajankohta);
    	
        String sijainti = idSijainti.getText();
        if (sijainti.contains("|")) { Dialogs.showMessageDialog("Sijainti ei saa sisältää | -merkkiä"); return; }
        reissu.setSijainti(sijainti);
        
        String kuvat = idKuvat.getText();       
        if (kuvat.contains("|")) { Dialogs.showMessageDialog("Kuvat ei saa sisältää | -merkkiä"); return; } 
        reissu.setKuvat(kuvat);    
    }
    
	@Override
	public Kerho getResult() {
		return kerho;
	}
	
	private void naytaReissu(Reissu oletus) {
		if (oletus == null) return;
		idReissunNimi.setText(oletus.getReissuNimi());
		idAjankohta.setText(oletus.getAjankohta());
		idSijainti.setText(oletus.getSijainti());
		idKuvat.setText(oletus.getUrl());
	}
	
	
	@Override
	public void handleShown() {
	}
	
	@Override
	public void setDefault(Kerho oletus) {
		this.kerho = oletus;
		reissu = kerho.getReissuKohdalla();
		naytaReissu(reissu);
	}
	
	/**
	 * Tulla muokkaamaan reissua
	 * @param modalityStage
	 * @param oletus
	 * @param kerho
	 * @return
	 */
	public static Kerho kysyReissu(Stage modalityStage, Reissu oletus, Kerho kerho) {
		return ModalController.<Kerho, ReissunlisaysController>showModal(
			           ReissunlisaysController.class.getResource("reissun_lisays_ikkuna.fxml"),
			                 "Kerho", modalityStage, kerho, ctrl -> { ctrl.setKerho(kerho); });
	}
	

	private void setKerho(Kerho kerho) {
		this.kerho = kerho;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}



}
