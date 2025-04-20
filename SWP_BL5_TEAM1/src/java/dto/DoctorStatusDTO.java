/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author Admin
 */
public class DoctorStatusDTO {
    private int doctorId;
    private String doctorName;
    private String department;
    private boolean free;
    private String currentPatient;  // null if none
    private String  nextPatient;     // null if none

    public DoctorStatusDTO(int doctorId, String doctorName, String department, boolean free, String currentPatient, String nextPatient) {
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.department = department;
        this.free = free;
        this.currentPatient = currentPatient;
        this.nextPatient = nextPatient;
    }

    

    

    // Getters

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public String getCurrentPatient() {
        return currentPatient;
    }

    public void setCurrentPatient(String currentPatient) {
        this.currentPatient = currentPatient;
    }

    public String getNextPatient() {
        return nextPatient;
    }

    public void setNextPatient(String nextPatient) {
        this.nextPatient = nextPatient;
    }

    
    
    
}