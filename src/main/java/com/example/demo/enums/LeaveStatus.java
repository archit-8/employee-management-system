package com.example.demo.enums;

/**
 * Enum representing the various statuses of a leave request
 */
public enum LeaveStatus {
    PENDING("Pending - Awaiting approval"),
    APPROVED("Approved - Leave has been approved"),
    REJECTED("Rejected - Leave request was rejected"),
    CANCELLED("Cancelled - Leave request was cancelled"),
    COMPLETED("Completed - Leave period has ended");

    private final String description;

    LeaveStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
