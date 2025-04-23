/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author LENOVO
 */
import java.math.BigDecimal;

public class ExaminationPackage {
    private int packageId;
    private String name;
    private String description;
    private BigDecimal price;
    private int specialtyId;

    public ExaminationPackage() {
    }

    public ExaminationPackage(int packageId, String name, String description, BigDecimal price, int specialtyId) {
        this.packageId = packageId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.specialtyId = specialtyId;
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

    public int getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(int specialtyId) {
        this.specialtyId = specialtyId;
    }

    @Override
    public String toString() {
        return "ExaminationPackage{" + "packageId=" + packageId + ", name=" + name + ", description=" + description + ", price=" + price + ", specialtyId=" + specialtyId + '}';
    }
}
