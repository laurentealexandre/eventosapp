package com.eventos.eventosapp.repositories;

import com.eventos.eventosapp.models.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    // Verifica se já existe uma inscrição para este evento e usuário
    boolean existsByEventIdAndUserEmail(Long eventId, String userEmail);
    
    // Busca uma inscrição específica por evento e usuário
    Optional<Subscription> findByEventIdAndUserEmail(Long eventId, String userEmail);
    
    // Conta quantas inscrições um evento tem
    int countByEventId(Long eventId);
}