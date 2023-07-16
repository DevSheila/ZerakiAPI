package com.devsheila.ZerakiAPI.service;

import com.devsheila.ZerakiAPI.exception.ResourceNotFoundException;
import com.devsheila.ZerakiAPI.model.Course;
import com.devsheila.ZerakiAPI.model.Student;
import com.devsheila.ZerakiAPI.payload.ResponseBody;
import com.devsheila.ZerakiAPI.payload.StudentDto;
import com.devsheila.ZerakiAPI.repository.CourseRepository;
import com.devsheila.ZerakiAPI.repository.InstitutionRepository;
import com.devsheila.ZerakiAPI.repository.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final InstitutionRepository institutionRepository;

    ModelMapper modelMapper;
    @Autowired
    public StudentService(StudentRepository studentRepository,CourseRepository courseRepository,InstitutionRepository institutionRepository,ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.courseRepository=courseRepository;
        this.institutionRepository= institutionRepository;
        this.modelMapper=modelMapper;
    }

    public StudentDto createStudent(long courseId, StudentDto studentDto) {
        Student student =mapToEntity(studentDto);
        // retrieve course entity by id
        Course course = courseRepository.findById(courseId).get();
        // add student to course entity
        student.setCourse(course);
        // add student to DB
        Student newStudent =  studentRepository.save(student);
        return mapToDTO(newStudent);
    }

    public ResponseBody deleteStudent(Long id) {
      studentRepository.findById(id)
              .orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));
      studentRepository.deleteById(id);

      return new ResponseBody(true,"Student deleted successfully",(Student)null);

    }

    public Student updateStudent(Long id, StudentDto studentDto) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));
        Course course = courseRepository.findById(student.getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id",student.getCourse().getId()));
        student.setName(studentDto.getName());
        student.setCourse(course);
        return studentRepository.save(student);
    }

    public ResponseBody changeCourse(Long id, Long courseId) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseId));

        Long studInstitutionID = student.getCourse().getInstitution().getId();
        Long courseInstitutionID = course.getInstitution().getId();

        if(studInstitutionID != courseInstitutionID){//if the courses are not from the same institution

            return new ResponseBody(false,"Course not from the same institution", (Student)null);
        }else{
            student.setCourse(course);
            return new ResponseBody(true,"Changed course successfully", studentRepository.save(student));
        }
    }

    private StudentDto mapToDTO(Student student){
        StudentDto studentDto = modelMapper.map(student, StudentDto.class);
        return  studentDto;
    }

    private Student mapToEntity(StudentDto studentDto){
        Student student = modelMapper.map(studentDto, Student.class);
        return  student;
    }

}

