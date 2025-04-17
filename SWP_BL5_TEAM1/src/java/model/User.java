    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;

/**
 *
 * @author LENOVO
 */
public class User {
    private int userId;
    private String email;
    private String passwordHash;
    private String phone;
    private String fullName;
    private boolean isVerified;
    private String otpCode;
    private java.sql.Timestamp otpExpiry;
    private java.sql.Timestamp createdAt;
    private String role;

    public User() {
    }

    public User(int userId, String email, String passwordHash, String phone, String fullName, boolean isVerified, String otpCode, Timestamp otpExpiry, Timestamp createdAt) {
        this.userId = userId;
        this.email = email;
        this.passwordHash = passwordHash;
        this.phone = phone;
        this.fullName = fullName;
        this.isVerified = isVerified;
        this.otpCode = otpCode;
        this.otpExpiry = otpExpiry;
        this.createdAt = createdAt;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isIsVerified() {
        return isVerified;
    }

    public void setIsVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public Timestamp getOtpExpiry() {
        return otpExpiry;
    }

    public void setOtpExpiry(Timestamp otpExpiry) {
        this.otpExpiry = otpExpiry;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" + "userId=" + userId + ", email=" + email + ", passwordHash=" + passwordHash + ", phone=" + phone + ", fullName=" + fullName + ", isVerified=" + isVerified + ", otpCode=" + otpCode + ", otpExpiry=" + otpExpiry + ", createdAt=" + createdAt + '}';
    }
}
