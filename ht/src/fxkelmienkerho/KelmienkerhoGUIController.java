package fxkelmienkerho;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Chooser;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.fxgui.StringGrid;
import fi.jyu.mit.fxgui.StringGrid.GridRowItem;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TextField;

/**
 * Pääikkuna rakenteen controlleri luokka. Sisältää siis Reissut, Jäsenet,
 * Tehtävät ja Ohjeet ikkunoiden hallinnan
 * 
 * @author Matias
 * @version 11.5.2024
 */
public class KelmienkerhoGUIController implements Initializable, ModalControllerInterface<Kerho> {

	private Kerho kerho;
	private Reissu reissuKohdalla;
	private Reissu reissuKohdalla2;
	private String kerhonnimi = "Kelmienkerho";

	// Reissut sivu
	@FXML private ListChooser<Reissu> idValitseReissu;
	@FXML private Label labelVirhe;
	@FXML private TextField idReissunAjankohta;
	@FXML private TextField idReissunNimi;
	@FXML private TextField idReissunSijainti;
	@FXML private Button idValitsereissu;
	@FXML private Button idLisaareissu;
	@FXML private Button idMuokkaareissu;
	@FXML private Button IdPoistareissu;
	@FXML private Button idSuljereissu;
    @FXML private Button idPoistajasenreissulta;
	@FXML private StringGrid<Osallistuminen> gridReissut;

	// Jäsenet sivu
	@FXML private Jasen jasenKohdalla;
	@FXML private StringGrid<Jasen> gridJasenet;
	@FXML private Button idUusijasen;
	@FXML private Button idMuokkaajasen;
	@FXML private Button idSuljejasen;
	@FXML private Button idPoistajasen;
    @FXML private TextField hakuehto;

	// Tehtävälista sivu
	@FXML private ListChooser<Reissu> idValitseReissu2;
	@FXML private StringGrid<Tehtava> gridTehtavat = new StringGrid<>();
	@FXML private Button idUusitehtava;
	@FXML private Button idMuokkaatehtava;
	@FXML private Button idPoistatehtava;
	@FXML private Button idSuljetehtava;
	@FXML private TextField idTextLisaaTehtava; 


	/**
	 * Tekee tarvittavat alustukset
	 */
	private void alusta() {
		idValitseReissu.addSelectionListener(e -> valitseReissu());
		idValitseReissu2.addSelectionListener(e -> valitseReissuTehtavat());
	}

	/**
	 * Asettaa kerho attribuuttiin ja hakee tiedot muistista
	 *  @param kerho Kerho jota käytetään tässä käyttöliittymässä
	 */
	public void setKerho(Kerho kerho) {
		this.kerho = kerho;
		lueTiedosto("data");
	}

	public Kerho getKerho() {
		return kerho;
	}

	
	/**
	 * Alustaa kerhon lukemalla tiedostosta
	 * @param tiedosto josta kerhon tiedot luetaan
	 */
	protected String lueTiedosto(String nimi) {
		kerhonnimi = nimi;
		try {
			kerho.lueTiedostosta(nimi);
			hae(0);
			haeReissut();
			return null;
		} catch (SailoException e) {
			hae(0);
			String virhe = e.getMessage();
			if (virhe != null)
				Dialogs.showMessageDialog(virhe);
			return virhe;
		}
	}

