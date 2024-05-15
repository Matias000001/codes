package fxkelmienkerho;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Kerho-luokka, joka huolehtii Jasenet, Reissut, Osallistumiset ja Tehtavat luokista. Pitää
 * hallussa myös apu attribuuttia reissuKohdalla.
 *
 * @author Matias
 * @version 11.5.2024
 */
public class Kerho {

	private Jasenet jasenet = new Jasenet();
	private Reissut reissut = new Reissut();
	private Tehtavat tehtavat = new Tehtavat();
	private Osallistumiset osallistumiset = new Osallistumiset();
	private Reissu reissuKohdalla;

	/**
	 *  Palautaa kerhon jäsenmäärän
	 * @return jäsenmäärä
	 */
	public int getJasenia() {
		return jasenet.getLkm();
	}

	/**
	 * Lisää kerhoon uuden jäsenen
	 * @param jasen lisättävä jäsen
	 * @throws SailoException jos lisäystä ei voida tehdä
	 * @example
	 * <pre name="test">
	 * #THROWS SailoException
	 * Kerho kerho = new Kerho();
	 * Jasen aku1 = new Jasen(), aku2 = new Jasen();
	 * aku1.rekisteroi(); aku2.rekisteroi();
	 * kerho.getJasenia() === 0;
	 * kerho.lisaaJasen(aku1); kerho.getJasenia() === 1;
	 * kerho.lisaaJasen(aku2); kerho.getJasenia() === 2;
	 * kerho.lisaaJasen(aku1); kerho.getJasenia() === 3;
	 * kerho.getJasenia() === 3;
	 * kerho.annaJasen(0) === aku1;
	 * kerho.annaJasen(1) === aku2;
	 * kerho.annaJasen(2) === aku1;
	 * kerho.annaJasen(3) === aku1; #THROWS IndexOutOfBoundsException 
	 * kerho.lisaaJasen(aku1); kerho.getJasenia() === 4;
	 * kerho.lisaaJasen(aku1); kerho.getJasenia() === 5;
	 * </pre>
	 */
	public void lisaaJasen(Jasen jasen) throws SailoException {
		jasenet.lisaa(jasen);
	}

// ========== TALLETUS =============

	/**
	 * Asettaa tiedostojen perusnimet
	 * @param nimi uusi nimi
	 */
	public void setTiedosto(String nimi) {
		File dir = new File(nimi);
		dir.mkdirs();
		String hakemistonNimi = "";
		if (!nimi.isEmpty()) hakemistonNimi = nimi + "/";
		jasenet.setTiedostonPerusNimi(hakemistonNimi + "nimet");
		tehtavat.setTiedostonPerusNimi(hakemistonNimi + "tehtavat"); // TODO
		reissut.setTiedostonPerusNimi(hakemistonNimi + "reissut"); // TODO
	}

	/**
	 * Lukee kerhon tiedot tiedostosta
	 * @param nimi jota käyteään lukemisessa
	 * @throws SailoException jos lukeminen epäonnistuu
	 */
	public void lueTiedostosta(String nimi) throws SailoException {
		jasenet = new Jasenet(); 
		tehtavat = new Tehtavat();
		reissut = new Reissut();
		osallistumiset = new Osallistumiset();

		setTiedosto(nimi);
		jasenet.lueTiedostosta(nimi);
		tehtavat.lueTiedostosta(nimi);
		reissut.lueTiedostosta(nimi);
		osallistumiset.lueTiedostosta(nimi);
	}

	/**
	 * Aliohjelma tallentaa tiedot
	 * @throws SailoException
	 */
	public void tallenna() throws SailoException {
		reissut.tallenna("data");
		jasenet.tallenna("data");
		tehtavat.tallenna("data");
		osallistumiset.tallenna("data");
	}
	
	// ==============================

	/**
	 * Palauttaa i:n jäsenen
	 * @param i monesko jäsen palautetaan
	 * @return viite i:teen jäseneen
	 * @throws IndexOutOfBoundsException jos i väärin
	 */
	public Jasen annaJasen(int i) throws IndexOutOfBoundsException {
		return jasenet.anna(i);
	}

