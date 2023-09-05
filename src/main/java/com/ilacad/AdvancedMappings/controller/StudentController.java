package com.ilacad.AdvancedMappings.controller;

import com.ilacad.AdvancedMappings.dto.CourseDto;
import com.ilacad.AdvancedMappings.entity.Course;
import com.ilacad.AdvancedMappings.entity.Instructor;
import com.ilacad.AdvancedMappings.service.InstructorService;
import com.ilacad.AdvancedMappings.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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



}
