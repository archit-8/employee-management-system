package com.example.demo.repository;

import com.example.demo.entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<LeaveRequest, Long> {

    /**
     * Find all leave requests for an employee
     */
    @Query("SELECT lr FROM LeaveRequest lr WHERE lr.employee.id = :employeeId ORDER BY lr.startDate DESC")
    List<LeaveRequest> findByEmployeeId(@Param("employeeId") Long employeeId);

    /**
     * Find leave requests by status
     */
    @Query("SELECT lr FROM LeaveRequest lr WHERE lr.status = :status ORDER BY lr.startDate DESC")
    List<LeaveRequest> findByStatus(@Param("status") String status);

    /**
     * Check for overlapping leave requests
     */
    @Query("SELECT CASE WHEN COUNT(lr) > 0 THEN true ELSE false END " +
            "FROM LeaveRequest lr WHERE lr.employee.id = :employeeId " +
            "AND lr.status IN ('APPROVED', 'PENDING') " +
            "AND ((lr.startDate <= :endDate AND lr.endDate >= :startDate))")
    boolean existsOverlappingLeave(
            @Param("employeeId") Long employeeId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    /**
     * Count approved leave days for an employee in a month
     */
    @Query("SELECT COUNT(lr) FROM LeaveRequest lr WHERE lr.employee.id = :employeeId " +
            "AND lr.status = 'APPROVED' " +
            "AND YEAR(lr.startDate) = :year AND MONTH(lr.startDate) = :month")
    long countApprovedLeaveDays(
            @Param("employeeId") Long employeeId,
            @Param("year") int year,
            @Param("month") int month);

    /**
     * Find all pending leaves for approval
     */
    @Query("SELECT lr FROM LeaveRequest lr WHERE lr.status = 'PENDING' ORDER BY lr.createdAt ASC")
    List<LeaveRequest> findAllPendingLeaves();

    /**
     * Find leave requests for a date range
     */
    @Query("SELECT lr FROM LeaveRequest lr WHERE lr.startDate BETWEEN :startDate AND :endDate " +
            "OR lr.endDate BETWEEN :startDate AND :endDate ORDER BY lr.startDate ASC")
    List<LeaveRequest> findByDateRange(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}
