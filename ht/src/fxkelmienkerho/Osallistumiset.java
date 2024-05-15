package fxkelmienkerho;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import fi.jyu.mit.fxgui.Dialogs;

/**
 * Tämä luokka ylläpitää listaa osallistumisista reissuille
 * 
 * @author Matias
 * @version 11.5.2024
 */
public class Osallistumiset {

	/**
	 * Täällä asuu osallistumiset
	 */
	private ArrayList<Osallistuminen> alkio;

	/**
	 * Oletusmuodostaja joka alustaa, osallisumisien listan
	 */
	public Osallistumiset() {
		this.alkio = new ArrayList<>();
	}

	/**
	 * Palauttaa viitteen osallistumis listaan
	 * @return viite alkio nimiseen listaan
	 */
	public ArrayList<Osallistuminen> getOsallistumiset() {
		return alkio;
	}

	/**
	 * Asettaan parametrilistan attribuuttilistaan
	 * @param osallistumiset
	 */
	public void setOsallistumiset(ArrayList<Osallistuminen> osallistumiset) {
		this.alkio = osallistumiset;
	}

	/**
	 * Lisää parametrina tuodun osallistumisen alkio listaan
	 * @param o osallistumisolio, joka lisätään listaan
	 * <pre name="test">
	 * 	Osallistumiset osallistumiset = new Osallistumiset();
	 * 	Osallistuminen o1 = new Osallistuminen(), o2 = new Osallistuminen();
	 * 	osallistumiset.osallistumisetLkm() === 0;
	 *	osallistumiset.lisaa(o1); osallistumiset.osallistumisetLkm() === 1;
	 * 	osallistumiset.lisaa(o2); osallistumiset.osallistumisetLkm() === 2;
	 * 	osallistumiset.lisaa(o1); osallistumiset.osallistumisetLkm() === 3;
	 *	osallistumiset.lisaa(o1); osallistumiset.osallistumisetLkm() === 4;
	 *	osallistumiset.lisaa(o1); osallistumiset.osallistumisetLkm() === 5;
	 * </pre>
	 */
	public void lisaa(Osallistuminen o) {
		alkio.add(o);
	}
	
	/**
	 * Palauttaa osallistumisien lukumäärän
	 * @return lkm
	 */
	public int osallistumisetLkm() {		
		return alkio.size();
	}
	

	/**
	 * Palauttaa listan jossa on kaikki ne osallistumisoliot jotka kuuluvat sille reissulle
	 * jonka id numero annetaan parametrina
	 * @param id numero
	 * @return viite uuteen listaan 
	 */
	public ArrayList<Osallistuminen> getOsallistumisetReissulle(int num) {
		ArrayList<Osallistuminen> loydetyt = new ArrayList();
		for (Osallistuminen x : alkio) {
			if (x.getOmistajaReissu() == num) {
				loydetyt.add(x);
			}
		}
		return loydetyt;
	}

	// ======== TIEDOSTOSTA LUKU =============

	/**
	 * Lukee tallennetut tiedot tiedostosta, luo niiden perusteella oliot ja asettaa ne listaan
	 * @param tied
	 * @throws SailoException
	 */
	public void lueTiedostosta(String tied) throws SailoException {
		String tiedostonNimi = tied + "/osallistumiset.dat";
		File ftied = new File(tiedostonNimi);
		try (Scanner fi = new Scanner(new FileInputStream(ftied))) {
			while (fi.hasNext()) {
				String s = "";
				s = fi.nextLine();
				Osallistuminen osallistuminen = new Osallistuminen();
				osallistuminen.parse(s);
				lisaa(osallistuminen);
			}
		} catch (FileNotFoundException e) {
			throw new SailoException("Ei saa luettua tiedoston " + tied);
		} catch (IOException e) {
			throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
		}
	}

	/**
	 * Pääohjelma testaamista varten
	 * @param args - ei käytössä
	 * @throws SailoException
	 */
	public static void main(String[] args) throws SailoException {
		Osallistumiset osallistumiset = new Osallistumiset();
		try {
			osallistumiset.lueTiedostosta("src");
		} catch (SailoException e) {
			System.err.println("Ei voi lukea" + e.getMessage());
		}
		int omistajaReissu = 43;
		int omistajaReissu2 = 69;
		int omistajaJasen = 7;
		int omistajaJasen2 = 6;
		Osallistuminen osallistuminen = new Osallistuminen(omistajaReissu, omistajaJasen);
		Osallistuminen osallistuminen2 = new Osallistuminen(omistajaReissu2, omistajaJasen2);
		osallistuminen.rekisteroi();
		osallistuminen2.rekisteroi();
		osallistumiset.lisaa(osallistuminen);
		osallistumiset.lisaa(osallistuminen2);
		System.out.println("========== Osallistumis testi =======");
		int i = 0;
		for (Osallistuminen os : osallistumiset.annaOsallistumiset()) {
			System.out.println("Tehtävä nro: " + i++);
			if (os != null) {
				os.tulosta(System.out);
			} else {
				System.out.println("Tehtävä oli null!");
			}
		}
		try {
			osallistumiset.tallenna("src");
		} catch (SailoException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Tallentaa listan oliot tiedostoon
	 * @param tiedoston nimi
	 * @throws SailoException
	 */
	void tallenna(String tied) throws SailoException {
		File ftied = new File(tied + "/osallistumiset.dat");
		try (PrintStream fo = new PrintStream(new FileOutputStream(ftied, false))) {
			for (Osallistuminen osallistuminen : alkio) {
				if (osallistuminen != null) {
					fo.println(osallistuminen.toString());
				}
			}
		} catch (FileNotFoundException ex) {
			throw new SailoException("Tiedosto " + ftied.getAbsolutePath() + " ei aukea");
		}
	}

	/**
	 * Luo uuden listan jonne laitetaan kaikki oliot alkio listasta ja palautetaan viite uuteen listaan
	 * @return viite loydetyt listaan
	 */
	public List<Osallistuminen> annaOsallistumiset() {
		List<Osallistuminen> loydetyt = new ArrayList<Osallistuminen>();
		for (Osallistuminen osallistuminen : alkio)
			loydetyt.add(osallistuminen);
		return loydetyt;
	}

	/**
	 * Apu aliohjelma estämään ettei samaa jäsentä voida lisätä monta kertaa samalle reissulle
	 * @param reissu id
	 * @param jasen id
	 * @return false jos jäsen on jo reissulla, true jos ei
	 */
	public Boolean onko(int reissuId, int jasenId) {
		for(Osallistuminen o : alkio) { 
			if(o.getOmistajaJasen() == jasenId && o.getOmistajaReissu() == reissuId) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Poistaa kaikki osallistumiset listasta joilla on parametrina tuotu reissuId
	 * @param poistettavan reissun id
	 */
	public void poistaReissu(int reissuId) {
	    Iterator<Osallistuminen> iterator = alkio.iterator();
	    while(iterator.hasNext()) {
	        Osallistuminen o = iterator.next();
	        if(o != null && o.getOmistajaReissu() == reissuId) {
	            iterator.remove();
	        }
	    }
	}

	
	/**
	 * Poistaa osallistumisen osallistumiset listasta käyttäen iteraattoria
	 * @param poistettava osallistuminen
	 */
	public void poistaOsallistuminen(Osallistuminen z) {
	    Iterator<Osallistuminen> iterator = alkio.iterator();
	    while (iterator.hasNext()) {
	        Osallistuminen o = iterator.next();
	        if (o.getOsallistumisId() == z.getOsallistumisId()) {
	            iterator.remove(); 
	        }
	    }
	}

}
