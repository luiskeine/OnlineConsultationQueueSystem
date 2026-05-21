package queue.system;
import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    private JTextField userField = new JTextField(15);
    private JPasswordField passField = new JPasswordField(15);
    private AuthManager auth = new AuthManager();

    public LoginPanel(MainFrame frame) {
        setLayout(new GridBagLayout());
        setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Title
        JLabel title = new JLabel("QUEUE SYSTEM LOGIN");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(title, gbc);

        // Inputs
        gbc.gridwidth = 1;
        gbc.gridy = 1; gbc.gridx = 0; add(new JLabel("Username:"), gbc);
        gbc.gridx = 1; add(userField, gbc);

        gbc.gridy = 2; gbc.gridx = 0; add(new JLabel("Password:"), gbc);
        gbc.gridx = 1; add(passField, gbc);

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton loginBtn = new JButton("Login");
        JButton regBtn = new JButton("Register");
        btnPanel.add(loginBtn);
        btnPanel.add(regBtn);

        gbc.gridy = 3; gbc.gridx = 0; gbc.gridwidth = 2;
        add(btnPanel, gbc);

        // LOGIN LOGIC
        loginBtn.addActionListener(e -> {
            User user = auth.login(userField.getText(), new String(passField.getPassword()));
            if (user != null) {
                if (user.getRole().equals("ADMIN")) frame.showScreen("ADMIN");
                else frame.loginAsClient(user.getUsername());
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username/Password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // REGISTER LOGIC
        regBtn.addActionListener(e -> {
            String u = JOptionPane.showInputDialog(this, "Enter new username:");
            if (u == null || u.isEmpty()) return;
            
            String p = JOptionPane.showInputDialog(this, "Enter new password:");
            if (p == null || p.isEmpty()) return;

            String[] roles = {"CLIENT", "ADMIN"};
            String r = (String) JOptionPane.showInputDialog(this, "Select Account Type:", "Role Selection",
                    JOptionPane.QUESTION_MESSAGE, null, roles, roles[0]);

            if (r != null) {
                if (auth.register(u, p, r)) {
                    JOptionPane.showMessageDialog(this, "Registration Successful! You can now log in.");
                } else {
                    JOptionPane.showMessageDialog(this, "Username already exists!");
                }
            }
        });
    }
}