/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author JackGarland
 */
public class InvoiceDetailed {

    private int invoiceId;
    private int appointmentId;
    private String address;
    private String patientName;
    private String patientPhone;
    private String packageName;
    private String doctorName;
    private Date issueDate;
    private Date dueDate;
    private String item1Description;
    private int item1Rate;
    private String item2Description;
    private int item2Rate;
    private String item3Description;
    private int item3Rate;

    public InvoiceDetailed() {
    }

    public InvoiceDetailed(int invoiceId, int appointmentId, String address, String patientName, String patientPhone,
            String packageName, String doctorName, Date issueDate, Date dueDate,
            String item1Description, int item1Rate,
            String item2Description, int item2Rate,
            String item3Description, int item3Rate
            ) {
        this.invoiceId = invoiceId;
        this.appointmentId = appointmentId;
        this.address = address;
        this.patientName = patientName;
        this.patientPhone = patientPhone;
        this.packageName = packageName;
        this.doctorName = doctorName;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.item1Description = item1Description;
        this.item1Rate = item1Rate;
        this.item2Description = item2Description;
        this.item2Rate = item2Rate;
        this.item3Description = item3Description;
        this.item3Rate = item3Rate;
       
    }

    // Getters and Setters
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getItem1Description() {
        return item1Description;
    }

    public void setItem1Description(String item1Description) {
        this.item1Description = item1Description;
    }

    public int getItem1Rate() {
        return item1Rate;
    }

    public void setItem1Rate(int item1Rate) {
        this.item1Rate = item1Rate;
    }

    public String getItem2Description() {
        return item2Description;
    }

    public void setItem2Description(String item2Description) {
        this.item2Description = item2Description;
    }

    public int getItem2Rate() {
        return item2Rate;
    }

    public void setItem2Rate(int item2Rate) {
        this.item2Rate = item2Rate;
    }

    public String getItem3Description() {
        return item3Description;
    }

    public void setItem3Description(String item3Description) {
        this.item3Description = item3Description;
    }

    public int getItem3Rate() {
        return item3Rate;
    }

    public void setItem3Rate(int item3Rate) {
        this.item3Rate = item3Rate;
    }

    /**
     * Calculates subtotal as sum of item rates.
     */
    public int getSubtotal() {
        

        

        return item2Rate + item3Rate + item1Rate;
    }

    /**
     * Total equals subtotal.
     */
    public int getTotal() {
        return getSubtotal();
    }

 

    @Override
    public String toString() {
        return "Invoice{"
                + "invoiceId=" + invoiceId
                + ", appointmentId=" + appointmentId
                + ", address='" + address + '\''
                + ", patientName='" + patientName + '\''
                + ", patientPhone='" + patientPhone + '\''
                + ", packageName='" + packageName + '\''
                + ", doctorName='" + doctorName + '\''
                + ", issueDate=" + issueDate
                + ", dueDate=" + dueDate
                + ", items=[['" + item1Description + "'," + item1Rate + "], ['" + item2Description + "'," + item2Rate + "], ['" + item3Description + "'," + item3Rate + "]]"
                + ", subtotal=" + getSubtotal()
                + ", total=" + getTotal()
          
                + '}';
    }
}
