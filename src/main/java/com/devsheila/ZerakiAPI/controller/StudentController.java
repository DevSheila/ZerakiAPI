package com.devsheila.ZerakiAPI.controller;

import com.devsheila.ZerakiAPI.model.Student;
import com.devsheila.ZerakiAPI.payload.ResponseBody;
import com.devsheila.ZerakiAPI.payload.StudentDto;
import com.devsheila.ZerakiAPI.response.ResponseHandler;
import com.devsheila.ZerakiAPI.service.CourseService;
import com.devsheila.ZerakiAPI.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // ADD STUDENT AND ASSIGN A COURSE
    @PostMapping("/course/{courseId}")
    public ResponseEntity<Object> createStudent(@PathVariable(value = "courseId") long courseId,@RequestBody StudentDto studentDto){
        return new ResponseEntity<>(studentService.createStudent(courseId, studentDto), HttpStatus.CREATED);
    }


    //DELETE STUDENT
    @DeleteMapping("/{studentId}")
    public ResponseEntity<Object> deleteStudent(@PathVariable Long studentId) {
        ResponseBody responseBody=studentService.deleteStudent(studentId);

        if(responseBody.success){
            return ResponseHandler.responseBuilder(responseBody.message, HttpStatus.OK, responseBody.student);
        }
        return ResponseHandler.responseBuilder(responseBody.message, HttpStatus.BAD_REQUEST, responseBody.student);

    }

    //UPDATE STUDENT DETAILS
    @PutMapping("/{studentId}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long studentId, @RequestBody StudentDto studentDto) {
        Student updatedStudent = studentService.updateStudent(studentId, studentDto);
        return ResponseEntity.ok(updatedStudent);
    }

    //CHANGE STUDENT COURSE
    @PutMapping("/{studentId}/course/{courseId}")
    public ResponseEntity<Object> changeCourse( @PathVariable Long studentId,  @PathVariable Long courseId) {

        ResponseBody responseBody= studentService.changeCourse(studentId, courseId);
        if(responseBody.success){
            return ResponseHandler.responseBuilder(responseBody.message, HttpStatus.OK, responseBody.student);
        }
        return ResponseHandler.responseBuilder(responseBody.message, HttpStatus.BAD_REQUEST, responseBody.student);

    }

    //LIST STUDENTS IN INSTITUTION-SEARCH,SORT,FILTER
    @GetMapping
    public ResponseEntity<Page<Student>> getAllStudents(Pageable pageable,
                                                        @RequestParam(required = false) Long institutionId,
                                                        @RequestParam(required = false) Long courseId,
                                                        @RequestParam(required = false) String query) {
        Page<Student> students = studentService.getAllStudents(pageable, institutionId, courseId,query);
        return ResponseEntity.ok(students);
    }

    //TRANSFER STUDENT AND ASSIGN COURSE
    @PostMapping("/{studentId}/transfer/{institutionId}/course/{courseId}")
    public ResponseEntity<Object> transferStudent(@PathVariable Long studentId, @PathVariable Long institutionId, @PathVariable Long courseId) {
        ResponseBody responseBody = studentService.transferStudent(studentId, institutionId, courseId);
        if (responseBody.success) {
            return ResponseHandler.responseBuilder(responseBody.message, HttpStatus.OK, responseBody.student);
        }
        return ResponseHandler.responseBuilder(responseBody.message, HttpStatus.BAD_REQUEST, responseBody.student);
    }

}
