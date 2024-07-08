package com.Task.JDBC.task.entity;

import jakarta.persistence.*;
import lombok.Cleanup;

@Entity
@Table(name = "visit_detail")
public class VisitDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "unit")
    private String unit;
    @Column(name = "patient_source")
    private String patientSource;
    @Column(name = "department")
    private String department;
    @Column(name = "doctor")
    private String doctor;
    @Column(name = "registration_Id")
    private Long registrationId;



}
