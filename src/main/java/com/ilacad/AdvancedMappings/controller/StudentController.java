package com.ilacad.AdvancedMappings.controller;

import com.ilacad.AdvancedMappings.entity.Course;
import com.ilacad.AdvancedMappings.entity.Instructor;
import com.ilacad.AdvancedMappings.service.InstructorService;
import com.ilacad.AdvancedMappings.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

        model.addAttribute("courses", courses);

        return "student-list";
    }

}
