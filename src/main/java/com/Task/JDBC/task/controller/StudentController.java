package com.Task.JDBC.task.controller;

import com.Task.JDBC.task.dto.StudentDto;
import com.Task.JDBC.task.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentController {
@Autowired
    private StudentService studentService;
@PostMapping("saveStudent")
    public ResponseEntity<?> save(@RequestBody StudentDto studentDto){
    return studentService.saveStudent(studentDto);
}
@GetMapping("getSubject/{id}")
    public ResponseEntity<?> getSub(@PathVariable Long id){
    return studentService.getStudentId(id);
}
@PutMapping("updateStudent/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@RequestBody StudentDto studentDto){
    return studentService.updateStudent(id,studentDto);
}

}
