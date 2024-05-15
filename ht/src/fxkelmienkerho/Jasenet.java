package fxkelmienkerho;

import java.io.*;
import java.util.*;


/**
 * Kerhon jäsenistö joka osaa mm. lisätä uuden jäsenen
 *
 * @author Matias
 * @version 11.5.2024
 */
public class Jasenet implements Iterable<Jasen> {
	private static final int MAX_JASENIA = 10;
	private Jasen[] jasenTaulukko = new Jasen[MAX_JASENIA];
	private int lkm;
	private String tiedostonPerusNimi = "nimet";

	/**
	 * Muodostaja testiä varten. Luo uuden apu nimisen jäsenen ja kutsuu sen metodia
	 * joka asettaa sen attribuutteihin arvot
	 */
	public Jasenet() {
		Jasen apu = new Jasen();
		apu.vastaaAkuAnkka();
	}

	/**
	 * Lisää uuden jäsenen tietorakenteeseen. 
	 * @param lisätäävän jäsenen viite.
	 * @example
	 * <pre name="test">
	 * 	#THROWS SailoException 
	 * 	Jasenet jasenet = new Jasenet();
	 * 	Jasen aku1 = new Jasen(), aku2 = new Jasen();
	 * 	jasenet.getLkm() === 0;
	 *	jasenet.lisaa(aku1); jasenet.getLkm() === 1;
	 * 	jasenet.lisaa(aku2); jasenet.getLkm() === 2;
	 * 	jasenet.lisaa(aku1); jasenet.getLkm() === 3;
	 *	jasenet.anna(0) === aku1;
	 * 	jasenet.anna(1) === aku2;
	 * 	jasenet.anna(2) === aku1;
	 * 	jasenet.anna(1) == aku1 === false;
	 * 	jasenet.anna(1) == aku2 === true;
	 * 	jasenet.anna(3) === aku1; #THROWS IndexOutOfBoundsException 
	 *	jasenet.lisaa(aku1); jasenet.getLkm() === 4;
	 *	jasenet.lisaa(aku1); jasenet.getLkm() === 5;
	 * </pre>
	 */
	public void lisaa(Jasen jasen) throws SailoException {
		if (lkm >= jasenTaulukko.length)
			throw new SailoException("Liikaa jäseniä!");
		jasenTaulukko[lkm++] = jasen;
	}

	/**
	 * Palauttaa viitteen taulukon i:teen jäseneen.
	 * @param i monennenko jäsenen viite halutaan
	 * @return viite jäseneen, jonka indeksi taulukossa on i
	 * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella
	 */
	public Jasen anna(int i) throws IndexOutOfBoundsException {
		if (i < 0 || lkm <= i)
			throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
		return jasenTaulukko[i];
	}

	/**
	 * Palauttaa kerhon jäsenten lukumäärän
	 * @return jäsenten lukumäärä
	 */
	public int getLkm() {
		return lkm;
	}

	/**
	 * Poistaa jäsenen taulukosta id numeron perusteella ja vähentää lukumäärää yhdellä
	 * @param jasenId
	 */
	public void poista(int jasenId) {
		jasenTaulukko[jasenId] = null;
		lkm--;
	}

	/**
	 * Palauttaa nimen perusteella id numeron, jos jäsentä ei löydy palauttaa -1
	 * @param nimi
	 * @return palauttaa jäsenen id numeron
	 */
	public int anna(String nimi) {
		for (int i = 0; i < lkm; i++) {
			if (jasenTaulukko[i].getNimi() == nimi) {
				return jasenTaulukko[i].getTunnusNro();
			}
		}
		return -1;
	}

	/**
	 * Hakee jasenTaulukosta annettua id:tä vastaavan jäsenen ja palauttaa viitteen siihen,
	 * muuten palauttaa null
	 * @param id
	 * @return viite jäseneen
	 */
	public Jasen haeJasen(int id) {
		for (int i = 0; i < lkm; i++) {
			if (jasenTaulukko[i].getTunnusNro() == id) {
				return jasenTaulukko[i];
			}
		}
		return null;
	}

	/**
	 * Palauttaa viitteen jäsentaulukkoon
	 * @return jasenTaulukko
	 */
	public Jasen[] haeJasenet() {
		return jasenTaulukko;
	}
	
	
	/**
	 * Pääohjelma testaamista varten
	 * @param args - ei käytössä
	 */
	public static void main(String[] args) {
		Jasenet jasenet = new Jasenet();
		try {
			jasenet.lueTiedostosta("src");
		} catch (SailoException e) {
			System.err.println("Ei voi lukea " + e.getMessage());
		}
		Jasen aku = new Jasen();
		Jasen aku2 = new Jasen();
		aku.rekisteroi();
		aku.vastaaAkuAnkka();
		aku2.rekisteroi();
		aku2.vastaaAkuAnkka();
		try {
			jasenet.lisaa(aku);
			jasenet.lisaa(aku2);
			System.out.println("========== Jäsenet testi =======");
			for (int i = 0; i < jasenet.getLkm(); i++) {
				Jasen jasen = jasenet.anna(i);
				System.out.println("Jäsen nro: " + i);
				jasen.tulosta(System.out);
			}
		} catch (SailoException ex) {
			System.out.println(ex.getMessage());
		}

		try {
			jasenet.tallenna("src");
		} catch (SailoException e) {
			e.printStackTrace();
		}
	}

