package com.example.ProjectManagementSystem.enums;

public class ProjectStatus {
    public static final String NOT_STARTED = "Not Started";
    public static final String IN_PROGRESS = "In Progress";
    public static final String COMPLETED = "Completed";
    public static final String ON_HOLD = "On Hold";
    public static final String CANCELLED = "Cancelled";

    // Private constructor to prevent instantiation
    private ProjectStatus() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
