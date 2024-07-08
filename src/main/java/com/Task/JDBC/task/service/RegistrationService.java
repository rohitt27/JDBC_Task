package com.Task.JDBC.task.service;

import com.Task.JDBC.task.dto.RegistrationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface RegistrationService {
    ResponseEntity<?> saveRegistration(RegistrationDto registrationDto);
    ResponseEntity<?> getRegistration(Long id);
}
