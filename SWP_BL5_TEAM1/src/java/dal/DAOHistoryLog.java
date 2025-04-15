package dal;

import entity.HistoryLog;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DAOHistoryLog {

    private DBContext dbcontext;

    public DAOHistoryLog() {
        this.dbcontext = new DBContext();
    }

    public DAOHistoryLog(String url, String username, String password) {
        dbcontext = new DBContext(url, username, password);
    }

    public void logAction(int userId, String actionType, Integer targetId, String details) throws SQLException {
        if (userId <= 0) {
            System.out.println("Invalid UserID: " + userId + ". Skipping log.");
            return;
        }

        String sql = "INSERT INTO HistoryLog (UserID, ActionType, TargetID, Details, LogDate) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dbcontext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, actionType);
            stmt.setObject(3, targetId);
            stmt.setString(4, details != null ? details : "No details");
            stmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            stmt.executeUpdate();
            System.out.println("Log recorded: " + actionType + " for UserID " + userId);
        }
    }

    public void logLogin(int userId) throws SQLException {
        logAction(userId, "LOGIN", null, "User logged in successfully");
    }

    public void logLogout(int userId) throws SQLException {
        logAction(userId, "LOGOUT", null, "User logged out");
    }

    public List<HistoryLog> getAllLogs() throws SQLException {
        List<HistoryLog> logs = new ArrayList<>();
        String sql = "SELECT TOP 1000 hl.*, u.FullName, u.Email, u.RoleID "
                + "FROM HistoryLog hl "
                + "LEFT JOIN Users u ON hl.UserID = u.UserID "
                + "ORDER BY hl.LogDate DESC";
        try (Connection conn = dbcontext.getConnection();
             ResultSet rs = conn.createStatement().executeQuery(sql)) {
            while (rs.next()) {
                HistoryLog log = new HistoryLog();
                log.setLogId(rs.getInt("LogID"));
                log.setUserId(rs.getInt("UserID"));
                log.setActionType(rs.getString("ActionType"));
                log.setTargetId(rs.getObject("TargetID") != null ? rs.getString("TargetID") : null);
                log.setDetails(rs.getString("Details"));
                log.setLogDate(rs.getTimestamp("LogDate"));
                log.setFullName(rs.getString("FullName"));
                log.setEmail(rs.getString("Email"));
                log.setRoleId(rs.getInt("RoleID"));
                logs.add(log);
            }
        }
        return logs;
    }

    public List<HistoryLog> getRecentLogs() throws SQLException {
        List<HistoryLog> logs = new ArrayList<>();
        String sql = "SELECT TOP 5 hl.*, u.FullName, u.Email, u.RoleID "
                + "FROM HistoryLog hl "
                + "LEFT JOIN Users u ON hl.UserID = u.UserID "
                + "ORDER BY hl.LogDate DESC";
        try (Connection conn = dbcontext.getConnection();
             ResultSet rs = conn.createStatement().executeQuery(sql)) {
            while (rs.next()) {
                HistoryLog log = new HistoryLog();
                log.setLogId(rs.getInt("LogID"));
                log.setUserId(rs.getInt("UserID"));
                log.setActionType(rs.getString("ActionType"));
                log.setTargetId(rs.getObject("TargetID") != null ? rs.getString("TargetID") : null);
                log.setDetails(rs.getString("Details"));
                log.setLogDate(rs.getTimestamp("LogDate"));
                log.setFullName(rs.getString("FullName"));
                log.setEmail(rs.getString("Email"));
                log.setRoleId(rs.getInt("RoleID"));
                logs.add(log);
            }
        }
        return logs;
    }

    public List<HistoryLog> getUserAndTutorLogs() throws SQLException {
        List<HistoryLog> logs = new ArrayList<>();
        String sql = "SELECT TOP 1000 hl.*, u.FullName, u.Email, u.RoleID "
                + "FROM HistoryLog hl "
                + "LEFT JOIN Users u ON hl.UserID = u.UserID "
                + "WHERE u.RoleID IN (2, 3) "
                + "ORDER BY hl.LogDate DESC";
        try (Connection conn = dbcontext.getConnection();
             ResultSet rs = conn.createStatement().executeQuery(sql)) {
            while (rs.next()) {
                HistoryLog log = new HistoryLog();
                log.setLogId(rs.getInt("LogID"));
                log.setUserId(rs.getInt("UserID"));
                log.setActionType(rs.getString("ActionType"));
                log.setTargetId(rs.getObject("TargetID") != null ? rs.getString("TargetID") : null);
                log.setDetails(rs.getString("Details"));
                log.setLogDate(rs.getTimestamp("LogDate"));
                log.setFullName(rs.getString("FullName"));
                log.setEmail(rs.getString("Email"));
                log.setRoleId(rs.getInt("RoleID"));
                logs.add(log);
            }
        }
        return logs;
    }
}