package com.example.expenseReimbursement.dto;

public class SignupRequest {
    private String name;
    private String email;
    private String password;
    private String role;
    private String phoneNumber;
    private String accNo;
    private String ifscCode;
    private String dob;

    // Getters
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getAccNo() { return accNo; }
    public String getIfscCode() { return ifscCode; }
    public String getDob() { return dob; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role) { this.role = role; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setAccNo(String accNo) { this.accNo = accNo; }
    public void setIfscCode(String ifscCode) { this.ifscCode = ifscCode; }
    public void setDob(String dob) { this.dob = dob; }
}
