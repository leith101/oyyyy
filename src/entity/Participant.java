package entity;

public class Participant {
    private int id;
    private int id_event;
    private String user_name;
    private String user_email;

    public Participant(int id, int eventId, String userName, String userEmail) {
        this.id = id;
        this.id_event = id_event;
        this.user_name = user_name;
        this.user_email = user_email;
    }

    public Participant(int id_event, String userName, String userEmail) {
        this.id_event = id_event;
        this.user_name = userName;
        this.user_email = userEmail;
    }

    public Participant() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEventId() {
        return id_event;
    }

    public void setEventId(int id_event) {
        this.id_event = id_event;
    }

    public String getUserName() {
        return user_name;
    }

    public void setUserName(String userName) {
        this.user_name = user_name;
    }

    public String getUserEmail() {
        return user_email;
    }

    public void setUserEmail(String userEmail) {
        this.user_email = user_email;
    }

    @Override
    public String toString() {
        return "Participant{" +
                "id=" + id +
                ", eventId=" + id_event +
                ", userName='" + user_name + '\'' +
                ", userEmail='" + user_email + '\'' +
                '}';
    }
}
