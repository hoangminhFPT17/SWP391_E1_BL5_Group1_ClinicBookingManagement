/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

/**
 *
 * @author Admin
 */
import dto.DoctorStatusDTO;
import dto.PatientQueueDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.PatientQueue;

public class PatientQueueDAO extends DBContext {

    public List<PatientQueue> getTodayPatientQueue() {
        List<PatientQueue> queueList = new ArrayList<>();
        String sql = "SELECT * FROM PatientQueue "
                + "WHERE queue_date = CURDATE() "
                + "ORDER BY "
                + "  (CASE WHEN patient_type = 'Appointment' AND arrival_time IS NOT NULL THEN 0 ELSE 1 END) ASC, "
                + "  CASE WHEN patient_type = 'Appointment' THEN priority_number ELSE NULL END DESC, "
                + "  CASE WHEN patient_type = 'Walk-in' THEN arrival_time ELSE NULL END ASC";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                PatientQueue pq = new PatientQueue();
                pq.setQueueId(rs.getInt("queue_id"));
                pq.setPatientPhone(rs.getString("patient_phone"));
                pq.setDoctorId(rs.getInt("doctor_id"));
                pq.setSlotId(rs.getInt("slot_id"));
                pq.setQueueDate(rs.getDate("queue_date"));
                pq.setPriorityNumber(rs.getInt("priority_number"));
                pq.setPatientType(rs.getString("patient_type"));
                pq.setStatus(rs.getString("status"));
                pq.setArrivalTime(rs.getTimestamp("arrival_time"));

                // Handle potential NULL for created_by
                int createdByVal = rs.getInt("created_by");
                if (rs.wasNull()) {
                    pq.setCreatedBy(null);
                } else {
                    pq.setCreatedBy(createdByVal);
                }
                queueList.add(pq);
            }
        } catch (SQLException e) {
            System.out.println("getTodayPatientQueue: " + e.getMessage());
        }
        return queueList;
    }

    public List<PatientQueueDTO> getActiveAppointments() {
        List<PatientQueueDTO> list = new ArrayList<>();
        String sql
                = "SELECT pq.queue_id, "
                + "       pq.patient_phone, "
                + "       p.full_name        AS patient_name, "
                + "       docUser.full_name  AS doctor_name, "
                + "       ts.start_time, "
                + "       ts.end_time, "
                + "       pq.queue_date, "
                + "       pq.priority_number, "
                + "       pq.patient_type, "
                + "       pq.status, "
                + "       pq.arrival_time, "
                + "       creator.full_name  AS created_by_name "
                + "  FROM PatientQueue pq "
                + "  JOIN Patient        p   ON pq.patient_phone = p.phone "
                + "  JOIN StaffAccount   sa  ON pq.doctor_id     = sa.staff_id "
                + "  JOIN User           docUser ON sa.user_id   = docUser.user_id "
                + "  JOIN TimeSlot       ts  ON pq.slot_id       = ts.slot_id "
                + "  LEFT JOIN User      creator ON pq.created_by = creator.user_id "
                + " WHERE  "
                + "    CURTIME() BETWEEN ts.start_time AND ts.end_time "
                + "         OR pq.status = 'Waiting' "
                + " ORDER BY pq.priority_number ASC";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                PatientQueueDTO dto = new PatientQueueDTO(
                        rs.getInt("queue_id"),
                        rs.getString("patient_phone"),
                        rs.getString("patient_name"),
                        rs.getString("doctor_name"),
                        rs.getTime("start_time"),
                        rs.getTime("end_time"),
                        rs.getDate("queue_date"),
                        rs.getInt("priority_number"),
                        rs.getString("patient_type"),
                        rs.getString("status"),
                        rs.getTimestamp("arrival_time"),
                        rs.getString("created_by_name")
                );
                list.add(dto);
            }
        } catch (SQLException e) {
            System.out.println("getActiveAppointments: " + e.getMessage());
        }
        return list;
    }
    
    public void reorderQueue(List<Integer> queueIds, List<Integer> priorities){
        if (queueIds == null || priorities == null || queueIds.size() != priorities.size()) {
            throw new IllegalArgumentException("Queue IDs and priorities must be non-null and of equal length");
        }

        String updateSql = "UPDATE PatientQueue SET priority_number = ? WHERE queue_id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = this.connection;
            conn.setAutoCommit(false);

            ps = conn.prepareStatement(updateSql);
            for (int i = 0; i < queueIds.size(); i++) {
                ps.setInt(1, priorities.get(i));
                ps.setInt(2, queueIds.get(i));
                ps.addBatch();
            }
            ps.executeBatch();

            conn.commit();
        }catch (SQLException e) {
            System.out.println("reorderQueue: " + e.getMessage());
        }
    }
    
    public List<DoctorStatusDTO> getDoctorStatusList(){
        List<DoctorStatusDTO> list = new ArrayList<>();
        String sql =
            """
            SELECT sa.staff_id, u.full_name AS doctor_name, sa.department, 
                   COUNT(pq.queue_id) = 0 AS free 
              FROM StaffAccount sa 
              JOIN User u ON sa.user_id = u.user_id 
              /* left join only queues that are In Progress and current time in slot */
              LEFT JOIN PatientQueue pq ON pq.doctor_id = sa.staff_id 
                 AND pq.status = 'In Progress' 
                 AND CURTIME() BETWEEN (SELECT start_time FROM TimeSlot ts WHERE ts.slot_id = pq.slot_id) 
                                 AND (SELECT end_time   FROM TimeSlot ts WHERE ts.slot_id = pq.slot_id) 
             WHERE sa.role = 'Doctor' 
             GROUP BY sa.staff_id, u.full_name, sa.department""";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString("doctor_name");
                String dept = rs.getString("department");
                boolean free = rs.getBoolean("free");
                list.add(new DoctorStatusDTO(name, dept, free));
            }
        }catch (SQLException e) {
            System.out.println("getDoctorStatusList: " + e.getMessage());
        }
        return list;
    }

}
