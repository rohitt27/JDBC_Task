package com.Task.JDBC.task.service;

import com.Task.JDBC.task.dto.StudentDto;
import org.springframework.http.ResponseEntity;

public interface StudentService {
    ResponseEntity<?> saveStudent(StudentDto studentDto);
    ResponseEntity<?> getStudentId(Long id);
    ResponseEntity<?> updateStudent(Long id ,StudentDto studentDto);
}
