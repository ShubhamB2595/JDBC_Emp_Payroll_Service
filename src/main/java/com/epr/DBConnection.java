package com.epr;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

public class DBConnection {

	static String url = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
	static String uname = "root";
	static String pwd = "Shubham@2595";

	// constructor
	public DBConnection() {

	}

	// method to check database connection
	public void makeConnection() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver id loaded");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Connection connection;
		try {
			connection = DriverManager.getConnection(url, uname, pwd);
			System.out.println("Connection to the DB successfull..! " + connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// method to checking the driver is loaded or not
	public void checkDriver() {

		Enumeration<Driver> drivers = DriverManager.getDrivers();
		while (drivers.hasMoreElements()) {
			Driver driver = drivers.nextElement();
			System.out.println("Driver Name: " + driver);
		}

	}
}
