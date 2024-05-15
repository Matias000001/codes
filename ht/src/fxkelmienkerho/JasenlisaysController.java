package fxkelmienkerho;

import java.io.PrintStream;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import fi.jyu.mit.fxgui.StringGrid.GridRowItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Tämä luokka hallinnoi jäsenen lisäystä
 * 
 * @author Matias
 * @version 11.5.2024
 * 
 */
public class JasenlisaysController implements ModalControllerInterface<Jasen> {

	@FXML private Button idUusijasencancel;
	@FXML private Button idUusijasenok;
	@FXML private TextField idKatuosoite;
	@FXML private TextField idPaikkakunta;
	@FXML private TextField idPostinumero;
	@FXML private TextField idTextAlias;
	@FXML private TextField idTextNimi;

	private Kerho kerho;
	private Jasen jasen;

	@FXML
	void handleUusijasencancel(ActionEvent event) {
		ModalController.closeStage(idUusijasencancel);
	}

	@FXML
	void handleUusijasenok(ActionEvent event) throws SailoException {
			String nimi = idTextNimi.getText();
			String alias = idTextAlias.getText();
			String katuosoite = idKatuosoite.getText();
			String postinumero = idPostinumero.getText();
			String paikkakunta = idPaikkakunta.getText();			
			if (nimi == null || nimi.isEmpty()) { Dialogs.showMessageDialog("Nimi ei voi olla tyhjä!"); return; }
			if (nimi.contains("|")) { Dialogs.showMessageDialog("Nimi ei saa sisältää | merkkiä."); return; }
			if (alias == null || alias.isEmpty()) alias = ""; 
			if (alias.contains("|")) { Dialogs.showMessageDialog("Alias ei saa sisältää | merkkiä."); return; }
			if (katuosoite == null || katuosoite.isEmpty()) katuosoite = "";
			if (katuosoite.contains("|")) { Dialogs.showMessageDialog("Katuosoite ei saa sisältää | merkkiä."); return; }
			if (postinumero == null || postinumero.isEmpty()) postinumero = "";
			if (postinumero.contains("|")) { Dialogs.showMessageDialog("Postinumero ei saa sisältää | merkkiä."); return; }
			if (paikkakunta == null || paikkakunta.isEmpty()) paikkakunta = "";
			if (paikkakunta.contains("|")) { Dialogs.showMessageDialog("Paikkakunta ei saa sisältää | merkkiä."); return; }
			jasen.setNimi(nimi);
			jasen.setAlias(alias);
			jasen.setKatuOsoite(katuosoite);
			jasen.setPaikkakunta(paikkakunta);
			jasen.setPostinumero(postinumero);
			ModalController.closeStage(idUusijasencancel);
	}

	public Kerho annaKerho() {
		return this.kerho;
	}

	public void setKerho(Kerho x) {
		this.kerho = x;
	}

	@Override
	public Jasen getResult() {
		return this.jasen;
	}

	@Override
	public void handleShown() {
	}

	@Override
	public void setDefault(Jasen oletus) {
		this.jasen = oletus;
		naytaJasen(jasen);
	}

	private void naytaJasen(Jasen j) {
		if (j == null) return;
		idTextNimi.setText(jasen.getNimi());
		idTextAlias.setText(jasen.getAlias());
		idKatuosoite.setText(jasen.getOsoite());
		idPostinumero.setText(jasen.getPostinumero());
		idPaikkakunta.setText(jasen.getPaikkakunta());
	}

	public static Jasen kysyJasen(Stage modalityStage, Jasen oletus, int kentta) {
		return ModalController.<Jasen, JasenlisaysController>showModal(
				JasenlisaysController.class.getResource("jasen_lisays_ikkuna.fxml"), "Kerho", modalityStage, oletus,
				ctrl -> ctrl.setKentta(kentta));
	}

	private Object setKentta(int kentta) {
		return null;
	}
}
