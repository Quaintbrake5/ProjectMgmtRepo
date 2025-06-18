package com.example.Project.enums;

import lombok.Getter;

@Getter
public enum TaskStatus {
    NOT_STARTED("Not Started"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed"),
    ON_HOLD("On Hold"),
    CANCELLED("Cancelled");

    private final String status;

    TaskStatus(String status) {
        this.status = status;
    }

//    private TaskStatus() {
//        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
//    }
}
