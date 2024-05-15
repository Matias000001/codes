package fxkelmienkerho;

import java.io.*;
import java.util.Comparator;
import java.util.Random;

import fi.jyu.mit.fxgui.StringGrid.GridRowItem;
import fi.jyu.mit.ohj2.Mjonot;

/**
 * Kerhon jäsen luokka joka tekee jäsen olioita
 *
 * @author Matias
 * @version 11.5.2024
 */
public class Jasen implements Cloneable {
	private int        jasenId        = 1;
    private String     nimi           = "";
    private String     alias          = "";
    private String     katuosoite     = "";
    private String     postinumero    = "";
    private String     paikkakunta    = "";
    private static int seuraavaNro    = 1;

    
    public Jasen() {}

   /**
    * Palauttaa jäsenen tiedot merkkijonona jonka voi tallentaa tiedostoon.
    * @return jäsen tolppaeroteltuna merkkijonona 
    * @example
    * <pre name="test">
    *   Jasen jasen = new Jasen();
    *   jasen.parse("   3  |  Ankka Aku   | Akkuli  |  Ankkatie |  30003  |  ANKKALINNA");
    *   jasen.toString().startsWith("3|Ankka Aku|Akkuli|Ankkatie|30003|ANKKALINNA") === true;
    * </pre>  
    */
    @Override
    public String toString() {
    	return "" +
    			getTunnusNro() + "|" +
    			nimi + "|" +
    			alias + "|" +
    			katuosoite + "|" +
    			postinumero + "|" +
    			paikkakunta;
    }
    
    /**
     * Palauttaa jäsenen nimen
     * @return jäsenen nimi
     * @example
     * <pre name="test">
     * Jasen aku = new Jasen();
     * aku.vastaaAkuAnkka();
     * aku.getNimi() === "Aku Ankka";
     * </pre>
     */
    public String getNimi() { 
    	return this.nimi; 
    	}
    
    /**
     * Esimerkki olio
     */
    public void vastaaJasen() {
        this.nimi = "Aku Ankka";
        this.alias = "Akkuli";
        this.katuosoite = "Ankkatie 1";
        this.postinumero = "30330";
        this.paikkakunta = "Ankkalinna";
    }
    
    /**
     * Esimerkki olio 2
     */
    public void vastaaAkuAnkka() {
		this.nimi = "Aku Ankka";
		this.alias = "Akkuli";
		this.katuosoite = "Ankkanummi 6";
		this.postinumero = "30303";
		this.paikkakunta = "Roskapönttö";
	}
    
    /**
     * Tulostetaan henkilön tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%02d", jasenId) + " " + (nimi != null ? nimi : "") + " " + (alias != null ? alias : "") + " "+ (katuosoite != null ? katuosoite : "") + " " + (postinumero != null ? postinumero : "") + " " + (paikkakunta != null ? paikkakunta : ""));
    }

    /**
     * Tulostetaan henkilön tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }


    /**
     * Testialiohjelma rekisteröintiä varten
     * @example
     * <pre name="test">
     *   Jasen aku1 = new Jasen();
     *   aku1.getTunnusNro() === 1;
     *   aku1.rekisteroi();
     *   Jasen aku2 = new Jasen();
     *   aku2.rekisteroi();
     *   int n1 = aku1.getTunnusNro();
     *   int n2 = aku2.getTunnusNro();
     *   n1 === n2-1;
     * </pre>
     */
    public void rekisteroi(String nimi, String alias, String katuosoite, String postinumero, String paikkakunta) {
        this.jasenId = seuraavaNro++;
        this.nimi = nimi;
        this.alias = alias;
        this.katuosoite = katuosoite;
        this.postinumero = postinumero;
        this.paikkakunta = paikkakunta;
    }

    /**
     * Rekisteröi jäsenen
     */
    public void rekisteroi() {
    	this.jasenId = seuraavaNro++;
	}

    /**
     * Palauttaa jäsenen tunnusnumeron.
     * @return jäsenen tunnusnumero
     */
    public int getTunnusNro() {
        return jasenId;
    }

    /**
     * Testiohjelma jäsenelle.
     * @param args ei käytössä
     */
    public static void main(String args[]) {
    	Jasen aku = new Jasen(); 
        Jasen aku2 = new Jasen();
        aku.rekisteroi( "Aku Ankka" , "Akkuli" , "Ankkatie 1" , "30330" , "Ankkalinna");
        aku.tulosta(System.out); 
        aku2.rekisteroi("Sepe Susi", "Seppuli", "Seppotie 2", "09878", "Takametsä");
        aku2.tulosta(System.out);
    }

