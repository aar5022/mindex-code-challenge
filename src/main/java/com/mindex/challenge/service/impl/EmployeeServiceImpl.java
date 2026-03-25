package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.HashSet;
import java.util.Set;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompensationRepository compensationRepository;

    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
        employeeRepository.insert(employee);

        return employee;
    }

    @Override
    public Employee read(String id) {
        LOG.debug("Creating employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return employee;
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);

        return employeeRepository.save(employee);
    }

    /**
    * Recursively counts all direct and indirect reports.
    *
    * @param employee The current employee node being evaluated
    * @param visited  Tracks visited employee IDs to avoid duplication
    *
    * @return total number of unique reports under this employee
    */
    private int countReports(Employee employee, Set<String> visited) {
        if (employee.getDirectReports() == null) {return 0;}

        int count = 0;
        for (Employee report : employee.getDirectReports()) {
            if (report != null && !visited.contains(report.getEmployeeId())) {
                visited.add(report.getEmployeeId());
                // Fetch full employee object since directReports may only contain IDs
                Employee fullEmployee = employeeRepository.findByEmployeeId(report.getEmployeeId());

                // Count this report and recursively count their reports
                count += 1 + countReports(fullEmployee, visited);
            }
        }
        return count;
    }


    /**
    * Retrieves the reporting structure for a given employee.
    *
    * Approach:
    * - Fetch the root employee
    * - Recursively traverse all direct reports
    * - Count each unique employee encountered
    *
    * A Set is used to prevent:
    * - Double counting
    * - Infinite loops in case of cyclic relationships
    */
    @Override
    public ReportingStructure getReportingStructure(String employeeId) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId);

        int count = countReports(employee, new HashSet<>());

        return new ReportingStructure(employee, count);
    }


    // Persists a compensation record.
    @Override
    public Compensation createCompensation(Compensation compensation) {
    Compensation existing =
        compensationRepository.findByEmployeeId(compensation.getEmployeeId());

    if (existing != null) {
        compensation.setId(existing.getId()); // overwrite
    }

    return compensationRepository.save(compensation);
}

    // Retrieves the compensation record for an employee.
    @Override
    public Compensation getCompensation(String employeeId) {
        return compensationRepository.findByEmployeeId(employeeId);
    }
}
