package queue.system;
import javax.swing.*;
import java.awt.*;

public class ClientPanel extends JPanel {
    private String username;
    private QueueManager qm = QueueManager.getInstance();
    private JLabel lblStatus = new JLabel("Status: Not in Queue");
    private JLabel lblPosition = new JLabel("Position: --");
    private DefaultListModel<String> listModel = new DefaultListModel<>();

    public ClientPanel(MainFrame frame, String username) {
        this.username = username;
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.add(new JLabel("Welcome, " + username), BorderLayout.WEST);
        JButton btnLogout = new JButton("Logout");
        header.add(btnLogout, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // Center
        JPanel main = new JPanel(new GridLayout(1, 2, 20, 20));
        JPanel left = new JPanel(new GridLayout(4, 1));
        left.setBorder(BorderFactory.createTitledBorder("My Info"));
        left.add(lblStatus); left.add(lblPosition);
        JButton btnBook = new JButton("Get Ticket");
        left.add(btnBook);
        main.add(left);

        JList<String> list = new JList<>(listModel);
        main.add(new JScrollPane(list));
        add(main, BorderLayout.CENTER);

        btnBook.addActionListener(e -> {
            String s = JOptionPane.showInputDialog("Service needed?");
            qm.addAppointment(username, s, "Now", "Now", "Regular");
            refresh();
        });
        btnLogout.addActionListener(e -> frame.showScreen("LOGIN"));

        new Timer(2000, e -> refresh()).start();
    }

    private void refresh() {
        listModel.clear();
        int pos = 1, myPos = -1;
        Appointment myT = null;
        for (Appointment a : qm.getQueue()) {
            listModel.addElement(a.getQueueNumber() + " - " + a.getStatus());
            if (a.getPatientName().equals(username)) { myPos = pos; myT = a; }
            pos++;
        }
        if (myT != null) {
            lblStatus.setText("Status: " + myT.getStatus());
            lblPosition.setText("Your Position: " + myPos);
        }
    }
}