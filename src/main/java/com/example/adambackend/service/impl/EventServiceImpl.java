package com.example.adambackend.service.impl;

import com.example.adambackend.entities.Event;
import com.example.adambackend.security.jwtConfig.repository.EventRepository;
import com.example.adambackend.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {
    @Autowired
    EventRepository eventRepository;

    @Override
    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    @Override
    public Event save(Event Event) {
        return eventRepository.save(Event);
    }

    @Override
    public void deleteById(Integer id) {
        eventRepository.deleteById(id);
    }

    @Override
    public Optional<Event> findById(Integer id) {
        return eventRepository.findById(id);
    }
}
