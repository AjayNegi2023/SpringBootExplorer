package com.ajay.UberReviewService.repository;
import com.ajay.UberReviewService.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review , Integer> {

}
