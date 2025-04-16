/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author LENOVO
 */
public class StaffAccount {
    private int staffId;
    private int userId;
    private String role; // Doctor, Manager, Receptionist
    private String department;

    public StaffAccount() {
    }

    public StaffAccount(int staffId, int userId, String role, String department) {
        this.staffId = staffId;
        this.userId = userId;
        this.role = role;
        this.department = department;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "StaffAccount{" + "staffId=" + staffId + ", userId=" + userId + ", role=" + role + ", department=" + department + '}';
    }
    
}
