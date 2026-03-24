package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeaveResponse {

    private Long id;
    private Long employeeId;
    private String employeeName;
    private LocalDate startDate;
    private LocalDate endDate;
    private long durationInDays;
    private String leaveType;
    private String status; // PENDING, APPROVED, REJECTED, CANCELLED
    private String reason;
    private String approverComments;
    private Long approvedBy;
    private String approverName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
