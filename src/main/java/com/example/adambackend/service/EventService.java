package com.example.adambackend.service;

import com.example.adambackend.entities.Event;
import com.example.adambackend.payload.event.EventDTO;
import com.example.adambackend.payload.event.EventResponse;

import java.util.List;
import java.util.Optional;

public interface EventService {

    Optional<Event> findById(Integer id);

    void deleteById(Integer id);

    Event save(Event event);

    List<Event> findAll(String name);

    Event findExistById(Integer id);

    Event createEvent(EventDTO eventDTO);

    Event updateEvent(EventDTO eventUpdateDTO);

    List<EventResponse> findAllEvent(String name);

    void deleteListEventById(List<Integer> listEventId);
}
