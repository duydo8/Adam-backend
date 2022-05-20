package com.example.adambackend.service;

import java.util.List;
import java.util.Optional;

import com.example.adambackend.entities.Event;

public interface EventService {

	Optional<Event> findById(Long id);

	void deleteById(Long id);

	Event create(Event Event);

	List<Event> findAll();
}
