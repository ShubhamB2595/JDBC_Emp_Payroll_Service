package com.jdbctest;

import java.time.LocalDate;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.exception.CustomException;
import com.services.EmployeePayrollData;
import com.services.EmployeePayrollService;

public class EmployeePayrollServiceTest {

	@Test
	public void EmployeePayrollDataRetrievedDFromDB_MatchEmployeeCount() throws CustomException {

		EmployeePayrollService employeePayrollService = new EmployeePayrollService();

		List<EmployeePayrollData> employeePayrollData = employeePayrollService
				.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);

		Assert.assertEquals(6, employeePayrollData.size());
	}

	@Test
	public void givenNewSalaryUpdated_ShouldSyncWithDB() throws CustomException {

		EmployeePayrollService employeePayrollService = new EmployeePayrollService();

		List<EmployeePayrollData> employeePayrollData = employeePayrollService
				.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);

		employeePayrollService.updateEmployeeSalary("Terisa", 300000.00);
		boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Terisa");
		Assert.assertTrue(result);
	}

	@Test
	public void givenNewSalaryUpdated_ShouldSyncWithDBUsingPreparedStatement() throws CustomException {

		EmployeePayrollService employeePayrollService = new EmployeePayrollService();

		employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
		employeePayrollService.updateEmployeeSalaryUsingPreparedStatement("Terisa", 3000000.00);
		boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Terisa");
		Assert.assertTrue(result);
	}

	@Test
	 public void givenDateRange_WhenRetrieved_ShouldMatchTheEmployeeCount() throws CustomException {
		
    	EmployeePayrollService employeePayrollService = new EmployeePayrollService();
    	
        employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
        LocalDate startDate = LocalDate.of(2015, 01, 01);
        LocalDate endDate = LocalDate.now();
        
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollForDateRange(EmployeePayrollService.IOService.DB_IO, startDate, endDate);
        Assert.assertEquals(6, employeePayrollData.size());
    }
}
