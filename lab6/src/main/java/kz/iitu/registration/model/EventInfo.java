package kz.iitu.registration.model;

public class EventInfo {
    private int id;
    private String title;
    private String location;

    public EventInfo(int id, String title, String location) {
        this.id = id;
        this.title = title;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }
}
