package db;

import com.sun.rowset.CachedRowSetImpl;

import java.sql.*;

public class DBManager {

	private static final String JDBC_URL = "jdbc:mysql://bms-rds.cpkg2qs6k11z.ap-northeast-2.rds.amazonaws.com/BMS_DB?zeroDateTimeBehavior=convertToNull";
	private static final String USERNAME = "dev_ersolution";
	private static final String PASSWORD = "er0313488157";

	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
			return connection;
		} catch(Exception e) {
			throw new IllegalStateException(e);
		}
	}

	public static ResultSet executeQuery(String sql) throws SQLException {
		Connection con = getConnection();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(sql);

		CachedRowSetImpl rowset = new CachedRowSetImpl();
		rowset.populate(rs);

		stmt.close();
		con.close();

		return rowset;
	}


}
