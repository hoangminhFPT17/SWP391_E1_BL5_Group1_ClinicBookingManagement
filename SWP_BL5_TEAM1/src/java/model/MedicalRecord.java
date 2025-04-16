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
public class MedicalRecord {
    private int recordId;
    private String patientPhone;
    private String diagnosis;
    private String prescription;
    private String notes;
    private java.sql.Timestamp createdAt;

    public MedicalRecord() {
    }

    public MedicalRecord(int recordId, String patientPhone, String diagnosis, String prescription, String notes, Timestamp createdAt) {
        this.recordId = recordId;
        this.patientPhone = patientPhone;
        this.diagnosis = diagnosis;
        this.prescription = prescription;
        this.notes = notes;
        this.createdAt = createdAt;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "MedicalRecord{" + "recordId=" + recordId + ", patientPhone=" + patientPhone + ", diagnosis=" + diagnosis + ", prescription=" + prescription + ", notes=" + notes + ", createdAt=" + createdAt + '}';
    }
}
