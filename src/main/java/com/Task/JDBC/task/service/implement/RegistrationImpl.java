package com.Task.JDBC.task.service.implement;

import com.Task.JDBC.task.dto.RegistrationDto;
import com.Task.JDBC.task.dto.VisitDto;
import com.Task.JDBC.task.service.RegistrationService;
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
public class RegistrationImpl implements RegistrationService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public ResponseEntity<?> saveRegistration(RegistrationDto registrationDto) {
        if (registrationDto.getId() == null) {
            String insertReg = "INSERT INTO registration(first_name, last_name, gender, address, mobile_no) VALUES (?, ?, ?, ?, ?) RETURNING id";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
                PreparedStatement pst = con.prepareStatement(insertReg, Statement.RETURN_GENERATED_KEYS);
                pst.setString(1, registrationDto.getFirstName());
                pst.setString(2, registrationDto.getLastName());
                pst.setString(3, registrationDto.getGender());
                pst.setString(4, registrationDto.getAddress());
                pst.setLong(5, registrationDto.getMobileNo());
                return pst;
            }, keyHolder);

            Long visitId = keyHolder.getKey().longValue();
            registrationDto.getVisit().forEach(visit -> {
                String visitInsert = "INSERT INTO visit_detail(unit, patient_source, department, doctor, registration_Id) VALUES (?, ?, ?, ?, ?)";
                jdbcTemplate.update(visitInsert, visit.getUnit(), visit.getPatientSource(), visit.getDepartment(), visit.getDoctor(), visitId);
            });

            return ResponseEntity.ok("Saved successfully");
        } else {
            String insertUpdate = "UPDATE registration SET first_name = ?, last_name = ?, gender = ?, address = ?, mobile_no = ? WHERE id = ?";
            jdbcTemplate.update(insertUpdate, registrationDto.getFirstName(), registrationDto.getLastName(), registrationDto.getGender(), registrationDto.getAddress(), registrationDto.getMobileNo(), registrationDto.getId());
            String deleteVisit = "DELETE FROM visit_detail WHERE registration_id = ?";
            jdbcTemplate.update(deleteVisit, registrationDto.getId());

            registrationDto.getVisit().forEach(visit -> {
                String visitInsert = "Insert into visit_detail(unit, patient_source, department, doctor, registration_Id) values (?, ?, ?, ?, ?)";
                jdbcTemplate.update(visitInsert, visit.getUnit(), visit.getPatientSource(), visit.getDepartment(), visit.getDoctor(), registrationDto.getId());
            });
            return ResponseEntity.ok("Update successfully");
        }
    }

    @Override
    public ResponseEntity<?> getRegistration(Long id) {
        String registration = "Select * from registration where id = ?";
        Map<String, Object> registrationMap = jdbcTemplate.queryForMap(registration,id);

        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setFirstName((String) registrationMap.get("first_name"));
        registrationDto.setLastName((String) registrationMap.get("last_name"));
        registrationDto.setGender((String) registrationMap.get("gender"));
        registrationDto.setAddress((String) registrationMap.get("address"));
        registrationDto.setMobileNo((Long) registrationMap.get("mobile_no"));

        String visitQuery = "Select * from visit_detail where registration_Id = ?";
        List<VisitDto> visitDtoList = jdbcTemplate.query(visitQuery,
                (rs, rowNum) -> {
                    VisitDto visitDto = new VisitDto();
                    visitDto.setUnit(rs.getString("unit"));
                    visitDto.setPatientSource(rs.getString("patient_source"));
                    visitDto.setDepartment(rs.getString("department"));
                    visitDto.setDoctor(rs.getString("doctor"));
                    return visitDto;
                },id);
        registrationDto.setVisit(visitDtoList);
        return ResponseEntity.ok(registrationDto);

        }
    }

