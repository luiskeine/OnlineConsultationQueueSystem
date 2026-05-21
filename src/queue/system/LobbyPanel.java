package queue.system;
import javax.swing.*;
import java.awt.*;

public class LobbyPanel extends JPanel {
    private JLabel lblNowServing = new JLabel("---", SwingConstants.CENTER);
    private JLabel lblCounter = new JLabel("Waiting for next...", SwingConstants.CENTER);
    private QueueManager qm = QueueManager.getInstance();

    public LobbyPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(20, 20, 20)); // Dark Mode for TV

        JLabel title = new JLabel("PUBLIC QUEUE DISPLAY", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 40));
        title.setForeground(Color.WHITE);
        add(title, BorderLayout.NORTH);

        lblNowServing.setFont(new Font("Arial", Font.BOLD, 150));
        lblNowServing.setForeground(new Color(0, 255, 127)); // Neon Green
        add(lblNowServing, BorderLayout.CENTER);

        lblCounter.setFont(new Font("Arial", Font.PLAIN, 40));
        lblCounter.setForeground(Color.LIGHT_GRAY);
        add(lblCounter, BorderLayout.SOUTH);

        // Refresh logic: Look for a ticket that is "Currently being called"
        // Note: For this to work perfectly, we'd add a "CurrentCalling" variable in QueueManager
        new Timer(2000, e -> refresh()).start();
    }

 // Inside LobbyPanel.java - Update the refresh method:

    private void refresh() {
        Appointment current = qm.getCurrentlyServing();
        if (current != null) {
            lblNowServing.setText(current.getQueueNumber());
            lblCounter.setText("Now Serving: " + current.getPatientName());
        } else {
            lblNowServing.setText("---");
            lblCounter.setText("Waiting for next...");
        }
    }
}