package fxkelmienkerho;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * Luokka huolehtii, että ohjelma tietää ketä jäseniä on liitetty millekkin reissulle
 * 
 * @author Matias
 * @version 11.5.2024
 */
public class Osallistuminen {

	private int osallistumisId = 0;
	private int omistajaReissu;
	private int omistajaJasen = 0;
	private static int seuraavaNro = 1;

	/**
	 * Oletusmuodostaja 
	 */
	public Osallistuminen() {
	}

	/**
	 * Kahden parametrin muodostaja
	 * @param omistajaReissu
	 * @param omistajaJasen
	 */
	public Osallistuminen(int omistajaReissu, int omistajaJasen) {
		this.omistajaReissu = omistajaReissu;
		this.omistajaJasen = omistajaJasen;
	}
	
	/**
	 * Rekisteröi osallistumisen
	 */
	public void rekisteroi() {
		this.osallistumisId = seuraavaNro++;
	}
	
	/**
    * Palauttaa osallistumisen tiedot merkkijonona, jonka voi tallentaa tiedostoon.
    * @return osallistuminen tolppaeroteltuna merkkijonona 
    * @example
    * <pre name="test">
    *   Osallistuminen osallistuminen = new Osallistuminen();
    *   osallistuminen.parse("   1  |  1  | 1 ");
    *   osallistuminen.toString().startsWith("1|1|1") === true;
    * </pre>  
    */
	@Override
	public String toString() {
	  	return ""  + 
	  		osallistumisId + "|" + 
	  	    omistajaReissu + "|" + 
	  	    omistajaJasen;
	}

	/**
	 * Palauttaa osallistumisen id:n
	 * @return osallistumisId
	 */
	public int getOsallistumisId() {
		return osallistumisId;
	}

	/**
	 * Asettaa osallistumisen osallistumisId:n 
	 * @param osallistumisId
	 */
	public void setOsallistumisId(int osallistumisId) {
		this.osallistumisId = osallistumisId;
	}
	
	/**
	 * Asettaa osallistumisId:n
	 * @param num
	 */
	private void setTunnusNro(int num) {
		if (num > seuraavaNro) seuraavaNro = num + 1;
		this.osallistumisId = num;
	}

	/**
	 * Palauttaa osallistumisen omistaja jäsenen id numeron
	 * @return omistajaJasen
	 */
	public int getOmistajaJasen() {
		return omistajaJasen;
	}

	/**
	 * Asettaa osallistumiselle jäsenen
	 * @param omistajaJasen
	 */
	public void setOmistajaJasen(int omistajaJasen) {
		this.omistajaJasen = omistajaJasen;
	}

	/**
	 * Palauttaa osallistumisen reissun id numeron 
	 * @return omistajaReissu
	 */
	public int getOmistajaReissu() {
		return omistajaReissu;
	}

	/**
	 * Asettaa osallitumiselle reissun id numeron
	 * @param omistajaReissu
	 */
	public void setOmistajaReissu(int omistajaReissu) {
		this.omistajaReissu = omistajaReissu;
	}

	/**
	 * Palauttaa seuraavan numeron
	 * @return seuraavaNro
	 */
	public static int getSeuraavaNro() {
		return seuraavaNro;
	}

	/**
	 * Asettaa osallistumiselle seuraavan numeron
	 * @param seuraavaNro
	 */
	public static void setSeuraavaNro(int seuraavaNro) {
		Osallistuminen.seuraavaNro = seuraavaNro;
	}

	/**
	 * Käsittelee merkkijonon ja asettaa sieltä oikeat arvot oikeisiin 
	 * attribuutteihin
	 * @param merkkijono jota käsitellään
	 */
	public void parse(String s) {
		var sb = new StringBuilder(s);
		setTunnusNro(Mjonot.erota(sb, '|', getOsallistumisId()));
		setOmistajaReissu(Mjonot.erota(sb, '|', getOmistajaReissu()));
		setOmistajaJasen(Mjonot.erota(sb, '|', getOmistajaJasen()));
		
	}

	/**
	 * Tulostetaan osallistumisen tiedot
	 * @param out
	 */
	public void tulosta(PrintStream out) {
        out.println(this.osallistumisId + " " + this.omistajaReissu + " " + this.omistajaJasen);
    }   

    /**
     * Tulostetaan osallistumisen tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }

}
