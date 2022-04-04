package com.example.InstagramClon.Model;

public enum Roles {
    USER("ROLE_USER"), ADMIN("ROLE_ADMIN");

    public final String role;

    Roles(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return role;
    }
}
