package com.ajay.UberReviewService.services;

import com.ajay.UberReviewService.entity.Booking;
import com.ajay.UberReviewService.entity.Review;
import com.ajay.UberReviewService.repository.BookingRepository;
import com.ajay.UberReviewService.repository.ReviewRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ReviewService implements CommandLineRunner {

    ReviewRepository reviewRepository;
    BookingRepository bookingRepository;

    public ReviewService(ReviewRepository reviewRepository, BookingRepository bookingRepository){
        this.reviewRepository = reviewRepository;
        this.bookingRepository=bookingRepository;
    }
    @Override
    public void run(String... args) throws Exception {
        System.out.println("**************************");
//        Review r = new Review();

//        Review r = Review.builder().content("Amazing").createdAt(new Date()).updatedAt(new Date()).rating(5.0).build(); // as we are using @Builder in Review Entity
        Review r = Review.builder().content("Excellent").rating(5.0).build(); // as we are using @Builder in Review Entity
        Booking b = Booking.builder()
                    .review(r)
                     .endTime(new Date()).build();
        bookingRepository.save(b);
        reviewRepository.save(r);
    }
}
