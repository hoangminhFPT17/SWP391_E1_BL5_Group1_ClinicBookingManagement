/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author JackGarland
 */

import java.sql.Timestamp;


public class Payment {

    private int paymentId;
    private int userId;
    private double amount;
    private Timestamp paymentDate;
    private int appointmentId;
    private String status;

    public Payment() {
    }

    public Payment(int paymentId, int userId, double amount,
            Timestamp paymentDate, int appointmentId, String status) {
        this.paymentId = paymentId;
        this.userId = userId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.appointmentId = appointmentId;
        this.status = status;
    }

    public Payment(int userId, double amount, Timestamp paymentDate, int appointmentId, String status) {
        this.userId = userId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.appointmentId = appointmentId;
        this.status = status;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Timestamp getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Timestamp paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Payment{"
                + "paymentId=" + paymentId
                + ", userId=" + userId
                + ", amount=" + amount
                + ", paymentDate=" + paymentDate
                + ", appointmentId=" + appointmentId
                + ", status='" + status + '\''
                + '}';
    }
}
