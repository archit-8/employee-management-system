package com.example.demo.service;

import com.example.demo.dto.LeaveApplyRequest;
import com.example.demo.dto.LeaveResponse;
import com.example.demo.entity.Employee;
import com.example.demo.entity.LeaveRequest;
import com.example.demo.enums.LeaveStatus;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.LeaveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LeaveServiceImpl implements LeaveService {

    private final LeaveRepository leaveRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public LeaveResponse applyLeave(LeaveApplyRequest request) {
        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        // Validate dates
        if (request.getStartDate().isAfter(request.getEndDate())) {
            throw new BadRequestException("Start date cannot be after end date");
        }

        if (request.getStartDate().isBefore(LocalDate.now())) {
            throw new BadRequestException("Cannot apply leave for past dates");
        }

        // Check for overlapping leave requests
        boolean hasOverlapping = leaveRepository.existsOverlappingLeave(
                request.getEmployeeId(),
                request.getStartDate(),
                request.getEndDate()
        );

        if (hasOverlapping) {
            throw new BadRequestException("Leave request overlaps with existing approved leave");
        }

        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setEmployee(employee);
        leaveRequest.setStartDate(request.getStartDate());
        leaveRequest.setEndDate(request.getEndDate());
        leaveRequest.setLeaveType(request.getLeaveType());
        leaveRequest.setStatus(LeaveStatus.PENDING.name());
        leaveRequest.setReason(request.getReason());

        LeaveRequest saved = leaveRepository.save(leaveRequest);
        return mapToResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeaveResponse> getEmployeeLeaves(Long employeeId) {
        employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        return leaveRepository.findByEmployeeId(employeeId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public LeaveResponse getLeaveById(Long id) {
        LeaveRequest leaveRequest = leaveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found"));
        return mapToResponse(leaveRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeaveResponse> getPendingLeaves() {
        return leaveRepository.findByStatus(LeaveStatus.PENDING.name())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public LeaveResponse approveLeave(Long id, String comments) {
        LeaveRequest leaveRequest = leaveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found"));

        if (!leaveRequest.getStatus().equals(LeaveStatus.PENDING.name())) {
            throw new BadRequestException("Only pending leave requests can be approved");
        }

        leaveRequest.setStatus(LeaveStatus.APPROVED.name());
        leaveRequest.setApproverComments(comments);
        // In production, set approvedBy to the current logged-in user
        // leaveRequest.setApprovedBy(currentUser);

        LeaveRequest updated = leaveRepository.save(leaveRequest);
        return mapToResponse(updated);
    }

    @Override
    public LeaveResponse rejectLeave(Long id, String comments) {
        LeaveRequest leaveRequest = leaveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found"));

        if (!leaveRequest.getStatus().equals(LeaveStatus.PENDING.name())) {
            throw new BadRequestException("Only pending leave requests can be rejected");
        }

        leaveRequest.setStatus(LeaveStatus.REJECTED.name());
        leaveRequest.setApproverComments(comments);

        LeaveRequest updated = leaveRepository.save(leaveRequest);
        return mapToResponse(updated);
    }

    @Override
    public void cancelLeave(Long id) {
        LeaveRequest leaveRequest = leaveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found"));

        if (leaveRequest.getStartDate().isBefore(LocalDate.now())) {
            throw new BadRequestException("Cannot cancel leave that has already started");
        }

        leaveRequest.setStatus(LeaveStatus.CANCELLED.name());
        leaveRepository.save(leaveRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LeaveResponse> getLeaves(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return leaveRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    /**
     * Map LeaveRequest entity to response DTO
     */
    private LeaveResponse mapToResponse(LeaveRequest leaveRequest) {
        LeaveResponse response = new LeaveResponse();
        response.setId(leaveRequest.getId());
        response.setEmployeeId(leaveRequest.getEmployee().getId());
        response.setEmployeeName(leaveRequest.getEmployee().getFirstName() + " " + leaveRequest.getEmployee().getLastName());
        response.setStartDate(leaveRequest.getStartDate());
        response.setEndDate(leaveRequest.getEndDate());
        response.setDurationInDays(leaveRequest.getDurationInDays());
        response.setLeaveType(leaveRequest.getLeaveType());
        response.setStatus(leaveRequest.getStatus());
        response.setReason(leaveRequest.getReason());
        response.setApproverComments(leaveRequest.getApproverComments());

        if (leaveRequest.getApprovedBy() != null) {
            response.setApprovedBy(leaveRequest.getApprovedBy().getId());
            response.setApproverName(leaveRequest.getApprovedBy().getFirstName() + " " + leaveRequest.getApprovedBy().getLastName());
        }

        response.setCreatedAt(leaveRequest.getCreatedAt());
        response.setUpdatedAt(leaveRequest.getUpdatedAt());
        return response;
    }
}
