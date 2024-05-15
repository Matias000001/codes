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

/**
 * Kerhon reissut, joka mm. ylläpitää listaa reissuista
 * 
 * @author Matias
 * @version 11.5.2024
 */
public class Reissut implements Iterable<Reissu>{
    
	private String tiedostonPerusNimi;
	
	/**
	 * Täällä asuu reissut
	 */
    private List<Reissu> reissut = new ArrayList<>();

    /**
     * Oletusmuodostaja
     */
    public Reissut() {
	}

	/**
	 * Lisää uuden reissun tietorakenteeseen.
	 * @param lisättävään jäseneen viite
	 * @example
	 * <pre name="test">
	 * 	 Reissut reissut = new Reissut();
	 * 	 Reissu r1 = new Reissu(), r2 = new Reissu();
	 * 	 reissut.getReissuLkm() === 0;
	 *	 reissut.lisaa(r1); reissut.getReissuLkm() === 1;
	 * 	 reissut.lisaa(r2); reissut.getReissuLkm() === 2;
	 * 	 reissut.lisaa(r1); reissut.getReissuLkm() === 3;
	 *	 reissut.lisaa(r1); reissut.getReissuLkm() === 4;
	 *	 reissut.lisaa(r1); reissut.getReissuLkm() === 5;
	 * </pre>
	 */
	public void lisaa(Reissu reissu) {
        reissut.add(reissu);
    }
	

	/**
	 * Palauttaa viitteen haluttuun reissuun
	 * @param reissunId numero
	 * @return viite reissu olioon
	 * @throws IndexOutOfBoundsException
	 */
    public Reissu annaReissu(int i) throws IndexOutOfBoundsException {
        if (i < 0 || i >= reissut.size()) throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return reissut.get(i);
    }

    /**
     * Palauttaa kerhon reissujen lukumäärän
     * @return reissujen lukumäärä
     */
    public int getReissuLkm() {
        return reissut.size();
    }

    /**
     * Poistaa halutun reissun listasta iteraattorilla
     * @param reissun id numero
     */
    public void poistaReissu(int id) {
        for (Iterator<Reissu> iterator = reissut.iterator(); iterator.hasNext();) {
            Reissu reissu = iterator.next();
            if (reissu.getReissuId() == id) {
                iterator.remove();
                break;
            }
        }
    }

    
 // ============= TALLETUS ================

    
 	/**
 	 * Tallentaa reissut tiedostoon
 	 * @param tied tallennettavan tiedoston nimi
 	 * @throws SailoException jos tallennus epäonnistuu
 	 */
 	public void tallenna(String tied) throws SailoException {
 		File ftied = new File(tied + "/reissut.dat");
 		try (PrintStream fo = new PrintStream(new FileOutputStream(ftied, false))) {
 			for (Reissu reissu : reissut) {
 				if (reissu != null) {
 					fo.println(reissu.toString());
 				}
 			}
 		} catch (FileNotFoundException ex) {
 			throw new SailoException("Tiedosto " + ftied.getAbsolutePath() + " ei aukea");
 		}
 	}

 	/**
 	 * Palauttaa viitteen reissuun jonka viite annetaan parametrina
 	 * @param reissun id
 	 * @return viite reissuun
 	 */
 	public Reissu anna(int id) {
 		for (Reissu reissu: reissut) {
 			if (reissu.getReissuId() == id) {
 				return reissu;
 			}
 		}
 		return null;
 	}
 	
 	// ======== TIEDOSTOSTA LUKU =============
 	

 	/**
 	 * Lukee tiedostosta tallennettujen reissujen tiedot ja luo oliot ja asettelee ne listaan
 	 * @param tied
 	 * @throws SailoException
 	 */
 	public void lueTiedostosta(String tied) throws SailoException {
 		String tiedostonNimi = tied + "/reissut.dat";
 		File ftied = new File(tiedostonNimi);
 		try (Scanner fi = new Scanner(new FileInputStream(ftied))) {
 			while (fi.hasNext()) {
 				String s = "";
 				s = fi.nextLine();
 				Reissu reissu = new Reissu();
 				reissu.parse(s); // voisi palauttaa jotakin??
 				lisaa(reissu);
 			}
 		} catch (FileNotFoundException e) {
 			throw new SailoException("Ei saa luettua tiedoston " + tied);
 		} catch (IOException e) {
 			throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
 		}
 	}
 	
 	/**
 	 * Pääohjelma testausta varten
 	 * @param args - ei käytössä
 	 * @throws SailoException
 	 */
 	public static void main(String[] args) throws SailoException {
		Reissut reissutt = new Reissut();
		try {
			reissutt.lueTiedostosta("src");
		} catch (SailoException e) {
			System.err.println("Ei voi lukea" + e.getMessage());
		}
		Reissu reissu = new Reissu("Rosvokoplan kokoontuminen","01.01.2025", "Erämaa", "www.omalinkki.fi"); 
		Reissu reissu2 = new Reissu("Ammatikalastajien vkloppu", "06.06.2024", "Inarijärvi", "Ei kuvia");
		reissu.rekisteroi();
		reissu2.rekisteroi();		
		reissutt.lisaa(reissu);
		reissutt.lisaa(reissu2);
		System.out.println("========== Reissut testi =======");
		int i = 0;
		for (Reissu reis : reissutt) {
		    System.out.println("Reissu nro: " + i++);
		    if (reis != null) {
		        reis.tulosta(System.out);
		    } else {
		        System.out.println("Reissu oli null!");
		    }
		}
		try {
			reissutt.tallenna("src");
		} catch (SailoException e) {
			e.printStackTrace();
		}
	}
 	
 	/**
 	 * Palauttaa viitteen uuteen reissu listaan
 	 * @return viite uuteen reissulistaan
 	 */
 	public List<Reissu> annaReissut() {
		List<Reissu> loydetyt = new ArrayList<Reissu>();
		for (Reissu reissu : reissut)
				loydetyt.add(reissu);
		return loydetyt;
	}
 	
 	 @Override
     public Iterator<Reissu> iterator() {
         return reissut.iterator();
     }

   /**
     * Asettaan tiedoston nimen
 	 * @param nimi
 	 */
	public void setTiedostonPerusNimi(String nimi) {
		this.tiedostonPerusNimi = nimi;
		
	}

	/**
	 * Palauttaa reissun nimen merkkijonona id numeron perusteella
	 * @param etsittävän reissun id numero
	 * @return reissun nimi merkkijonona
	 */
	public String annaReissunNimi(int i) {
		return reissut.get(i).getReissuNimi();
	}

	/**
	 * Korvaa muokatun reissun vanhan reissun paikalle
	 * @param reissu joka on muokattt
	 */
	public void korvaaTaiLisaa(Reissu r) {
	    for (int i = 0; i < reissut.size(); i++) {
	        Reissu reissu = reissut.get(i);
	        if (reissu != null && reissu.getReissuId() == r.getReissuId()) {
	            reissut.set(i, r);
	            return;
	        }
	    }
	    reissut.add(r);
	}

 	
}

