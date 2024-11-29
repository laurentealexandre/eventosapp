package com.eventos.eventosapp.repositories;

import com.eventos.eventosapp.models.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    
    boolean existsByEventIdAndUserEmail(Long eventId, String userEmail);
    
    
    Optional<Subscription> findByEventIdAndUserEmail(Long eventId, String userEmail);
    
    
    int countByEventId(Long eventId);
}