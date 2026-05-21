package queue.system;

public class Appointment {
    private String patientName, concern, queueNumber, date, time, priority, status;

    public Appointment(String patientName, String concern, String queueNumber, String date, String time, String priority) {
        this.patientName = patientName;
        this.concern = concern;
        this.queueNumber = queueNumber;
        this.date = date;
        this.time = time;
        this.priority = priority;
        this.status = "Pending";
    }

    public String getQueueNumber() { return queueNumber; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getPriority() { return priority; }
    public String getPatientName() { return patientName; }
    public String getConcern() { return concern; }
}