package kz.iitu.registration.service;

import java.util.List;
import org.springframework.stereotype.Service;
import kz.iitu.registration.model.EventInfo;

@Service
public class EventService {
    private final List<EventInfo> events = List.of(
        new EventInfo(1, "Web Design Workshop", "Room 101"),
        new EventInfo(2, "JavaScript Quiz", "Room 202"),
        new EventInfo(3, "IT Career Talk", "Main Hall")
    );

    public String getWelcomeMessage() {
        return "Welcome to the Event Registration API";
    }

    public List<EventInfo> getEvents() {
        return events;
    }
}
