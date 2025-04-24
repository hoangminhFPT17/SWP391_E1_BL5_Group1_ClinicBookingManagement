/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.math.BigDecimal;

/**
 *
 * @author Admin
 */
public class ExaminationPackageDTO {

    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
     */
    /**
     *
     * @author LENOVO
     */
    private int packageId;
    private String name;
    private String description;
    private BigDecimal price;
    private String specialty;

    public ExaminationPackageDTO() {
    }

    public ExaminationPackageDTO(int packageId, String name, String description, BigDecimal price, String specialty) {
        this.packageId = packageId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.specialty = specialty;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    @Override
    public String toString() {
        return "ExaminationPackage{" + "packageId=" + packageId + ", name=" + name + ", description=" + description + ", price=" + price + ", specialtyId=" + specialty + '}';
    }

}
