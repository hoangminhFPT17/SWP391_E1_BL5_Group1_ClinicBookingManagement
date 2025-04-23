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
    private int doctorId;
    private String fullName;
    private int maxAppointments;

    public AssignedDoctorDTO() {
    }

    public AssignedDoctorDTO(int doctorId, String fullName, int maxAppointments) {
        this.doctorId = doctorId;
        this.fullName = fullName;
        this.maxAppointments = maxAppointments;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
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
