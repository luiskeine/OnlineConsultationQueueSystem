package queue.system;

public class VoiceService {
    public static void announceTicket(String ticketNumber) {
        new Thread(() -> {
            try {
                // This uses Windows PowerShell to speak. 
                // No JAR files required, sounds 10x better than the robot.
                String text = "Now serving ticket " + ticketNumber.replace("-", " ");
                String command = "Add-Type –AssemblyName System.Speech; " +
                                 "(New-Object System.Speech.Synthesis.SpeechSynthesizer).Speak('" + text + "')";
                
                ProcessBuilder pb = new ProcessBuilder("powershell", "-Command", command);
                pb.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}