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
import java.util.Date;

import com.google.common.base.Strings;
import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.MazoviaDbfFileReader;

public class WnioskodawcaImportDBF {
	   String path = Okno.getPathToDM_WNIO();
	   Connection conn = BaseConnection.connectToBase();
	   Statement stmt = BaseConnection.stmt(); 
	   /**
	    * Funkcja ma za zadanie znaleźć i zamienić nr akt na id zarządcy
	    * 
	    * @param nrAkt
	    * @return zwraca idZarzadcy 
	    */  
	   public Integer wstawTeGlupieID(String nrAkt) {
		   int id= 0;
		   nrAkt = nrAkt.trim();
		   try{
			   	String sql = null;
			   	if (!Strings.isNullOrEmpty(nrAkt)) {
		    	  sql = "SELECT ID_ZARZADCY FROM NRAKTZARZ  WHERE NUMER_AKT = '"+nrAkt+"'";
		    	  System.out.print(sql);
			   	}
			   	ResultSet rs = stmt.executeQuery(sql); 
			   	while (rs.next()) {
				   	id  = rs.getInt("ID_ZARZADCY");
			   	}
			   	System.out.print("ID zarządcy dla tego wnioskodawcy to: "+id+".\n");
			   	return id;
		   }catch(SQLException se) {
			   	se.printStackTrace();
			   	return null;
		   }catch(Exception e){
			  	e.printStackTrace();
			  	return null;
		  }
	   }      
	   
	   public Integer plec(String imie) {
		   return imie.matches(".*[aA]") ? 0 : 1;
	   }
	   public java.sql.Date getDate(Date sData){ 
                   long t = sData.getTime();
                   java.sql.Date dt = new java.sql.Date(t);
                   return dt;
	   }  
	   /**
	    * 
	    * @param imie
	    * @param nazwisko
	    * @param pesel
	    * @param nrDowodu
	    * @param plec
	    * @param dataUr
	    * @param kontoId
	    * @param adresId
	    * @param zarzadcaId
	    * @return
	    */
	   public Integer utworzWnioskodawce(String imie, String nazwisko, String pesel, String nrDowodu, Integer plec, java.sql.Date dataUr, Integer kontoId, Integer adresId, Integer zarzadcaId) {
			String sql;
				   try {
				      sql = "SELECT * FROM OSOBA";
			    	  System.out.print("\nzmienna sql= "+sql+"\n");
			    	  ResultSet rs = stmt.executeQuery(sql);
			    	  Integer wnioskodawcaId=0;
			    	  while (rs.next()) {
			    	    wnioskodawcaId++;
			    	  }    	  
				    System.out.print("Ustalone id: "+wnioskodawcaId+"\n");
				      PreparedStatement pstm;
				      sql = "INSERT INTO OSOBA (ID, IMIE, NAZWISKO, PESEL, NR_DOWODU, PLEC, DATA_URODZENIA, KONTO_ID, ADRES_ID, ZARZADCA_ID) VALUES ( (?), (?), (?), (?), (?), (?), (?), (?), (?), (?) )";
				      pstm = conn.prepareStatement(sql);
				      pstm.setInt(1, wnioskodawcaId);
				      pstm.setString(2, imie);
				      pstm.setString(3, nazwisko);
				      pstm.setString(4, pesel);
				      pstm.setString(5, nrDowodu);
				      pstm.setInt(6, plec);
				      pstm.setDate(7, dataUr);
				      try {
				    	  pstm.setInt(8, kontoId);
				      } catch (NullPointerException e) {
				    	  pstm.setString(8, null);
				      }
				      pstm.setInt(9, adresId);
				      pstm.setInt(10, zarzadcaId);
				      pstm.executeUpdate();
				      pstm.close();			     
				   return wnioskodawcaId;
				   }catch(SQLException se) {
					   se.printStackTrace();
					   return null;
				   }catch(Exception e){
					   e.printStackTrace();
					   return null;
				   }
	   }
	/**
	 	* Główna funkcja przenoszenia wnioskodawcy i wniosku
	 * 
	 * @throws FileNotFoundException
	 * @throws DBFException
	 */
	    
