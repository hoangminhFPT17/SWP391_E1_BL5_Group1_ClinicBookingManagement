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
public class Appointment {
    private int appointmentId;
    private String patientPhone;
    private int doctorId;
    private int slotId;
    private java.sql.Date appointmentDate;
    private String status;
    private java.sql.Timestamp createdAt;
    private String description;
    private Integer packageId;

    public Appointment() {
    }

    public Appointment(int appointmentId, String patientPhone, int doctorId, int slotId, Date appointmentDate, String status, Timestamp createdAt) {
        this.appointmentId = appointmentId;
        this.patientPhone = patientPhone;
        this.doctorId = doctorId;
        this.slotId = slotId;
        this.appointmentDate = appointmentDate;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Appointment(int appointmentId, String patientPhone, int doctorId, int slotId, Date appointmentDate, String status, Timestamp createdAt, String description, Integer packageId) {
        this.appointmentId = appointmentId;
        this.patientPhone = patientPhone;
        this.doctorId = doctorId;
        this.slotId = slotId;
        this.appointmentDate = appointmentDate;
        this.status = status;
        this.createdAt = createdAt;
        this.description = description;
        this.packageId = packageId;
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

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
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

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    @Override
    public String toString() {
        return "Appointment{" + "appointmentId=" + appointmentId + ", patientPhone=" + patientPhone + ", doctorId=" + doctorId + ", slotId=" + slotId + ", appointmentDate=" + appointmentDate + ", status=" + status + ", createdAt=" + createdAt + ", description=" + description + ", packageId=" + packageId + '}';
    }
}
