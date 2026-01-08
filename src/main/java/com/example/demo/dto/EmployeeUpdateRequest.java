package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeUpdateRequest {

    @NotBlank(message = "First name is required")
    private String firstName;

    private String lastName;

    @Pattern(
            regexp = "^[0-9]{10,15}$",
            message = "Invalid phone number"
    )
    private String phoneNumber;
    @NotBlank(message = "Department is required")
    private String department;
    @NotBlank(message = "Designation is required")
    private String designation;
    private Long managerId;

}
