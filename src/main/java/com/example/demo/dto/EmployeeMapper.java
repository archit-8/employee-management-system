package com.example.demo.dto;

import com.example.demo.entity.Employee;
import com.example.demo.enums.EmployeeStatus;

public class EmployeeMapper {

    public  static Employee toEntity(EmployeeCreateRequest request){
        Employee emp= new Employee();
        emp.setFirstName(request.getFirstName());
        emp.setLastName(request.getLastName());
        emp.setEmail(request.getEmail());
        emp.setPhoneNumber(request.getPhoneNumber());
        emp.setDepartment(request.getDepartment());
        emp.setDesignation(request.getDesignation());
        emp.setStatus(EmployeeStatus.ACTIVE);
        return  emp;
    }

    public static EmployeeResponse toResponse(Employee employee){

        EmployeeResponse response=new EmployeeResponse();
        response.setId(employee.getId());
        response.setFullName(employee.getFirstName()+" "+employee.getLastName());
        response.setEmail(employee.getEmail());
        response.setDepartment(employee.getDepartment());
        response.setDesignation(employee.getDesignation());
        response.setStatus(employee.getStatus().name());
        return  response;
    }
}
