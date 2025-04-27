/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.sql.Date;

/**
 *
 * @author LENOVO
 */
public class DoctorUnavailabilityDTO {
    private int unavailabilityId;
    private int index; // 1,2,3,...
    private String slotNameWithTime; // "Morning2: 09:00:00-11:00:00"
    private Date unavailableDate;
    private String reason;

    public DoctorUnavailabilityDTO() {
    }

    public DoctorUnavailabilityDTO(int unavailabilityId, int index, String slotNameWithTime, Date unavailableDate, String reason) {
        this.unavailabilityId = unavailabilityId;
        this.index = index;
        this.slotNameWithTime = slotNameWithTime;
        this.unavailableDate = unavailableDate;
        this.reason = reason;
    }

    public int getUnavailabilityId() {
        return unavailabilityId;
    }

    public void setUnavailabilityId(int unavailabilityId) {
        this.unavailabilityId = unavailabilityId;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getSlotNameWithTime() {
        return slotNameWithTime;
    }

    public void setSlotNameWithTime(String slotNameWithTime) {
        this.slotNameWithTime = slotNameWithTime;
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
    
    
}
