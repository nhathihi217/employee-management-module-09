package com.example.employeemanagement.auth;

public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException(String username) {
        super("Username is already registered: " + username);
    }
}
