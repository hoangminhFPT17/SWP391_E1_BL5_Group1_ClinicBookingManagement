/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author JackGarland
 */
public class Invoice {

    private int invoiceId; 
    private int appointmentId;
    private int packageId;
    private String patientPhone; 
    private String paymentMethod;
    private String status;
    private java.sql.Date generatedDate;

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public Invoice(int invoiceId, int appointmentId, int packageId, String phone, String paymentMethod, String status) {
        this.invoiceId = invoiceId;
        this.appointmentId = appointmentId;
        this.packageId = packageId;
        this.patientPhone = phone;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.generatedDate = new java.sql.Date(System.currentTimeMillis());
    }

    public Invoice(int invoiceId, int appointmentId, String phone, String paymentMethod, String status) {
        this.invoiceId = invoiceId;
        this.appointmentId = appointmentId;
        this.patientPhone = phone;
        this.paymentMethod = paymentMethod;
        this.packageId = 1;
        this.status = status;
        this.generatedDate = new java.sql.Date(System.currentTimeMillis());
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public void setPatientPhone(String phone) {
        this.patientPhone = phone;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(Date GeneratedDate) {
        this.generatedDate = GeneratedDate;
    }
}
