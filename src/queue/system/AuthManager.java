package queue.system;

import java.sql.*;
import org.mindrot.jbcrypt.BCrypt; // This is the library you just added!

public class AuthManager {

    // LOGIN: We no longer compare passwords in the SQL query
    public User login(String username, String password) {
        // Step 1: Just find the user by their name
        String sql = "SELECT * FROM users WHERE username = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password");
                
                // Step 2: Use BCrypt to see if the typed password matches the scrambled one
                if (BCrypt.checkpw(password, storedHash)) {
                    return new User(
                        rs.getString("username"), 
                        storedHash, 
                        rs.getString("role")
                    );
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    // REGISTER: Scramble the password BEFORE it touches the database
    public boolean register(String username, String password, String role) {
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // This is the "Magic" line: It creates a unique, un-hackable hash
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            
            pstmt.setString(1, username);
            pstmt.setString(2, hashedPassword); // We save the scramble, not the password!
            pstmt.setString(3, role);
            
            pstmt.executeUpdate();
            return true;
        } catch (Exception e) { 
            return false; // Usually means duplicate username
        }
    }
}