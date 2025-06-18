package com.example.Project.enums;

import lombok.Getter;

@Getter
public enum UserStatus {
    ACTIVE ("Active"),
    INACTIVE ("Inactive"),
    SUSPENDED ("Suspended"),
    DELETED ("Deleted"),
    LOCKED ("Locked");

    private final String status;


//    public static UserStatus fromString(String status) {
//        for (UserStatus userStatus : UserStatus.values()) {
//            if (userStatus.name().equalsIgnoreCase(status)) {
//                return userStatus;
//            }
//        }
//        throw new IllegalArgumentException("No enum constant " + UserStatus.class.getCanonicalName() + "." + status);
//    }

    UserStatus(String status) {
        this.status = status;
    }
}
