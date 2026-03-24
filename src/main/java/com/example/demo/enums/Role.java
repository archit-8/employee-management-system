package com.example.demo.enums;

/**
 * Enum representing user roles in the system
 */
public enum Role {
    ADMIN("Administrator - Full system access"),
    MANAGER("Manager - Can manage team members"),
    EMPLOYEE("Employee - Standard employee access"),
    HR("HR - Human Resources officer"),
    PAYROLL("Payroll - Payroll management access");

    private final String description;

    Role(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
