package queue.system;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import com.formdev.flatlaf.FlatDarkLaf; // This is the modern skin

public class Main {
    public static void main(String[] args) {
        try {
            // This transforms the app from 90s Grey to 2026 Sleek Dark
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize modern UI");
        }

        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}