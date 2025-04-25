    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

/**
 *
 * @author LENOVO
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.ExaminationPackage_OLD;
import dto.ExaminationPackageDTO;

public class ExaminationPackageDAO_OLD extends DBContext {

    // Get all packages
    public List<ExaminationPackage_OLD> getAllPackages() {
        List<ExaminationPackage_OLD> list = new ArrayList<>();
        String sql = "SELECT * FROM ExaminationPackage";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ExaminationPackage_OLD pkg = new ExaminationPackage_OLD();
                pkg.setPackageId(rs.getInt("package_id"));
                pkg.setName(rs.getString("name"));
                pkg.setDescription(rs.getString("description"));
                pkg.setPrice(rs.getBigDecimal("price"));
                pkg.setSpecialtyId(rs.getInt("specialty_id"));
                list.add(pkg);
            }
        } catch (SQLException ex) {
            System.out.println("getAllPackages: " + ex.getMessage());
        }
        return list;
    }

    // Get package by ID
    public ExaminationPackage_OLD getPackageById(int id) {
        String sql = "SELECT * FROM ExaminationPackage WHERE package_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ExaminationPackage_OLD pkg = new ExaminationPackage_OLD();
                    pkg.setPackageId(rs.getInt("package_id"));
                    pkg.setName(rs.getString("name"));
                    pkg.setDescription(rs.getString("description"));
                    pkg.setPrice(rs.getBigDecimal("price"));
                    pkg.setSpecialtyId(rs.getInt("specialty_id"));
                    return pkg;
                }
            }
        } catch (SQLException ex) {
            System.out.println("getPackageById: "  + ex.getMessage());
        }
        return null;
    }

    // Add new package
    public boolean insertPackage(ExaminationPackage_OLD pkg) {
        String sql = "INSERT INTO ExaminationPackage (name, description, price, specialty_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, pkg.getName());
            ps.setString(2, pkg.getDescription());
            ps.setBigDecimal(3, pkg.getPrice());
            ps.setInt(4, pkg.getSpecialtyId());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("insertPackage: " + ex.getMessage());
            return false;
        }
    }

    // Update existing package
    public boolean updatePackage(ExaminationPackage_OLD pkg) {
        String sql = "UPDATE ExaminationPackage SET name = ?, description = ?, price = ?, specialty_id = ? WHERE package_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, pkg.getName());
            ps.setString(2, pkg.getDescription());
            ps.setBigDecimal(3, pkg.getPrice());
            ps.setInt(4, pkg.getSpecialtyId());
            ps.setInt(5, pkg.getPackageId());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("updatePackage: " + ex.getMessage());
            return false;
        }
    }

    // Delete package
    public boolean deletePackage(int id) {
        String sql = "DELETE FROM ExaminationPackage WHERE package_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("deletePackage: " + ex.getMessage());
            return false;
        }
    }
    
    public List<ExaminationPackageDTO> getAllPackagesByDTO() {
        List<ExaminationPackageDTO> packages = new ArrayList<>();
        String sql = ""
            + "SELECT ep.package_id, ep.name, ep.description, ep.price, s.name AS specialty "
            + "FROM ExaminationPackage ep "
            + "JOIN Specialty s ON ep.specialty_id = s.specialty_id";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ExaminationPackageDTO pkg = new ExaminationPackageDTO();
                pkg.setPackageId(rs.getInt("package_id"));
                pkg.setName(rs.getString("name"));
                pkg.setDescription(rs.getString("description"));
                pkg.setPrice(rs.getBigDecimal("price"));
                pkg.setSpecialty(rs.getString("specialty"));
                packages.add(pkg);
            }

        } catch (SQLException ex) {
            System.out.println("getAllPackages: " + ex.getMessage());
        }

        return packages;
    }
    
}

