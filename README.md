# Spring Boot


##  Handling `@CreatedDate` and `@LastModifiedDate` in Spring Boot

###  Problem: `PropertyValueException` When Saving Entity

**Error Message:**

```
Caused by: org.hibernate.PropertyValueException: not-null property references a null or transient value: com.ajay.UberReviewService.entity.Review.createdAt
```

###  Root Cause

This error occurs because the `createdAt` field in the `Review` entity is marked with `@Column(nullable = false)`, which means it cannot be `null` when persisting the entity. However, even though the field is annotated with `@CreatedDate`, **Spring does not automatically populate this value unless auditing is explicitly enabled**.

In other words:

> `@CreatedDate` and `@LastModifiedDate` **only work** when JPA Auditing is properly configured in your Spring Boot application.

---

### ✅ Solution: Enable JPA Auditing

To fix this issue, ensure the following steps are implemented:

---

### Step 1: Enable Auditing in Your Spring Boot Application

Add `@EnableJpaAuditing` to your main application class (or any `@Configuration` class):

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class UberReviewServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UberReviewServiceApplication.class, args);
    }
}
```

---

### Step 2: Annotate Your Entity for Auditing

Make sure your entity uses these annotations:

```java
package com.ajay.UberReviewService.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Table(name="bookingreview")
@Data
@Builder
@EntityListeners(AuditingEntityListener.class)
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

```

---

### 🔎 What @CreatedDate and @LastModifiedDate Do

* `@CreatedDate`: Automatically sets the field with the current timestamp when the entity is **first created**.
* `@LastModifiedDate`: Automatically updates the field with the current timestamp when the entity is **updated**.

> These are managed by Spring **only if auditing is enabled** and the `EntityListeners(AuditingEntityListener.class)` is set.

---
