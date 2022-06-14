package com.example.adambackend.service;

import com.example.adambackend.entities.Event;

import java.util.List;
import java.util.Optional;

public interface EventService {

    Optional<Event> findById(Integer id);

    void deleteById(Integer id);

    Event save(Event event);

    List<Event> findAll();
}
