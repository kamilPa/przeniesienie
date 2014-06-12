package pl.topteam.przeniesienieBazyTTMieszkanie;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.linuxense.javadbf.*; 

public class ImportDBF {
	   String path = Okno.getPathToDM_KZAR();
	   Connection conn = BaseConnection.connectToBase();
	   Statement stmt = BaseConnection.stmt();
	   
	/**
	 * wyszukuje element w bazie danych 
	 * @param nazwaTabeli ulica lub miejscowosc 
	 * @param nazwa nazwa elementu który ma być sprawdzony
	 * @return zwraca prawdę jeśli istnieje taki element w bazie i falsz w przeciwnym wypadku
	 */
	public Boolean sprawdzPrzedTworzeniem(String nazwaTabeli, String nazwa) {
		if (Strings.isNullOrEmpty(nazwa)) {
			nazwa = "uzupełnij dane";
		}
		nazwa = nazwa.trim();
			try {
				Preconditions.checkNotNull(nazwaTabeli);
				String sql = null;
				if (nazwaTabeli=="MIEJSCOWOSC") {
					sql = "SELECT * FROM MIEJSCOWOSC a WHERE a.NAZWA = '"+nazwa+"'";
					System.out.print(sql);
					System.out.print(" dla miejscowosci");
				} if (nazwaTabeli=="ULICA") {
					sql = "SELECT * FROM ULICA a WHERE a.NAZWA = '"+nazwa+"'";
					System.out.print(sql);
					System.out.print(" dla ulicy");
				} if (nazwaTabeli=="BANK") {
					sql = "SELECT * FROM BANK a WHERE a.NAZWA = '"+nazwa+"'";
					System.out.print(sql);
					System.out.print(" dla banku");
				} 
				ResultSet rs = stmt.executeQuery(sql);
				System.out.print("\n tera Ci drukne wynik: ");
				int id=0;
				while (rs.next()) {
					id++;
				}
				if (id==0) {
					System.out.print("Stwierdzam że nie ma tego w bazie bo miód - 'Dolan' ");
					return true;
				} else {
					System.out.print("Stwierdzam że jest to w bazie bo zielone - 'Dolan'");
					return false;
				}	  
			} catch(SQLException se) {
				se.printStackTrace();
				return false;
			} catch(Exception e){
				e.printStackTrace();
				return false;
			}      
	}
	/**
	 * Pobiera ID istniejącego elementu 
	 * @param nazwaTabeli miejscowosc lub ulica
	 * @param nazwaSzukanegoElementu nazwa miejscowosci lub ulicy
	 * @return zwraca id elementu potrzebne dla poprawnego utworzenia adresu
	 */	
	public Integer pobierzId(String nazwaTabeli, String nazwaSzukanegoElementu) {
		if (Strings.isNullOrEmpty(nazwaSzukanegoElementu)) {
			nazwaSzukanegoElementu = "uzupełnij dane";
		}
		nazwaSzukanegoElementu = nazwaSzukanegoElementu.trim();
			   try{
			      String sql = null;
			      if (nazwaTabeli== "MIEJSCOWOSC") {
			    	  sql = "SELECT ID FROM MIEJSCOWOSC WHERE NAZWA = '"+nazwaSzukanegoElementu+"'";
			      } if (nazwaTabeli == "ULICA" ) {
			    	  sql = "SELECT ID FROM ULICA WHERE NAZWA = '"+nazwaSzukanegoElementu+"'";
			      } if (nazwaTabeli == "BANK" ) {
			    	  sql = "SELECT ID FROM BANK WHERE NAZWA = '"+nazwaSzukanegoElementu+"'";
			      }
			      System.out.print("Zmienna sql= "+sql+"\n");
			      ResultSet rs = stmt.executeQuery(sql);
			      int id=0;
			      while (rs.next()) {
			    	  id  = rs.getInt("ID");
			      }
			      System.out.print("Pobrane ID to: ");
			      System.out.print(id);
			      System.out.print("\n"+"");
			      return id;
			   } catch(SQLException se) {
				   se.printStackTrace();
				   return null;				   
			   } catch(Exception e){
				   e.printStackTrace();
				   return null;
			   }      
	}
/**
 	 * Tworzy nowy rekord w bazie
 * @param nazwaTabeli MIEJSCOWOŚĆ LUB ULICA
 * @param nazwa Wartość nazwa ulicy lub miejscowosci 
 * @return zwraca nowe ID potrzebne dla utworzenia adresu zawierające daną ulice czy miejscowość
 */
	public Integer utworz(String nazwaTabeli, String nazwa) {
		if (Strings.isNullOrEmpty(nazwa)) {
			nazwa = "uzupełnij dane";
		}
		Ulica ul = new Ulica();
		nazwa = nazwa.trim();
		String sql;
			   try {
			      sql = "SELECT * FROM "+nazwaTabeli+"";
			      System.out.print("\nzmienna sql= "+sql+"\n");
			   	  ResultSet rs = stmt.executeQuery(sql);
			   	  int id=0;
			   	  while (rs.next()) {
			   		  id++;
			   	  }  
				  System.out.print("Ustalone id: "+id+"\n");
			      ul.setId(id);
			      ul.setNazwa(nazwa);
			      PreparedStatement pstm;
			      sql = "INSERT INTO "+nazwaTabeli+" (ID, NAZWA) VALUES ( (?), (?) )";
			      pstm = conn.prepareStatement(sql);
			      pstm.setInt(1, ul.getId());
			      pstm.setString(2, ul.getNazwa()); 
			      pstm.executeUpdate();
			      pstm.close();
			      return ul.getId();
			   }catch(SQLException se) {
				   se.printStackTrace();
				   return null;				   
			   }catch(Exception e){
				   e.printStackTrace();
				   return null;
			   }  
	}
	/**
	 * Tworzy nowy adres
	 * @param ulicaId  ID ulicy (z funkcji zewnętrznej)
	 * @param mscId j.w. ale dla miejscowosci 
	 * @param nrDomu zwykły string
	 * @param nrLok zwykły string
	 * @param kodPocztowy zwykły string
	 * @return 
	 * Zwraca ID nowo utworzonego adresu
	 * 
	 */
	public Integer utworzAdres(Integer ulicaId, Integer mscId, String nrDomu, String nrLok, String kodPocztowy) {
				String sql;
			   try {
			      sql = "SELECT * FROM ADRES";
		    	  System.out.print("\nzmienna sql= "+sql+"\n");
		    	  ResultSet rs = stmt.executeQuery(sql);
		    	  Integer adresId=0;
		    	  while (rs.next()) {
		    	    adresId++;
		    	  }
		    	  
		    	  System.out.print("Ustalone id: "+adresId+"\n");
			      PreparedStatement pstm;
			      sql = "INSERT INTO ADRES (ID, NR_DOMU, NR_MIESZKANIA, KOD_POCZTOWY, ULICA_ID, MIEJSCOWOSC_ID, GMINA_ID, WOJEWODZTWO_ID, POCZTA_ID ) VALUES ( (?), (?), (?), (?), (?), (?), (?), (?), (?) )";
			      System.out.print("Uruchamiam zapytanie tworzące adres \n");
			      pstm = conn.prepareStatement(sql);
			      pstm.setString(1, adresId.toString());
			      pstm.setString(2, nrDomu);
			      pstm.setString(3, nrLok);
			      pstm.setString(4, kodPocztowy);
			      pstm.setString(5, ulicaId.toString());
			      pstm.setString(6, mscId.toString());
			      pstm.setString(7, null);
			      pstm.setString(8, null);
			      pstm.setString(9, null);
			      pstm.executeUpdate();
			      pstm.close();
			      return adresId;
			   } catch(SQLException se) {
				   se.printStackTrace();
				   return null;				   
			   } catch(Exception e){
				   e.printStackTrace();
				   return null;
			   }
	}
	/**
	 * Funkcja tworzy nowe konto
	 * @param numer
	 * @param bankId
	 * @return zwraca id nowoutworzonego konta
	 */
	public Integer utworzKonto(String numer, Integer bankId) {
		if (Strings.isNullOrEmpty(numer)) {
			return null;
		}
		String sql;
		try {
		      sql = "SELECT * FROM KONTO";
	    	  System.out.print("\nzmienna sql= "+sql+"\n");
	    	  ResultSet rs = stmt.executeQuery(sql);
	    	  Integer kontoId=0;
	    	  while (rs.next()) {
	    	    kontoId++;
	    	  }
	    	  System.out.print("Ustalone id: "+kontoId+"\n");
		      PreparedStatement pstm;
		      sql = "INSERT INTO KONTO (ID, NUMER, BANK_ID) VALUES ( (?), (?), (?))";
		      System.out.print(sql);
		      pstm = conn.prepareStatement(sql);
		      pstm.setString(1, kontoId.toString());
		      pstm.setString(2, numer);
		      pstm.setInt(3, bankId);
		      pstm.executeUpdate();
		      pstm.close();
		      return kontoId;
		   } catch(SQLException se) {
			   se.printStackTrace();
			   return null;				   
		   } catch(Exception e){
			   e.printStackTrace();
			   return null;
		   }      
	}
	/**
	 * Funkcja tworzy nowego zarządce
	 * @param nazwa nazwa zarządcy
	 * @param adresId Id adresu zarządcy
	 * @param kontoId Id konta zarządcy
	 * Zwraca ID nowoutworzonego zarządcy 
	 * 
	 * dodatkowe info
	 * wypłada gotówką - 36
	 * przelewem - 37
	 * przekazem - 38
	 * w kasie - 39
	 */
	public Integer utworzZarzadce(String nazwa, Integer adresId, Integer kontoId, String nrAkt, String rodzajWyplaty) {
		String sql;
		if (Strings.isNullOrEmpty(nazwa)) {
			nazwa = "uzupełnij dane";
		}
			   try {
			      sql = "SELECT * FROM ZARZADCA";
		    	  System.out.print("\nzmienna sql= "+sql+"\n");
		    	  ResultSet rs = stmt.executeQuery(sql);
		    	  Integer zarzadcaId=0;
		    	  while (rs.next()) {
		    	    zarzadcaId++;
		    	  }
		    	  System.out.print(rodzajWyplaty);
		    	  rodzajWyplaty=rodzajWyplaty.trim();
		    	  Integer typWyplaty = 1;
		    	  if (Strings.isNullOrEmpty(rodzajWyplaty)) {
		    		  typWyplaty = null;
		    	  }
		    	  if (rodzajWyplaty.equals("pl")) {
		    		  typWyplaty= 37;
		    	  }
		    	  if (rodzajWyplaty.equals("gt")) {
		    		  typWyplaty = 36;
		    	  }
		    	 		    	  
			    System.out.print("Ustalone id: "+adresId+"\n");
			      PreparedStatement pstm;
			      sql = "INSERT INTO ZARZADCA (ID, NAZWA, RODZAJ, ADRES_ID, KONTO_ID, FAX, TELEFON, KARTA_PRZEDPLACONA, UPOW_IMIE, UPOW_NAZWISKO, UPOW_NR_DOWODU, UPOW_ADRES_ID, UWAGI, SPOSOB_WYPLATY ) VALUES ( (?), (?), (?), (?), (?), (?), (?), (?), (?), (?), (?), (?), (?), (?) )";
			      pstm = conn.prepareStatement(sql);
			      pstm.setInt(1, zarzadcaId);
			      pstm.setString(2, nazwa);
			      pstm.setString(3, null);
			      pstm.setInt(4, adresId);
			      try {
			    	  pstm.setInt(5, kontoId);
			      } catch (NullPointerException e) {
			    	  pstm.setString(5, null);
			      }
			      pstm.setString(6, null);
			      pstm.setString(7, null);
			      pstm.setString(8, null);
			      pstm.setString(9, null);
			      pstm.setString(10, null);
			      pstm.setString(11, null);
			      pstm.setString(12, null);
			      pstm.setString(13, null);
			      pstm.setInt(14, typWyplaty);
			      pstm.executeUpdate();
			      pstm.close();			     
			      System.out.print("Utworzono zarządce o następujących parametrach \n ID: "+zarzadcaId.toString()+" \n NAZWA: "+nazwa+"\n AdresId: "+adresId.toString()+"\n KontoId: "+"\n"+typWyplaty+"\n");
			   return zarzadcaId;
			   }catch(SQLException se) {
				   se.printStackTrace();
				   return null;
			   }catch(Exception e){
				   e.printStackTrace();
				   return null;
			   }	
	}
	public String ogarnijNrKonta(Object aobj[], int czyWnioskodawca) {
		int numerWiersza = 0;
		if (czyWnioskodawca == 1) {
			numerWiersza = 67;
		} else if (czyWnioskodawca == 0) {
			numerWiersza = 17;
		}
		try {	
			String numer= (String) aobj[numerWiersza];
			if (Strings.isNullOrEmpty(numer)) {
				return "";
			} else {
			System.out.print("Nr konta to: "+numer);
			return numer;
			}
		} catch (NullPointerException e) {
			return "";
		}
	}
	/**
	 * Główna funkcja importu zarządcy
	 * @throws FileNotFoundException
	 * @throws DBFException
	 */
	public void importujZarzadce() throws FileNotFoundException, DBFException  { //GŁÓNNA FUNKCJA
		
		Long czasStart = System.nanoTime();
		File kzar = new File(path);
		InputStream is = new FileInputStream(kzar);
		MazoviaDbfFileReader reader = new MazoviaDbfFileReader (is);
            Integer iloscRekordow = reader.getRecordCount(); //50
            System.out.println(reader.getFieldCount()); //24 równe aobj.length
            String[] data_mod = new String[reader.getRecordCount()];
            String[] nr_akt = new String[reader.getRecordCount()];
            Integer[] id_zarz = new Integer[reader.getRecordCount()];
            String[] rodzajWyplaty = new String[reader.getRecordCount()];
            for (int i=0; i<(reader.getRecordCount()); i++) {
            	Object aobj[] = reader.nextRecord();
            	String nrDomu = ((String)aobj[12]);
            	String nrLok = ((String)aobj[13]);
            	id_zarz[i] = 
	            	utworzZarzadce( //tworzenie zarządcy trzy argumenty 1. nazwa 2. adresId 3. kontoId
	            			((String)aobj[3]).trim()+" "+((String)aobj[4]).trim(),  //argument pierwszy - nazwa zarządcy
		            			utworzAdres( 
		            	            	sprawdzPrzedTworzeniem("ULICA",	(String)aobj[11].toString()) ? utworz("ULICA", (String)aobj[11].toString()) : pobierzId("ULICA", (String)aobj[11].toString()) ,
		            	            	sprawdzPrzedTworzeniem("MIEJSCOWOSC", (String)aobj[10].toString()) ? utworz("MIEJSCOWOSC", (String)aobj[10].toString()) : pobierzId("MIEJSCOWOSC", (String)aobj[10].toString()) ,		
		            	            	nrDomu, 
		            	            	nrLok, 
		            	            	aobj[9].toString()
		            	            	),    // argument drugi adres id
		            	        utworzKonto(
		            	        		ogarnijNrKonta(aobj, 0), 
		            	        		sprawdzPrzedTworzeniem("BANK", (String)aobj[16]) ? utworz("BANK", (String)aobj[16]) : pobierzId("BANK", (String)aobj[16]) 
		            	        		),     //argument trzeci konto id 
		            	        aobj[0].toString(),
		            	        aobj[18].toString()
	            	);
            	nr_akt[i] = aobj[0].toString();
            	data_mod[i] = aobj[23].toString();
            	rodzajWyplaty[i] = aobj[18].toString();
            	System.out.print("\n");
            }
    		String sql;
    			   try {
    			      sql = "CREATE TABLE NRAKTZARZ (ID_ZARZADCY int, NUMER_AKT varchar(12) );";
    			      System.out.print("Uruchamiam tworzenie tymczasowej tabeli \n");
    			      conn.createStatement().execute(sql);
    			      System.out.print("Utworzona!");
    			   }catch(SQLException se) {
    				   se.printStackTrace();
    			   }catch(Exception e){
    				   e.printStackTrace();
    			   }
    			   for (int i=0; i<id_zarz.length; i++) {
    	            	System.out.print(id_zarz[i]+" --- "+nr_akt[i]+"\n");
	    	    			   try {
	    	    			      PreparedStatement pstm;
	    	    			      sql = "INSERT INTO NRAKTZARZ (ID_ZARZADCY, NUMER_AKT) VALUES ( (?), (?) )";
	    	    			      pstm = conn.prepareStatement(sql);
	    	    			      pstm.setInt(1, id_zarz[i]);
	    	    			      pstm.setString(2, nr_akt[i]);
	       	    			      pstm.executeUpdate();
	    	    			      pstm.close();
	    	    			   }catch(SQLException se) {
	    	    				   se.printStackTrace();
	    	    			   }catch(Exception e){
	    	    				   e.printStackTrace();
	    	    			   }
    			   }
    			   BaseConnection.closeStatement(stmt);
    			   BaseConnection.disconnect(conn);
	Long czasStop = System.nanoTime();
	System.out.print("Wygenerowano w :"+((czasStop-czasStart)/1000000000)+" s \n");
	Float czasPracy = Float.valueOf(czasStop)-Float.valueOf(czasStart);
	Double czasPracyNaRekord = Double.valueOf(czasPracy)/Double.valueOf(iloscRekordow);
	
	System.out.print("Co daje "+czasPracyNaRekord/1000000000+" sekund na każdy z " +iloscRekordow + " rekordów \n");
	}
}