package com.example.demo.util;

import com.example.demo.dto.EmployeeResponse;
import com.example.demo.entity.Employee;
import com.example.demo.enums.EmployeeStatus;

public class TestData {

    public static Employee employee() {
        Employee e = new Employee();
        e.setId(1L);
        e.setFirstName("Archit");
        e.setLastName("Singh");
        e.setStatus(EmployeeStatus.ACTIVE);
        return e;
    }

    public static EmployeeResponse employeeResponse() {
        EmployeeResponse r = new EmployeeResponse();
        r.setId(1L);
        r.setStatus("ACTIVE");
        return r;
    }
}
