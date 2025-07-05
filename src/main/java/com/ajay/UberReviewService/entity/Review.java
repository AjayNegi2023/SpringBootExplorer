package com.ajay.UberReviewService.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Entity
@Table(name="bookingreview")
@Data
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String content;

    @Column
    private Double rating;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP) // This annotation tells spring about the formats of date object to be stored i.e. Date / Time / TimeStamp
    @CreatedDate // This annotation tells Spring only handle it for Object Creation
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate // This annotation tells Spring only handle it for Object updation
    private Date updatedAt;

    @Override
    public  String toString(){
        return this.content+" "+this.createdAt+" "+this.updatedAt;
    }

}
