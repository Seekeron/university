package kz.iitu.registration.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Service;
import kz.iitu.registration.model.EventInfo;

@Service
public class EventService {
    private final List<EventInfo> events = new ArrayList<>();
    private int nextId = 4;

    public EventService() {
        events.add(new EventInfo(1, "Web Design Workshop", "Room 101"));
        events.add(new EventInfo(2, "JavaScript Quiz", "Room 202"));
        events.add(new EventInfo(3, "IT Career Talk", "Main Hall"));
    }

    public String getWelcomeMessage() {
        return "Welcome to the Event Registration API";
    }

    public List<EventInfo> getEvents(String title) {
        List<EventInfo> result = new ArrayList<>();
        for (EventInfo event : events) {
            if (title == null || event.getTitle().toLowerCase(Locale.ROOT)
                    .contains(title.toLowerCase(Locale.ROOT))) {
                result.add(event);
            }
        }
        return result;
    }

    public EventInfo findById(int id) {
        for (EventInfo event : events) {
            if (event.getId() == id) {
                return event;
            }
        }
        return null;
    }

    public EventInfo addEvent(String title, String location) {
        EventInfo event = new EventInfo(nextId, title.trim(), location.trim());
        events.add(event);
        nextId++;
        return event;
    }

    public EventInfo updateEvent(int id, String title, String location) {
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getId() == id) {
                EventInfo updated = new EventInfo(id, title.trim(), location.trim());
                events.set(i, updated);
                return updated;
            }
        }
        return null;
    }

    public boolean deleteEvent(int id) {
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getId() == id) {
                events.remove(i);
                return true;
            }
        }
        return false;
    }
}
