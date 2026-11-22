package kz.iitu.registration.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import kz.iitu.registration.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByTitleContainingIgnoreCase(String title);
}
