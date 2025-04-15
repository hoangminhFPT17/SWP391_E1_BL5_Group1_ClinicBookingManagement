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
    private int roleID;
    private String email;
    private String fullName;
    private String phone;
    private Date createAt; // Changed to Date type
    private int isActive;
    private Date dob;
    private String address;
    private String avatar;
    private String userName;
    private String password;
    private String subjectName; // Thêm thuộc tính này
    private int cvID; // Thêm thuộc tính này
    private String education;    // Thêm
    private String experience;   // Thêm
    private String certificates; // Thêm
    // Constructor mặc định

    public User() {
    }

    public User(int userID, int roleID, String email, String fullName, String phone, Date createAt, int isActive, Date dob, String address, String avatar, String userName, String password) {
        this.userID = userID;
        this.roleID = roleID;
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
        this.createAt = createAt;
        this.isActive = isActive;
        this.dob = dob;
        this.address = address;
        this.avatar = avatar;
        this.userName = userName;
        this.password = password;
    }

    public User(int userID, String email, String fullName, String phone, String password, Date dob, String address, String avatar) {
        this.userID = userID;
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
        this.password = password;
        this.dob = dob;
        this.address = address;
        this.avatar = avatar;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
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

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getCvID() {
        return cvID;
    }

    public void setCvID(int cvID) {
        this.cvID = cvID;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getCertificates() {
        return certificates;
    }

    public void setCertificates(String certificates) {
        this.certificates = certificates;
    }

    @Override
    public String toString() {
        return "User{"
                + "userID=" + userID
                + ", roleID=" + roleID
                + ", email='" + email + '\''
                + ", fullName='" + fullName + '\''
                + ", phone='" + phone + '\''
                + ", createAt=" + createAt
                + ", isActive=" + isActive
                + ", dob=" + dob
                + ", address='" + address + '\''
                + ", avatar='" + avatar + '\''
                + ", userName='" + userName + '\''
                + ", password='" + password + '\''
                + '}';
    }

    public Object getImage() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
