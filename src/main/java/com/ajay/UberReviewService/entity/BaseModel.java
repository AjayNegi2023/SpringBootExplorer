package com.ajay.UberReviewService.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;


@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass

public abstract  class BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    protected int id;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP) // This annotation tells spring about the formats of date object to be stored i.e. Date / Time / TimeStamp
    @CreatedDate // This annotation tells Spring only handle it for Object Creation
    protected Date createdAt;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate // This annotation tells Spring only handle it for Object updation
    protected Date updatedAt;

}
