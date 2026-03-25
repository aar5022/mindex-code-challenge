package com.mindex.challenge.dao;
import com.mindex.challenge.data.Compensation;
import org.springframework.data.mongodb.repository.MongoRepository;


// Repository for accessing Compensation data.
public interface CompensationRepository extends MongoRepository<Compensation, String>{
    Compensation findByEmployeeId(String employeeId);
}
