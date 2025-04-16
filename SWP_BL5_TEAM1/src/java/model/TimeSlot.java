/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Time;

/**
 *
 * @author LENOVO
 */
public class TimeSlot {
    private int slotId;
    private String name;
    private java.sql.Time startTime;
    private java.sql.Time endTime;
    private boolean isActive;

    public TimeSlot() {
    }

    public TimeSlot(int slotId, String name, Time startTime, Time endTime, boolean isActive) {
        this.slotId = slotId;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isActive = isActive;
    }

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "TimeSlot{" + "slotId=" + slotId + ", name=" + name + ", startTime=" + startTime + ", endTime=" + endTime + ", isActive=" + isActive + '}';
    }
}
