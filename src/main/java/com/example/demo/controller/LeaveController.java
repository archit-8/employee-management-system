package com.example.demo.controller;

import com.example.demo.dto.LeaveApplyRequest;
import com.example.demo.dto.LeaveResponse;
import com.example.demo.service.LeaveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/leaves")
@RequiredArgsConstructor
public class LeaveController {

    private final LeaveService leaveService;

    /**
     * Apply for leave
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<LeaveResponse> applyLeave(
            @Valid @RequestBody LeaveApplyRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(leaveService.applyLeave(request));
    }

    /**
     * Get all leaves for a specific employee
     */
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<LeaveResponse>> getEmployeeLeaves(
            @PathVariable Long employeeId) {
        return ResponseEntity.ok(leaveService.getEmployeeLeaves(employeeId));
    }

    /**
     * Get leave by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<LeaveResponse> getLeaveById(@PathVariable Long id) {
        return ResponseEntity.ok(leaveService.getLeaveById(id));
    }

    /**
     * Get all pending leaves (for managers/approvers)
     */
    @GetMapping("/pending")
    public ResponseEntity<List<LeaveResponse>> getPendingLeaves() {
        return ResponseEntity.ok(leaveService.getPendingLeaves());
    }

    /**
     * Approve a leave request
     */
    @PutMapping("/{id}/approve")
    public ResponseEntity<LeaveResponse> approveLeave(
            @PathVariable Long id,
            @RequestBody(required = false) String comments) {
        return ResponseEntity.ok(leaveService.approveLeave(id, comments));
    }

    /**
     * Reject a leave request
     */
    @PutMapping("/{id}/reject")
    public ResponseEntity<LeaveResponse> rejectLeave(
            @PathVariable Long id,
            @RequestBody String comments) {
        return ResponseEntity.ok(leaveService.rejectLeave(id, comments));
    }

    /**
     * Cancel a leave request
     */
    @PutMapping("/{id}/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelLeave(@PathVariable Long id) {
        leaveService.cancelLeave(id);
    }

    /**
     * Get paginated leave records
     */
    @GetMapping
    public ResponseEntity<Page<LeaveResponse>> getLeaves(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        return ResponseEntity.ok(
                leaveService.getLeaves(page, size, sortBy, sortDir));
    }
}
