package com.example.demo.config;

import com.example.demo.dto.EmployeeCreateRequest;
import com.example.demo.entity.Employee;
import com.example.demo.enums.EmployeeStatus;
import com.example.demo.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Profile("dev")
@Component
@RequiredArgsConstructor
public class EmployeeDataLoader implements CommandLineRunner {

    private final EmployeeRepository repository;
    private static final Random RANDOM = new Random();

    @Override
    public void run(String... args) {

        // safety: don't insert again
        if (repository.count() > 0) {
            return;
        }

        List<Employee> employees = IntStream.range(0, 10)
                .mapToObj(i -> {
                    EmployeeCreateRequest req = randomRequest(i);
                    return mapToEntity(req);
                })
                .collect(Collectors.toList());

        repository.saveAll(employees);
    }

    // ---------------- DTO → ENTITY ----------------

    private Employee mapToEntity(EmployeeCreateRequest req) {
        Employee e = new Employee();
        e.setFirstName(req.getFirstName());
        e.setLastName(req.getLastName());
        e.setEmail(req.getEmail());
        e.setPhoneNumber(req.getPhoneNumber());
        e.setDepartment(req.getDepartment());
        e.setDesignation(req.getDesignation());
        e.setDateOfJoining(LocalDate.now().minusDays(RANDOM.nextInt(1000)));
        e.setEmployeeCode(UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        e.setStatus(EmployeeStatus.ACTIVE);

        // manager intentionally skipped (null)
        return e;
    }

    // ---------------- RANDOM REQUEST ----------------

    private EmployeeCreateRequest randomRequest(int i) {
        EmployeeCreateRequest req = new EmployeeCreateRequest();
        req.setFirstName(randomFirstName());
        req.setLastName(randomLastName());
        req.setEmail(randomEmail());
        req.setPhoneNumber(randomPhone());
        req.setDepartment(randomDepartment());
        req.setDesignation(randomDesignation());
        req.setManagerId(null); // optional

        return req;
    }

    // ---------------- RANDOM HELPERS ----------------

    private String randomFirstName() {
        return List.of("Archit", "Rohit", "Neha", "Amit", "Pooja", "Kunal", "Sneha", "Rahul")
                .get(RANDOM.nextInt(8));
    }

    private String randomLastName() {
        return List.of("Singh", "Sharma", "Verma", "Patel", "Jain", "Gupta")
                .get(RANDOM.nextInt(6));
    }

    private String randomEmail() {
        return UUID.randomUUID().toString().substring(0, 6) + "@gmail.com";
    }

    private String randomPhone() {
        return "9" + (100000000 + RANDOM.nextInt(900000000));
    }

    private String randomDepartment() {
        return List.of("Engineering", "QA", "HR", "Sales", "Support")
                .get(RANDOM.nextInt(5));
    }

    private String randomDesignation() {
        return List.of("Software Engineer", "Tester", "Manager", "Lead", "Developer")
                .get(RANDOM.nextInt(5));
    }
}
