package pl.topteam.przeniesienieBazyTTMieszkanie;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.google.common.base.Strings;
import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.MazoviaDbfFileReader;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
public class WniosekImportDBF {
	 	String path = Okno.getPathToDM_WNIO();
	 	static Integer[] idWnio;
	 	static String[] numerWnio;
	 	Connection conn = BaseConnection.connectToBase();
		Statement stmt = BaseConnection.stmt();
	 	
		/**
		 * wyszukuje powiazanie z wnioskodawca
		 * @param nrWnio
		 * @return id wnioskodawcy
		 */
	   public Integer wstawTeGlupieID(String nrWnio) {
		   	int id= 0;
			nrWnio = nrWnio.trim();
				   try{
				      String sql = null;
				      if (!Strings.isNullOrEmpty(nrWnio)) {
				    	  sql = "SELECT ID_WNIOSKODAWCA FROM NRWNIO_WNIOSKODAWCA  WHERE NUMER_WNIOSKU = '"+nrWnio+"'";
				    	  System.out.print(sql);
				      }
				      ResultSet rs = stmt.executeQuery(sql); 
				      while (rs.next()) {
				    	  id  = rs.getInt("ID_WNIOSKODAWCA");
				      }
				      System.out.print("ID wnioskodawcy dla tego wniosku to: "+id+".\n");
				      return id;
				   }catch(SQLException se) {
					   se.printStackTrace();
					   return null;
				   }catch(Exception e){
					   e.printStackTrace();
					   return null;
				   }      
	   }
	   /**
	    * Tworzy wniosek
	    * @param data
	    * @param powPokoiKuchni
	    * @param brCenOg
	    * @param brCieplaWoda
	    * @param brGaz
	    * @param iloscOsob
	    * @param iloscNiepelnosprawnych
	    * @param wydatki
	    * @param dochod
	    * @param osobaId
	    * @param powLokalu
	    * @param numerWniosku
	    * @return zwraca ID nowoutworzonego wniosku
	    */
	   public Integer utworzWniosek(java.sql.Date data, Double powPokoiKuchni, Integer brCenOg, Integer brCieplaWoda, Integer brGaz, Integer iloscOsob, Integer iloscNiepelnosprawnych, Double wydatki, Double dochod, Integer osobaId, Double powLokalu, String numerWniosku, Double cenaKWh) {
			String sql;
				   try {
				      sql = "SELECT * FROM WNIOSEK";
			    	  System.out.print("\nzmienna sql= "+sql+"\n");
			    	  ResultSet rs = stmt.executeQuery(sql);
			    	  Integer wniosekId=0;
			    	  while (rs.next()) {
			    	    wniosekId++;
			    	  }
			    	  System.out.print("Ustalone id: "+wniosekId+"\n");
				      PreparedStatement pstm;
				      sql = "INSERT INTO WNIOSEK (ID, DATA_DNIA, POW_POKOI_KUCHNI, BRAK_CEN_OGRZEWANIE, BRAK_CEN_CIEPLA_WODA, BRAK_GAZ_PRZEWODOWY, ILOSC_OSOB, ILOSC_NIEPELN, WYDATKI, DOCHOD, OSOBA_ID, LACZNA_POWIERZCHNIA, NUMER, CENA_KWH_ENERGII) VALUES ( (?), (?), (?), (?), (?), (?), (?), (?), (?), (?), (?), (?), (?), (?) )";
				      pstm = conn.prepareStatement(sql);
				      pstm.setInt(1, wniosekId);
				      pstm.setDate(2, data);
				      pstm.setDouble(3, powPokoiKuchni);
				      pstm.setInt(4, brCenOg);
				      pstm.setInt(5, brCieplaWoda);
				      pstm.setInt(6, brGaz);
				      pstm.setInt(7, iloscOsob);
				      pstm.setInt(8, iloscNiepelnosprawnych);
				      pstm.setDouble(9, wydatki);
				      pstm.setDouble(10, dochod);
				      pstm.setInt(11, osobaId);
				      pstm.setDouble(12, powLokalu);
				      pstm.setString(13, numerWniosku);
				      pstm.setDouble(14, cenaKWh);
				      pstm.executeUpdate();
				      pstm.close();
				   return wniosekId;
				   }catch(SQLException se) {
					   se.printStackTrace();
					   return null;
				   }catch(Exception e){
					   e.printStackTrace();
					   return null;
				   }		
	   }
	   /**
	    * Wyciaga z uwag cene kwh
	    * @param syf i ból przemijania 
	    * @return wspaniala potrzebna wartosc 1 kwh dla wniosku
	    */
	   public Double ogarnijCenaKWh(String syf) {
		   Pattern pat = Pattern.compile("[0-9][.][0-9]{4}");
		   List<String> allMatches = new ArrayList<String>();
		   Matcher m = pat.matcher(syf);
		   while (m.find()) {
			   allMatches.add(m.group());
		   }
		   return Double.valueOf(allMatches.toArray(new String[0])[0]);
	   }
	   /**
	    * Zamienia N/T na 0/1
	    * @param doPoprawki
	    * @return
	    */
	   public Integer poprawiamyPrawdaFalsz(String doPoprawki) {
		   doPoprawki = doPoprawki.trim();
		   Integer wynik = null;
		   if (doPoprawki.equals("N")) {
			   wynik = 0;
		   } else if (doPoprawki.equals("T")) {
			   wynik = 1;
		   }
		   return wynik;
	   }
	   
