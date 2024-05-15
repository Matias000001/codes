package fxkelmienkerho;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

/**
 * Kerhon tehtävät, joka osaa mm. lisätä uuden tehtävän
 *
 * @author Matias
 * @version 11.5.2024
 */
public class Tehtavat {
	
	private boolean muutettu = false;
	private static final int MAX = 100;
	private String tehtavanNimi = "";
	private static int idYleinen = 1;
	private int tehtavanOmaId;
	private int omistajaJasenId;
	private int omistajaReissuId;
	private List<Tehtava> tehtavaLista = new ArrayList<>();
	private String tiedostonPerusNimi;

	/**
	 * Muodostaja jossa asetetaan tehtävän omistajareissun ja jasenen arvoiksi attribuutteihin
	 * nollat
	 */
	public Tehtavat() {
		this.omistajaReissuId = 0;
		this.omistajaJasenId = 0;
	}

	/**
	 * Muodostaja kolmella parametrilla
	 * @param tehtavaNimi
	 * @param kenelleId
	 * @param reissu
	 */
	public Tehtavat(String tehtavaNimi, int kenelleId, int reissu) {
		this.tehtavanNimi = tehtavaNimi;
		this.tehtavanOmaId = idYleinen++;
		this.omistajaJasenId = kenelleId;
		this.omistajaReissuId = reissu;
	}

	/**
	 * Lisää parametrina tuodun tehtävän alkion thetävälistaan
	 * @param tehtava, joka lisätään listaan
	 * <pre name="test">
	 * 	Tehtavat tehtavat = new Tehtavat();
	 * 	Tehtava t1 = new Tehtava(), t2 = new Tehtava();
	 * 	tehtavat.getLkm() === 0;
	 *	tehtavat.lisaa(t1); tehtavat.getLkm() === 1;
	 * 	tehtavat.lisaa(t2); tehtavat.getLkm() === 2;
	 * 	tehtavat.lisaa(t1); tehtavat.getLkm() === 3;
	 *	tehtavat.lisaa(t1); tehtavat.getLkm() === 4;
	 *	tehtavat.lisaa(t1); tehtavat.getLkm() === 5;
	 * </pre>
	 */
	public void lisaa(Tehtava tehtava) {
		tehtavaLista.add(tehtava);
	}

	/**
	 * Palauttaa listaan koon
	 * @return listan koko
	 */
	public int getLkm() {
		return tehtavaLista.size();
	}

	/**
	 * Haetaan kaikki reissun tehtavat
	 * @param tunnusnro jäsenen tunnusnumero jolle tehtavia haetaan
	 * @return tietorakenne jossa viiteet löydetteyihin tehtaviin
	 */
	public List<Tehtava> annaTehtavat(int reissuId) {
		List<Tehtava> loydetyt = new ArrayList<Tehtava>();
		for (Tehtava tehtava : tehtavaLista)
			if (tehtava.getOmistajaReissu() == reissuId)
				loydetyt.add(tehtava);
		return loydetyt;
	}
	
	/**
	 * Haetaan kaikki reissun tehtavat
	 * @param tunnusnro jäsenen tunnusnumero jolle tehtavia haetaan
	 * @return tietorakenne jossa viiteet löydetteyihin tehtaviin
	 */
	public List<Tehtava> annaTehtavat() {
		List<Tehtava> loydetyt = new ArrayList<Tehtava>();
		for (Tehtava tehtava : tehtavaLista)
				loydetyt.add(tehtava);
		return loydetyt;
	}

	/**
	 * Lisää tehtävän listaan ja asettaa sille omistaja reissun attribuuttiin
	 * @param tehtava
	 * @param reissuId
	 */
	public void lisaaTehtava(Tehtava tehtava, int reissuId) {
		tehtavaLista.add(tehtava);
		tehtava.setOmistajaReissu(reissuId);
	}

	// ============= TALLETUS ================

