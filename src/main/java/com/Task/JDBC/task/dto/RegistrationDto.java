package com.Task.JDBC.task.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


public class RegistrationDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String gender;
    private String address;
    private Long mobileNo;
    private List<VisitDto> visit;

    public Long getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(Long mobileNo) {
        this.mobileNo = mobileNo;
    }

    public List<VisitDto> getVisit() {
        return visit;
    }

    public void setVisit(List<VisitDto> visit) {
        this.visit = visit;
    }
}
