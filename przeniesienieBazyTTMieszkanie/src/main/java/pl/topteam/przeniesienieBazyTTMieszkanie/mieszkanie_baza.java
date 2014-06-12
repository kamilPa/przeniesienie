package pl.topteam.przeniesienieBazyTTMieszkanie;

import java.io.FileNotFoundException;

import com.linuxense.javadbf.DBFException;

import pl.topteam.przeniesienieBazyTTMieszkanie.ImportDBF;

public class mieszkanie_baza {   
   
 

public void importerDlaZarzadcy()  {   
		try {
			new ImportDBF().importujZarzadce();
		} catch (DBFException e) {
			e.printStackTrace();
		} catch (FileNotFoundException es) {
			es.printStackTrace();
		}
	}
   public void importerDlaWnioskodawcy()  {   
 		try {
 			new WnioskodawcaImportDBF().importujWnioskodawce();
 		} catch (DBFException e) {
 			e.printStackTrace();
 		} catch (FileNotFoundException es) {
 			es.printStackTrace();
 		}
 	}
   public void importerDlaWniosku()  {   
		try {
			new WniosekImportDBF().importujWniosek();
		} catch (DBFException e) {
			e.printStackTrace();
		} catch (FileNotFoundException es) {
			es.printStackTrace();
		}
	}
   public void importerDlaSzablonu() {
	   try {
		   new ImportSzablonowDecyzjiDBF().importujSzablony();
	   } catch (DBFException e) {
		   e.printStackTrace();
	   } catch (FileNotFoundException es) {
		   es.printStackTrace();
	   }
   }
   public void importerDlaDecyzji() {
	   try {
		new DecyzjaImportDBF().importujDecyzje();
	} catch (FileNotFoundException | DBFException e) {
		e.printStackTrace();
	}
   }
   public void nowaBaza() {
	   new NowaBaza().utworzBaze();
   }
   /**
    * Funkcja do celów testowych
    * @param nazwaTabeli
    * @param nazwaSzukanegoElementu
    * @throws FileNotFoundException
    * @throws DBFException
    */
   public void sprawdzPobieranieID(String nazwaTabeli, String nazwaSzukanegoElementu) throws FileNotFoundException, DBFException {
	   int a = new ImportDBF().pobierzId(nazwaTabeli, nazwaSzukanegoElementu);
	   System.out.print(a);
   }
   public static void main(String[] args)  {	
	   
	   @SuppressWarnings("unused")
	   Okno o = new Okno();
	}
}