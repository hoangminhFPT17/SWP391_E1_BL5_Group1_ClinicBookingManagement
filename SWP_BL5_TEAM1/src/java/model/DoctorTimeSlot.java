/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author LENOVO
 */
public class DoctorTimeSlot {
    private int id;
    private int staffId;
    private int slotId;
    private String dayOfWeek; // Monday to Sunday
    private int maxAppointments;

    public DoctorTimeSlot() {
    }

    public DoctorTimeSlot(int id, int staffId, int slotId, String dayOfWeek) {
        this.id = id;
        this.staffId = staffId;
        this.slotId = slotId;
        this.dayOfWeek = dayOfWeek;
    }

    public DoctorTimeSlot(int id, int staffId, int slotId, String dayOfWeek, int maxAppointments) {
        this.id = id;
        this.staffId = staffId;
        this.slotId = slotId;
        this.dayOfWeek = dayOfWeek;
        this.maxAppointments = maxAppointments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getMaxAppointments() {
        return maxAppointments;
    }

    public void setMaxAppointments(int maxAppointments) {
        this.maxAppointments = maxAppointments;
    }

    @Override
    public String toString() {
        return "DoctorTimeSlot{" + "id=" + id + ", staffId=" + staffId + ", slotId=" + slotId + ", dayOfWeek=" + dayOfWeek + ", maxAppointments=" + maxAppointments + '}';
    }
}
