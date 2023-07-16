package com.devsheila.ZerakiAPI.payload;

import com.devsheila.ZerakiAPI.model.Course;
import com.devsheila.ZerakiAPI.model.Institution;
import com.devsheila.ZerakiAPI.model.Student;

import java.util.List;

public class ResponseBody {

    public Boolean success;
    public String message;
    public Course course;
    public List<Course> courses;

    public Institution institution;
    public List<Institution> institutions;
    public Student student;

    public ResponseBody(Boolean success, String message, Course course) {
        this.success = success;
        this.message = message;
        this.course = course;
    }

    public ResponseBody(Boolean success, String message, Institution institution) {
        this.success = success;
        this.message = message;
        this.institution = institution;
    }

    public ResponseBody(Boolean success, String message, List<Institution> institutions) {
        this.success = success;
        this.message = message;
        this.institutions = institutions;
    }

    public ResponseBody(Boolean success, String message,  Student student) {
        this.success = success;
        this.message = message;
        this.student = student;
    }
}
