package pl.topteam.przeniesienieBazyTTMieszkanie;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashSet;

import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.MazoviaDbfFileReader;

public class ImportSzablonowDecyzjiDBF {
	static Szablon[] tablicaSzablonow;
	String path = Okno.getPathToDM_KTXT();
	public void importujSzablony() throws FileNotFoundException, DBFException {
		Long czasStart = System.nanoTime();
	   	File dwni = new File(path);
		InputStream is = new FileInputStream(dwni);
		MazoviaDbfFileReader reader = new MazoviaDbfFileReader (is);
		reader.setCharactersetName(reader.getCharactersetName());
		Integer iloscRekordow = reader.getRecordCount();
		String[][] kodySzablonu = new String[3][reader.getRecordCount()];
		

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		/**
		 * Wczytanie zawartości pliku DM_KTXT.dbf		
		 */
		for (int i=0; i<(reader.getRecordCount()); i++) {
			Object aobj[] = reader.nextRecord();
			kodySzablonu[0][i] = ((String)aobj[0]).trim();
			kodySzablonu[1][i] = ((String)aobj[2]).trim();
			kodySzablonu[2][i] = ((String)aobj[5]).trim();
		}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/**
		 * Obróbka zawartości pliku DM_KTXT.dbf		
		 */
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/**
		 * Wybiera z tablicy unikalne kody rodzajów decyzji 
		 */
		Szablon szabla = new Szablon(); 
		HashSet<String> hasz= new HashSet<String>();
		for (int i=0; i<kodySzablonu[0].length; i++) {
			hasz.add(kodySzablonu[0][i]);//unikalne kody decyzji 
		}
		String[] a = new String[hasz.size()];
		hasz.toArray(a);
		Szablon[] tablicaSzablonow = new Szablon[hasz.size()];
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/**
		 * Sortowanie tablicy po wierszach
		 */
		
		
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		/**
		 * Podział szablonów na poszczególne rodzaje + składanie treści szablonu oraz nazwy
		 */
		for (int j=0; j<hasz.size(); j++) {
			for (int i=0; i<reader.getRecordCount(); i++) {
				if (kodySzablonu[0][i].equals(a[j])) {
					szabla.setNazwa(kodySzablonu[0][i]);
					szabla.setTresc(szabla.getTresc()+kodySzablonu[2][i]+"\n");
					szabla.setNrLinijki(kodySzablonu[1][i]);
				} 
			}
			tablicaSzablonow[j] = szabla; 
		}
		System.out.print("Liczba rekordow \n");
		System.out.print(tablicaSzablonow.length);
		System.out.print("\n");
		for (int i=0; i<tablicaSzablonow.length; i++) {
			System.out.print("Szablon nr "+i+tablicaSzablonow[i].getNazwa()+tablicaSzablonow[i].getTresc()+"Kolejny Szablon \n\n\n\n");
		}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		Long czasStop = System.nanoTime();
		System.out.print("Wygenerowano w :"+((czasStop-czasStart)/1000000000)+" s \n");
		Float czasPracy = Float.valueOf(czasStop)-Float.valueOf(czasStart);
		Double czasPracyNaRekord = Double.valueOf(czasPracy)/Double.valueOf(iloscRekordow);
		System.out.print("Co daje "+czasPracyNaRekord/1000000000+" sekund na każdy z " +iloscRekordow + " rekordów \n");
	}
}