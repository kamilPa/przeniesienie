package pl.topteam.przeniesienieBazyTTMieszkanie;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.google.common.base.Strings;
import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.MazoviaDbfFileReader;

public class DecyzjaImportDBF {
 	String path = Okno.getPathToDM_WNIO();
 	Connection conn = BaseConnection.connectToBase();
	Statement stmt = BaseConnection.stmt();
 	
	public Integer wstawNumerWniosku(String nrDecyzji) {
		Integer id = null;
		nrDecyzji = nrDecyzji.trim();
			   try{
			      String sql = null;
			      if (!Strings.isNullOrEmpty(nrDecyzji)) {
			    	  sql = "SELECT ID_WNIOSEK FROM IDWNIO_DECYZJA WHERE NUMER_DECYZJI = '"+nrDecyzji+"'";
			    	  System.out.print(sql);
			      }
			      ResultSet rs = stmt.executeQuery(sql); 
			      while (rs.next()) {
			    	  id  = rs.getInt("ID_WNIOSEK");
			      }
			      System.out.print("ID Wniosku dla tej decyzji to: "+id+".\n");
			      return id;
			   }catch(SQLException se) {
				   se.printStackTrace();
				   return null;
			   }catch(Exception e){
				   e.printStackTrace();
				   return null;
			   }
	}
	public String generujTrescDecyzji(String rodzajDecyzji) {
		String tresc=null;
		/**
		 * pobranie treści decyzji
		 */
		for (int i=0; i<ImportSzablonowDecyzjiDBF.tablicaSzablonow.length; i++) {
			if (rodzajDecyzji.equals(ImportSzablonowDecyzjiDBF.tablicaSzablonow[i].getNazwa())) {
				tresc = ImportSzablonowDecyzjiDBF.tablicaSzablonow[i].getTresc();
			}
		}
		/**
		 * obróbka treści decyzji 
		 */
		
		
		
		
		
		return tresc;
	}
	public String generujTrescWyliczenia() {
		String tresc= "Treść wyliczenia niedostępna....";
		return tresc;
	}
	public String powodOdmowy() {
		String a=null;
		return a;
	}
	public Integer rodzajDecyzji(String rodzajDecyzji) {
		Integer rodzaj = null;
		rodzajDecyzji=rodzajDecyzji.trim();
		if (Strings.isNullOrEmpty(rodzajDecyzji)) {
			return null;
		}
		if (rodzajDecyzji.equals("11")) { //przyznajaca
			rodzaj = 31;
		} if (rodzajDecyzji.equals("1101")) { //przyznajaca ?zrzeczenie sie ryczaltu?
			rodzaj = 31;
		} if (rodzajDecyzji.equals("zd")) { //decyzja zmieniajaca
			rodzaj = 33;
		}// if (rodzajDecyzji.equals("1101")) {
		//	rodzaj = 31;
		//}
		
		// 31 - przyznajaca 32- odmowna 33- zmieniajaca 34- wstrzymyjaca 35- wznawiajaca 36- umarzajaca 
		return rodzaj;
	}
	public void utworzDecyzje(String numer, Integer rodzajDecyzji, Double przyznanaKwota, Double dodatekKwota, Double ryczaltKwota, Boolean dodatekDla, Boolean ryczaltDla, java.sql.Date dataUtworzenia, java.sql.Date przyznanyOd, java.sql.Date przyznanyDo, Integer wniosekId, String powodOdmowy, Integer nr, Integer sposobWyplaty, Integer szablonDecyzjiId, String uwagi, Integer pracownikId, String trescDecyzji, String trescWyliczenia) {
		String sql;
			   try {
			      sql = "SELECT * FROM DECYZJA";
		    	  System.out.print("\nzmienna sql= "+sql+"\n");
		    	  ResultSet rs = stmt.executeQuery(sql);
		    	  Integer decyzjaId=0;
		    	  while (rs.next()) {
		    	    decyzjaId++;
		    	  }
		    	  System.out.print("Ustalone id: "+decyzjaId+"\n");
			      PreparedStatement pstm;
			      sql = "INSERT INTO DECYZJA (ID, NUMER, RODZAJ_DECYZJI, PRZYZNANA_KWOTA, DODATEK, RYCZALT, DODATEK_DLA, RYCZALT_DLA, DATA_UTWORZENIA, PRZYZNANY_OD, PRZYZNANY_DO, WNIOSEK_ID, POWOD_ODMOWY, NR, SPOSOB_WYPLATY, UWAGI, PRACOWNIK_ID, TRESC_DECYZJI, TRESC_WYLICZENIA) VALUES ( (?), (?), (?), (?), (?), (?), (?), (?), (?), (?), (?), (?), (?), (?), (?), (?), (?), (?), (?) )";
			      pstm = conn.prepareStatement(sql);
			      pstm.setInt(1, decyzjaId);
			      pstm.setString(2, numer);
			      pstm.setInt(3, rodzajDecyzji);
			      pstm.setDouble(4, przyznanaKwota);
			      pstm.setDouble(5, dodatekKwota);
			      pstm.setDouble(6, ryczaltKwota);
			      pstm.setBoolean(7, dodatekDla);
			      pstm.setBoolean(8, ryczaltDla);
			      pstm.setDate(9, dataUtworzenia);
			      pstm.setDate(10, przyznanyOd);
			      pstm.setDate(11, przyznanyDo);
			      pstm.setInt(12, wniosekId);
			      pstm.setString(13, powodOdmowy);
			      pstm.setInt(14, nr);
			      pstm.setInt(15, sposobWyplaty);
			      pstm.setString(16, uwagi);
			      pstm.setInt(17, pracownikId);
			      pstm.setString(18, trescDecyzji);
			      pstm.setString(19, trescWyliczenia);
			      pstm.executeUpdate();
			      pstm.close();
			   }catch(SQLException se) {
				   se.printStackTrace();
			   }catch(Exception e){
				   e.printStackTrace();
			   }
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void importujDecyzje() throws FileNotFoundException, DBFException {
		Long czasStart = System.nanoTime();
	   	File dwni = new File(path);
		InputStream is = new FileInputStream(dwni);
		MazoviaDbfFileReader reader = new MazoviaDbfFileReader (is);
		reader.setCharactersetName("utf-8");
		Integer iloscRekordow = reader.getRecordCount();
		WnioskodawcaImportDBF pom = new WnioskodawcaImportDBF();		
		for (int i=0; i<(reader.getRecordCount()); i++) {
			Object aobj[] = reader.nextRecord();
			String numerDecyzji = ((String)aobj[40]).trim();
			Integer rodzajDecyzji = rodzajDecyzji(aobj[39].toString());
			System.out.print(rodzajDecyzji);
			Double dodatekKwota = (Double)aobj[44];
			Double ryczaltKwota = (Double)aobj[45];
			Double przyznanaKwota = dodatekKwota+ryczaltKwota;
			java.sql.Date dataUtworzenia = pom.getDate((java.util.Date)aobj[37]);
			java.sql.Date dataDecyzjiOd = pom.getDate((java.util.Date)aobj[38]);
			@SuppressWarnings("deprecation")
			java.sql.Date dataDecyzjiDo = pom.getDate(new Date(dataDecyzjiOd.getYear(),dataDecyzjiOd.getMonth()+6,dataDecyzjiOd.getDay()));
			Integer sposobWyplaty = 37;
			String powodOdmowy = "";
			Integer wniosekId = wstawNumerWniosku(numerDecyzji);
			String trescDecyzji = "xxxxxxxxx";
			String trescWyliczenia = "xxxx";
			String uwagi = "";
			utworzDecyzje( 
					numerDecyzji,
					rodzajDecyzji,
					przyznanaKwota,
					dodatekKwota,
					ryczaltKwota,
					false,
					false,
					dataUtworzenia,
					dataDecyzjiOd,
					dataDecyzjiDo,
					wniosekId,
					powodOdmowy,
					1,
					sposobWyplaty,
					0,
					uwagi,
					0,
					trescDecyzji,
					trescWyliczenia
					);
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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