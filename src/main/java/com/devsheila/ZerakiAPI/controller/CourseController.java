package com.devsheila.ZerakiAPI.controller;

import com.devsheila.ZerakiAPI.model.Course;
import com.devsheila.ZerakiAPI.model.Institution;
import com.devsheila.ZerakiAPI.model.Student;
import com.devsheila.ZerakiAPI.payload.CourseDto;
import com.devsheila.ZerakiAPI.payload.ResponseBody;
import com.devsheila.ZerakiAPI.response.ResponseHandler;
import com.devsheila.ZerakiAPI.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    //GET COURSE BY INSTITUTION ID
    @GetMapping("/institution/{institutionId}")
    public ResponseEntity<Object> getAllCoursesByInstitution(@PathVariable(value = "institutionId") Long institutionId){
        return ResponseHandler.responseBuilder("Courses for requested institution.", HttpStatus.OK, courseService.getAllCoursesByInstitution(institutionId));
    }

    //CREATE AND ADD A COURSE TO AN INSTITUTION
    @PostMapping("/institution/{institutionId}")
    public ResponseEntity<Object> createComment(@PathVariable(value = "institutionId") long institutionId,  @RequestBody CourseDto courseDto){
        ResponseBody responseBody=courseService.addCourseToInstitution(institutionId, courseDto);
        if(responseBody.success){
            return ResponseHandler.responseBuilder(responseBody.message, HttpStatus.CREATED, responseBody.course);
        }
        return ResponseHandler.responseBuilder(responseBody.message, HttpStatus.BAD_REQUEST,responseBody.course );
    }

    //FILTER COURSE BY A QUERY
    @GetMapping("/search")
    public ResponseEntity<Object> searchCourses(@RequestParam("query") String query) {
        List<Course> courses = courseService.searchCourses(query);
        return ResponseHandler.responseBuilder("Filter courses", HttpStatus.OK, courses);
    }

    //SORT COURSES IN ASC OR DESC
    @GetMapping("/sort")
    public ResponseEntity<Object> sortInstitutions(@RequestParam("order") String order) {
        if(order.equalsIgnoreCase("asc")|| order.equalsIgnoreCase("desc")){
            List<Course> courses = courseService.sortCourses(order);
            return ResponseHandler.responseBuilder("Sorted list of institutions", HttpStatus.OK, courses);
        }else{
            return ResponseHandler.responseBuilder("Invalid sort order request", HttpStatus.BAD_REQUEST, null);
        }
    }

    //GET A COURSE BY ITS ID
    @GetMapping
    public ResponseEntity<Object> getCourseById(@PathVariable("institutionId") Long institutionId, @PathVariable("courseId") Long courseId) {
        CourseDto course = courseService.getCourseById(institutionId, courseId);
            return ResponseHandler.responseBuilder("Course retrieved successfully.", HttpStatus.OK, null);
    }


    //DELETE A COURSE BY IT'S ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCourse(@PathVariable("id") Long id) {

        boolean isDeleted = courseService.deleteCourse(id);
         if (isDeleted) {
             return ResponseHandler.responseBuilder("Course deleted successfully.", HttpStatus.OK, null);
         } else {
             return ResponseHandler.responseBuilder("Cannot delete course assigned to at least one student.", HttpStatus.BAD_REQUEST, null);
         }
     }


     //UPDATE COURSE NAME
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCourseName(@PathVariable("id") Long id, @RequestBody String newName) {
        ResponseBody responseBody=courseService.updateCourseName(id, newName);
        if (responseBody.success) {
            return ResponseHandler.responseBuilder(responseBody.message, HttpStatus.OK, responseBody.course);
        } else {
            return ResponseHandler.responseBuilder(responseBody.message, HttpStatus.BAD_REQUEST, responseBody.course);
        }
    }
}
