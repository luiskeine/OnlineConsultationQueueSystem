package queue.system;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QueueManager {
    private static QueueManager instance;
    private QueueManager() {}

    public static synchronized QueueManager getInstance() {
        if (instance == null) instance = new QueueManager();
        return instance;
    }

    public void addAppointment(String name, String concern, String date, String time, String priority) {
        String sql = "INSERT INTO appointments(name, concern, qNum, date, time, priority, status) VALUES(?,?,?,?,?,?,'Pending')";
        try (Connection conn = DatabaseConfig.getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            String qNum = "T-" + (System.currentTimeMillis() % 10000); // Simple unique ticket num
            pstmt.setString(1, name);
            pstmt.setString(2, concern);
            pstmt.setString(3, qNum);
            pstmt.setString(4, date);
            pstmt.setString(5, time);
            pstmt.setString(6, priority);
            pstmt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public List<Appointment> getQueue() {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT * FROM appointments ORDER BY id ASC";
        try (Connection conn = DatabaseConfig.getConnection(); 
             Statement stmt = conn.createStatement(); 
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Appointment a = new Appointment(rs.getString("name"), rs.getString("concern"),
                    rs.getString("qNum"), rs.getString("date"), rs.getString("time"), rs.getString("priority"));
                a.setStatus(rs.getString("status"));
                list.add(a);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public void approve(String qNum) {
        String sql = "UPDATE appointments SET status = 'Approved' WHERE qNum = ?";
        try (Connection conn = DatabaseConfig.getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, qNum);
            pstmt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

 // Inside QueueManager.java

    public Appointment serveNext() {
        // 1. Find the best candidate (Emergency first, then Regular)
        Appointment selected = fetchOne("SELECT * FROM appointments WHERE priority='Emergency' AND status='Approved' ORDER BY id ASC LIMIT 1");
        if (selected == null) {
            selected = fetchOne("SELECT * FROM appointments WHERE status='Approved' ORDER BY id ASC LIMIT 1");
        }

        if (selected != null) {
            // 2. Instead of deleting, update status to 'Calling'
            try (Connection conn = DatabaseConfig.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement("UPDATE appointments SET status = 'Calling' WHERE qNum = ?")) {
                pstmt.setString(1, selected.getQueueNumber());
                pstmt.executeUpdate();
            } catch (Exception e) { e.printStackTrace(); }
        }
        return selected;
    }

    // NEW: This is for the Lobby Display to find who is on the screen
    public Appointment getCurrentlyServing() {
        return fetchOne("SELECT * FROM appointments WHERE status = 'Calling' ORDER BY id DESC LIMIT 1");
    }

    // NEW: Use this when the Admin is done with the patient/client
    public void completeTicket(String qNum) {
        deleteAppointment(qNum);
    }

    private Appointment fetchOne(String sql) {
        try (Connection conn = DatabaseConfig.getConnection(); 
             Statement stmt = conn.createStatement(); 
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                Appointment a = new Appointment(rs.getString("name"), rs.getString("concern"),
                    rs.getString("qNum"), rs.getString("date"), rs.getString("time"), rs.getString("priority"));
                a.setStatus(rs.getString("status"));
                return a;
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    private void deleteAppointment(String qNum) {
        String sql = "DELETE FROM appointments WHERE qNum = ?";
        try (Connection conn = DatabaseConfig.getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, qNum);
            pstmt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }
}