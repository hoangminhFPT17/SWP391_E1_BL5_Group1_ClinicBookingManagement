/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author Admin
 */

import java.sql.Date;
import java.sql.Timestamp;

public class PatientQueueDTO {
    
    private int appointmentId;
    private String doctorName;
    private String slot;
    private Date appointmentDate;
    private String status;
    private Timestamp createdAt;
    private int queueNumber;

    public PatientQueueDTO(int appointmentId, String doctorName, String slot, Date appointmentDate, String status, Timestamp createdAt, int queueNumber) {
        this.appointmentId = appointmentId;
        this.doctorName = doctorName;
        this.slot = slot;
        this.appointmentDate = appointmentDate;
        this.status = status;
        this.createdAt = createdAt;
        this.queueNumber = queueNumber;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
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

    public int getQueueNumber() {
        return queueNumber;
    }

    public void setQueueNumber(int queueNumber) {
        this.queueNumber = queueNumber;
    }
    
    
    
}
