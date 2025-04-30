/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import dal.DBContext;

/**
 *
 * @author LENOVO
 */
public class Testing_connection {
   public static void main(String[] args) {
        //System.out.println("===== Test without disconnect =====");
        //testWithoutDisconnect();

        System.out.println("\n===== Test with disconnect =====");
        testWithDisconnect();
    }

    // Test 1: Open 1000 connections without closing
    public static void testWithoutDisconnect() {
        for (int i = 1; i <= 1000; i++) {
            try {
                DBContext db = new DBContext();
                if (db.isConnected()) {
                    System.out.println("[" + i + "] Connection successful");
                } else {
                    System.out.println("[" + i + "] Connection failed");
                    break;
                }
            } catch (Exception e) {
                System.out.println("[" + i + "] Exception: " + e.getMessage());
                break;
            }
        }
    }

    // Test 2: Open and close 1000 connections
    public static void testWithDisconnect() {
        for (int i = 1; i <= 1000; i++) {
            try {
                DBContext db = new DBContext();
                if (db.isConnected()) {
                    System.out.println("[" + i + "] Connection successful");
                    db.disconnect(); // Close it after using
                } else {
                    System.out.println("[" + i + "] Connection failed");
                    break;
                }
            } catch (Exception e) {
                System.out.println("[" + i + "] Exception: " + e.getMessage());
                break;
            }
        }
    }
}
