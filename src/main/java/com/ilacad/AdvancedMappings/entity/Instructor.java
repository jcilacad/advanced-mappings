package com.ilacad.AdvancedMappings.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "instructor")
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", unique = true)
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "instructor_detail_id", referencedColumnName = "id")
    private InstructorDetail instructorDetail;

    @OneToMany(
            mappedBy = "instructor",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                       CascadeType.REFRESH, CascadeType.DETACH},
            fetch = FetchType.LAZY
            )
    private List<Course> courses;

    public Instructor(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public void addCourse (Course course) {
        course.setInstructor(this);
        courses.add(course);
    }

    public void deleteCourse (Course course) {
        course.setInstructor(null);
        courses.remove(course);
    }
}
