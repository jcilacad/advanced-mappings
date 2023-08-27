package com.ilacad.AdvancedMappings.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InstructorController {

    @GetMapping("/")
    public String dashboard () {
        return "home";
    }

}
