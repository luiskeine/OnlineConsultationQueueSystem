package queue.system;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainContainer = new JPanel(cardLayout);
    

    public MainFrame() {
        setTitle("Universal Queue Management System");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainContainer.add(new LoginPanel(this), "LOGIN");
        mainContainer.add(new AdminPanel(this), "ADMIN");
        mainContainer.add(new LobbyPanel(), "LOBBY");
        add(mainContainer);
        cardLayout.show(mainContainer, "LOGIN");
        
     // Temporary test code for MainFrame constructor
        JFrame lobbyWindow = new JFrame("Lobby TV Display");
        lobbyWindow.setSize(600, 400);
        lobbyWindow.add(new LobbyPanel());
        lobbyWindow.setVisible(true);
    }

    public void showScreen(String name) { cardLayout.show(mainContainer, name); }

    public void loginAsClient(String username) {
        mainContainer.add(new ClientPanel(this, username), "CLIENT");
        showScreen("CLIENT");
    }
    public void openLobbyWindow() {
        JFrame lobbyWindow = new JFrame("Public Display");
        lobbyWindow.setSize(800, 600);
        lobbyWindow.add(new LobbyPanel());
        lobbyWindow.setVisible(true);
    }
}