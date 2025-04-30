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
public class DAOUtils {
    public static void disconnectAll(DBContext... daos) {
        for (DBContext dao : daos) {
            dao.disconnect();
        }
    }
}