	// ============= TALLETUS ================

	/**
	 * Tallentaa jäsenistön tiedostoon.
	 * @param tied tallennettavan tiedoston nimi
	 * @throws SailoException jos tallennus epäonnistuu
	 */
	public void tallenna(String tied) throws SailoException {
		File ftied = new File(tied + "/nimet.dat");
		try (PrintStream fo = new PrintStream(new FileOutputStream(ftied, false))) {
			for (int i = 0; i < getLkm(); i++) {
				Jasen jasen = anna(i);
				fo.println(jasen.toString());
			}
		} catch (FileNotFoundException ex) {
			throw new SailoException("Tiedosto " + ftied.getAbsolutePath() + " ei aukea");
		}
	}

	// ======== TIEDOSTOSTA LUKU =============

	/**
	 * Lukee tiedoston
	 * @throws SailoException
	 */
	public void lueTiedostosta() throws SailoException {
		lueTiedostosta(getTiedostonPerusNimi());
	}

	/**
	 * Lukee tiedoston, jonne jäsenet tallennettu 
	 * @param Tiedoston nimi
	 * @throws SailoException
	 */
	public void lueTiedostosta(String tied) throws SailoException {
		setTiedostonPerusNimi(tied);
		String tiedostonNimi = tied + "/nimet.dat";
		File ftied = new File(tiedostonNimi);
		try (Scanner fi = new Scanner(new FileInputStream(ftied))) {
			while (fi.hasNext()) {
				String s = "";
				s = fi.nextLine();
				Jasen jasen = new Jasen();
				jasen.parse(s);
				lisaa(jasen);
			}
		} catch (FileNotFoundException e) {
			throw new SailoException(" Ei saa luettua tiedostoa " + tiedostonNimi);
		} catch (IOException e) {
			throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
		}
	}

	/**
	 * Iteraattori luokka
	 */
	public class JasenetIterator implements Iterator<Jasen> {
		private int kohdalla = 0;

		@Override
		public boolean hasNext() {
			return kohdalla < getLkm();
		}

		@Override
		public Jasen next() throws NoSuchElementException {
			if (!hasNext())
				throw new NoSuchElementException("Ei oo");
			return anna(kohdalla++);
		}

		@Override
		public void remove() throws UnsupportedOperationException {
			throw new UnsupportedOperationException("Me ei poisteta");
		}

		public Iterator<Jasen> iterator() {
			return new JasenetIterator();
		}

		@SuppressWarnings("unused")
		public Collection<Jasen> etsi(String hakuehto, int k) {
			Collection<Jasen> loytyneet = new ArrayList<Jasen>();
			for (Jasen jasen : loytyneet) {
				loytyneet.add(jasen);
			}
			return loytyneet;
		}
	}

	/**
	 * Etsii korvattavan olion taulukosta ja lisää muokatun olion sen paikalle
	 * @param jasen
	 * @throws SailoException
	 */
	public void korvaaTaiLisaa(Jasen jasen) throws SailoException {
		int id = jasen.getTunnusNro();
		for (int i = 0; i < lkm; i++) {
			if (jasenTaulukko[i].getTunnusNro() == id) {
				jasenTaulukko[i] = jasen;
				return;
			}
		}
		lisaa(jasen);
	}

	@Override
	public Iterator<Jasen> iterator() {
		return null;
	}

	/**
	 * Palauttaa jäsenen niment id numeron perusteella
	 * @param id
	 * @return viite jäsen olioon
	 */
	public Jasen getNimi(int id) {
		for (Jasen j : jasenTaulukko) {
			if (id == j.getTunnusNro())
				return j;
		}
		return null;
	}

	/**
	 * Palauttaa jäsenen nimen id numeron perusteella
	 * @param idn
	 * @return nimi
	 */
	public String annaJasenNimi(int idn) {
		for (int i = 0; i < lkm; i++) {
			if (jasenTaulukko[i].getTunnusNro() == idn) {
				return jasenTaulukko[i].getNimi();
			}
		}	
		return null;
	}
	
	/**
	 * Asettaan tiedoston nimen
	 * @param nimi
	 */
	public void setTiedostonPerusNimi(String nimi) {
		this.tiedostonPerusNimi = nimi;
	}

	/**
	 * Palauttaa tiedoston nimen + ".dat" 
	 * @return
	 */
	public String getTiedostonPerusNimi() {
		return getTiedostonPerusNimi() + ".dat";
	}

	/**
	 * Palauttaa tiedoston nimet + ".bak"
	 * @return
	 */
	public String getBakNimi() {
		return tiedostonPerusNimi + ".bak";
	}

	/**
	 * Etsii hakuehdon mukaisien jäsenien nimet listaan ja palauttaa viitteen siihen
	 */
	public ArrayList<Jasen> etsi(String hakuehto) {
	    ArrayList<Jasen> loytyneet = new ArrayList<Jasen>();
		for (Jasen jasen : jasenTaulukko) {
	        if (jasen != null && jasen.getNimi().startsWith(hakuehto)) {
	        	loytyneet.add(jasen);
	        }
	    }
	    return loytyneet;
	}
}
