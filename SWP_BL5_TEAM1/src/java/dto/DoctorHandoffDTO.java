/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.sql.Timestamp;

/**
 *
 * @author Admin
 */
public class DoctorHandoffDTO {
    private int handoffId;
    private int fromDoctorId;
    private int toDoctorId;
    private int appointmentId;
    private String reason;
    private String status;
    private Timestamp createdAt;
    private String fromDoctorName;
    private String toDoctorName;

    public DoctorHandoffDTO(int handoffId, int fromDoctorId, int toDoctorId, int appointmentId, String reason, String status, Timestamp createdAt, String fromDoctorName, String toDoctorName) {
        this.handoffId = handoffId;
        this.fromDoctorId = fromDoctorId;
        this.toDoctorId = toDoctorId;
        this.appointmentId = appointmentId;
        this.reason = reason;
        this.status = status;
        this.createdAt = createdAt;
        this.fromDoctorName = fromDoctorName;
        this.toDoctorName = toDoctorName;
    }

    public DoctorHandoffDTO() {
    }

    public int getHandoffId() {
        return handoffId;
    }

    public void setHandoffId(int handoffId) {
        this.handoffId = handoffId;
    }

    public int getFromDoctorId() {
        return fromDoctorId;
    }

    public void setFromDoctorId(int fromDoctorId) {
        this.fromDoctorId = fromDoctorId;
    }

    public int getToDoctorId() {
        return toDoctorId;
    }

    public void setToDoctorId(int toDoctorId) {
        this.toDoctorId = toDoctorId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getFromDoctorName() {
        return fromDoctorName;
    }

    public void setFromDoctorName(String fromDoctorName) {
        this.fromDoctorName = fromDoctorName;
    }

    public String getToDoctorName() {
        return toDoctorName;
    }

    public void setToDoctorName(String toDoctorName) {
        this.toDoctorName = toDoctorName;
    }
    
    
    
}
