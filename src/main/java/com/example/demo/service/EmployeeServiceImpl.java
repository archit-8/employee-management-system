package com.example.demo.service;
import com.example.demo.dto.*;
import com.example.demo.entity.Employee;
import com.example.demo.enums.EmployeeStatus;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {


    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public EmployeeResponse createEmployee(EmployeeCreateRequest request) {

        if (employeeRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        Employee employee = EmployeeMapper.toEntity(request);

        if (request.getManagerId() != null) {
            Employee manager = employeeRepository.findById(request.getManagerId())
                    .orElseThrow(() -> new BadRequestException("Manager not found"));
            employee.setManager(manager);
        }

        employee.setStatus(EmployeeStatus.ACTIVE);
        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.toResponse(savedEmployee);
    }
    @Override
    @Transactional()
    public EmployeeResponse updateEmployee(Long id , EmployeeUpdateRequest request){
        Employee employee=employeeRepository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("employee not found"));
        EmployeeMapper.updateEnity(employee,request);
        Employee updateEmployee=employeeRepository.save(employee);
        return  EmployeeMapper.toResponse(updateEmployee);

    }


    @Override
    @Transactional(readOnly=true)
    public  EmployeeResponse getEmployeeById(Long id){
        Employee employee=employeeRepository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("employee  not found"));
        return  EmployeeMapper.toResponse(employee);
    }
    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponse> getAllEmployees(){
        return  employeeRepository.findAll()
                .stream()
                .map(EmployeeMapper::toResponse)
                .collect(Collectors.toList());
    }



    @Override
    @Transactional
    public void deleteEmployeeId(Long id){
        Employee employee=employeeRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Employee not found"));
        employeeRepository.delete(employee);
    }
    @Override
    @Transactional
    public  void deleteAllEmployee(){
        employeeRepository.deleteAll();
    }

    @Override
    @Transactional
    public EmployeeResponse patchEmployee(Long id, EmployeeUpdateRequest request){
        Employee employee=employeeRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Employee not found"));

        if(request.getFirstName()!=null){
            employee.setFirstName(request.getFirstName());

        }
        if(request.getLastName()!=null){
            employee.setLastName(request.getLastName());

        }
        if(request.getPhoneNumber()!=null){
            employee.setPhoneNumber(request.getPhoneNumber());
        }
        if(request.getDepartment()!=null){
            employee.setDepartment(request.getDepartment());
        }
        if(request.getDesignation()!=null){
            employee.setDesignation(request.getDesignation());
        }
        if(request.getManagerId()!=null){
            Employee manager=employeeRepository.findById(request.getManagerId())
                    .orElseThrow(()->new ResourceNotFoundException("Manager not found"));
            employee.setManager(manager);
        }
        return  EmployeeMapper.toResponse(employee);
    }
    public Page<EmployeeResponse> getEmployees(int page, int size, String sortBy, String sortDir){
        Sort sort=sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                :Sort.by(sortBy).ascending();
        Pageable pageable= PageRequest.of(page,size,sort);

        return employeeRepository.findAll(pageable)
                .map(EmployeeMapper::toResponse);
    }

}
