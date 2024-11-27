package com.eventos.eventosapp.models;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonBackReference;  // Importar para Jackson

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "subscriptions",
    uniqueConstraints = @UniqueConstraint(columnNames = {"event_id", "user_email"}))
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Adicionando @JsonBackReference aqui para evitar o ciclo de referência
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    @JsonBackReference
    private Event event;

    @Column(name = "user_email", nullable = false)
    private String userEmail;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
