package com.ajay.UberReviewService.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="bookingreview")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Review extends  BaseModel {

    @Column(nullable = false)
    private String content;

    @Column
    private Double rating;

    @Override
    public  String toString(){
        return this.content+" "+this.createdAt+" "+this.updatedAt;
    }

}
