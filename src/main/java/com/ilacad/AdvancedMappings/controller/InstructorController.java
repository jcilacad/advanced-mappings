package com.ilacad.AdvancedMappings.controller;

import com.ilacad.AdvancedMappings.dto.InstructorDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InstructorController {

    @GetMapping("/")
    public String dashboard (Model model) {

        model.addAttribute("instructor", new InstructorDto());
        return "home";
    }

}
