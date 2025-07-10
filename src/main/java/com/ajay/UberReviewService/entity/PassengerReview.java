package com.ajay.UberReviewService.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.*;

@Entity
@Setter
@Getter

@PrimaryKeyJoinColumn(name ="passengerId")
public class PassengerReview extends Review{
    String passengerComment;
}
