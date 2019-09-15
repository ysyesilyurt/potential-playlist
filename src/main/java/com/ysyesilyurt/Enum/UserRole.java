package com.ysyesilyurt.Enum;

public enum UserRole {
    USER("USER"),
    ADMIN("ADMIN");

    private String displayName;

    UserRole(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return this.displayName;
    }
}
