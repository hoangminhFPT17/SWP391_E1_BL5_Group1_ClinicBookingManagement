/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author LENOVO
 */
public class AppointmentDTO {
    private int index; // For row number
    private String patientName;
    private java.sql.Date patientDateOfBirth;
    private java.sql.Date appointmentDate;
    private String timeSlotName;
    private String doctorFullName;
    private String status;
    
    // Optionally: appointmentId, if needed for action buttons
    private int appointmentId;

    // Constructors, Getters, Setters
    public AppointmentDTO() {
    }

    public AppointmentDTO(int index, String patientName, java.sql.Date patientDateOfBirth, java.sql.Date appointmentDate,
                          String timeSlotName, String doctorFullName, String status, int appointmentId) {
        this.index = index;
        this.patientName = patientName;
        this.patientDateOfBirth = patientDateOfBirth;
        this.appointmentDate = appointmentDate;
        this.timeSlotName = timeSlotName;
        this.doctorFullName = doctorFullName;
        this.status = status;
        this.appointmentId = appointmentId;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public java.sql.Date getPatientDateOfBirth() {
        return patientDateOfBirth;
    }

    public void setPatientDateOfBirth(java.sql.Date patientDateOfBirth) {
        this.patientDateOfBirth = patientDateOfBirth;
    }

    public java.sql.Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(java.sql.Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getTimeSlotName() {
        return timeSlotName;
    }

    public void setTimeSlotName(String timeSlotName) {
        this.timeSlotName = timeSlotName;
    }

    public String getDoctorFullName() {
        return doctorFullName;
    }

    public void setDoctorFullName(String doctorFullName) {
        this.doctorFullName = doctorFullName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }
}
