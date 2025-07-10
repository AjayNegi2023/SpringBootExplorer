package com.ajay.UberReviewService.entity;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class DriverReview {

    String driverComment;
}
