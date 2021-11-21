package com.jdbctest;

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
}
