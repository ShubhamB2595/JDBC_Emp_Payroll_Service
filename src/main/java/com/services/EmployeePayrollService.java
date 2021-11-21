package com.services;

import java.util.List;

import com.exception.CustomException;

public class EmployeePayrollService {

	EmployeePayrollDBService employeePayrollDBService = new EmployeePayrollDBService();

	public enum IOService {

		DB_IO
	}

	private List<EmployeePayrollData> employeePayrollList;

	// method to read the table data
	public List<EmployeePayrollData> readEmployeePayrollData(IOService ioService) throws CustomException {

		if (ioService.equals(IOService.DB_IO)) {
			this.employeePayrollList = employeePayrollDBService.readData();
		}
		return this.employeePayrollList;
	}

	// update the salary
	public void updateEmployeeSalary(String name, double salary) {

		int result = employeePayrollDBService.updateEmployeeData(name, salary);

		if (result == 0) {
			return;
		}

		EmployeePayrollData employeePayrollData = this.getEmployeePayrollData(name);

		if (employeePayrollData != null) {
			employeePayrollData.salary = salary;
		}
	}

	// check the employee is available or not
	public boolean checkEmployeePayrollInSyncWithDB(String name) {
		
		List<EmployeePayrollData> employeePayrollDataList = employeePayrollDBService.getEmployeePayrollData(name);
		return employeePayrollDataList.get(0).equals(getEmployeePayrollData(name));
	}

	// method to get the filtered data from table
	private EmployeePayrollData getEmployeePayrollData(String name) {
		
		return this.employeePayrollList.stream()
				.filter(employeePayrollDataItem -> employeePayrollDataItem.name.equals(name)).findFirst().orElse(null);
	}
}
