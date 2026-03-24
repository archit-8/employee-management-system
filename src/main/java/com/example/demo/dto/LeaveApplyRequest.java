package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeaveApplyRequest {

    @NotNull(message = "Employee ID is required")
    private Long employeeId;

    @NotNull(message = "Start date is required")
    @FutureOrPresent(message = "Start date must be in present or future")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @FutureOrPresent(message = "End date must be in present or future")
    private LocalDate endDate;

    @NotBlank(message = "Leave type is required")
    private String leaveType; // CASUAL, SICK, EARNED, UNPAID

    @NotBlank(message = "Reason is required")
    private String reason;

    private String attachmentUrl; // Optional attachment URL
}
