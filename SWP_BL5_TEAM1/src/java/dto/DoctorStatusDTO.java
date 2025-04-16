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
    private String doctorName;
    private String department;
    private boolean free;

    public DoctorStatusDTO(String doctorName, String department, boolean free) {
        this.doctorName = doctorName;
        this.department = department;
        this.free = free;
    }

    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public boolean isFree() { return free; }
    public void setFree(boolean free) { this.free = free; }
}
