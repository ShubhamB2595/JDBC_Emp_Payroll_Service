package com.epr;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;


public class DBConnection {

	private static String url = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
	private static String uname = "root";
	private static String pwd = "Shubham@2595";

	Connection connection;

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

		try {
			connection = DriverManager.getConnection(url, uname, pwd);
			System.out.println("Connection to the DB successfull..! " + connection);
			Statement stat = connection.createStatement();
			ResultSet rs = stat.executeQuery("SELECT * FROM employee_payroll");
			System.out.println("ID  |   Name \n----------------------");
			while(rs.next()) {
				Integer id = rs.getInt("id");
				String name = rs.getString("name");
				System.out.println(id  + "   |   " + name);
			}
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
