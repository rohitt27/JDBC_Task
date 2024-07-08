package com.Task.JDBC.task.service.implement;

import com.Task.JDBC.task.dto.StudentDto;
import com.Task.JDBC.task.dto.SubjectDto;
import com.Task.JDBC.task.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

@Service
public class StudentImpl implements StudentService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ResponseEntity<?> saveStudent(StudentDto studentDto) {
        String insertQuery =
                "Insert into student (first_name, last_name, roll_no, address) values (?, ?, ?, ?) returning id";

        KeyHolder keyHolder=new GeneratedKeyHolder();
        jdbcTemplate.update(connection->{
            PreparedStatement pst=connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1,studentDto.getFirstName());
            pst.setString(2,studentDto.getLastName());
            pst.setInt(3,studentDto.getRollNo());
            pst.setString(4,studentDto.getAddress());

            return pst;
        },keyHolder);

        Long studentId=keyHolder.getKey().longValue();
        studentDto.getSubjects().forEach(subject->{
            String subjectInsert = "Insert into subject (subjects, student_id) values (?, ?)";
            jdbcTemplate.update(subjectInsert, subject.getSubjects(), studentId);
        });

        return ResponseEntity.ok("Saved successfully");

    }

    @Override
    public ResponseEntity<?> getStudentId(Long id) {

        String studentQuery = "Select * from student where id = ?";

        Map<String, Object> studentMap = jdbcTemplate.queryForMap(studentQuery, id);

        StudentDto studentDto = new StudentDto();
        studentDto.setFirstName((String) studentMap.get("first_name"));
        studentDto.setLastName((String) studentMap.get("last_name"));
        studentDto.setRollNo((Integer) studentMap.get("roll_no"));
        studentDto.setAddress((String) studentMap.get("address"));

        String subjectQuery = "select subjects from subject where student_id = ?";
        List<SubjectDto> subjects = jdbcTemplate.query(subjectQuery,
                (rs, rowNum) -> {
                    SubjectDto subjectDto = new SubjectDto();
                    subjectDto.setSubjects(rs.getString("subjects"));
                    return subjectDto;
                },id

        );
        studentDto.setSubjects(subjects);

        return ResponseEntity.ok(studentDto);

    }

    @Override
    public ResponseEntity<?> updateStudent(Long id, StudentDto studentDto) {
        String updateStudent = "Update student set first_name = ?, last_name = ?, roll_no = ?, address = ? where id = ?";
        int updateRow = jdbcTemplate.update(updateStudent,
                studentDto.getFirstName(),
                studentDto.getLastName(),
                studentDto.getRollNo(),
                studentDto.getAddress(),
                id
        );

        if (updateRow == 0) {
            return ResponseEntity.notFound().build();
        }

        String deleteSubjectsQuery = "Delete from subject where student_id = ?";
        jdbcTemplate.update(deleteSubjectsQuery, id);

        for (SubjectDto subject : studentDto.getSubjects()) {
            String subjectInsert = "Insert into subject (subjects, student_id) values (?, ?)";
            jdbcTemplate.update(subjectInsert, subject.getSubjects(), id);
        }

        return ResponseEntity.ok("Updated successfully");
    }
}