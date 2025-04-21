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

    public List<DoctorStatusDTO> getDoctorStatusWithPriorities() {
        List<DoctorStatusDTO> list = new ArrayList<>();
        String sql
                = """
            SELECT 
                          sa.staff_id              AS doctor_id,
                          u.full_name              AS doctor_name,
                          sa.department            AS department,
                          -- free if no in-progress in current timeslot
                          CASE WHEN EXISTS (
                            SELECT 1
                              FROM PatientQueue pq0
                              JOIN TimeSlot ts0 ON pq0.slot_id = ts0.slot_id
                             WHERE pq0.doctor_id = sa.staff_id
                               AND pq0.status = 'In Progress'
                               AND CURTIME() BETWEEN ts0.start_time AND ts0.end_time
                          ) THEN FALSE ELSE TRUE END AS free,
            
                          -- current in-progress priority (if any)
                         (
                        SELECT p1.full_name
                        FROM PatientQueue pq1
                        JOIN TimeSlot ts1 ON pq1.slot_id = ts1.slot_id
                        JOIN Patient p1 ON pq1.patient_phone = p1.phone
                        WHERE pq1.doctor_id = sa.staff_id
                          AND pq1.status = 'In Progress'
                        LIMIT 1
                      ) AS currentPatient,

                                    -- next waiting priority (if any)
                                    (
                        SELECT p2.full_name
                        FROM PatientQueue pq2
                        JOIN TimeSlot ts2 ON pq2.slot_id = ts2.slot_id
                        JOIN Patient p2 ON pq2.patient_phone = p2.phone
                        WHERE pq2.doctor_id = sa.staff_id
                          AND pq2.status = 'Waiting'
                        ORDER BY pq2.priority_number ASC
                        LIMIT 1
                      ) AS nextPatient
            
                        FROM StaffAccount sa
                        JOIN User u ON sa.user_id = u.user_id
                        WHERE sa.role = 'Doctor'
            """;

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("doctor_id");
                String name = rs.getString("doctor_name");
                String dept = rs.getString("department");
                boolean free = rs.getBoolean("free");

                // currentPriority and nextPriority may be NULL in the DB
                String curr = rs.getObject("currentPatient") == null
                        ? null
                        : rs.getString("currentPatient");
                String next = rs.getObject("nextPatient") == null
                        ? null
                        : rs.getString("nextPatient");

                list.add(new DoctorStatusDTO(id, name, dept, free, curr, next));
            }
        } catch (SQLException e) {
            System.out.println("getDoctorStatusWithPriorities: " + e.getMessage());
        }
        return list;
    }

    public List<PatientQueueDTO> getActiveAppointments(int doctorId) {
        List<PatientQueueDTO> list = new ArrayList<>();
        String sql
                = """
                  SELECT pq.queue_id, pq.patient_phone, p.full_name AS patient_name,
                         docUser.full_name  AS doctor_name,
                         ts.start_time, ts.end_time, pq.queue_date,
                         pq.priority_number, pq.patient_type, pq.status, pq.arrival_time,
                         creator.full_name  AS created_by_name
                    FROM PatientQueue pq
                    JOIN Patient p        ON pq.patient_phone = p.phone
                    JOIN StaffAccount sa  ON pq.doctor_id     = sa.staff_id
                    JOIN User docUser     ON sa.user_id        = docUser.user_id
                    JOIN TimeSlot ts      ON pq.slot_id        = ts.slot_id
                    LEFT JOIN User creator ON pq.created_by    = creator.user_id
                   WHERE pq.doctor_id    = ?
                     AND (
                          (CURTIME() BETWEEN ts.start_time AND ts.end_time)
                          OR pq.status = 'Waiting'
                         )
                   ORDER BY pq.priority_number ASC""";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, doctorId);
            try (ResultSet rs = ps.executeQuery()) {
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
            }
        } catch (SQLException ex) {
            System.out.println("getActiveAppointments: " + ex.getMessage());
        }
        return list;
    }

    public void reorderQueue(List<Integer> queueIds, List<Integer> priorities) {
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
        } catch (SQLException e) {
            System.out.println("reorderQueue: " + e.getMessage());
        }
    }

    public void autoAssignPatient(int doctorId) {
        String pickSql = """
        SELECT pq.queue_id
                      FROM PatientQueue pq
                      JOIN TimeSlot ts ON pq.slot_id = ts.slot_id
                     WHERE pq.doctor_id = ?
                       AND pq.status = 'Waiting'
                     ORDER BY pq.priority_number ASC
                     LIMIT 1""";

        String updSql = "UPDATE PatientQueue SET status = 'In Progress' WHERE queue_id = ?";

        try (PreparedStatement pick = connection.prepareStatement(pickSql)) {
            pick.setInt(1, doctorId);
            try (ResultSet rs = pick.executeQuery()) {
                if (rs.next()) {
                    int qid = rs.getInt("queue_id");
                    System.out.println("Found queue_id to update: " + qid);

                    try (PreparedStatement upd = connection.prepareStatement(updSql)) {
                        upd.setInt(1, qid);
                        int rows = upd.executeUpdate();
                        System.out.println("Rows updated: " + rows);
                    }

                } else {
                    System.out.println("No waiting patient found for doctor " + doctorId);
                }
            }
        } catch (SQLException e) {
            System.out.println("autoAssignPatient: " + e.getMessage());
        }
    }

    public void manualAssignPatient(int doctorId, int priorityNumber) {
        String sql
                = """
            UPDATE PatientQueue
               SET status = 'In Progress'
             WHERE doctor_id = ?
               AND priority_number = ?
               AND status = 'Waiting'""";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, doctorId);
            ps.setInt(2, priorityNumber);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("manualAssignPatient: " + e.getMessage());
        }
    }

    public String nextPriorityName(int doctorId) {
        String sql
                = "SELECT p.full_name AS patient_name "
                + "  FROM PatientQueue pq "
                + "  JOIN Patient p     ON pq.patient_phone = p.phone "
                + "  JOIN TimeSlot ts   ON pq.slot_id        = ts.slot_id "
                + " WHERE pq.doctor_id = ? "
                + "   AND ( (CURTIME() BETWEEN ts.start_time AND ts.end_time) "
                + "         OR pq.status = 'Waiting' ) "
                + " ORDER BY pq.priority_number ASC "
                + " LIMIT 1";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, doctorId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("patient_name");
                }
            }
        } catch (SQLException e) {
            System.out.println("nextPriorityName: " + e.getMessage());
        }
        return null;
    }

}
