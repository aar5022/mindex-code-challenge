package com.mindex.challenge.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;


/**
 * Represents an employee's compensation details.
 *
 * Stored in a separate collection to:
 * - Allow independent scaling
 * - Support multiple compensation records over time
 *
 * employeeId is used as a reference instead of embedding
 * the full Employee object for better normalization.
 */
@Document(collection = "compensation")
public class Compensation {
    
    @Id
    private String id;

    private String employeeId;
    private double salary;
    private LocalDate effectiveDate;

    public Compensation() {
    }

    public Compensation(String employeeId, double salary, LocalDate effectiveDate) {
        this.employeeId = employeeId;
        this.salary = salary;
        this.effectiveDate = effectiveDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}
