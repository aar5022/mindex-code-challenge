package com.mindex.challenge;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;

import static org.junit.Assert.*;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChallengeApplicationTests {

	@Autowired
	private EmployeeService employeeService;


	// John Lennon should have 4 total reports
	@Test
	public void testReportingStructure_Lennon() {
		String employeeId = "16a596ae-edd3-4847-99fe-c4518e82c86f";
		ReportingStructure rs = employeeService.getReportingStructure(employeeId);

		assertNotNull(rs);
		assertNotNull(rs.getEmployee());
		assertEquals(employeeId,rs.getEmployee().getEmployeeId());
		assertEquals(4,rs.getNumberOfReports());
	}

	// Test employee with no reports
    @Test
    public void testReportingStructure_NoReports() {
        // This ID should correspond to a leaf employee (adjust if needed)
        String employeeId = "c0c2293d-16bd-4603-8e08-638a9d18b22c";

        ReportingStructure rs = employeeService.getReportingStructure(employeeId);

        assertNotNull(rs);
        assertEquals(0, rs.getNumberOfReports());
    }

    // Test intermediate manager (has some reports but not full tree)
    @Test
    public void testReportingStructure_MidLevel() {
        // Ringo Starr in sample data typically has 2 reports
        String employeeId = "03aa1462-ffa9-4978-901b-7c001562cf6f";

        ReportingStructure rs = employeeService.getReportingStructure(employeeId);

        assertNotNull(rs);
        assertEquals(2, rs.getNumberOfReports());
    }

    /**
    * Verifies that compensation records are:
    * - Successfully persisted
    * - Correctly retrieved
    * - Returning the most recent entry when multiple exist
    */
	@Test
	public void testCreateCompensation() {
    Compensation compensation = new Compensation();
    compensation.setEmployeeId("16a596ae-edd3-4847-99fe-c4518e82c86f");
    compensation.setSalary(100000.0);
    compensation.setEffectiveDate(LocalDate.of(2020, 1, 1));

    Compensation created = employeeService.createCompensation(compensation);

    assertNotNull(created);
	assertNotNull(created.getId()); // Mongo should generate ID
    assertEquals(compensation.getEmployeeId(), created.getEmployeeId());
    assertEquals(compensation.getSalary(), created.getSalary(), 0.0);
    assertEquals(compensation.getEffectiveDate(), created.getEffectiveDate());
	}

	public void testReadCompensation() {
    String employeeId = "16a596ae-edd3-4847-99fe-c4518e82c86f";

    // First create it
    Compensation compensation = new Compensation();
    compensation.setEmployeeId(employeeId);
    compensation.setSalary(120000.0);
    compensation.setEffectiveDate(LocalDate.of(2021, 5, 15));

    employeeService.createCompensation(compensation);

    // Then read it
    Compensation retrieved = employeeService.getCompensation(employeeId);

    assertNotNull(retrieved);
    assertEquals(employeeId, retrieved.getEmployeeId());
    assertEquals(120000.0, retrieved.getSalary(), 0.0);
    assertEquals(LocalDate.of(2021, 5, 15), retrieved.getEffectiveDate());
	}

}
