/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.sql.Date;

/**
 *
 * @author Heizxje
 */
public class User {

    private int userID;
    private String email;
    private String password;
    private String phone;
    private String fullName;
    private boolean isVertified;
    private String otpCode; 
    private Date otpExpire;
    private Date createAt; // Changed to Date type
    
    // Thêm thuộc tính này
//    private int cvID; // Thêm thuộc tính này
//    private String education;    // Thêm
//    private String experience;   // Thêm
//    private String certificates; // Thêm
    // Constructor mặc định

    public User() {
    }

    public User(int userID, String email, String password, String phone, String fullName, boolean isVertified, String otpCode, Date otpExpire, Date createAt) {
        this.userID = userID;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.fullName = fullName;
        this.isVertified = isVertified;
        this.otpCode = otpCode;
        this.otpExpire = otpExpire;
        this.createAt = createAt;
    }

    public boolean isIsVertified() {
        return isVertified;
    }

    public void setIsVertified(boolean isVertified) {
        this.isVertified = isVertified;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public Date getOtpExpire() {
        return otpExpire;
    }

    public void setOtpExpire(Date otpExpire) {
        this.otpExpire = otpExpire;
    }

    

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

  

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "User{"
                + "userID=" + userID
          
                + ", email='" + email + '\''
                + ", fullName='" + fullName + '\''
                + ", phone='" + phone + '\''
                + ", createAt=" + createAt
               
            
               
               
         
                + ", password='" + password + '\''
                + '}';
    }

    public Object getImage() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
