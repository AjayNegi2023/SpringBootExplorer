package com.ajay.UberReviewService.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Passenger extends  BaseModel {

    @OneToMany(mappedBy ="passenger")
    private List<Booking> bookings = new ArrayList<>();
}
