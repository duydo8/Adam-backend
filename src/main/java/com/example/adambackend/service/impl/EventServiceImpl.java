package com.example.adambackend.service.impl;

import com.example.adambackend.entities.Event;
import com.example.adambackend.repository.EventRepository;
import com.example.adambackend.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {
    @Autowired
    EventRepository EventRepository;
    @Override
    public List<Event> findAll() {
        return EventRepository.findAll();
    }

    @Override
    public Event create(Event Event) {
        return EventRepository.save(Event);
    }

    @Override
    public void deleteById(Long id) {
        EventRepository.deleteById(id);
    }

    @Override
    public Optional<Event> findById(Long id) {
        return EventRepository.findById(id);
    }
}
