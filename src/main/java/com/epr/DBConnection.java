package com.epr;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

public class DBConnection {

	public static void main(String[] args) {
		
		String url = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
		String uname = "root";
		String pwd = "Shubham@2595";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver id loaded");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		while(drivers.hasMoreElements()) {
			Driver driver = drivers.nextElement();
			System.out.println("Driver Name: " + driver);
		}

		Connection connection;
		try {
			connection = DriverManager.getConnection(url, uname, pwd);
			System.out.println("Connection to the DB successfull..! " + connection);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
