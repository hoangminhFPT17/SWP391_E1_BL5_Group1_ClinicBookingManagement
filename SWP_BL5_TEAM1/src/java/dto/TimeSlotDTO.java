/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;
import java.sql.Time;
import java.util.List;
/**
 *
 * @author LENOVO
 */
public class TimeSlotDTO {
    private int index;
    private int slotId;
    private String timeSlotName;
    private Time startTime;
    private Time endTime;
    private List<AssignedDoctorDTO> assignedDoctors;
    private boolean isActive;

    public TimeSlotDTO() {
    }

    public TimeSlotDTO(int index, int slotId, String timeSlotName, Time startTime, Time endTime, List<AssignedDoctorDTO> assignedDoctors, boolean isActive) {
        this.index = index;
        this.slotId = slotId;
        this.timeSlotName = timeSlotName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.assignedDoctors = assignedDoctors;
        this.isActive = isActive;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public String getTimeSlotName() {
        return timeSlotName;
    }

    public void setTimeSlotName(String timeSlotName) {
        this.timeSlotName = timeSlotName;
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

    public List<AssignedDoctorDTO> getAssignedDoctors() {
        return assignedDoctors;
    }

    public void setAssignedDoctors(List<AssignedDoctorDTO> assignedDoctors) {
        this.assignedDoctors = assignedDoctors;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