    /**
     * Parsii merkkijonon ja asettaa attribuutit
     * @param rivi josta jäsenen tiedot otetaan
     * @example
     * <pre name="test">
     * 	Jasen jasen = new Jasen();
     *  jasen.parse("    3   |   Aku Ankka   |   Akkuli");
     *  jasen.getTunnusNro() === 3;
     *  jasen.toString().startsWith("3|Aku Ankka| Akkuli|")  === false;
     * </pre>
     */
	public void parse(String rivi) {
		var sb = new StringBuilder(rivi);
		setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
		nimi = Mjonot.erota(sb, '|', nimi);
		alias = Mjonot.erota(sb, '|', alias);
		katuosoite = Mjonot.erota(sb, '|', katuosoite);
		postinumero = Mjonot.erota(sb, '|', postinumero);
		paikkakunta = Mjonot.erota(sb, '|', paikkakunta);
	}
	
	/**
	 * Vertailija luokka
	 */
	public static class Vertailija implements Comparator<Jasen> {
	    private final int kenttanro;
        public Vertailija(int k) {
            this.kenttanro = k;
        }
    	public String getAvain(int kenttanro) {
    		return "";
    	}

        @Override
        public int compare(Jasen j1, Jasen j2) {
            String s1 = j1.getNimi();
            String s2 = j2.getNimi();
            return s1.compareTo(s2);
        }
    }

	/**
	 * Asettaa jäsen id:n
	 * @param id numero joka asetetaan
	 */
	private void setTunnusNro(int numero) {
		this.jasenId = numero;
		if ( jasenId >= seuraavaNro ) seuraavaNro++;
	}


	/**
	 * Palauttaa jäsenen aliaksen
	 * @return alias
	 */
	public String getAlias() {
		return alias;
	}
	
	/**
	 * Palauttaa jäsenen osoitteen
	 * @return katuosoite
	 */
	public String getOsoite() {
		return katuosoite;
	}
	
	/**
	 * Palauttaa jäsenen postinumeron
	 * @return postinumero
	 */
	public String getPostinumero() {
		return postinumero;
	}
	
	/**
	 * Palauttaa jäsenen paikkakunnan
	 * @return postiosoite
	 */
	public String getPaikkakunta() {
		return paikkakunta;
	}
	
	/**
	 * Asettaa jäsenelle nimen
	 * Asettaan jäsenen nimen
	 * @param nimi
	 */
	public void setNimi(String nimi) {
		this.nimi = nimi;
	}

	/**
	 * Asettaa jäsenen aliaksen
	 * @param alias
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	/**
	 * Asettaa jäsenen katuosoitteen
	 * @param katuosoite
	 */
	public void setKatuOsoite(String katuosoite) {
		this.katuosoite = katuosoite;
	}

	/**
	 * Asettaa jäsenen postinumeron
	 * @param postinumero
	 */
	public void setPostinumero(String postinumero) {
		this.postinumero = postinumero;
	}

	/**
	 * Asettaa jäsenen paikkakunnan
	 * @param paikkakunnan
	 */
	public void setPaikkakunta(String paikkakunta) {
		this.paikkakunta = paikkakunta;
	}
	
    /**
     * Tehdään identtinen klooni jäsenestä
     * @return Object kloonattu jäsen
     * @example
     * <pre name="test">
     * 	 #THROWS CloneNotSupportedException 
     *   Jasen jasen = new Jasen();
     *   jasen.parse("   3  |  Ankka Aku   | Akkuli | Ankkatie | 30003 | ANKKALINNA");
     *   Jasen kopio = jasen.clone();
     *   kopio.toString() === jasen.toString();
     *   jasen.parse("   4  |  Ankka Tupu   | Ankkatie | 30003 | ANKKALINNA");
     *   kopio.toString().equals(jasen.toString()) === false;
     * </pre>
     */
	public Jasen clone() throws CloneNotSupportedException {
		Jasen uusi;
		uusi = (Jasen) super.clone();
		return uusi;
	 }

	/**
	 * Palauttaa arvon 1 
	 * @return 1
	 */
	public int ekaKentta() {
		return 1;
	}
	
	/**
	 * Palauttaa Jäsenen nimen
	 * @return nimi
	 */
	public String getNimiString() {
		return nimi;
	}
}
