package com.services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.exception.CustomException;

public class EmployeePayrollDBService {

	private static EmployeePayrollDBService employeePayrollDBService;
	private java.sql.PreparedStatement employeePayrollDataStatement;

	// method to get instance
	public static EmployeePayrollDBService getInstance() {

		if (employeePayrollDBService == null) {
			employeePayrollDBService = new EmployeePayrollDBService();
		}
		return employeePayrollDBService;
	}

	// method to connect to the database
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

	// method to get the data from table and storing to list
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

	// method to update the table data using the prepared statement
	int updateEmployeeDataUsingStatement(String name, double salary) {

		String sql = String.format("UPDATE employee_payroll SET basic_pay = %.2f WHERE name = '%s';", salary, name);

		try (Connection connection = this.getConnection()) {

			java.sql.Statement statement = connection.createStatement();
			return statement.executeUpdate(sql);
		} catch (SQLException | CustomException e) {
			e.printStackTrace();
		}
		return 0;
	}

	// method for dynamic prepared statement
	private void preparedStatementForEmployeeData() {

		try {
			Connection connection = this.getConnection();
			String sql = "SELECT * FROM employee_payroll WHERE name = ?";
			employeePayrollDataStatement = connection.prepareStatement(sql);
		} catch (SQLException | CustomException e) {
			e.printStackTrace();
		}
	}

	// method to get table data and storing into list
	public List<EmployeePayrollData> getEmployeePayrollData(String name) {

		List<EmployeePayrollData> employeePayrollList = null;

		if (this.employeePayrollDataStatement == null) {
			this.preparedStatementForEmployeeData();
		}
		try {
			employeePayrollDataStatement.setString(1, name);
			ResultSet resultSet = employeePayrollDataStatement.executeQuery();
			employeePayrollList = this.getEmployeePayrollData(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employeePayrollList;
	}

	// method to get table data and storing into list
	private List<EmployeePayrollData> getEmployeePayrollData(ResultSet resultSet) {

		List<EmployeePayrollData> employeePayrollList = new ArrayList<>();

		try {
			while (resultSet.next()) {

				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				double salary = resultSet.getDouble("basic_pay");
				LocalDate startDate = resultSet.getDate("start").toLocalDate();
				employeePayrollList.add(new EmployeePayrollData(id, name, salary, startDate));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employeePayrollList;
	}

	// method to update table data
	public int updateEmployeeData(String name, double salary) {

		return this.updateEmployeeDataUsingStatement(name, salary);
	}

	// method to read the data from table
	public List<EmployeePayrollData> readData() throws CustomException {

		String sql = "SELECT * FROM employee_payroll";

		return getEmployeePayrollDataDB(sql);
	}

	// method for the getting data in date range
	public List<EmployeePayrollData> getEmployeeForDateRange(LocalDate startDate, LocalDate endDate) {

		String sql = String.format("SELECT * FROM employee_payroll WHERE START BETWEEN '%s' AND '%s';",
				Date.valueOf(startDate), Date.valueOf(endDate));

		return getEmployeePayrollData(sql);
	}
}
