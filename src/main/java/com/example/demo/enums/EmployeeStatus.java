package com.example.demo.enums;

/**
 * Enum representing the various statuses an employee can have
 */
public enum EmployeeStatus {
    ACTIVE("Active - Currently employed"),
    INACTIVE("Inactive - Temporarily inactive"),
    ON_LEAVE("On leave - Currently on leave"),
    SUSPENDED("Suspended - Temporarily suspended"),
    TERMINATED("Terminated - Employment terminated"),
    RETIRED("Retired - Retired from employment");

    private final String description;

    EmployeeStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
