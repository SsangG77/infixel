package DAO;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conn {
	public Connection con() {
		String url = "jdbc:oracle:thin:@localhost:1521/xe";
		String uid = "green01";
		String upw = "1234";
		
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			Connection conn = DriverManager.getConnection(url, uid, upw);
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
