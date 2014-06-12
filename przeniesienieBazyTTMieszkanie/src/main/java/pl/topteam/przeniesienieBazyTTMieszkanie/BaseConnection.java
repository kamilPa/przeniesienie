package pl.topteam.przeniesienieBazyTTMieszkanie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseConnection {
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = Okno.pathToBaza+"?useUnicode=true&characterEncoding=UTF-8";
	   static final String USER = "sysdba";
	   static final String PASS = "masterkey";
	   /**
	    * 
	    * @return Connection conn
	    */
	   static Connection connectToBase() {
		   try {
			   Class.forName("org.firebirdsql.jdbc.FBDriver");
			   Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);	   
			   return conn;
		   } catch (SQLException | ClassNotFoundException e) {
			   e.printStackTrace();
			   return null;
		   }
	   }
	   /**
	    * 
	    * @return Statement stmt
	    */
	   static Statement stmt() {
		   Statement stmt;
		   try {
			   stmt = BaseConnection.connectToBase().createStatement();
		   } catch (SQLException e) {
			   e.printStackTrace();
			   return null;
		   }
		   return stmt;
	   }
	   /**
	    * 
	    * @param Statement stmt
	    */
	   static void closeStatement(Statement stmt) {
		   try {
			   stmt.close();
		   } catch (SQLException e) {
			   e.printStackTrace();
		   }
	   }
	   /**
	    * 
	    * @param String sql
	    */
	   static void executeStatement(String sql) {
		   try {
			   BaseConnection.stmt().executeUpdate(sql);
		   } catch (SQLException e) {
			   e.printStackTrace();
		   }
	   }
	   /**
	    * 
	    * @param sql
	    * @return ResultSet rs lub null przy sqlexception
	    */
	   static ResultSet executeStatementWithResultSet(String sql) {
		   try {
			   ResultSet rs = BaseConnection.stmt().executeQuery(sql);
			   return rs;
		   } catch (SQLException e) {
			   e.printStackTrace();
			   return null;
		   }
	   }
	   /**
	    * 
	    * @param Connection conn
	    */
	   static void disconnect(Connection conn) {
		   try {
			   conn.close();
		   } catch (SQLException e) {
			e.printStackTrace();
		   }
	   }
}
