package com.eventos.eventosapp.services;

import com.eventos.eventosapp.models.Event;
import com.eventos.eventosapp.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList; 
import java.time.LocalDateTime;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<Event> findAllEvents() {
        try {
            return eventRepository.findAll();
        } catch (Exception e) {
            System.err.println("Erro ao buscar todos os eventos: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar eventos", e);
        }
    }

    public Event findEventById(Long id) {
        try {
            System.out.println("Buscando evento com ID: " + id); 
            Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado: ID " + id));
            System.out.println("Evento encontrado: " + event); 
            return event;
        } catch (Exception e) {
            System.err.println("Erro ao buscar evento por ID " + id + ": " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar evento com ID: " + id, e);
        }
    }

    public Event createEvent(Event event) {
        try {
            if (event.getSubscriptions() == null) {
                event.setSubscriptions(new ArrayList<>());
            }
            return eventRepository.save(event);
        } catch (Exception e) {
            System.err.println("Erro ao criar evento: " + e.getMessage());
            throw new RuntimeException("Erro ao criar evento", e);
        }
    }

    public Event updateEvent(Long id, Event eventDetails) {
        try {
            Event event = findEventById(id);
            
            event.setTitle(eventDetails.getTitle());
            event.setDate(eventDetails.getDate());
            event.setLocation(eventDetails.getLocation());
            event.setDescription(eventDetails.getDescription());
            event.setCapacity(eventDetails.getCapacity());
            
            return eventRepository.save(event);
        } catch (Exception e) {
            System.err.println("Erro ao atualizar evento: " + e.getMessage());
            throw new RuntimeException("Erro ao atualizar evento", e);
        }
    }

    public void deleteEvent(Long id) {
        try {
            Event event = findEventById(id);
            eventRepository.delete(event);
        } catch (Exception e) {
            System.err.println("Erro ao deletar evento: " + e.getMessage());
            throw new RuntimeException("Erro ao deletar evento", e);
        }
    }

    public List<Event> findUpcomingEvents() {
        try {
            return eventRepository.findByDateAfterOrderByDateAsc(LocalDateTime.now());
        } catch (Exception e) {
            System.err.println("Erro ao buscar eventos futuros: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar eventos futuros", e);
        }
    }

    public List<Event> searchEvents(String query) {
        try {
            return eventRepository.findByTitleContainingIgnoreCase(query);
        } catch (Exception e) {
            System.err.println("Erro ao pesquisar eventos: " + e.getMessage());
            throw new RuntimeException("Erro ao pesquisar eventos", e);
        }
    }
}