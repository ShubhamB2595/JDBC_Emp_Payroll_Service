package com.epr;

public class JDBCMain {

	public static void main(String[] args) {

		DBConnection connectDB = new DBConnection();
		
		connectDB.checkDriver();
		connectDB.makeConnection();
		

	}

}
