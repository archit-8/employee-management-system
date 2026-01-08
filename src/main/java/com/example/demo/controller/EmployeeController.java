package com.example.demo.controller;

import com.example.demo.dto.EmployeeCreateRequest;
import com.example.demo.dto.EmployeeResponse;
import com.example.demo.dto.EmployeeUpdateRequest;
import com.example.demo.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private  final EmployeeService employeeService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponse createEmployee(
            @Valid @RequestBody EmployeeCreateRequest request) {
        return employeeService.createEmployee(request);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<EmployeeResponse> employeeUpdate(
            @PathVariable Long id,@Valid @RequestBody EmployeeUpdateRequest request){
        return  ResponseEntity.ok(employeeService.updateEmployee(id,request));
    }




    @GetMapping("/{id}")
    public  ResponseEntity<EmployeeResponse> EmployeeById(@PathVariable Long id){
        EmployeeResponse response =employeeService.getEmployeeById(id);
        return   ResponseEntity.ok(response);
    }
    @GetMapping("all")
    public ResponseEntity<List<EmployeeResponse>> getAllEmployee() {
        List<EmployeeResponse> response=employeeService.getAllEmployees();

        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity<Page<EmployeeResponse>> getEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ){
        return ResponseEntity.ok(employeeService.getEmployees(page,size,sortBy,sortDir));

    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployeeById(@PathVariable Long id){
        employeeService.deleteEmployeeId(id);
    }
// NOTE: deleteAll endpoint is intentionally disabled to prevent accidental data loss
    /*@DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public  void deleteAllEmployee(){
        employeeService.deleteAllEmployee();
    }*/

     @PatchMapping("/{id}")
     public  ResponseEntity<EmployeeResponse> patchEmployee(
             @PathVariable Long id, @RequestBody EmployeeUpdateRequest request){
         return ResponseEntity.ok(employeeService.patchEmployee(id,request));

     }
}