	   public void importujWniosek() throws FileNotFoundException, DBFException {
		   	Long czasStart = System.nanoTime();
		   	File dwni = new File(path);
			InputStream is = new FileInputStream(dwni);
			MazoviaDbfFileReader reader = new MazoviaDbfFileReader (is);
			Integer iloscRekordow = reader.getRecordCount();
			WnioskodawcaImportDBF a = new WnioskodawcaImportDBF();
			Integer[] idWnio = new Integer[reader.getRecordCount()];
			String[] numerWnio = new String[reader.getRecordCount()];
			
			for (int i=0; i<(reader.getRecordCount()); i++) {
				Object aobj[] = reader.nextRecord();
				Double powPokoiKuchni = ((Double)aobj[19]);
				Integer iloscOsob = (((Double)aobj[26])).intValue();	
				Integer iloscInwal = ((Double)aobj[28]).intValue();	
				Double wydatki = ((Double)aobj[31]);
				Double dochod = ((Double)aobj[30]);
				String nrWnio = (((String)aobj[1])).trim();
				Double powLokalu = ((Double)aobj[17]);
				Double cenaKWh = ogarnijCenaKWh(aobj[52].toString());
				java.sql.Date data = a.getDate((java.util.Date)(aobj[73]));
				numerWnio[i] = (((String)aobj[40])).trim();
				idWnio[i] = utworzWniosek(
			   								data,
							   				powPokoiKuchni,
							   				poprawiamyPrawdaFalsz(aobj[22].toString().trim()),
							   				poprawiamyPrawdaFalsz(aobj[23].toString().trim()),
							   				poprawiamyPrawdaFalsz(aobj[24].toString().trim()),
							   				iloscOsob+iloscInwal,
							   				iloscInwal,
							   				wydatki,
							   				dochod,
							   				wstawTeGlupieID(nrWnio),
							   				powLokalu,
							   				nrWnio,
							   				cenaKWh
							);
			}
			String sql;
				   try {
	   			      PreparedStatement pstm;
				      sql = "CREATE TABLE IDWNIO_DECYZJA (ID_WNIOSEK integer, NUMER_DECYZJI varchar(25) );";
				      System.out.print("Uruchamiam tworzenie tymczasowej tabeli \n");
				      pstm = conn.prepareStatement(sql);
				      System.out.print("Zapytanie stworzone!");
				      pstm.executeUpdate(sql);
				      System.out.print("Utworzona!");
				      pstm.close();
				   }catch(SQLException se) {
					   se.printStackTrace();
				   }catch(Exception e){
					   e.printStackTrace();
				   }
				   for (int i=0; i<idWnio.length; i++) {
					   		System.out.print(idWnio[i]+" --- "+numerWnio[i]+"\n");
	           				try {
		    			      PreparedStatement pstm;
		    			      sql = "INSERT INTO IDWNIO_DECYZJA (ID_WNIOSEK, NUMER_DECYZJI) VALUES ( (?), (?) )";
		    			      pstm = conn.prepareStatement(sql);
		    			      pstm.setInt(1, idWnio[i]);
		    			      pstm.setString(2, numerWnio[i]);
	  	    			      pstm.executeUpdate();
		    			      pstm.close();
	           				}catch(SQLException se) {
		    				   se.printStackTrace();
	           				}catch(Exception e){
		    				   e.printStackTrace();
	           				}
				   }
				   BaseConnection.executeStatement("DROP TABLE NRWNIO_WNIOSKODAWCA");
				   BaseConnection.closeStatement(stmt);
				   BaseConnection.disconnect(conn);
			Long czasStop = System.nanoTime();
			System.out.print("Wygenerowano w :"+((czasStop-czasStart)/1000000000)+" s \n");
			Float czasPracy = Float.valueOf(czasStop)-Float.valueOf(czasStart);
			Double czasPracyNaRekord = Double.valueOf(czasPracy)/Double.valueOf(iloscRekordow);
			
			System.out.print("Co daje "+czasPracyNaRekord/1000000000+" sekund na każdy z " +iloscRekordow + " rekordów \n");
	   }  
}