package com.ilacad.AdvancedMappings.controller;

import com.ilacad.AdvancedMappings.dto.CourseDto;
import com.ilacad.AdvancedMappings.dto.EmailDto;
import com.ilacad.AdvancedMappings.dto.InstructorDto;
import com.ilacad.AdvancedMappings.entity.Course;
import com.ilacad.AdvancedMappings.entity.Instructor;
import com.ilacad.AdvancedMappings.service.InstructorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping({"/instructor", "/"})
public class InstructorController {

    InstructorService instructorService;

    @Autowired
    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @GetMapping("")
    public String home() {
        return "home";
    }

    @GetMapping("/form")
    public String dashboard(Model model) {

        model.addAttribute("instructor", new InstructorDto());
        return "form";
    }

    @PostMapping("/save-instructor")
    public String saveInstructor(@Valid @ModelAttribute(name = "instructor") InstructorDto instructorDto, BindingResult result, Model model) {

        // Throw an error message if email exists
        String email = instructorDto.getEmail();
        if (instructorService.isEmailExists(email)) {
            result.rejectValue("email", null, "Email already exists");
        }

        // Display an error message if user provides an empty or null value/s.
        if (result.hasErrors()) {
            model.addAttribute("instructor", instructorDto);
            return "form";
        }

        instructorService.saveInstructor(instructorDto);

        return "redirect:/instructor/form?success";
    }


    @GetMapping("/list")
    public String instructorList(Model model) {

        List<Instructor> instructorList = instructorService.getAllInstructors();

        model.addAttribute("email", new EmailDto());
        model.addAttribute("instructorList", instructorList);
        return "instructor-list";
    }

    @PostMapping("/search-instructor")
    public String searchInstructor(@Valid @ModelAttribute(name = "email") EmailDto emailDto, BindingResult result, Model model) {

        if (result.hasErrors()) {
            List<Instructor> instructorList = instructorService.getAllInstructors();

            model.addAttribute("email", emailDto);
            model.addAttribute("instructorList", instructorList);
            return "instructor-list";
        }

        String email = emailDto.getEmail();
        if (instructorService.isEmailExists(email)) {
            Long id = instructorService.getIdByEmail(email);
            return "redirect:/instructor/details?id=" + id;
        }

        return "redirect:/instructor/list?undefined";
    }


    @GetMapping("/details")
    public String instructorDetails(@RequestParam("id") Long id, Model model) {

        Instructor instructorDetails = instructorService.findInstructorById(id);

        String youtubeChannel = "N/A";
        String hobby = "N/A";
        if (instructorDetails.getInstructorDetail() != null) {
            youtubeChannel = instructorDetails.getInstructorDetail().getYoutubeChannel();
            hobby = instructorDetails.getInstructorDetail().getHobby();
        }

        model.addAttribute("updatedDetails", new InstructorDto());
        model.addAttribute("course", new CourseDto());
        model.addAttribute("youtubeChannel", youtubeChannel);
        model.addAttribute("hobby", hobby);
        model.addAttribute("instructor", instructorDetails);
        return "instructor-details";
    }

    @PostMapping("/update-details")
    public String updateInstructorDetails(@Valid @ModelAttribute(name = "updatedDetails") InstructorDto instructorDto, BindingResult result, Model model) {

        Long id = instructorService.getIdByEmail(instructorDto.getEmail());
        // If the first name or last name is empty, display an error message
        if (instructorDto.getFirstName().isEmpty() || instructorDto.getLastName().isEmpty()) {
            return "redirect:/instructor/details?id=" + id.toString() + "&emptyField";
        }

        instructorService.updateInstructor(instructorDto);

        return "redirect:/instructor/details?id=" + id.toString() + "&updated";
    }

    @PostMapping("/delete-instructor/{id}")
    public String deleteInstructor(@PathVariable(name = "id") Long id) {

        // Delete an instructor
        instructorService.deleteInstructorById(id);
        return "redirect:/instructor/list?successDelete";
    }

    @GetMapping("/delete")
    public String deleteOtherDetails(@RequestParam(name = "id") Long id) {

        instructorService.deleteOtherDetailsById(id);
        return "redirect:/instructor/details?id=" + id.toString() + "&deleteOtherDetails";
    }

    @PostMapping("/add-course/{id}")
    public String addCourse(@PathVariable Long id, @Valid @ModelAttribute("course") CourseDto courseDto,
                            BindingResult result) {

        if (result.hasErrors()) {
            return "redirect:/details?id=" + id + "&emptyField";
        }

        instructorService.addCourse(id, courseDto);

        return "redirect:/details?id=" + id + "&addCourse";
    }


    @PostMapping("/delete-course/instructor-id/{instructorId}/course-id/{courseId}")
    public String deleteCourse(@PathVariable(name = "instructorId") Long instructorId,
                               @PathVariable(name = "courseId") Long courseId) {

        instructorService.deleteCourseById(courseId);

        return "redirect:/instructor/details?id=" + instructorId;
    }


}
