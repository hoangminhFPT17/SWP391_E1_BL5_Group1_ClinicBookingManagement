/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author LENOVO
 */
public class Specialty {
    private int specialtyId;
    private String name;

    public Specialty() {
    }

    public Specialty(int specialtyId, String name) {
        this.specialtyId = specialtyId;
        this.name = name;
    }

    public int getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(int specialtyId) {
        this.specialtyId = specialtyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Specialty{" + "specialtyId=" + specialtyId + ", name=" + name + '}';
    }
}

