package fxkelmienkerho;

import java.io.*;
import java.util.ArrayList;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * Kerhon Reissu luokka joka tekee Reissu olioita
 * 
 * @author Matias
 * @version 11.5.2024
 */
public class Reissu implements Cloneable {
	private int reissuId = 1;
	private String reissunNimi = "";
	private String ajankohta = "";
	private String sijainti = "";
	private String kuvat = "";
	private static int seuraavaNum = 1;

	public Reissu() {
	}

	/**
	 * Muodostaja jossa asetetaan reissun nimi, sijainti ja ajankohta
	 * @param Reissun nimi
	 * @param Reissun sijainti
	 * @param Reissun ajankohta
	 * @param Reissun kuvien osoite
	 */
	public Reissu(String nimi, String sijainti, String ajankohta, String kuvat) {
		this.reissunNimi = nimi;
		this.sijainti = sijainti;
		this.ajankohta = ajankohta;
		this.kuvat = kuvat;
	}

	/**
	 * Palauttaa reissun tiedot merkkijonona, jonka voi tallentaa tiedostoon
	 */
	@Override
	public String toString() {
	  	return ""  + 
	  		reissuId + "|" + 
	  	    reissunNimi + "|" + 
	  	    ajankohta + "|" +
	  	    sijainti + "|" +
	  	    kuvat;
	}
	
    /**
     * Aliohjelma parsii merkkijonon attribuutteihin
     * @param rivi josta tehtävän tiedot otetaan
     * <pre name="test">
     * 	Reissu reissu = new Reissu();
     *  reissu.parse("1|Testi reissu|01.01.2025|Määränpää|-");
     *  reissu.getReissuId() === 1;
     *  reissu.getReissuNimi() === "Testi reissu"
     * </pre>
     */
	public void parse(String s) {
		var sb = new StringBuilder(s);
		setTunnusNro(Mjonot.erota(sb, '|', getReissuId()));
		reissunNimi = Mjonot.erota(sb, '|', reissunNimi);
		ajankohta = Mjonot.erota(sb, '|', ajankohta);
		sijainti = Mjonot.erota(sb, '|', sijainti);
		kuvat = Mjonot.erota(sb, '|', kuvat);
	}

	/**
	 * Rekisteröi uuden reissun
	 */
	public void rekisteroi() {
		this.reissuId = seuraavaNum;
	}

	/**
	 * Tulostetaan reissun tiedot
	 * @param out tietovirta johon tulostetaan
	 */
	public void tulosta(PrintStream out) {
		out.println(reissuId + " " + reissunNimi + " " + sijainti + " " + ajankohta);
	}

	/**
	 * Tulostetaan reissun tiedot
	 * @param os tietovirta johon tulostetaan
	 */
	public void tulosta(OutputStream os) {
		tulosta(new PrintStream(os));
	}

	
	/**
	 * Tekee kloonin reissusta muokkausta varten
	 */
	public Reissu clone() throws CloneNotSupportedException {
		Reissu uusi;
		uusi = (Reissu) super.clone();
		return uusi;
	 }
	
	/**
	 * Pääohjelma testaamista varten
	 * @param args
	 */
	public static void main(String[] args) {
		Reissu reissu = new Reissu();
	    reissu.parse("1|Testi reissu|01.01.2025|Määränpää|-");
	    reissu.tulosta(System.out);
	    reissu.rekisteroi();
	    int n = reissu.getReissuId();
	    reissu.parse(""+(n+20));         
	    reissu.rekisteroi();
	    reissu.getReissuId();
	    reissu.tulosta(System.out);
	}
	
	
	// ---- Getterit
	
	/**
	 * Palauttaa reissun id numeron
	 * @return reissuId
	 */
	public int getReissuId() {
		return this.reissuId;
	}

	/**
	 * Palauttaa seuraava numeron
	 * @return seuraavaNum
	 */
	public int getReissuIdSeuraava() {
		return this.seuraavaNum;
	}

	/**
	 * Palauttaa reissun nimen
	 * @return reissunNimi
	 */
	public String getReissuNimi() {
		return this.reissunNimi;
	}

	/**
	 * Palauttaa reissun sijainnin
	 * @return sijainti
	 */
	public String getSijainti() {
		return this.sijainti;
	}

	/**
	 * Palauttaa reissun ajankohdan
	 * @return ajankohta
	 */
	public String getAjankohta() {
		return this.ajankohta;
	}

	/**
	 * Palauttaa kuviin asetetun merkkijonon
	 * @return kuvat
	 */
	public String getUrl() {
		return this.kuvat;
	}

	
	// ------- Setterit ---------

	/**
	 * Asettaa tehtävälle tunnusnumeron
	 * Testaamista varten
	 * @param num
	 */
	private void setTunnusNro(int num) {
		if (num > seuraavaNum) seuraavaNum = num + 1;
		this.reissuId = num;
	}

	/**
	 * Asettaa tehtävälle ajankohdan
	 * @param ajankohta
	 */
	public void setAjankohta(String ajankohta) {
		this.ajankohta = ajankohta;
	}

	/**
	 * Asettaa tehtävälle kuvan (mahdollinen osoite merkkijonona)
	 * @param kuvat
	 */
	public void setKuvat(String kuvat) {
		this.kuvat = kuvat;
	}

	/**
	 * Asettaa reissulle nimen
	 * @param reissunNimi
	 */
	public void setReissunNimi(String reissunNimi) {
		this.reissunNimi = reissunNimi;
	}

	/**
	 * Asettaa reissulle sijainnin
	 * @param sijainti
	 */
	public void setSijainti(String sijainti) {
		this.sijainti = sijainti;
	}

	/**
	 * Kasvattaa seuraavaan numeroa
	 */
	public void kasvataSeuravaa() {
		seuraavaNum++;
	}

}
