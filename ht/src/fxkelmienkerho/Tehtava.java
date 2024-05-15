package fxkelmienkerho;

import java.io.*;
import java.util.Arrays;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * Kerhon tehtävä luokka
 * 
 * @author Matias
 * @version 11.5.2024
 */
public class Tehtava implements Cloneable {
	private int tehtavaId = 0;
    private String tehtavanNimi = "";
    private int omistajaJasen = 0;
    private int omistajaReissu;
    private static int seuraavaNro = 1;
    
    /**
     * Oletusmuodostaja
     */
    public Tehtava() {
    }
    
    /**
     * Muodostaja joka lisää nimen
     * @param nimi
     */
    public Tehtava(String nimi) {
    	tehtavanNimi = nimi;
    }

    /**
     * Kolmen parametrin muodostaja
     * @param tehtava
     * @param tehtNum
     * @param kenen
     */
    public Tehtava(String tehtava, int tehtNum,  int kenen) {
    	this.tehtavaId = tehtNum;
    	this.tehtavanNimi = tehtava; 
    	this.omistajaJasen = kenen;	
    }
    
    /**
     * Palauttaa tehtävän tiedot merkkijonona, jonka voi tallentaa tiedostoon.
     * @return tehtävä tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Tehtava tehtava = new Tehtava();
     *   tehtava.parse("   1  |  Testi tehtävä   | 1|  1");
     *   tehtava.toString().startsWith("1|Testi tehtävä|1|1") === true;
     * </pre>  
     */
    @Override
    public String toString() {
    	return ""  + 
    	this.tehtavaId + "|" + 
    	this.tehtavanNimi + "|" + 
    	this.omistajaJasen + "|" +
    	this.omistajaReissu;
    }
    
    /**
     * Rekisteröi tehtävän
     */
	public void rekisteroi() {
		this.tehtavaId = seuraavaNro;
		this.seuraavaNro++;
	}
		
    /**
     * Tulostetaan tehtävän tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(this.tehtavaId + " " + this.tehtavanNimi + " " + this.omistajaJasen + " "+ this.omistajaReissu);
    }   

    /**
     * Tulostetaan tehtävän tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }

    // ----- Setterit -----

    /**
     * Asettaa tehtävälle sen reissun id numeron jolle tehtävä on asetettu
     * @param reissuId
     */
	public void setOmistajaReissu(int reissuId) {
		this.omistajaReissu = reissuId;
	}

	/**
	 * Asettaa tehtävälle nimen
	 * @param tehtava
	 */
	public void setNimi(String tehtava) {
		this.tehtavanNimi = tehtava;	
	}

	/**
	 * Asettaa tehtävälle sen jäsenen id numeron jolle tehtävä on laittettu kuulumaan 
	 * @param valittuJasen
	 */
	public void setvalittuJasen(int valittuJasen) {
		this.omistajaJasen = valittuJasen;		
	}
	
	/**
	 * Asettaan tehtävälle tunnusnumeron
	 * @param num
	 */
	private void setTunnusNro(int num) {
	   	if (num > seuraavaNro) seuraavaNro = num + 1;
		this.tehtavaId = num;
	}

	// --------  Getterit ---------
	
	/**
	 * Palauttaa tehtävän nimen
	 * @return tehtavanaNimi
	 */
	public String getNimi() {
		return this.tehtavanNimi;
	}
	
	/**
	 * Palauttaa reissun id numeron jolle tehtävä on osoitettu
	 * @return omistajaReissu
	 */
	public int getOmistajaReissu() {
		return this.omistajaReissu;
	}
	
    /**
     * Palautetaan reissu id jolle tehtävä kuuluu 
     * @return omistajaReissu
     */
    public int getTehtavaNro() {
        return this.tehtavaId;
    }

    /**
     * Palautetaan mille jäsenelle tehtävä on laitettu kuulumaan
     * @return jäsenen id
     */
    public int getOmistajaJasen() {
        return this.omistajaJasen;
    }

	// -------------
	
	/**
	 * Tekee kloonin muokkaamista varten
	 * @return viitteen kloonattuun tehtävän instanssiin
	 */
	public Tehtava clone() throws CloneNotSupportedException {
		Tehtava uusi = (Tehtava) super.clone();
		return uusi;
	 }
	
    /**
     * Parsii merkkijonon ja asettaa attribuutit
     * @param rivi josta tehtävän tiedot otetaan
     * <pre name="test">
     * 	Tehtava tehtava = new Tehtava();
     *  tehtava.parse("    3   |   Keitä puuro   |   1  | 2 ");
     *  tehtava.getTehtavaNro() === 3;
     *  tehtava.toString().startsWith("3|Keitä puuro| 1|2")  === false;
     * </pre>
     */
	public void parse(String s) {
		var sb = new StringBuilder(s);
		setTunnusNro(Mjonot.erota(sb, '|', getTehtavaNro()));
		this.tehtavanNimi = Mjonot.erota(sb, '|', tehtavanNimi);
		this.omistajaJasen = Mjonot.erota(sb, '|', omistajaJasen);
		this.omistajaReissu = Mjonot.erota(sb, '|', omistajaReissu);
	}

	
	/**
	 * Pääohjelma testausta varten
	 * @param args ei käytössä
	 */
	public static void main(String[] args) {
		Tehtava t1 = new Tehtava();
		t1.setNimi("Testi tehtävä");
		t1.setOmistajaReissu(1);
		t1.setvalittuJasen(1);
		t1.rekisteroi();
		t1.tulosta(System.out);
	}
}

