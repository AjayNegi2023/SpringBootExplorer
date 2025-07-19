package com.ajay.UberReviewService.services;

import com.ajay.UberReviewService.entity.Booking;
import com.ajay.UberReviewService.entity.Driver;
import com.ajay.UberReviewService.entity.Review;
import com.ajay.UberReviewService.repository.BookingRepository;
import com.ajay.UberReviewService.repository.DriverRepository;
import com.ajay.UberReviewService.repository.ReviewRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService implements CommandLineRunner {

    ReviewRepository reviewRepository;
    BookingRepository bookingRepository;
    DriverRepository driverRepository;
    public ReviewService(ReviewRepository reviewRepository, BookingRepository bookingRepository,DriverRepository driverRepository){
        this.reviewRepository = reviewRepository;
        this.bookingRepository=bookingRepository;
        this.driverRepository=driverRepository;
    }
    @Override
    public void run(String... args) throws Exception {
        System.out.println("**************************");
//        Review r = new Review();

//        Review r = Review.builder().content("Amazing").createdAt(new Date()).updatedAt(new Date()).rating(5.0).build(); // as we are using @Builder in Review Entity
//        Review r = Review.builder().content("Excellent").rating(5.0).build(); // as we are using @Builder in Review Entity
//        Booking b = Booking.builder()
//                    .review(r)
//                     .endTime(new Date()).build();
//        bookingRepository.save(b);
//        reviewRepository.save(r);

//        Driver d = Driver.builder().licenseNumber("1").name("ABCD").build();
//        driverRepository.save(d);

//      List<Booking> listOfBookings=  bookingRepository.findAllByDriverId(101);

        Optional<Driver> d1 = driverRepository.rawFindByIDAndLicenseNumber(152,"1");
        System.out.println(d1.get().getName());
    }
}
