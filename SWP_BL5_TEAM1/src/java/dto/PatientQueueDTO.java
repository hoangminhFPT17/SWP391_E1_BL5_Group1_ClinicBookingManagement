/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class PatientQueueDTO {
    private int queueId;
    private String patientPhone;
    private String patientName;
    private String doctorName;
    private Time startTime;
    private Time endTime;
    private Date queueDate;
    private int priorityNumber;
    private String patientType;
    private String status;
    private Timestamp arrivalTime;
    private String createdByName;

    public PatientQueueDTO(int queueId, String patientPhone, String patientName, String doctorName, Time startTime, Time endTime, Date queueDate, int priorityNumber, String patientType, String status, Timestamp arrivalTime, String createdByName) {
        this.queueId = queueId;
        this.patientPhone = patientPhone;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.queueDate = queueDate;
        this.priorityNumber = priorityNumber;
        this.patientType = patientType;
        this.status = status;
        this.arrivalTime = arrivalTime;
        this.createdByName = createdByName;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
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

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
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

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    
}
