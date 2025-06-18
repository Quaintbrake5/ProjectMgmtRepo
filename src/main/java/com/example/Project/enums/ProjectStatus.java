package com.example.Project.enums;

import lombok.Getter;

@Getter
public enum ProjectStatus {
    NOT_STARTED("Not Started"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed"),
    ON_HOLD("On Hold"),
    CANCELLED("Cancelled");

    private final String status;

    ProjectStatus(String status) {
        this.status = status;
    }


    // Private constructor to prevent instantiation
//    private ProjectStatus() {
//        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
//    }
}
