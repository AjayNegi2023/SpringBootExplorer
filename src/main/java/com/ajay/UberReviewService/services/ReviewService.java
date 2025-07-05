package com.ajay.UberReviewService.services;

import com.ajay.UberReviewService.entity.Review;
import com.ajay.UberReviewService.repository.ReviewRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ReviewService implements CommandLineRunner {

    ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository){
        this.reviewRepository = reviewRepository;
    }
    @Override
    public void run(String... args) throws Exception {
        System.out.println("**************************");
//        Review r = new Review();

        Review r = Review.builder().content("Amazing").createdAt(new Date()).updatedAt(new Date()).rating(5.0).build(); // as we are using @Builder in Review Entity
        System.out.println(r);
        reviewRepository.save(r);
    }
}
