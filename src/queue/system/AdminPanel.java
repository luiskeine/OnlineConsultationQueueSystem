package queue.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdminPanel extends JPanel {
    private DefaultTableModel model;
    private QueueManager qm = QueueManager.getInstance();

    public AdminPanel(MainFrame frame) {
        setLayout(new BorderLayout());
        
        // 1. Setup Table
        String[] cols = {"Ticket #", "Name", "Service", "Priority", "Status"};
        model = new DefaultTableModel(cols, 0);
        add(new JScrollPane(new JTable(model)), BorderLayout.CENTER);

        // 2. Setup Buttons
        JPanel btns = new JPanel();
        JButton btnApprove = new JButton("Approve");
        JButton btnServe = new JButton("Call Next");
        JButton btnLogout = new JButton("Logout");

        btns.add(btnApprove); 
        btns.add(btnServe); 
        btns.add(btnLogout);
        add(btns, BorderLayout.SOUTH);

        // 3. Action Listeners
        btnApprove.addActionListener(e -> {
            String id = JOptionPane.showInputDialog("Enter Ticket ID to Approve:");
            if (id != null && !id.isEmpty()) {
                qm.approve(id); 
                refresh();
            }
        });

     // ... existing code in AdminPanel.java ...
        btnServe.addActionListener(e -> {
            Appointment a = qm.serveNext();
            if (a != null) {
                Toolkit.getDefaultToolkit().beep(); // The sound you heard
                
                // >>> ADD THIS LINE BELOW <<<
                VoiceService.announceTicket(a.getQueueNumber()); 
                
                JOptionPane.showMessageDialog(this, "CALLING: " + a.getQueueNumber() + "\nPatient: " + a.getPatientName());

                int confirm = JOptionPane.showConfirmDialog(this, "Done with this ticket?", "Complete Session", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    qm.completeTicket(a.getQueueNumber());
                }
            } else {
                JOptionPane.showMessageDialog(this, "No approved tickets in line.");
            }
            refresh();
        });
        // ... rest of your code ...
        btnLogout.addActionListener(e -> frame.showScreen("LOGIN"));

        // 4. Auto-refresh timer (Updates the table every 3 seconds)
        new Timer(3000, e -> refresh()).start();
        
        // Initial load
        refresh();
    } // <--- Constructor ends here

    // 5. The refresh method must be OUTSIDE the constructor
    private void refresh() {
        model.setRowCount(0);
        for (Appointment a : qm.getQueue()) {
            // Only show tickets that aren't currently being called in the main table
            if (!a.getStatus().equals("Calling")) {
                model.addRow(new Object[]{
                    a.getQueueNumber(), 
                    a.getPatientName(), 
                    a.getConcern(), 
                    a.getPriority(), 
                    a.getStatus()
                });
            }
        }
    }
}