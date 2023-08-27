package com.ilacad.AdvancedMappings.controller;

import com.ilacad.AdvancedMappings.dto.InstructorDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class InstructorController {

    @GetMapping("/")
    public String dashboard (Model model) {

        model.addAttribute("instructor", new InstructorDto());
        return "home";
    }

    @PostMapping("/save-instructor")
    public String saveInstructor(@Valid @ModelAttribute(name = "instructor") InstructorDto instructorDto, BindingResult result, Model model) {

        // Display an error message if user provides an empty or null value/s.
        if (result.hasErrors()) {
            model.addAttribute("instructor", instructorDto);
            return "home";
        }

        return "redirect:/?success";
    }

}
