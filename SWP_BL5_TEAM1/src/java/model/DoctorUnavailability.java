/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author LENOVO
 */
public class DoctorUnavailability {
    private int unavailabilityId;
    private int staffId;
    private int slotId;
    private java.sql.Date unavailableDate;
    private String reason;

    public DoctorUnavailability() {
    }

    public DoctorUnavailability(int unavailabilityId, int staffId, int slotId, Date unavailableDate, String reason) {
        this.unavailabilityId = unavailabilityId;
        this.staffId = staffId;
        this.slotId = slotId;
        this.unavailableDate = unavailableDate;
        this.reason = reason;
    }

    public int getUnavailabilityId() {
        return unavailabilityId;
    }

    public void setUnavailabilityId(int unavailabilityId) {
        this.unavailabilityId = unavailabilityId;
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

    public Date getUnavailableDate() {
        return unavailableDate;
    }

    public void setUnavailableDate(Date unavailableDate) {
        this.unavailableDate = unavailableDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "DoctorUnavailability{" + "unavailabilityId=" + unavailabilityId + ", staffId=" + staffId + ", slotId=" + slotId + ", unavailableDate=" + unavailableDate + ", reason=" + reason + '}';
    }
}
