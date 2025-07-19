package com.ajay.UberReviewService.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course extends  BaseModel{

    private String name;

    @ManyToMany
    private List<Student> student = new ArrayList<>();

}
