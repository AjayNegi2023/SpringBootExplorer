package com.ajay.UberReviewService.entity;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Setter
@Getter
public class PassengerReview extends Review{
    String passengerComment;
}
