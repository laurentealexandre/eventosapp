package com.eventos.eventosapp.repositories;

import com.eventos.eventosapp.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByDateAfterOrderByDateAsc(LocalDateTime date);
    List<Event> findByTitleContainingIgnoreCase(String title);
}