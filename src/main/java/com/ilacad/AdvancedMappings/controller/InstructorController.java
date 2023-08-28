package com.ilacad.AdvancedMappings.controller;

import com.ilacad.AdvancedMappings.dto.EmailDto;
import com.ilacad.AdvancedMappings.dto.InstructorDto;
import com.ilacad.AdvancedMappings.service.InstructorService;
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
@RequestMapping("/instructor")
public class InstructorController {

    InstructorService instructorService;

    @Autowired
    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @GetMapping("")
    public String home () {
        return "home";
    }

    @GetMapping("/form")
    public String dashboard (Model model) {

        model.addAttribute("instructor", new InstructorDto());
        return "form";
    }

    @PostMapping("/save-instructor")
    public String saveInstructor(@Valid @ModelAttribute(name = "instructor") InstructorDto instructorDto, BindingResult result, Model model) {

        // Display an error message if user provides an empty or null value/s.
        if (result.hasErrors()) {
            model.addAttribute("instructor", instructorDto);
            return "form";
        }

        instructorService.saveInstructor(instructorDto);

        return "redirect:/?success";
    }


    @GetMapping("/list")
    public String instructorList (Model model) {

        List<InstructorDto> instructorList = instructorService.getAllInstructors();

        model.addAttribute("instructor", new EmailDto());
        model.addAttribute("instructorList", instructorList);
        return "instructor-list";
    }




}
