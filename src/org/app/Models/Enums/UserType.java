package org.app.Models.Enums;

public enum UserType {

    USER("USER"),
    ADMIN("ADMIN");

    private String userType;
    UserType(String userType) {
        userType = userType;
    }

    public String getUserType() {
        return this.userType;
    }

    public static UserType fromString(String userType) {
        for (UserType ut : UserType.values()) {
            if (ut.getUserType().equalsIgnoreCase(userType)) {
                return ut;
            }
        }
        throw new IllegalArgumentException("No enum constant for role: " + userType);
    }
}
