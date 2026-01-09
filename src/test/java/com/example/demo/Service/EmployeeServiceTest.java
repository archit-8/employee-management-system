package com.example.demo.Service;

import com.example.demo.dto.EmployeeResponse;
import com.example.demo.entity.Employee;
import com.example.demo.enums.EmployeeStatus;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;
    @Test
    void shouldReturnEmployee_whenEmployeeExists(){
        Employee employee=new Employee();
        employee.setId(1L);
        employee.setFirstName("Archit");
        employee.setLastName("Singh");
        employee.setEmail("archit@gmail.com");
        employee.setDepartment("IT");
        employee.setDesignation("Developer");
        employee.setStatus(EmployeeStatus.ACTIVE);
        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));
        //when
        EmployeeResponse response=employeeService.getEmployeeById(1L);
        //then
        assertEquals(1L,response.getId());
        assertEquals("ACTIVE", response.getStatus());
        verify(employeeRepository).findById(1L);

    }
    @Test
    void showThrowException_whenEmployeeNotFound(){
        when(employeeRepository.findById(1L))
                .thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                ()->employeeService.getEmployeeById(1L));
    }
}
