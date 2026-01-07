package com.example.demo.controller;

import com.example.demo.dto.EmployeeCreateRequest;
import com.example.demo.dto.EmployeeResponse;
import com.example.demo.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private  final EmployeeService employeeService;


    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(
            @Valid @RequestBody EmployeeCreateRequest request
            ){
        EmployeeResponse response=employeeService.createEmployee(request);
        return  new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
