/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ExaminationPackage;
/**
 *
 * @author LENOVO
 */
public class ExaminationPackageDAO extends DBContext {

    public void addPackage(ExaminationPackage pkg) {
        String sql = "INSERT INTO ExaminationPackage (name, description, specialty_id) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, pkg.getName());
            ps.setString(2, pkg.getDescription());
            ps.setInt(3, pkg.getSpecialtyId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ExaminationPackageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ExaminationPackage getPackageById(int packageId) {
        String sql = "SELECT * FROM ExaminationPackage WHERE package_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, packageId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ExaminationPackage pkg = new ExaminationPackage();
                pkg.setPackageId(rs.getInt("package_id"));
                pkg.setName(rs.getString("name"));
                pkg.setDescription(rs.getString("description"));
                pkg.setSpecialtyId(rs.getInt("specialty_id"));
                return pkg;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ExaminationPackageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<ExaminationPackage> getAllPackages() {
        List<ExaminationPackage> list = new ArrayList<>();
        String sql = "SELECT * FROM ExaminationPackage";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ExaminationPackage pkg = new ExaminationPackage();
                pkg.setPackageId(rs.getInt("package_id"));
                pkg.setName(rs.getString("name"));
                pkg.setDescription(rs.getString("description"));
                pkg.setSpecialtyId(rs.getInt("specialty_id"));
                list.add(pkg);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ExaminationPackageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void deletePackage(int packageId) {
        String sql = "DELETE FROM ExaminationPackage WHERE package_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, packageId);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ExaminationPackageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}