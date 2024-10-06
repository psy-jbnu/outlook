package pe.contacts.connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ManageConn {
	private static String db_url		= "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	private static String db_user		= "kedu";
	private static String db_pw			= 	"1234";
	private static Connection conn;
	public static Connection getConnection() throws SQLException{
		if (conn == null) {
			conn = DriverManager.getConnection(db_url, db_user, db_pw);
			conn.setAutoCommit(false);
		}
		return conn;
	}
	public static boolean serverDisconnect(int command) throws SQLException {
		boolean isDisconnected = false;
		if (conn == null) {
			return isDisconnected;
		}
		if (command == 1) {
			if (conn != null) {
				conn.commit();
				conn.close();
				isDisconnected = true;
			}
		}
		else if (command == 2) {
			if (conn != null) {
				conn.rollback();
				conn.close();
				isDisconnected = true;
			}
		}
		return isDisconnected;
	}
}
