/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author LENOVO
 */
public class PatientAccount {
    private int patientAccountId;
    private int userId;

    public PatientAccount() {
    }

    public PatientAccount(int patientAccountId, int userId) {
        this.patientAccountId = patientAccountId;
        this.userId = userId;
    }

    public int getPatientAccountId() {
        return patientAccountId;
    }

    public void setPatientAccountId(int patientAccountId) {
        this.patientAccountId = patientAccountId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "PatientAccount{" + "patientAccountId=" + patientAccountId + ", userId=" + userId + '}';
    }
}
