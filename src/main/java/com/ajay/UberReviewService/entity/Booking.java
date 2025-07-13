package com.ajay.UberReviewService.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Booking extends  BaseModel{

    @Enumerated(value = EnumType.STRING)
    private BookingStatus bookingStatus;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Review review;// composition : we have defined one to one reation betweem booking and review

    private Date startTime;

    private Date endTime;

    private long totalDistance;
}
