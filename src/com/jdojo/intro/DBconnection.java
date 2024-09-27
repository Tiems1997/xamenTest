package com.jdojo.intro;

import java.sql.*;


public class DBconnection {

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:mysql://ls-0f19f4268096a452a869b6f8467bc299c51da519.cz6cgwgke8xd.eu-west-3.rds.amazonaws.com:3306/db0075850","user0075850","Yf3IgyBsOPa34WR");
		
	}

}