	/**
	 * Tallentaa tehtävän tiedostoon.
	 * @param tied tallennettavan tiedoston nimi
	 * @throws SailoException jos tallennus epäonnistuu
	 */
	public void tallenna(String tied) throws SailoException {
		File ftied = new File(tied + "/tehtavat.dat");
		try (PrintStream fo = new PrintStream(new FileOutputStream(ftied, false))) {
			for (Tehtava tehtava : tehtavaLista) {
				if (tehtava != null) {
					fo.println(tehtava.toString());
				}
			}
		} catch (FileNotFoundException ex) {
			throw new SailoException("Tiedosto " + ftied.getAbsolutePath() + " ei aukea");
		}
	}

	/**
	 * Palauttaa viitteen tehtävälistassa olevaan tehtävään id numeron perusteella
	 * @param id jonka perusteella tehtävä haetaan
	 * @return viite haettuun tehtävään tai null
	 */
	private Tehtava anna(int id) {
		for (Tehtava tehtava: tehtavaLista) {
			if (tehtava.getTehtavaNro() == id) {
				return tehtava;
			}
		}
		return null;
	}
	
	// ======== TIEDOSTOSTA LUKU =============
	

	/** 
	 * Lukee tiedostosta tiedot käsittelee ne ja lisää sen perusteella oliot listaan  
	 * @param tiedoston nimi
	 * @throws SailoException
	 */
	public void lueTiedostosta(String tied) throws SailoException {
		String tiedostonNimi = tied + "/tehtavat.dat";
		File ftied = new File(tiedostonNimi);
		try (Scanner fi = new Scanner(new FileInputStream(ftied))) {
			while (fi.hasNext()) {
				String s = "";
				s = fi.nextLine();
				Tehtava tehtava = new Tehtava();
				tehtava.parse(s);
				lisaa(tehtava);
			}
		} catch (FileNotFoundException e) {
			throw new SailoException("Ei saa luettua tiedoston " + tied);
		} catch (IOException e) {
			throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
		}
	}
	
	/**
	 * Pääohjelma testaamista varten
	 * @param args ei käytössä
	 * @throws SailoException
	 */
	public static void main(String[] args) throws SailoException {
		Tehtavat tehtavat = new Tehtavat();
		try {
			tehtavat.lueTiedostosta("src");
		} catch (SailoException e) {
			System.err.println("Ei voi lukea" + e.getMessage());
		}
		Tehtava tehtava = new Tehtava("Osta luvat"); 
		Tehtava tehtava2 = new Tehtava("Keitä kahvit");
		tehtava.rekisteroi();
		tehtava2.rekisteroi();
		tehtavat.lisaa(tehtava);
		tehtavat.lisaa(tehtava2);
		System.out.println("========== Tehtävät testi =======");
		
		int i = 0;
		for (Tehtava teht : tehtavat.annaTehtavat()) {
		    System.out.println("Tehtävä nro: " + i++);
		    if (teht != null) {
		        teht.tulosta(System.out);
		    } else {
		        System.out.println("Tehtävä oli null!");
		    }
		}

		try {
			tehtavat.tallenna("src");
		} catch (SailoException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Asettaa tiedostolle nimen attribuutteihin
	 * @param nimi
	 */
	public void setTiedostonPerusNimi(String nimi) {
		this.tiedostonPerusNimi = nimi;
		
	}

	/**
	 * Korvaa tehtävälistassa olevan tehtävän muokatulla tehtävällä
	 * @param tehtava
	 * @throws SailoException
	 */
	public void korvaaTaiLisaa(Tehtava tehtava) throws SailoException {
	    int id = tehtava.getTehtavaNro();
	    for (int i = 0; i < tehtavaLista.size(); i++) {
	        Tehtava t = tehtavaLista.get(i);
	        if (t.getTehtavaNro() == id) {
	            tehtavaLista.set(i, tehtava);
	            muutettu = true;
	            return;
	        }
	    }
	    lisaa(tehtava);
	}

	/**
	 * Poistaa tehtävä listasta parametrina tuodun tehtävän
	 * @param tehtava
	 */
	public void poista(Tehtava tehtava) {
		tehtavaLista.remove(tehtava);
	}
}
