package com.ajay.UberReviewService.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
public class Student extends  BaseModel {
    private String name;

    private String rollNo;

    @ManyToMany
    @JoinTable(name ="course_student",
    joinColumns = @JoinColumn(name="student_id"),// will add this in join table for student
            inverseJoinColumns = @JoinColumn(name= "course_id")//// will add this in join table for course
    )
    private List<Course> course = new ArrayList<>();
}
