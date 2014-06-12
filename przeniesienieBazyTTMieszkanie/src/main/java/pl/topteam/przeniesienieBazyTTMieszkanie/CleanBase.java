package pl.topteam.przeniesienieBazyTTMieszkanie;

import java.sql.Connection;
import java.sql.Statement;

public class CleanBase {
	Connection conn = BaseConnection.connectToBase();
	Statement stmt = BaseConnection.stmt();
	String[] tabela = {"DECYZJA", "WNIOSEK", "ADRES", "KONTO", "ULICA", "MIEJSCOWOSC", "BANK", "OSOBA", "ZARZADCA"};
	String[] tabela2 = {"NRAKTZARZ", "NRWNIO_WNIOSKODAWCA", "IDWNIO_DECYZJA"}; 
	public void oczyscBaze() {
		for (String nazwa : tabela) {
			String sql = "DELETE FROM "+nazwa+"";
			System.out.print(sql+"\n");
			BaseConnection.executeStatement(sql);
		}
		for (String nazwa : tabela2) {
			String sql = "DELETE FROM "+nazwa+"";
			System.out.print(sql+"\n");
			BaseConnection.executeStatement(sql);
		}
		BaseConnection.closeStatement(stmt);
		BaseConnection.disconnect(conn);
	}
}
