package com.ilacad.AdvancedMappings.controller;

import com.ilacad.AdvancedMappings.entity.Course;
import com.ilacad.AdvancedMappings.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/list")
    public String list() {

        List<Course> courses = studentService.findAllCourses();

        return "student-list";
    }

}
