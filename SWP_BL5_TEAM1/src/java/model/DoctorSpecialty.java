/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author LENOVO
 */
public class DoctorSpecialty {
    private int staffId;
    private int specialtyId;

    public DoctorSpecialty() {
    }

    public DoctorSpecialty(int staffId, int specialtyId) {
        this.staffId = staffId;
        this.specialtyId = specialtyId;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public int getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(int specialtyId) {
        this.specialtyId = specialtyId;
    }

    @Override
    public String toString() {
        return "DoctorSpecialty{" + "staffId=" + staffId + ", specialtyId=" + specialtyId + '}';
    }
}

