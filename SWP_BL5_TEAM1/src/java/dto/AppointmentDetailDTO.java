/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author Admin
 */
public class AppointmentDetailDTO {
    private int appointmentId;
    private String patientPhone;
    private String doctorId;
    private String slot;
    private java.sql.Date appointmentDate;
    private String status;
    private java.sql.Timestamp createdAt;
    private String description;
    private String examinationPackage;

    public AppointmentDetailDTO() {
    }

    public AppointmentDetailDTO(int appointmentId, String patientPhone, String doctorId, String slot, Date appointmentDate, String status, Timestamp createdAt, String description, String examinationPackage) {
        this.appointmentId = appointmentId;
        this.patientPhone = patientPhone;
        this.doctorId = doctorId;
        this.slot = slot;
        this.appointmentDate = appointmentDate;
        this.status = status;
        this.createdAt = createdAt;
        this.description = description;
        this.examinationPackage = examinationPackage;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExaminationPackage() {
        return examinationPackage;
    }

    public void setExaminationPackage(String examinationPackage) {
        this.examinationPackage = examinationPackage;
    }
    
    
    
}


