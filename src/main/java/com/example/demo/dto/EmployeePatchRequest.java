package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeePatchRequest {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private  String department;
    private String designation;
    private Long managerId;
}
