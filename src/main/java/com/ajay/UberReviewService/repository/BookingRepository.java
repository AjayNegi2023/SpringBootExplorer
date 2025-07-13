package com.ajay.UberReviewService.repository;

import com.ajay.UberReviewService.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking , Long> {
}
