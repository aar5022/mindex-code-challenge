package com.mindex.challenge.data;

/**
 * Represents a computed view of an employee's reporting hierarchy.
 *
 * This object is NOT persisted in the database. Instead, it is built
 * dynamically when requested via the API.
 *
 * numberOfReports includes:
 * - Direct reports
 * - Indirect reports (recursive traversal of the hierarchy)
 */
public class ReportingStructure {
    private Employee employee;
    private int numberOfReports;
    
    public ReportingStructure(Employee employee, int numberOfReports) {
        this.employee = employee;
        this.numberOfReports = numberOfReports;
    }

    public Employee getEmployee() {
        return employee;
    }

    public int getNumberOfReports() {
        return numberOfReports;
    }
}
