package com.ilacad.AdvancedMappings.service;

import com.ilacad.AdvancedMappings.dto.CourseDto;
import com.ilacad.AdvancedMappings.entity.Course;
import com.ilacad.AdvancedMappings.entity.Review;
import com.ilacad.AdvancedMappings.repository.CourseRepository;
import com.ilacad.AdvancedMappings.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private CourseRepository courseRepository;
    private ReviewRepository reviewRepository;

    @Autowired
    public StudentServiceImpl(CourseRepository courseRepository, ReviewRepository reviewRepository) {
        this.courseRepository = courseRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Course> findAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public List<Course> findCourse(CourseDto courseDto) {

        String courseName = courseDto.getCourseName();

        List<Course> courses = courseRepository.findByTitleContainsIgnoreCase(courseName);

        return courses;
    }

    @Override
    public Course findCourseByCourseId(Long courseId) {

        Optional<Course> result = courseRepository.findById(courseId);
        Course course;

        if (result.isPresent()) {
            course = result.get();
            List<Review> reviews = reviewRepository.findByCourse_Id(courseId);

            course.setReviews(reviews);
        } else {
            throw new RuntimeException("Did not find course id - " + courseId);
        }

        return course;
    }

}
