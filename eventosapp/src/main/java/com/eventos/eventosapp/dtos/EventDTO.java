package com.eventos.eventosapp.dtos;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EventDTO {
    private Long id;
    private String title;
    private LocalDateTime date;
    private String location;
    private String description;
    private Integer capacity;
}