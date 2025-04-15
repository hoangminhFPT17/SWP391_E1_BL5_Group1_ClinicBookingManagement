/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.Token;
import entity.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Heizxje
 */


public class DAOToken extends DBContext {

    public boolean insertTokenForget(Token tokenForget) {
        String sql = "INSERT INTO Token (token, expiryTime, isUsed, userId) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tokenForget.getToken());
            ps.setTimestamp(2, Timestamp.valueOf(tokenForget.getExpiryTime()));
            ps.setBoolean(3, tokenForget.isUsed());
            ps.setInt(4, tokenForget.getUserId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Token getTokenPassword(String token) {
        String sql = "SELECT * FROM Token WHERE token = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, token);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return new Token(
                            rs.getInt("id"),
                            rs.getInt("userId"),
                            rs.getBoolean("isUsed"),
                            rs.getString("token"),
                            rs.getTimestamp("expiryTime").toLocalDateTime()
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateStatus(Token token) {
        String sql = "UPDATE Token SET isUsed = ? WHERE token = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setBoolean(1, token.isUsed());
            st.setString(2, token.getToken());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}