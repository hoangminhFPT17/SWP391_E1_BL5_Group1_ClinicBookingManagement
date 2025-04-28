/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author LENOVO
 */
public class ExaminationPackage {
    private int packageId;
    private String name;
    private String description;
    private int specialtyId;

    public ExaminationPackage() {
    }

    public ExaminationPackage(int packageId, String name, String description, int specialtyId) {
        this.packageId = packageId;
        this.name = name;
        this.description = description;
        this.specialtyId = specialtyId;
    }

    public ExaminationPackage(String name, String description, int specialtyId) {
        this.name = name;
        this.description = description;
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

    public int getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(int specialtyId) {
        this.specialtyId = specialtyId;
    }

    @Override
    public String toString() {
        return "ExaminationPackage{" + "packageId=" + packageId + ", name=" + name + ", description=" + description + ", specialtyId=" + specialtyId + '}';
    }
}
