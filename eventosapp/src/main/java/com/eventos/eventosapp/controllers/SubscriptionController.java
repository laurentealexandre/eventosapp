package com.eventos.eventosapp.controllers;

import com.eventos.eventosapp.services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/events/{eventId}/subscriptions")
@CrossOrigin(origins = "http://localhost:5173")
public class SubscriptionController {

   @Autowired
   private SubscriptionService subscriptionService;

   // Endpoint para fazer inscrição
   @PostMapping
   public ResponseEntity<?> subscribe(
           @PathVariable Long eventId, 
           @RequestParam String userEmail) {
       subscriptionService.subscribe(eventId, userEmail);
       return ResponseEntity.ok().build();
   }

   // Endpoint para cancelar inscrição
   @DeleteMapping
   public ResponseEntity<?> unsubscribe(
           @PathVariable Long eventId, 
           @RequestParam String userEmail) {
       subscriptionService.unsubscribe(eventId, userEmail);
       return ResponseEntity.ok().build();
   }

   // Endpoint para verificar se está inscrito
   @GetMapping("/check")
   public ResponseEntity<Map<String, Boolean>> checkSubscription(
           @PathVariable Long eventId, 
           @RequestParam(required = false) String userEmail) {
       boolean isSubscribed = userEmail != null ? 
           subscriptionService.isSubscribed(eventId, userEmail) : false;
       return ResponseEntity.ok(Map.of("isSubscribed", isSubscribed));
   }

   // Endpoint para contar inscrições
   @GetMapping("/count")
   public ResponseEntity<Map<String, Integer>> getSubscriptionCount(
           @PathVariable Long eventId) {
       int count = subscriptionService.getSubscriptionCount(eventId);
       return ResponseEntity.ok(Map.of("count", count));
   }
}