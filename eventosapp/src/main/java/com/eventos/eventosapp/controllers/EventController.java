package com.eventos.eventosapp.controllers;

import com.eventos.eventosapp.models.Event;
import com.eventos.eventosapp.services.EventService;
import com.eventos.eventosapp.services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "http://localhost:5173")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        try {
            List<Event> events = eventService.findAllEvents();
            events.forEach(event -> {
                try {
                    int count = subscriptionService.getSubscriptionCount(event.getId());
                    event.setSubscribedCount(count);
                } catch (Exception e) {
                    System.err.println("Erro ao contar inscrições para evento " + event.getId() + ": " + e.getMessage());
                    event.setSubscribedCount(0);
                }
            });
            return ResponseEntity.ok(events);
        } catch (Exception e) {
            System.err.println("Erro ao buscar todos os eventos: " + e.getMessage());
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEventById(@PathVariable Long id) {
        try {
            System.out.println("Buscando evento com ID: " + id);
            Event event = eventService.findEventById(id);
            
            if (event == null) {
                System.err.println("Evento não encontrado: " + id);
                return ResponseEntity.notFound().build();
            }
            
            System.out.println("Evento encontrado: " + event);
            
            try {
                int count = subscriptionService.getSubscriptionCount(id);
                System.out.println("Número de inscritos: " + count);
                event.setSubscribedCount(count);
            } catch (Exception e) {
                System.err.println("Erro ao contar inscrições: " + e.getMessage());
                event.setSubscribedCount(0);
            }
            
            return ResponseEntity.ok(event);
        } catch (Exception e) {
            System.err.println("Erro ao buscar evento por ID: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Erro ao buscar evento: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody Event event) {
        try {
            Event createdEvent = eventService.createEvent(event);
            return ResponseEntity.ok(createdEvent);
        } catch (Exception e) {
            System.err.println("Erro ao criar evento: " + e.getMessage());
            return ResponseEntity.badRequest().body("Erro ao criar evento: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable Long id, @RequestBody Event event) {
        try {
            Event updatedEvent = eventService.updateEvent(id, event);
            return ResponseEntity.ok(updatedEvent);
        } catch (Exception e) {
            System.err.println("Erro ao atualizar evento: " + e.getMessage());
            return ResponseEntity.badRequest().body("Erro ao atualizar evento: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long id) {
        try {
            eventService.deleteEvent(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            System.err.println("Erro ao deletar evento: " + e.getMessage());
            return ResponseEntity.badRequest().body("Erro ao deletar evento: " + e.getMessage());
        }
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<Event>> getUpcomingEvents() {
        try {
            List<Event> events = eventService.findUpcomingEvents();
            events.forEach(event -> {
                try {
                    int count = subscriptionService.getSubscriptionCount(event.getId());
                    event.setSubscribedCount(count);
                } catch (Exception e) {
                    System.err.println("Erro ao contar inscrições para evento futuro " + event.getId() + ": " + e.getMessage());
                    event.setSubscribedCount(0);
                }
            });
            return ResponseEntity.ok(events);
        } catch (Exception e) {
            System.err.println("Erro ao buscar eventos futuros: " + e.getMessage());
            throw e;
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Event>> searchEvents(@RequestParam String query) {
        try {
            List<Event> events = eventService.searchEvents(query);
            events.forEach(event -> {
                try {
                    int count = subscriptionService.getSubscriptionCount(event.getId());
                    event.setSubscribedCount(count);
                } catch (Exception e) {
                    System.err.println("Erro ao contar inscrições para evento " + event.getId() + ": " + e.getMessage());
                    event.setSubscribedCount(0);
                }
            });
            return ResponseEntity.ok(events);
        } catch (Exception e) {
            System.err.println("Erro ao pesquisar eventos: " + e.getMessage());
            throw e;
        }
    }
}