package com.example.demo.repository;

import com.example.demo.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    /**
     * Find attendance records for an employee within a date range
     */
    @Query("SELECT a FROM Attendance a WHERE a.employee.id = :employeeId " +
            "AND a.attendanceDate BETWEEN :startDate AND :endDate " +
            "ORDER BY a.attendanceDate DESC")
    List<Attendance> findByEmployeeIdAndDateRange(
            @Param("employeeId") Long employeeId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    /**
     * Check if attendance already exists for an employee on a specific date
     */
    boolean existsByEmployeeIdAndAttendanceDate(Long employeeId, LocalDate attendanceDate);

    /**
     * Find all attendance records for a specific date
     */
    List<Attendance> findByAttendanceDate(LocalDate attendanceDate);

    /**
     * Find attendance records for a date range
     */
    @Query("SELECT a FROM Attendance a WHERE a.attendanceDate BETWEEN :startDate AND :endDate " +
            "ORDER BY a.attendanceDate DESC, a.employee.firstName ASC")
    List<Attendance> findByDateRange(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    /**
     * Get attendance summary statistics for an employee
     */
    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.employee.id = :employeeId AND a.status = :status " +
            "AND a.attendanceDate BETWEEN :startDate AND :endDate")
    long countByEmployeeStatusAndDateRange(
            @Param("employeeId") Long employeeId,
            @Param("status") String status,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}
