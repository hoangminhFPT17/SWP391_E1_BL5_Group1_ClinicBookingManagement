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

    private int invoiceId, appointmentId, packageId; //packageId to find price etc.........
    private String phone, paymentMethod, status;
    private java.sql.Date GeneratedDate;

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
        this.phone = phone;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.GeneratedDate = new java.sql.Date(System.currentTimeMillis());
    }

    public Invoice(int invoiceId, int appointmentId, String phone, String paymentMethod, String status) {
        this.invoiceId = invoiceId;
        this.appointmentId = appointmentId;
        this.phone = phone;
        this.paymentMethod = paymentMethod;
         this.packageId = 1;
        this.status = status;
        this.GeneratedDate = new java.sql.Date(System.currentTimeMillis());
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
        return GeneratedDate;
    }

    public void setGeneratedDate(Date GeneratedDate) {
        this.GeneratedDate = GeneratedDate;
    }
}
