package com.example.adambackend.service;

import com.example.adambackend.entities.Event;

import java.util.List;
import java.util.Optional;

public interface EventService {

    Optional<Event> findById(Long id);

    void deleteById(Long id);

    Event save(Event event);

    List<Event> findAll();
}
