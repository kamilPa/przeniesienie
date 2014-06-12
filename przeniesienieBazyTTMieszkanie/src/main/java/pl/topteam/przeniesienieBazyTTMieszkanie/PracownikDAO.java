package pl.topteam.przeniesienieBazyTTMieszkanie;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Random;

public class PracownikDAO {
	
	private String login = new Random().toString();
	public void utworzPracownika() {
		Connection conn = BaseConnection.connectToBase();
		Statement stmt = BaseConnection.stmt();
		BaseConnection.executeStatement("INSERT INTO PRACOWNIK (ID, IMIE, NAZWISKO, LOGIN) VALUES ( '0', 'Przeniesienie bazy', 'Radix NDM+', '"+login+"');");
		BaseConnection.closeStatement(stmt);
		BaseConnection.disconnect(conn);
	}

}

