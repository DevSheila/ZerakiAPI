package com.devsheila.ZerakiAPI.service;

import com.devsheila.ZerakiAPI.model.Course;
import com.devsheila.ZerakiAPI.model.Institution;
import com.devsheila.ZerakiAPI.model.Student;
import com.devsheila.ZerakiAPI.payload.CourseDto;
import com.devsheila.ZerakiAPI.payload.ResponseBody;
import com.devsheila.ZerakiAPI.repository.CourseRepository;
import com.devsheila.ZerakiAPI.repository.InstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final InstitutionRepository institutionRepository;

    ModelMapper mapper;
    @Autowired
    public CourseService(CourseRepository courseRepository, InstitutionRepository institutionRepository,ModelMapper mapper) {
        this.courseRepository = courseRepository;
        this.institutionRepository = institutionRepository;
        this.mapper=mapper;
    }

    //GET COURSE
    public Optional<Course> getCourse(Long institutionId, Long courseId) {
        return courseRepository.findByIdAndInstitutionId(courseId, institutionId);
    }

    //GET ALL COURSES BY AN INSTITUTION
    public List<CourseDto> getAllCoursesByInstitution(Long institutionId) {
        // retrieve courses by institutionId
        List<Course> courses = courseRepository.findAllByInstitutionId(institutionId);

        // convert list of course entities to list of courses dto's
        return courses.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());

    }

    //GET A COURSE BY IT'S ID
    public CourseDto getCourseById(Long institutionId, Long courseId) {
        // retrieve post entity by id
        Course course = courseRepository.findById(courseId).get();

        // retrieve comment by id
        Institution institution = institutionRepository.findById(institutionId).get();


        return mapToDTO(course);
    }

    //SARCH A COURSE
    public List<Course> searchCourses(String query) {
        return courseRepository.findByNameContainingIgnoreCase(query);
    }

    //SORT THROUGH COURSES
    public List<Course> sortCourses(String order) {
        if (order.equalsIgnoreCase("asc")) {
            return courseRepository.findAllByOrderByNameAsc();
        } else {
            return courseRepository.findAllByOrderByNameDesc();
        }
    }

    //ADD INSTITUTION
    public ResponseBody addInstitution(Institution institution) {
        ResponseBody responseBody;

        if (institutionRepository.findByName(institution.getName()).isPresent()) {
            responseBody= new ResponseBody(false,"Institution with the same name already exists", (Institution) null);
            return responseBody; // Institution with the same name already exists
        }
        institutionRepository.save(institution);
        responseBody= new ResponseBody(true,"Institution created successfully",institution);

        return responseBody;
    }


    //ADD COURSE TO AN INSTITUTION
    public ResponseBody  addCourseToInstitution(Long institutionId, CourseDto courseDto)  {

        Course course = mapToEntity(courseDto);

        if(courseRepository.findByNameAndInstitutionId(courseDto.getName(),institutionId).isPresent()){
            return  new ResponseBody(false,"course of same name in same institution exists",(Course) null);
        }
        Institution institution=institutionRepository.findById(institutionId).get();
        course.setInstitution(institution);
        // comment entity to DB
        Course newCourse = courseRepository.save(course);
        return  new ResponseBody(true,"Course added successfully",newCourse);
    }



    //DELETE COURSE
    public boolean deleteCourse(Long courseId) {
        Optional<Course> course = courseRepository.findById(courseId);

        if (course.isPresent()) { //check if course exists
            if (course.get().getStudentSet().isEmpty()) {//Check if course has students
                courseRepository.deleteById(courseId);
                return true;
            }
        }
        return false; // Cannot delete course assigned to at least one student
    }



    //UPDATE COURSE NAME

    public ResponseBody updateCourseName(Long id, String newName) {
        Optional<Course> courseOptional = courseRepository.findById(id);//find the course


        if (courseOptional.isPresent()) { //check if course exists
            Long institutionId=courseOptional.get().getInstitution().getId();

            //check if there are courses of the same name and in same institution
            if(courseRepository.findByNameAndInstitutionId(newName,institutionId).isPresent()){
                return  new ResponseBody(false,"course of same name in same institution exists",(Course) null);
            }
            //update course name
            courseOptional.get().setName(newName);
            Course updatedCourse=courseRepository.save(courseOptional.get());
            return  new ResponseBody(true,"Course updated successfully",updatedCourse);
        }
        return  new ResponseBody(false,"Course not found",(Course) null);

    }


    //GET,SORT,SEARCH COURSES
    public Page<Course> getCoursesByInstitution(Long institutionId, String search, Pageable pageable, String sort) {
        if (sort.equalsIgnoreCase("DESC")) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("name").descending());
        } else {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("name").ascending());
        }

        if (search != null && !search.isEmpty()) {
            return courseRepository.findByInstitutionIdAndNameContainingIgnoreCase(institutionId, search, pageable);
        } else {
            return courseRepository.findByInstitutionId(institutionId, pageable);
        }
    }

    private CourseDto mapToDTO(Course course){
        CourseDto courseDto = mapper.map(course, CourseDto.class);
        return  courseDto;
    }

    private Course mapToEntity(CourseDto courseDto){
        Course course = mapper.map(courseDto, Course.class);

        return  course;
    }
}
