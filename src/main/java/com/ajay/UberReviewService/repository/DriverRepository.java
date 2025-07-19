package com.ajay.UberReviewService.repository;

import com.ajay.UberReviewService.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver , Integer> {

    // nativeQuery = true means : SQL Query
    // In raw MYSQL query We have to give column name as it is in DB like license_number, it cannot be licenseNumber
   // ANd any error in MYSQL raw query , if there is any error that will not detected at compile time
    @Query(nativeQuery = true, value = "Select * from Driver WHERE id= :id AND license_number=:license_number")
    Optional<Driver> rawFindByIDAndLicenseNumber(int id , String license_number );

//    nativeQuery = false means : hibernate query
//      In Hibernate : Error is thrown at compile time
//
    @Query( value = "Select d from Driver as d WHERE id= :id AND licenseNumber=:license_number")
    Optional<Driver> rawFindByIDAndLicenseNumberHB(int id , String license_number );
}
