package com.example.adambackend.service.impl;

import com.example.adambackend.entities.DiscountOrder;
import com.example.adambackend.entities.Event;
import com.example.adambackend.payload.event.EventDTO;
import com.example.adambackend.payload.event.EventResponse;
import com.example.adambackend.repository.EventRepository;
import com.example.adambackend.service.DiscountOrderService;
import com.example.adambackend.service.EventService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {
	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private DiscountOrderService discountOrderService;

	@Override
	public List<Event> findAll(String name) {
		return eventRepository.findAll(name);
	}

	@Override
	public Event save(Event Event) {
		return eventRepository.save(Event);
	}

	@Override
	public void deleteById(Integer id) {
		eventRepository.updateEventDeleted(id);
	}

	@Override
	public Optional<Event> findById(Integer id) {
		return eventRepository.findById(id);
	}

	@Override
	public Event findExistById(Integer id) {
		return eventRepository.findExistById(id);
	}

	@Override
	public Event createEvent(EventDTO eventDTO) {
		Event event = new Event();
		event.setCreateDate(LocalDateTime.now());
		event.setEventName(eventDTO.getEventName());
		event.setEndTime(eventDTO.getEndTime());
		event.setStartTime(eventDTO.getStartTime());
		event.setStatus(1);
		event.setImage(eventDTO.getImage());
		event.setType(eventDTO.getType());
		event.setDescription(eventDTO.getDescription());
		return eventRepository.save(event);
	}

	@Override
	public Event updateEvent(EventDTO eventDTO) {
		Optional<Event> eventOptional = eventRepository.findById(eventDTO.getId());
		if (eventOptional.isPresent()) {
			eventOptional.get().setEventName(eventDTO.getEventName());
			eventOptional.get().setStatus(eventDTO.getStatus());
			eventOptional.get().setImage(eventDTO.getImage());
			return eventRepository.save(eventOptional.get());
		}
		return null;
	}

	@Override
	public List<EventResponse> findAllEvent(String name) {

		List<Event> events = eventRepository.findAll(name);
		List<EventResponse> eventResponses = new ArrayList<>();
		for (Event e : events
		) {
			EventResponse eventResponse = new EventResponse();
			BeanUtils.copyProperties(e, eventResponse);
			List<DiscountOrder> discountOrders = discountOrderService.findByEventId(e.getId());
			Double salePrice = 0.0;
			int count = 0;
			for (DiscountOrder d : discountOrders
			) {
				salePrice += d.getSalePrice();
				count++;
			}
			double averageSalePrice = (count > 0) ? salePrice / count : 0;
			eventResponse.setSalePrice(averageSalePrice);
			eventResponses.add(eventResponse);
		}
		return eventResponses;
	}

	@Override
	public void deleteListEventById(List<Integer> listEventId) {
		for (Integer id : listEventId) {
			Optional<Event> eventOptional = eventRepository.findById(id);
			if (eventOptional.isPresent()) {
				eventRepository.updateEventDeleted(id);
			}
		}
	}
}
