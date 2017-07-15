package com.zerofang.pagerank.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCUtil {

	private static String DB_DRIVER = null;
	private static String DB_USER = null;
	private static String DB_URL = null;
	private static String DB_PASSWORD = null;

	static {
		DB_DRIVER = "com.mysql.jdbc.Driver";
		DB_USER = "root";
		DB_URL = "jdbc:mysql://127.0.0.1:3306/wordsplit?characterEncoding=utf8";
		DB_PASSWORD = "xjtu";
	}

	public static Connection getConnection() throws Exception {
		Class.forName(DB_DRIVER);
		return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	}

	public static void closes(ResultSet rs, Statement st, Connection conn) {
		try {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
