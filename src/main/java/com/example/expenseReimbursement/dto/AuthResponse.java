package com.example.expenseReimbursement.dto;

public class AuthResponse {

    private String token;
    private Number statusCode;

    public AuthResponse() {
    }

    public AuthResponse(String token, Number statusCode) {
        this.token = token;
        this.statusCode = statusCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Number getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Number statusCode) {
        this.statusCode = statusCode;
    }
}
