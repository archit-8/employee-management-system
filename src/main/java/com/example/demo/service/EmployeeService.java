package com.example.demo.service;

import com.example.demo.dto.EmployeeCreateRequest;
import com.example.demo.dto.EmployeeResponse;
import com.example.demo.dto.EmployeeUpdateRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {

    EmployeeResponse createEmployee(EmployeeCreateRequest request);
    EmployeeResponse getEmployeeById(Long id);
    EmployeeResponse updateEmployee(Long id , EmployeeUpdateRequest request);
    List<EmployeeResponse> getAllEmployees();
    public void deleteEmployeeId(Long id);
    public  void deleteAllEmployee();
    EmployeeResponse patchEmployee(Long id, EmployeeUpdateRequest request);
    Page<EmployeeResponse> getEmployees(int page, int size, String sortBy, String sortDir);

}
