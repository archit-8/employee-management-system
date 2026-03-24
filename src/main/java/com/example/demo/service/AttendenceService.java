package com.example.demo.service;

import com.example.demo.dto.AttendanceRequest;
import com.example.demo.dto.AttendanceSummaryResponse;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {

    /**
     * Mark attendance for an employee
     */
    AttendanceSummaryResponse markAttendance(AttendanceRequest request);

    /**
     * Get attendance for a specific employee
     */
    List<AttendanceSummaryResponse> getEmployeeAttendance(Long employeeId, LocalDate startDate, LocalDate endDate);

    /**
     * Get attendance summary for a date range
     */
    List<AttendanceSummaryResponse> getAttendanceSummary(LocalDate startDate, LocalDate endDate);

    /**
     * Get paginated attendance records
     */
    Page<AttendanceSummaryResponse> getAttendances(int page, int size, String sortBy, String sortDir);

    /**
     * Delete attendance record
     */
    void deleteAttendance(Long id);
}
