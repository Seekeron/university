package kz.iitu.registration.controller;

import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import kz.iitu.registration.model.Event;
import kz.iitu.registration.service.EventService;

@RestController
@RequestMapping("/api")
public class RegistrationController {
    private final EventService eventService;

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${app.course}")
    private String course;

    @Value("${app.semester}")
    private String semester;

    public RegistrationController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/hello")
    public Map<String, String> hello() {
        return Map.of("message", eventService.getWelcomeMessage());
    }

    @GetMapping("/events")
    public List<Event> events(@RequestParam(name = "title", required = false) String title) {
        return eventService.getEvents(title);
    }

    @GetMapping("/info")
    public Map<String, String> info() {
        return Map.of("application", applicationName, "course", course, "semester", semester);
    }
    @GetMapping("/events/{id}")
    public ResponseEntity<Event> findEvent(@PathVariable("id") Long id) {
        Event event = eventService.findById(id);
        if (event == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(event);
    }

    @PostMapping("/events")
    public ResponseEntity<?> addEvent(@RequestBody Event input) {
        if (!validInput(input)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Title and location must not be blank."));
        }
        Event event = eventService.addEvent(input.getTitle(), input.getLocation());
        return ResponseEntity.status(201).body(event);
    }

    @PutMapping("/events/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable("id") Long id, @RequestBody Event input) {
        if (!validInput(input)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Title and location must not be blank."));
        }
        Event event = eventService.updateEvent(id, input.getTitle(), input.getLocation());
        if (event == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(event);
    }

    @DeleteMapping("/events/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable("id") Long id) {
        if (!eventService.deleteEvent(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    private boolean validInput(Event input) {
        return input != null
            && input.getTitle() != null && !input.getTitle().isBlank()
            && input.getLocation() != null && !input.getLocation().isBlank();
    }
}