	/**
	 * Kerhon tietojen tallennus
	 */
	private void tallenna() {
		try {
			kerho.tallenna();
		} catch (SailoException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tarkistetaan onko tallennus tehty
	 * @return true jos saa sulkea sovelluksen, false jos ei
	 */
	public boolean voikoSulkea() {
		tallenna();
		return true;
	}

 // =====================   Jäsenen juttuja   ======================   
	
	/**
	 * Hakee jäsenten tiedot listaan
	 * @param jnro jäsenen numero, joka aktivoidaan haun jälkeen
	 */
	protected void hae(int jnro) {
		gridJasenet.clear();
		for (int i = 0; i < kerho.getJasenia(); i++) {
			Jasen jasen = kerho.annaJasen(i);
			String[] rivi = { jasen.getNimi(), jasen.getAlias(), jasen.getOsoite(), jasen.getPostinumero(),
					jasen.getPaikkakunta() };
			gridJasenet.add(jasen, rivi);
		}
	}

	// Ei käytössä
	private void setTitle(String title) {
        ModalController.getStage(hakuehto).setTitle(title);
	}
	
	/**
	 * Luo uuden jäsenen
	 */
	protected void uusiJasen() {
		Jasen uusi = new Jasen();
		uusi = ModalController.<Jasen, JasenlisaysController>showModal(
				KelmienkerhoGUIController.class.getResource("jasen_lisays_ikkuna.fxml"), "Jäsenet", null, uusi,
				ctrl -> ctrl.setKerho(kerho));
		if (uusi == null)
			return;
		uusi.rekisteroi();
		try {
			kerho.lisaaJasen(uusi);
			hae(uusi.getTunnusNro());
		} catch (SailoException e) {
			Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
			return;
		}
	}

	/**
	 * Muokkaa jäsenen tietoja
	 */
	private void muokkaaJasen() {
		GridRowItem<Jasen> jasenKohdalla = gridJasenet.getSelectionModel().getSelectedItem();
		if (jasenKohdalla == null) {
			Dialogs.showMessageDialog("Valitse jäsen!");
			return;
		}
		Jasen valittuJasen = jasenKohdalla.getItem();
		try {
			Jasen jasen;
			jasen = JasenlisaysController.kysyJasen(null, valittuJasen.clone(), 0);
			if (jasen == null) return;
			kerho.korvaaTaiLisaa(jasen);
			tallenna();
			hae(jasen.getTunnusNro());
		} catch (CloneNotSupportedException e) {
		} catch (SailoException e) {
			Dialogs.showMessageDialog(e.getMessage());
		}
	}

	/**
	 * Poistaa jäsenen kerhosta
	 */
	private void poistaJasen() {
		GridRowItem<Jasen> jasenKohdalla;
		Jasen valittuJasen;
		if ( this.jasenKohdalla == null) {
			jasenKohdalla = gridJasenet.getSelectionModel().getSelectedItem();
			valittuJasen = jasenKohdalla.getItem();
		}
		else {
			valittuJasen = this.jasenKohdalla;
		}
		if ( valittuJasen == null ) return;
		if ( !Dialogs.showQuestionDialog("Poisto", "Poistetaanko jäsen: " + valittuJasen.getNimi(), "Kyllä", "Ei") )
		 		return;
		 kerho.poista(valittuJasen);
		 hae(0);
	}
	
	/**
	 * Hakee hakulaatikkoon syötettyjen merkkien perusteella jäsenet gridiin
	 */
	private void haeJasen() {
		gridJasenet.clear();
		String hae = hakuehto.getText();  
		List<Jasen> loytyneet = kerho.etsi(hae); 
		gridJasenet.clear(); 
	        for (Jasen j : loytyneet) { 
	            gridJasenet.add(j, j.getNimi(),j.getAlias(), j.getOsoite(), j.getPostinumero(), j.getPaikkakunta() );  
	        }
	}
	
 //  =======================  TEHTÄVÄLISTA SIVU ===============================

	/**
	 * Valitsee reissun Tehtävälista sivulla
	 */
	private void valitseReissuTehtavat() {
		Reissu r = idValitseReissu2.getSelectedObject();
		this.reissuKohdalla2 = r;
		this.reissuKohdalla = r;
		if (r == null) return;
		gridTehtavat.clear();
		List<Tehtava> t = kerho.getTehtavat(r.getReissuId());
		if (!t.isEmpty() ) {
			for (Tehtava t2 : kerho.getTehtavat(r.getReissuId())) {
				Jasen jasen = kerho.haeJasen(t2.getOmistajaJasen());
				if (jasen == null) gridTehtavat.add(t2.getNimi(), "");
				else gridTehtavat.add(t2, t2.getNimi(), jasen.getNimiString());
			}
		}
		else {
			gridTehtavat.add("Ei tehtäviä", "Ei jäsentä");
		}
	}

	/**
	 * Näyttää tehtävät valitulle reissulle Tehtävälista sivulla
	 */
	private void naytaTehtavat() {
		gridTehtavat.clear();
		List<Tehtava> t = kerho.getTehtavat(reissuKohdalla2.getReissuId());
		if (!t.isEmpty() ) {
			for (Tehtava t2 : kerho.getTehtavat(reissuKohdalla2.getReissuId())) {
				Jasen jasen = kerho.haeJasen(t2.getOmistajaJasen());
				if (jasen == null) gridTehtavat.add(t2.getNimi(), "");
				else gridTehtavat.add(t2, t2.getNimi(), jasen.getNimiString());
			}
		}
		else {
			gridTehtavat.add("Ei tehtäviä");
		}
	}
	
	/**
	 * Lisää uuden tehtävän ja tallentaa sen 
	 */
	private void lisaaTehtava() {
		Tehtava uusi = new Tehtava();
		uusi = ModalController.<Tehtava, TehtavanlisaysController>showModal(
				TehtavanlisaysController.class.getResource("tehtavan_lisays_ikkuna.fxml"), "Kerho", null, uusi,
				ctrl -> ctrl.setKerho(kerho, reissuKohdalla2));
		if ( uusi == null) return;
		uusi.rekisteroi();
		kerho.lisaaTehtava(uusi);
		System.out.println("Lisätty tehtävä: " + uusi + " " + uusi.getOmistajaJasen());
	    try {	    	
			kerho.tallenna();
			naytaTehtavat();
		} catch (SailoException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Muokkaa tehtävää
	 */
	private void muokkaaTehtava() {
		GridRowItem<Tehtava> tehtavaKohdalla = gridTehtavat.getSelectionModel().getSelectedItem();
		if (tehtavaKohdalla == null) {
			Dialogs.showMessageDialog("Valitse tehtava!");
			return;
		}
		Tehtava valittuTehtava = tehtavaKohdalla.getItem();
		try {
			Tehtava tehtava = valittuTehtava.clone();
			ModalController.<Tehtava, TehtavanlisaysController>showModal(
				TehtavanlisaysController.class.getResource("tehtavan_lisays_ikkuna.fxml"), "Kerho", null, tehtava,
				ctrl -> ctrl.setKerho(kerho, reissuKohdalla));
			if (tehtava == null) return;
			kerho.korvaaTaiLisaa(tehtava);
			tallenna();
		} catch (CloneNotSupportedException e) {
		} catch (SailoException e) {
			Dialogs.showMessageDialog(e.getMessage());
		}
	}
	
	
	/**
	 * Poistaa valitun tehtavatGridista tehtävän ja tallentaa tiedot
	 */
	private void poistaTehtava() {
		Dialogs.showMessageDialog("Poistetaan valittu tehtävä.");
		GridRowItem<Tehtava> tehtavaKohdalla = gridTehtavat.getSelectionModel().getSelectedItem();
		if (tehtavaKohdalla == null) {
			Dialogs.showMessageDialog("Ei valittua tehtävää");
			return;
		}
		Tehtava valittuTehtava = tehtavaKohdalla.getItem();
		kerho.poistaTehtava(valittuTehtava);
		tallenna();
		naytaTehtavat();
   }
	
	
	
	
 // ========================= REISSUN JUTTUJA =============================	
	
		/**
		 * Valitsee reissun ja tulostaa reissun tiedot
		 */
		private void valitseReissu() {
			Reissu r = idValitseReissu.getSelectedObject();
			if (r == null) return;
			idReissunNimi.setText(r.getReissuNimi());
			idReissunSijainti.setText(r.getSijainti());
			idReissunAjankohta.setText(r.getAjankohta());
			gridReissut.clear();
			for (Osallistuminen o : kerho.haeOsallistumisetReissulle(r.getReissuId())) {
				Jasen jasen = kerho.haeJasen(o.getOmistajaJasen());
				String[] rivi = { jasen.getNimi(), jasen.getAlias() };
				gridReissut.add(o, rivi);
			}
		}
		
		/**
		 * Tähän tullaan kun painetaan Uusi reissu -painiketta etusivulla
		 */
		private void uusiReissu() throws SailoException {
			Reissu uusi = new Reissu();
			kerho.setReissuKohdalla(uusi);
			kerho.lisaaReissu(uusi);
			uusi.rekisteroi();
			ModalController.showModal(KelmienkerhoGUIController.class.getResource("reissun_lisays_ikkuna.fxml"), "Reissut",
					null, kerho);
			uusi.kasvataSeuravaa();
			tallenna();
			gridReissut.clear();
			haeReissut();
		}

		/**
		 * Hakee ja päivittää Reissut ja Tehtävälista sivun reissut (ListChooserit)
		 */
		private void haeReissut() {
			int lkm = kerho.annaPituus();
			idValitseReissu.clear();
			idValitseReissu2.clear();
			for (int i = 0; i < lkm; i++) {
				Reissu r = kerho.annaReissu(i);
				idValitseReissu.add(r.getReissuNimi(), r);
				idValitseReissu2.add(r.getReissuNimi(),r);
			}
		}
		
		/**
		 * Poistaa valitun reissun
		 */
		private void poistaReissu() {
			Reissu reissu = idValitseReissu.getSelectedObject();
	        if ( reissu == null ) return;
	        if ( !Dialogs.showQuestionDialog("Poisto", "Poistetaanko reissu: " + reissu.getReissuNimi(), "Kyllä", "Ei") )
	        	return;
	        kerho.poista(reissu);
	        tallenna();
	        haeReissut();
	        gridReissut.clear();
	        idReissunNimi.clear();
	        idReissunSijainti.clear();
	        idReissunAjankohta.clear();
		}
		  
		
		/**
		 * Muokkaa reissua
		 */
		private void muokkaaReissu() throws CloneNotSupportedException {
			Reissu reissu = idValitseReissu.getSelectedObject();
			if (reissu == null) {
				Dialogs.showMessageDialog("Ei reissua valittuna!");
				return;
			}
				Reissu r = reissu.clone();
				kerho.setReissuKohdalla(r);
				ReissunlisaysController.kysyReissu(null, r, kerho);
				if (r == null) return;
				kerho.korvaaTaiLisaa(r);
				tallenna();
				haeReissut();
				idReissunNimi.setText(r.getReissuNimi());;
		        idReissunSijainti.setText(r.getSijainti());
		        idReissunAjankohta.setText(r.getAjankohta());	
		}
		
		/**
		 * Poistaa jäsenen reissulta
		 */
		private void poistaJasenReissulta() {
			Dialogs.showMessageDialog("Poistetaan valittu jäsen reissulta");
			GridRowItem<Osallistuminen> osallistuminenKohdalla = gridReissut.getSelectionModel().getSelectedItem();
			if (osallistuminenKohdalla == null) {
				Dialogs.showMessageDialog("Ei valittua jäsentä");
				return;
			}
			Osallistuminen valittuOsallistuminen = osallistuminenKohdalla.getItem(); // Antaa null jos tehtävälle ei ole valittu jäsentä
			kerho.poistaOsallistuminen(valittuOsallistuminen);
			tallenna();
			valitseReissu();
		}

		
		
		

 // =====================      FXML JUTUT      =============================== 
 // =====================   Etusivu - REISSUT  ===============================


		@FXML
		private void handleLisaareissu(ActionEvent event) throws SailoException {
			uusiReissu();			
		}

		@FXML
		private void handleMuokkaareissu(ActionEvent event) {
			try {
				muokkaaReissu();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}

		@FXML
		private void handlePoistareissu(ActionEvent event) {
			poistaReissu();
		}

		@FXML
		private void handleSuljereissu(ActionEvent event) {
			tallenna();
			Dialogs.showMessageDialog("Tallennetaan tiedot ja lopetetaan ohjelma. Kiitos ohjelman käytöstä.");
			Platform.exit();
		}

	    @FXML
	    void handlePoistajasenReissulta(ActionEvent event) {
	    	poistaJasenReissulta();
	    }



	// =============== Jäsenet sivu =====================
	    
	@FXML
	void handleUusijasen(ActionEvent event) {
		uusiJasen();
	}

	@FXML
	void handleMuokkaajasen(ActionEvent event) {
		muokkaaJasen();
	}

	@FXML
	void handlePoistajasen(ActionEvent event) {
		Dialogs.showMessageDialog("Poistetaan valittu jäsen.");
		poistaJasen();
	}

	@FXML
	void handleSuljejasen(ActionEvent event) {
		tallenna();
		Dialogs.showMessageDialog("Tallennetaan tiedot ja suljetaan ohjelma. Kiitos ohjelman käytöstä.");
		Platform.exit();
	}
	
    @FXML
    void handleHakuehto(ActionEvent event) {
    	haeJasen();
    }

	// ============= Tehtävä sivu ==================


	@FXML
	void handleUusitehtava(ActionEvent event) {
		lisaaTehtava();
		naytaTehtavat();
	}

	@FXML
	void handleMuokkaatehtava(ActionEvent event) {
		muokkaaTehtava();
		naytaTehtavat();
	}

	@FXML
	void handlePoistatehtava(ActionEvent event) {
		poistaTehtava();
	}

	@FXML
	void handleSuljetehtava(ActionEvent event) {
		tallenna();
		Dialogs.showMessageDialog("Tallennetaan tiedot ja lopetetaan ohjelma. Kiitos ohjelman käytöstä.");
		Platform.exit();
	}

	// ==== muuta höpöhöpöä =====
	
	@Override
	public Kerho getResult() { return null;	}
	
	@Override
	public void handleShown() {}

	@Override
	public void setDefault(Kerho oletus) {}

	public Reissu annaReissuKohdalla() { return reissuKohdalla;	}
	public Reissu annaReissuKohdalla2() { return reissuKohdalla2; }
	
	public void initialize() {
        idReissunSijainti.setEditable(false);
        idReissunNimi.setEditable(false);
        idReissunAjankohta.setEditable(false);
    }
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		alusta();
	}

}
