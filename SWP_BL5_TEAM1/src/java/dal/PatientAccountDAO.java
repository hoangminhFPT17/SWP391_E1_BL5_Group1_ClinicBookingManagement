/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.PatientAccount;

/**
 *
 * @author LENOVO
 */
public class PatientAccountDAO extends DBContext {

    public PatientAccount getPatientByUserId(int userId) {
        String sql = "SELECT * FROM PatientAccount WHERE user_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    PatientAccount patient = new PatientAccount();
                    patient.setPatientAccountId(rs.getInt("patient_account_id"));
                    patient.setUserId(rs.getInt("user_id"));
                    return patient;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Not found
    }

    public void insertPatientAccount(int userId) {
        String sql = "INSERT INTO PatientAccount (user_id) VALUES (?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
