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
public class PatientQueue {
    private int queueId;
    private String patientPhone;
    private int doctorId;
    private int slotId;
    private java.sql.Date queueDate;
    private int priorityNumber;
    private String patientType; // 'Appointment' or 'Walk-in'
    private String status; // 'Hasn\'t Arrived', 'Waiting', etc.
    private java.sql.Timestamp arrivalTime;
    private Integer createdBy;

    public PatientQueue() {
    }

    public PatientQueue(int queueId, String patientPhone, int doctorId, int slotId, Date queueDate, int priorityNumber, String patientType, String status, Timestamp arrivalTime, Integer createdBy) {
        this.queueId = queueId;
        this.patientPhone = patientPhone;
        this.doctorId = doctorId;
        this.slotId = slotId;
        this.queueDate = queueDate;
        this.priorityNumber = priorityNumber;
        this.patientType = patientType;
        this.status = status;
        this.arrivalTime = arrivalTime;
        this.createdBy = createdBy;
    }

    public int getQueueId() {
        return queueId;
    }

    public void setQueueId(int queueId) {
        this.queueId = queueId;
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

    public Date getQueueDate() {
        return queueDate;
    }

    public void setQueueDate(Date queueDate) {
        this.queueDate = queueDate;
    }

    public int getPriorityNumber() {
        return priorityNumber;
    }

    public void setPriorityNumber(int priorityNumber) {
        this.priorityNumber = priorityNumber;
    }

    public String getPatientType() {
        return patientType;
    }

    public void setPatientType(String patientType) {
        this.patientType = patientType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Timestamp arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "PatientQueue{" + "queueId=" + queueId + ", patientPhone=" + patientPhone + ", doctorId=" + doctorId + ", slotId=" + slotId + ", queueDate=" + queueDate + ", priorityNumber=" + priorityNumber + ", patientType=" + patientType + ", status=" + status + ", arrivalTime=" + arrivalTime + ", createdBy=" + createdBy + '}';
    }
}