	public void importujWnioskodawce() throws FileNotFoundException, DBFException { //GŁÓWNA FUNKCJA 
			Long czasStart = System.nanoTime();
			File dwni = new File(path);
			InputStream is = new FileInputStream(dwni);
			MazoviaDbfFileReader reader = new MazoviaDbfFileReader (is);
			Integer iloscRekordow = reader.getRecordCount();
			ImportDBF a = new ImportDBF();
			Integer[] id_wnio = new Integer[reader.getRecordCount()];
			String[] nr_wnio = new String[reader.getRecordCount()];
			for (int i=0; i<(reader.getRecordCount()); i++) {
				Object aobj[] = reader.nextRecord();
	        	String nazwisko= ((String)aobj[3]).trim();
	        	if (Strings.isNullOrEmpty(nazwisko)) {
	        		nazwisko = "uzupełnij dane";
	        	}
	        	String imie =((String)aobj[4]).trim();
	        	if (Strings.isNullOrEmpty(imie)) {
	        		imie = "uzupełnij dane";
	        	}
	        	String pesel = ((String)aobj[5]).trim();
	            java.sql.Date data = getDate((Date)aobj[6]);
				String nrDomu = ((String)aobj[12]);
            	String nrLok = ((String)aobj[13]);
	        	String nrDowodu = ((String)aobj[7]).trim();
	        	String nrAkt = ((String)aobj[14]).trim();
	        	nr_wnio[i] =  ((String)aobj[1]).trim();
	        	id_wnio[i] = utworzWnioskodawce(
	        			imie,
	        			nazwisko,
	        			pesel,
	        			nrDowodu,
	        			plec(imie),
	        			data,
	        			a.utworzKonto(
	        	        		a.ogarnijNrKonta(aobj, 1), 
	        	        		a.sprawdzPrzedTworzeniem("BANK", (String)aobj[66]) ? a.utworz("BANK", (String)aobj[66]) : a.pobierzId("BANK", (String)aobj[66]) 
	        	        		),    
	        	        a.utworzAdres( 
		            	       	a.sprawdzPrzedTworzeniem("ULICA",	(String)aobj[11].toString()) ? a.utworz("ULICA", (String)aobj[11].toString()) : a.pobierzId("ULICA", (String)aobj[11].toString()) ,
		            	       	a.sprawdzPrzedTworzeniem("MIEJSCOWOSC", (String)aobj[10].toString()) ? a.utworz("MIEJSCOWOSC", (String)aobj[10].toString()) : a.pobierzId("MIEJSCOWOSC", (String)aobj[10].toString()) ,		
		            	        nrDomu, 
		            	        nrLok, 
		            	        aobj[9].toString()
	        			),
	        			(wstawTeGlupieID(nrAkt))
	        	);
			}
		String sql;
			   try {
			      sql = "CREATE TABLE NRWNIO_WNIOSKODAWCA (ID_WNIOSKODAWCA varchar(25), NUMER_WNIOSKU varchar(25) );";
			      System.out.print("Uruchamiam tworzenie tymczasowej tabeli \n");
			      conn.createStatement().execute(sql);
			      System.out.print("Utworzona!");
			   }catch(SQLException se) {
				   se.printStackTrace();
			   }catch(Exception e){
				   e.printStackTrace();
			   }
			   for (int i=0; i<nr_wnio.length; i++) {
				   		System.out.print(nr_wnio[i]+" --- "+id_wnio[i]+"\n");
           				try {
	    			      PreparedStatement pstm;
	    			      sql = "INSERT INTO NRWNIO_WNIOSKODAWCA (ID_WNIOSKODAWCA, NUMER_WNIOSKU) VALUES ( (?), (?) )";
	    			      pstm = conn.prepareStatement(sql);
	    			      pstm.setInt(1, id_wnio[i]);
	    			      pstm.setString(2, nr_wnio[i]);
  	    			      pstm.executeUpdate();
	    			      pstm.close();
           				}catch(SQLException se) {
	    				   se.printStackTrace();
           				}catch(Exception e){
	    				   e.printStackTrace();
           				} 
			   }
				BaseConnection.executeStatement("DROP TABLE NRAKTZARZ");
			   	BaseConnection.closeStatement(stmt);
			   	BaseConnection.disconnect(conn);
			   	Long czasStop = System.nanoTime();
				System.out.print("Wygenerowano w :"+((czasStop-czasStart)/1000000000)+" s \n");
				Float czasPracy = Float.valueOf(czasStop)-Float.valueOf(czasStart);
				Double czasPracyNaRekord = Double.valueOf(czasPracy)/Double.valueOf(iloscRekordow);
				
				System.out.print("Co daje "+czasPracyNaRekord/1000000000+" sekund na każdy z " +iloscRekordow + " rekordów \n");
	}
}