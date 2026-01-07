package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SecondaryRow;

@Getter
@Setter
public class EmployeeResponse {

    private  Long id;
    private  String fullName;
    private  String email;
    private  String department;
    private  String designation;
    private  String status;

}
