package com.example.demo.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.bridge.IMessage;

@Getter
@Setter
public class EmployeeCreateRequest {

    @NotBlank(message="First name required")
    private  String firstName;

    private  String lastName;
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private  String email;
    @Size(min=10,max=15,message = "phone number is required")
    private  String phoneNumber;
    @NotBlank(message = "Department is required")
    private  String department;
    @NotBlank(message = "Designation is required")
    private String designation;

    private Long managerId;


}
