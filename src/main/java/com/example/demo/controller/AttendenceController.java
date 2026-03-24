package com.example.demo.controller;

import com.example.demo.dto.AttendanceRequest;
import com.example.demo.dto.AttendanceSummaryResponse;
import com.example.demo.service.AttendanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/v1/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    /**
     * Mark attendance for an employee
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AttendanceSummaryResponse> markAttendance(
            @Valid @RequestBody AttendanceRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(attendanceService.markAttendance(request));
    }

    /**
     * Get attendance for a specific employee
     */
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<AttendanceSummaryResponse>> getEmployeeAttendance(
            @PathVariable Long employeeId,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        return ResponseEntity.ok(
                attendanceService.getEmployeeAttendance(employeeId, startDate, endDate));
    }

    /**
     * Get attendance summary for a date range
     */
    @GetMapping("/summary")
    public ResponseEntity<List<AttendanceSummaryResponse>> getAttendanceSummary(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(
                attendanceService.getAttendanceSummary(startDate, endDate));
    }

    /**
     * Get paginated attendance records
     */
    @GetMapping
    public ResponseEntity<Page<AttendanceSummaryResponse>> getAttendances(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "attendanceDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        return ResponseEntity.ok(
                attendanceService.getAttendances(page, size, sortBy, sortDir));
    }

    /**
     * Delete attendance record
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAttendance(@PathVariable Long id) {
        attendanceService.deleteAttendance(id);
    }
}
