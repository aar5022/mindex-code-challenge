package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.mindex.challenge.data.ReportingStructure;

@RestController
public class EmployeeController {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/employee")
    public Employee create(@RequestBody Employee employee) {
        LOG.debug("Received employee create request for [{}]", employee);

        return employeeService.create(employee);
    }

    @GetMapping("/employee/{id}")
    public Employee read(@PathVariable String id) {
        LOG.debug("Received employee create request for id [{}]", id);

        return employeeService.read(id);
    }

    @PutMapping("/employee/{id}")
    public Employee update(@PathVariable String id, @RequestBody Employee employee) {
        LOG.debug("Received employee create request for id [{}] and employee [{}]", id, employee);

        employee.setEmployeeId(id);
        return employeeService.update(employee);
    }


    /**
     * GET endpoint to retrieve a reporting structure by employee ID.
     *
     * This endpoint does not persist any data and instead computes
     * the reporting structure dynamically on each request.
     */
    @GetMapping("/reportingStructure/{id}")
    public ReportingStructure getReportingStructure(@PathVariable String id) {
        return employeeService.getReportingStructure(id);
    }

    /**
    * POST endpoint to create a compensation record.
    *
    * Accepts a JSON payload and persists it to the database.
    */
    @PostMapping("/compensation")
    public Compensation createCompensation(@RequestBody Compensation compensation) {
        return employeeService.createCompensation(compensation);
    }

    /**
    * GET endpoint to retrieve the latest compensation for an employee.
    *
    * Returns the most recent record based on effective date.
    */
    @GetMapping("/compensation/{employeeId}")
    public Compensation getCompensation(@PathVariable String employeeId) {
        return employeeService.getCompensation(employeeId);
    }
}
