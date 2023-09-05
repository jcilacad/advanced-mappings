package com.ilacad.AdvancedMappings.controller;

import com.ilacad.AdvancedMappings.dto.CourseDto;
import com.ilacad.AdvancedMappings.dto.ReviewDto;
import com.ilacad.AdvancedMappings.entity.Course;
import com.ilacad.AdvancedMappings.entity.Instructor;
import com.ilacad.AdvancedMappings.service.InstructorService;
import com.ilacad.AdvancedMappings.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    private StudentService studentService;
    private InstructorService instructorService;

    @Autowired
    public StudentController(StudentService studentService, InstructorService instructorService) {
        this.studentService = studentService;
        this.instructorService = instructorService;
    }

    @GetMapping("/list")
    public String list(Model model) {

        List<Course> courses = studentService.findAllCourses();

        model.addAttribute("courseDto", new CourseDto());
        model.addAttribute("courses", courses);

        return "student-list";
    }

    @PostMapping("/search-course")
    public String searchCourse (@Valid @ModelAttribute(name = "courseDto") CourseDto courseDto,
                                BindingResult result, Model model) {

        List<Course> courses = studentService.findCourse(courseDto);

        // Validate the input of student for searching course
        if (result.hasErrors()) {
            courses = studentService.findAllCourses();
            model.addAttribute("courseDto", courseDto);
            model.addAttribute("courses", courses);
            return "student-list";
        }


        if (courses.isEmpty()) {
            return "redirect:/student/list?unknownTitle";
        }
        model.addAttribute("courses", courses);

        return "student-list";

    }

    @GetMapping("/course-details/{courseId}")
    public String courseDetails(@PathVariable(name = "courseId") Long courseId, Model model) {

        Course course = studentService.findCourseByCourseId(courseId);

        model.addAttribute("course", course);
        model.addAttribute("reviewDto", new ReviewDto());

        return "course-details";
    }

    @PostMapping("/add-review")
    public String addReview (@Valid @ModelAttribute("reviewDto") ReviewDto reviewDto,
                             @RequestParam(name = "instructorId") Long instructorId,
                             @RequestParam(name = "courseId") Long courseId,
                             BindingResult result) {

        if (result.hasErrors()) {
            return "redirect:/student/course-details/" + courseId + "?emptyField";
        }

        instructorService.addReview(instructorId, courseId, reviewDto);

        return "redirect:/student/course-details/" + courseId + "?addReview";

    }

    @PostMapping("/delete-review")
    public String deleteReview(@RequestParam("reviewId") Long reviewId) {

        Course course = instructorService.deleteReview(reviewId);

        return "redirect:/student/course-details/" + course.getId() + "?deleteSuccess";

    }


    @PostMapping("/update-review")
    public String updateReview (@RequestParam("reviewId") Long reviewId,
                                @ModelAttribute("updateReview") ReviewDto reviewDto) {

        // update review
        Course course = instructorService.updateReview(reviewDto, reviewId);

        // Get course id for return
        Long courseId = course.getId();
        return "redirect:/student/course-details/" + courseId + "?updateSuccess";

    }




}
