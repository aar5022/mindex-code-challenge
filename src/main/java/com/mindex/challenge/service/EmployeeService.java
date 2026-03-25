package com.mindex.challenge.service;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;

public interface EmployeeService {
    Employee create(Employee employee);
    Employee read(String id);
    Employee update(Employee employee);


    /**
    *  Builds a ReportingStructure for a given employee.
    *
    * This method calculates the total number of reports dynamically
    * to ensure the value is always consistent with the current
    * state of the employee hierarchy.
    */
    ReportingStructure getReportingStructure(String employeeId);

    
    // Creates and persists a Compensation record for an employee.
    Compensation createCompensation(Compensation compensation);

    // Retrieves the most recent Compensation for a given employee.
    Compensation getCompensation(String employeeId);
}