	/**
	 * Välittää pyynnän jäsenet luokalle palauttaa parametrina tuodun jäsenen id:n
	 * @param nimi merkkijonona
	 * @return jäsenen id numero
	 */
	public int annaJasen(String nimi) {
		return jasenet.anna(nimi);
	}

	/**
	 * Välittää pyynnän reissut luokalle luoda uusi reissu
	 * @param reissu
	 * @throws SailoException
	 */
	public void uusiReissu(Reissu reissu) throws SailoException {
		reissut.lisaa(reissu);
	}

	/**
	 * Välittää pyynnön reissut luokalle palauttaa viite reissuun jonka id numeron on parametrina
	 * @param reissu id
	 * @return viite reissuun
	 * @throws IndexOutOfBoundsException
	 */
	public Reissu annaReissu(int i) throws IndexOutOfBoundsException {
		return reissut.annaReissu(i);
	}

	/**
	 * Välittää pyynnön reissut luokalle lisätä viitteenä tuotu reissu reissu listaan
	 * @param uusi reissu
	 * @throws SailoException 
	 */
	public void lisaaReissu(Reissu uusi) throws SailoException {
		reissut.lisaa(uusi);
	}
	
	/**
	 * Välittää pyynön reissut luokalle palauttaa reissujen lukumäärän
	 * @return reissujen lukumäärä
	 */
	public int getReissuLkm() {
		return reissut.getReissuLkm();
	}
	
	/**
	 * Välittää pyynnön reissut luokalle palauttaa reissujen lukumäärän
	 * @return lukumäärä
	 */
	public int annaPituus() {
		return reissut.getReissuLkm();
	}

	/**
	 * Välittää pyynnön jäsenet luokalle palauttaa kerhon jäsenten lukumäärä
	 * @return jäsenten määrä
	 */
	public int annaJasenPituus() {
		return jasenet.getLkm();
	}

	/**
	 * Väliuttää pyynnön osallistumiset luokalle lisätä osallistuminen osallistumiset listaan
	 * @param lisättävä osallistuminen
	 */
	public void lisaaOsallistuminen(Osallistuminen o) {
		osallistumiset.lisaa(o);
	}

	/**
	 * Välittää pyynnön osallistumiset luokalle hakea osallistumiset reissulle ja palauttaa listassa nämä
	 * @param reissu jonka osallistumisia haetaan
	 * @return lista osallistumisista halutulle reissulle
	 */
	public ArrayList<Osallistuminen> haeOsallistumisetReissulle(int num) {
		return osallistumiset.getOsallistumisetReissulle(num);
	}

	/**
	 * Välitetään pyyntö jäsenet luokalle palauttaa viite jäseneen jonka id on parametrina
	 * @param jasenen id
	 * @return viite jäseneen
	 */
	public Jasen haeJasen(int omistajaJasen) {
		return jasenet.haeJasen(omistajaJasen);
	}

	/**
	 * Välittää pyynnön jäsenet luokalle palauttaa viitte kerhon jäsentaulukkoon
	 * @return viite jäsen taulukkoon
	 */
	public Jasen[] annaJasenet() {
		return jasenet.haeJasenet();
	}

	/**
	 * Välittää pyynnön tehtävät luokalle lisätä tehtävä listaan ja asettaa sille omistaja reissun
	 * @param tehtava joka lisätään listaan
	 * @param omistaja reissu
	 */
	public void lisaaTehtava(Tehtava tehtava, int reissu) {
		tehtavat.lisaaTehtava(tehtava, reissu);
	}

	/**
	 * Välittää pyynnön tehtävät luokalle lisätä tehtävä listaan
	 * @param uusi
	 */
	public void lisaaTehtava(Tehtava uusi) {
		tehtavat.lisaa(uusi);
	}

	/**
	 * Välitää pyynnön reissut luokalle hakea id:tä vastaava nimi, ja palauttaa se sen
	 * jälkeen kysyjälle
	 * @param id
	 * @return reissun nimi
	 */
	public String annaReissunNimi(int i) {
		return reissut.annaReissunNimi(i);
	}

	/**
	 * Välittää pyynnän jäsenet luokalle lisätä muutettu jäsen listaan
	 * @param jäsen joka lisätään listaan
	 * @throws SailoException
	 */
	public void korvaaTaiLisaa(Jasen jasen) throws SailoException { 
	        jasenet.korvaaTaiLisaa(jasen); 
	    }
	
