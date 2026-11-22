package kz.iitu.registration.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kz.iitu.registration.model.Event;
import kz.iitu.registration.repository.EventRepository;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public String getWelcomeMessage() {
        return "Welcome to the Event Registration API";
    }

    public List<Event> getEvents(String title) {
        if (title == null) {
            return eventRepository.findAll();
        }
        return eventRepository.findByTitleContainingIgnoreCase(title);
    }

    public Event findById(Long id) {
        return eventRepository.findById(id).orElse(null);
    }

    public Event addEvent(String title, String location) {
        Event event = new Event(title.trim(), location.trim());
        return eventRepository.save(event);
    }

    @Transactional
    public Event updateEvent(Long id, String title, String location) {
        Event event = findById(id);
        if (event == null) {
            return null;
        }
        event.setTitle(title.trim());
        event.setLocation(location.trim());
        return eventRepository.save(event);
    }

    @Transactional
    public boolean deleteEvent(Long id) {
        Event event = findById(id);
        if (event == null) {
            return false;
        }
        eventRepository.delete(event);
        return true;
    }
}
