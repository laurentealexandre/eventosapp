package com.eventos.eventosapp.services;

import com.eventos.eventosapp.models.Event;
import com.eventos.eventosapp.models.Subscription;
import com.eventos.eventosapp.repositories.EventRepository;
import com.eventos.eventosapp.repositories.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private EventRepository eventRepository;

    
    @Transactional
    public void subscribe(Long eventId, String userEmail) {
        
        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

        
        if (subscriptionRepository.existsByEventIdAndUserEmail(eventId, userEmail)) {
            throw new RuntimeException("Usuário já está inscrito neste evento");
        }

        
        if (subscriptionRepository.countByEventId(eventId) >= event.getCapacity()) {
            throw new RuntimeException("Evento já atingiu a capacidade máxima");
        }

        
        Subscription subscription = new Subscription();
        subscription.setEvent(event);
        subscription.setUserEmail(userEmail);
        
        subscriptionRepository.save(subscription);
    }

    
    @Transactional
    public void unsubscribe(Long eventId, String userEmail) {
        Subscription subscription = subscriptionRepository.findByEventIdAndUserEmail(eventId, userEmail)
            .orElseThrow(() -> new RuntimeException("Inscrição não encontrada"));
        
        subscriptionRepository.delete(subscription);
    }

    
    public boolean isSubscribed(Long eventId, String userEmail) {
        return subscriptionRepository.existsByEventIdAndUserEmail(eventId, userEmail);
    }

    
    public int getSubscriptionCount(Long eventId) {
        return subscriptionRepository.countByEventId(eventId);
    }
}