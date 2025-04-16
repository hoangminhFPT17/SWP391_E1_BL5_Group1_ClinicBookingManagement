/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author LENOVO
 */
public class Patient {
    private String phone; // Primary key
    private Integer patientAccountId; // Nullable
    private String fullName;
    private java.sql.Date dateOfBirth;
    private String gender;
    private String email;
    private java.sql.Timestamp createdAt;

    public Patient() {
    }

    public Patient(String phone, Integer patientAccountId, String fullName, Date dateOfBirth, String gender, String email, Timestamp createdAt) {
        this.phone = phone;
        this.patientAccountId = patientAccountId;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.email = email;
        this.createdAt = createdAt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getPatientAccountId() {
        return patientAccountId;
    }

    public void setPatientAccountId(Integer patientAccountId) {
        this.patientAccountId = patientAccountId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Patient{" + "phone=" + phone + ", patientAccountId=" + patientAccountId + ", fullName=" + fullName + ", dateOfBirth=" + dateOfBirth + ", gender=" + gender + ", email=" + email + ", createdAt=" + createdAt + '}';
    }
}
