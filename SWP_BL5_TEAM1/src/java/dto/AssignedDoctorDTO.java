/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author LENOVO
 */
public class AssignedDoctorDTO {
    private String fullName;
    private int maxAppointments;

    public AssignedDoctorDTO() {
    }

    public AssignedDoctorDTO(String fullName, int maxAppointments) {
        this.fullName = fullName;
        this.maxAppointments = maxAppointments;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getMaxAppointments() {
        return maxAppointments;
    }

    public void setMaxAppointments(int maxAppointments) {
        this.maxAppointments = maxAppointments;
    }
}
