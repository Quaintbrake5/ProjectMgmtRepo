package com.example.Project.utils;

import com.example.Project.enums.PermissionName;
import com.example.Project.models.Role;
import com.example.Project.models.User;

public class AuthUtils {

    private AuthUtils() { /* static only */ }

    /**
     * Returns true if the user has the given permission (directly via any of their roles).
     */
    public static boolean hasPermission(User user, PermissionName perm) {
        if (user.getRoles() == null) return false;
        return user.getRoles().stream()
                .flatMap(r -> r.getPermissions().stream())
                .anyMatch(p -> p.equals(perm));
    }

    /**
     * Returns true if the user has the "ROLE_ADMIN" role.
     */
    public static boolean isAdmin(User user) {
        if (user.getRoles() == null) return false;
        return user.getRoles().stream()
                .map(Role::getName)
                .anyMatch(name -> name.equals("ROLE_ADMIN"));
    }
}
