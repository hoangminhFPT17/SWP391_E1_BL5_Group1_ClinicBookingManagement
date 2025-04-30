/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import dto.ExaminationPackageDTO;
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
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
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

    public List<ExaminationPackageDTO> getAllPackagesByDTO() {
        List<ExaminationPackageDTO> packages = new ArrayList<>();
        String sql = ""
                + "SELECT ep.package_id, ep.name, ep.description, s.name AS specialty "
                + "FROM ExaminationPackage ep "
                + "JOIN Specialty s ON ep.specialty_id = s.specialty_id";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ExaminationPackageDTO pkg = new ExaminationPackageDTO();
                pkg.setPackageId(rs.getInt("package_id"));
                pkg.setName(rs.getString("name"));
                pkg.setDescription(rs.getString("description"));
                pkg.setSpecialty(rs.getString("specialty"));
                packages.add(pkg);
            }

        } catch (SQLException ex) {
            System.out.println("getAllPackages: " + ex.getMessage());
        }

        return packages;
    }

    public boolean insertPackage(ExaminationPackage pkg) {
        String sql = "INSERT INTO ExaminationPackage (name, description, specialty_id) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, pkg.getName());
            ps.setString(2, pkg.getDescription());
            ps.setInt(3, pkg.getSpecialtyId());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("insertPackage: " + ex.getMessage());
            return false;
        }
    }

    public boolean updatePackage(ExaminationPackage pkg) {
        String sql = "UPDATE ExaminationPackage SET name = ?, description = ?, specialty_id = ? WHERE package_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, pkg.getName());
            ps.setString(2, pkg.getDescription());
            ps.setInt(4, pkg.getSpecialtyId());
            ps.setInt(5, pkg.getPackageId());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("updatePackage: " + ex.getMessage());
            return false;
        }
    }

    public List<ExaminationPackage> searchAndFilter(String tier, int specialtyId, String search, int page, int pageSize) {
        List<ExaminationPackage> list = new ArrayList<>();
        String sql = "SELECT * FROM ExaminationPackage WHERE 1=1";

        if (tier != null && !tier.isEmpty()) {
            sql += " AND tier = ?";
        }
        if (specialtyId != -1) {
            sql += " AND specialtyId = ?";
        }
        if (search != null && !search.isEmpty()) {
            sql += " AND name LIKE ?";
        }

        sql += " ORDER BY packageId OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int index = 1;
            if (tier != null && !tier.isEmpty()) {
                ps.setString(index++, tier);
            }
            if (specialtyId != -1) {
                ps.setInt(index++, specialtyId);
            }
            if (search != null && !search.isEmpty()) {
                ps.setString(index++, "%" + search + "%");
            }
            ps.setInt(index++, (page - 1) * pageSize);
            ps.setInt(index, pageSize);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ExaminationPackage ep = new ExaminationPackage();
                ep.setPackageId(rs.getInt("packageId"));
                ep.setName(rs.getString("name"));
                ep.setDescription(rs.getString("description"));
                ep.setSpecialtyId(rs.getInt("specialtyId"));
                ep.setTier(rs.getString("tier"));
                list.add(ep);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countFiltered(String tier, int specialtyId, String search) {
        String sql = "SELECT COUNT(*) FROM ExaminationPackage WHERE 1=1";

        if (tier != null && !tier.isEmpty()) {
            sql += " AND tier = ?";
        }
        if (specialtyId != -1) {
            sql += " AND specialtyId = ?";
        }
        if (search != null && !search.isEmpty()) {
            sql += " AND name LIKE ?";
        }

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int index = 1;
            if (tier != null && !tier.isEmpty()) {
                ps.setString(index++, tier);
            }
            if (specialtyId != -1) {
                ps.setInt(index++, specialtyId);
            }
            if (search != null && !search.isEmpty()) {
                ps.setString(index++, "%" + search + "%");
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<ExaminationPackage> getFilteredPackages(String tier, Integer specialtyId, String search, int page, int pageSize) {
        List<ExaminationPackage> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM ExaminationPackage WHERE 1=1");

        if (tier != null && !tier.isEmpty()) {
            sql.append(" AND tier = ?");
        }
        if (specialtyId != null) {
            sql.append(" AND specialty_id = ?");
        }
        if (search != null && !search.trim().isEmpty()) {
            sql.append(" AND name LIKE ?");
        }

        sql.append(" LIMIT ? OFFSET ?");

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int index = 1;

            if (tier != null && !tier.isEmpty()) {
                ps.setString(index++, tier);
            }
            if (specialtyId != null) {
                ps.setInt(index++, specialtyId);
            }
            if (search != null && !search.trim().isEmpty()) {
                ps.setString(index++, "%" + search + "%"); //wildcard search
            }

            ps.setInt(index++, pageSize);
            ps.setInt(index, (page - 1) * pageSize);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ExaminationPackage ep = new ExaminationPackage();
                ep.setPackageId(rs.getInt("package_id"));
                ep.setName(rs.getString("name"));
                ep.setDescription(rs.getString("description"));
                ep.setSpecialtyId(rs.getInt("specialty_id"));
                ep.setTier(rs.getString("tier"));
                list.add(ep);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public int countFilteredPackages(String tier, Integer specialtyId, String search) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM ExaminationPackage WHERE 1=1");

        if (tier != null && !tier.isEmpty()) {
            sql.append(" AND tier = ?");
        }
        if (specialtyId != null) {
            sql.append(" AND specialty_id = ?");
        }
        if (search != null && !search.trim().isEmpty()) {
            sql.append(" AND name LIKE ?");
        }

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int index = 1;

            if (tier != null && !tier.isEmpty()) {
                ps.setString(index++, tier);
            }
            if (specialtyId != null) {
                ps.setInt(index++, specialtyId);
            }
            if (search != null && !search.trim().isEmpty()) {
                ps.setString(index++, "%" + search + "%");
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
