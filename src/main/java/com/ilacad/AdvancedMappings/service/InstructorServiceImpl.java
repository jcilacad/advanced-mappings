package com.ilacad.AdvancedMappings.service;

import com.ilacad.AdvancedMappings.dto.CourseDto;
import com.ilacad.AdvancedMappings.dto.InstructorDto;
import com.ilacad.AdvancedMappings.entity.Course;
import com.ilacad.AdvancedMappings.entity.Instructor;
import com.ilacad.AdvancedMappings.entity.InstructorDetail;
import com.ilacad.AdvancedMappings.repository.CourseRepository;
import com.ilacad.AdvancedMappings.repository.InstructorDetailRepository;
import com.ilacad.AdvancedMappings.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InstructorServiceImpl implements InstructorService {

    InstructorRepository instructorRepository;
    InstructorDetailRepository instructorDetailRepository;
    CourseRepository courseRepository;

    @Autowired
    public InstructorServiceImpl(InstructorRepository instructorRepository, InstructorDetailRepository instructorDetailRepository, CourseRepository courseRepository) {
        this.instructorRepository = instructorRepository;
        this.instructorDetailRepository = instructorDetailRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public void saveInstructor(InstructorDto instructorDto) {

        // Get each value from instructor DTO
        String firstName = instructorDto.getFirstName();
        String lastName = instructorDto.getLastName();
        String email = instructorDto.getEmail();
        String youtubeChannel = instructorDto.getYoutubeChannel();
        String hobby = instructorDto.getHobby();

        youtubeChannel = youtubeChannel.isEmpty() ? "N/A" : youtubeChannel;
        hobby = hobby.isEmpty() ? "N/A" : hobby;

        // Create a new instance of instructor detail and instructor
        InstructorDetail instructorDetail = new InstructorDetail(youtubeChannel, hobby);
        Instructor instructor = new Instructor(firstName, lastName, email);
        instructor.setInstructorDetail(instructorDetail);

        // Save the instance of instructor
        instructorRepository.save(instructor);
    }

    @Override
    public void updateInstructor(InstructorDto instructorDto) {

        Long id = getIdByEmail(instructorDto.getEmail());
        Instructor foundInstructor = findInstructorById(id);


        if (foundInstructor.getInstructorDetail() == null) {
            foundInstructor.setInstructorDetail(new InstructorDetail(
                    instructorDto.getYoutubeChannel(),
                    instructorDto.getHobby()
            ));
        }

        foundInstructor.setFirstName(instructorDto.getFirstName());
        foundInstructor.setLastName(instructorDto.getLastName());
        foundInstructor.setEmail(instructorDto.getEmail());
        foundInstructor.getInstructorDetail().setYoutubeChannel(instructorDto.getYoutubeChannel());
        foundInstructor.getInstructorDetail().setHobby(instructorDto.getHobby());

        instructorRepository.save(foundInstructor);
    }

    @Override
    public List<Instructor> getAllInstructors() {
        return instructorRepository.findAll();
    }

    @Override
    public boolean isEmailExists(String email) {
        return instructorRepository.existsByEmail(email);
    }

    @Override
    public Long getIdByEmail(String email) {

        Long id;
        Optional<Instructor> result = instructorRepository.findByEmail(email);

        if (result.isPresent()) {
            id = result.get().getId();
        } else {
            throw new RuntimeException("Did not find email - " + email);
        }
        return id;
    }

    @Override
    public Instructor findInstructorById(Long id) {

        Instructor instructor;
        Optional<Instructor> result = instructorRepository.findById(id);

        if (result.isPresent()) {
            instructor = result.get();

            List<Course> courses = courseRepository.findByInstructor_id(id);
            instructor.setCourses(courses);
        } else {
            throw new RuntimeException("Did not find id - " + id);
        }

        return instructor;

    }

    @Override
    public void deleteInstructorById(Long id) {

        Instructor instructor = findInstructorById(id);
        List<Course> courses = instructor.getCourses();

        if (!courses.isEmpty()) {
            for (Course course : courses) {
                course.setInstructor(null);
            }
            courseRepository.saveAllAndFlush(courses);
        }

        instructorRepository.deleteById(id);

    }

    @Override
    public void deleteOtherDetailsById(Long id) {

        Instructor instructor = findInstructorById(id);
        instructor.setInstructorDetail(null);

        instructorRepository.save(instructor);
        instructorDetailRepository.deleteById(id);
    }

    @Override
    public void addCourse(Long id, CourseDto courseDto) {

        // Get the instructor
        Instructor instructor = findInstructorById(id);

        // Create an instance of course
        Course course = new Course(courseDto.getCourseName());

        instructor.addCourse(course);

        // Save it to database
        instructorRepository.save(instructor);

    }

    @Override
    public Course findCourseById(Long courseId) {

        Optional<Course> result = courseRepository.findById(courseId);
        Course course;

        if (result.isPresent()) {
            course = result.get();
        } else {
            throw new RuntimeException("Did not find course id - " + courseId);
        }

        return course;
    }


    @Override
    public void deleteCourseById(Long id) {

        Course course = findCourseById(id);

        // Make the foreign key as null, before deleting the course
        course.setInstructor(null);

        // Save the modified course
        courseRepository.save(course);

        courseRepository.deleteById(id);
    }

    @Override
    public void updateCourse(Long courseId, CourseDto courseDto) {

        Course course = findCourseById(courseId);

        String newTitle = courseDto.getCourseName();

        course.setTitle(newTitle);

        courseRepository.save(course);
    }
}
