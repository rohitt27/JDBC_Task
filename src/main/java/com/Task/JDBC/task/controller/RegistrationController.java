package com.Task.JDBC.task.controller;

import com.Task.JDBC.task.dto.RegistrationDto;
import com.Task.JDBC.task.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;

    @PostMapping("save/Registration")
    public ResponseEntity<?> saveReg(@RequestBody RegistrationDto registrationDto){
        return registrationService.saveRegistration(registrationDto);
    }
    @GetMapping("getRegistration/{id}")
    public ResponseEntity<?> get(@PathVariable Long id){
        return registrationService.getRegistration(id);
    }
}
