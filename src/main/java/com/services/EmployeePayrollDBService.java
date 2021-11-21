package com.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.exception.CustomException;

public class EmployeePayrollDBService {

	private Connection getConnection() throws CustomException, SQLException {

		String url = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
		String uname = "root";
		String pwd = "Shubham@2595";

		Connection connection;
		System.out.println("Connecting to database: " + url);
		connection = DriverManager.getConnection(url, uname, pwd);
		System.out.println("Connection to the DB successfull..! " + connection);
		return connection;
	}

	private List<EmployeePayrollData> getEmployeePayrollDataDB(String sql) throws CustomException {

		List<EmployeePayrollData> employeePayrollList = new ArrayList<>();

		try (Connection connection = this.getConnection()) {

			java.sql.Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				int id = result.getInt("id");
				String name = result.getString("name");
				double salary = result.getDouble("basic_pay");
				LocalDate startDate = result.getDate("start").toLocalDate();
				employeePayrollList.add(new EmployeePayrollData(id, name, salary, startDate));
			}
		} catch (SQLException e) {
			throw new CustomException("Entered Wrong query", CustomException.exceptionType.CHECK_THE_QUERY);
		}
		return employeePayrollList;
	}

	public List<EmployeePayrollData> readData() throws CustomException {

		String sql = "SELECT * FROM employee_payroll";

		return getEmployeePayrollDataDB(sql);
	}
}
