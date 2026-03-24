package com.example.demo.service;

import com.example.demo.dto.LeaveApplyRequest;
import com.example.demo.dto.LeaveResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LeaveService {

    /**
     * Apply for leave
     */
    LeaveResponse applyLeave(LeaveApplyRequest request);

    /**
     * Get all leaves for a specific employee
     */
    List<LeaveResponse> getEmployeeLeaves(Long employeeId);

    /**
     * Get leave by ID
     */
    LeaveResponse getLeaveById(Long id);

    /**
     * Get all pending leaves (for managers/approvers)
     */
    List<LeaveResponse> getPendingLeaves();

    /**
     * Approve a leave request
     */
    LeaveResponse approveLeave(Long id, String comments);

    /**
     * Reject a leave request
     */
    LeaveResponse rejectLeave(Long id, String comments);

    /**
     * Cancel a leave request
     */
    void cancelLeave(Long id);

    /**
     * Get paginated leave records
     */
    Page<LeaveResponse> getLeaves(int page, int size, String sortBy, String sortDir);
}
