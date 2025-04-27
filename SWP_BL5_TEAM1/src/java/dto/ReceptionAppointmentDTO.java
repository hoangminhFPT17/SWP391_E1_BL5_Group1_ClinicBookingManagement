/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.sql.Time;

/**
 *
 * @author JackGarland
 */
public class ReceptionAppointmentDTO {

    private int appointment_id;
    private String patient_phone;
    private String patient_fullName;
    private int doctor_userId;
    private String doctor_fullName;
    private java.sql.Time slot_startTime;
    private String package_name;
    private String appointment_description;
    private java.sql.Time slot_endTime;

    public ReceptionAppointmentDTO(int appointment_id, String patient_phone, String patient_fullName, int doctor_userId, String doctor_fullName, Time slot_startTime, String package_name, String appointment_description, Time slot_endTime) {
        this.appointment_id = appointment_id;
        this.patient_phone = patient_phone;
        this.patient_fullName = patient_fullName;
        this.doctor_userId = doctor_userId;
        this.doctor_fullName = doctor_fullName;
        this.slot_startTime = slot_startTime;
        this.package_name = package_name;
        this.appointment_description = appointment_description;
        this.slot_endTime = slot_endTime;
    }

    public int getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(int appointment_id) {
        this.appointment_id = appointment_id;
    }

    public ReceptionAppointmentDTO(String patient_phone, String patient_fullName, int doctor_userId, String doctor_fullName, Time slot_startTime, String package_name, String appointment_description, Time slot_endTime) {
        this.patient_phone = patient_phone;
        this.patient_fullName = patient_fullName;
        this.doctor_userId = doctor_userId;
        this.doctor_fullName = doctor_fullName;
        this.slot_startTime = slot_startTime;
        this.package_name = package_name;
        this.appointment_description = appointment_description;
        this.slot_endTime = slot_endTime;
    }

    public String getPatient_phone() {
        return patient_phone;
    }

    public void setPatient_phone(String patient_phone) {
        this.patient_phone = patient_phone;
    }

    public String getPatient_fullName() {
        return patient_fullName;
    }

    public void setPatient_fullName(String patient_fullName) {
        this.patient_fullName = patient_fullName;
    }

    public int getDoctor_userId() {
        return doctor_userId;
    }

    public void setDoctor_userId(int doctor_userId) {
        this.doctor_userId = doctor_userId;
    }

    public String getDoctor_fullName() {
        return doctor_fullName;
    }

    public void setDoctor_fullName(String doctor_fullName) {
        this.doctor_fullName = doctor_fullName;
    }

    public Time getSlot_startTime() {
        return slot_startTime;
    }

    public void setSlot_startTime(Time slot_startTime) {
        this.slot_startTime = slot_startTime;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getAppointment_description() {
        return appointment_description;
    }

    public void setAppointment_description(String appointment_description) {
        this.appointment_description = appointment_description;
    }

    public Time getSlot_endTime() {
        return slot_endTime;
    }

    public void setSlot_endTime(Time slot_endTime) {
        this.slot_endTime = slot_endTime;
    }

}
