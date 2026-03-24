package com.example.demo.repository;

import com.example.demo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * Find employee by email
     */
    Optional<Employee> findByEmail(String email);

    /**
     * Check if employee exists by email
     */
    boolean existsByEmail(String email);

    /**
     * Find employee by employee code
     */
    Optional<Employee> findByEmployeeCode(String employeeCode);

    /**
     * Check if employee code exists
     */
    boolean existsByEmployeeCode(String employeeCode);

    /**
     * Find all employees by department
     */
    @Query("SELECT e FROM Employee e WHERE e.department = :department ORDER BY e.firstName ASC")
    List<Employee> findByDepartment(@Param("department") String department);

    /**
     * Find all employees by designation
     */
    @Query("SELECT e FROM Employee e WHERE e.designation = :designation ORDER BY e.firstName ASC")
    List<Employee> findByDesignation(@Param("designation") String designation);

    /**
     * Find all employees by status
     */
    @Query("SELECT e FROM Employee e WHERE e.status = :status ORDER BY e.firstName ASC")
    List<Employee> findByStatus(@Param("status") String status);

    /**
     * Find all active employees
     */
    @Query("SELECT e FROM Employee e WHERE e.status = 'ACTIVE' ORDER BY e.firstName ASC")
    List<Employee> findAllActiveEmployees();

    /**
     * Find employees by department and status
     */
    @Query("SELECT e FROM Employee e WHERE e.department = :department AND e.status = :status ORDER BY e.firstName ASC")
    List<Employee> findByDepartmentAndStatus(
            @Param("department") String department,
            @Param("status") String status);

    /**
     * Find all employees managed by a specific manager
     */
    @Query("SELECT e FROM Employee e WHERE e.manager.id = :managerId ORDER BY e.firstName ASC")
    List<Employee> findByManagerId(@Param("managerId") Long managerId);

    /**
     * Search employees by first name or last name (case-insensitive)
     */
    @Query("SELECT e FROM Employee e WHERE LOWER(e.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(e.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(e.email) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "ORDER BY e.firstName ASC")
    List<Employee> searchEmployees(@Param("searchTerm") String searchTerm);

    /**
     * Get count of employees by department
     */
    @Query("SELECT COUNT(e) FROM Employee e WHERE e.department = :department")
    long countByDepartment(@Param("department") String department);

    /**
     * Get count of active employees
     */
    @Query("SELECT COUNT(e) FROM Employee e WHERE e.status = 'ACTIVE'")
    long countActiveEmployees();

    /**
     * Find employees by phone number
     */
    Optional<Employee> findByPhoneNumber(String phoneNumber);

    /**
     * Check if phone number exists
     */
    boolean existsByPhoneNumber(String phoneNumber);
}
