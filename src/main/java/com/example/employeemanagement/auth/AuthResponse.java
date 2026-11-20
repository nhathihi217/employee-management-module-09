package com.example.employeemanagement.auth;

public record AuthResponse(String accessToken, String tokenType, long expiresInSeconds, String username, String role) {}