	/**
	 * Välittää pyynnön tehtävät luokalle korvata vanha tehtävä muutetulla tehtävällä
	 * @param muokattu tehtava
	 * @throws SailoException
	 */
	public void korvaaTaiLisaa(Tehtava tehtava) throws SailoException { 
        tehtavat.korvaaTaiLisaa(tehtava); 
    }

	/**
	 * Välittää pyynnön tehtävät luokalle palauttaa lista tehtävistä, joiden reissuId
	 * on parametrina välitetty
	 * @param reissuId
	 * @return
	 */
	public List<Tehtava> getTehtavat(int reissuId) {
		return tehtavat.annaTehtavat(reissuId);
	}

	/**
	 * Välittää pyynnön jasenet luokalle palauttaa viite jäseneen, jonka tunnusnumeron 
	 * on viitteenä annettu numero
	 * @param nimiId
	 * @return viite jäseneen
	 */
	public Jasen getNimi(int nimiId) {
		return jasenet.getNimi(nimiId);
	}

	/**
	 * Välittää pyynnön jäsenet luokalle palauttaa jäsenen nimi merkkijonona,
	 * jonka id numero on annettu parametrina
	 * @param jäsenen id numero
	 * @return nimi merkkijonona
	 */
	public String haeJasenString(int idn) {
		return jasenet.annaJasenNimi(idn);
	}

	/**
	 * Välittää pyynnön tehtävät luokalle poistaa parametrina annettu tehtävä listasta
	 * @param tehtava
	 */
	public void poistaTehtava(Tehtava tehtava) {
		tehtavat.poista(tehtava);
	}
	
	/**
	 * Välittää pyynnön jäsenet luokalle poistaa liitteenä oleva jäsen listasta
	 * @param jasen
	 */
	public void poista(Jasen jasen) {
		jasenet.poista(jasen.getTunnusNro()); 
	}

	/**
	 * Välittää pyynnön osallistumiset luokalle selvittää onko jäsen jonka ide parametrina,
	 * reissulla jonka id myös parametrina.
	 * @param reissuId
	 * @param id
	 * @return true jos on, false jos ei ole
	 */
	public Boolean onkoJoReissulla(int reissuId, int id) {
		return osallistumiset.onko(reissuId, id);
	}

	/**
	 * Välittää pyynnöt eteenpäin poistaa parametrina saatu reissu reissut listasta sekä 
	 * kaikki osallistumiset kyseiselle reissulle
	 * @param reissu jonka tiedot poistetaan
	 */
	public void poista(Reissu reissu) {
		reissut.poistaReissu(reissu.getReissuId());
		osallistumiset.poistaReissu(reissu.getReissuId());
	}

	/**
	 * Palauttaa viitteen reissuun, joka on asetettu tämän luokan attribuutteihin
	 * @return viite reissuun
	 */
	public Reissu getReissuKohdalla() {
		return reissuKohdalla;
	}

	/**
	 * Asettaa attribuuttiin viitteen parametrina saatuun reissuun
	 * @param reissuKohdalla
	 */
	public void setReissuKohdalla(Reissu reissuKohdalla) {
		this.reissuKohdalla = reissuKohdalla;
	}

	/**
	 * Välittää pyynnön osallistumiset luokalle poistaa parametrina oleva osallistuminen
	 * osallistumiset listasta
	 * @param valittuOsallistuminen
	 */
	public void poistaOsallistuminen(Osallistuminen valittuOsallistuminen) {
		osallistumiset.poistaOsallistuminen(valittuOsallistuminen);
	}

	/**
	 * Etsii kaikki jäsenet listasta joiden nimi alkaa parametrina saadulla merkkijonolla
	 * @param ehto
	 * @return
	 */
	public ArrayList<Jasen> etsi(String ehto) {
		return jasenet.etsi(ehto);
	}

	/**
	 * Välittää pyynnön reissut luokalle korvata sama vanha reissu uudella parametrina
	 * saadulla reissulla
	 * @param muokattu reissu
	 */
	public void korvaaTaiLisaa(Reissu r) {
		reissut.korvaaTaiLisaa(r);
	}
	
}
