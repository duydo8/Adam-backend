package com.example.adambackend.controller.admin;

import com.example.adambackend.entities.Event;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("admin/event")
public class EventController {
    @Autowired
    EventService eventService;

    @PostMapping("create")
    public ResponseEntity<?> createEvent(@RequestBody Event event) {
        return ResponseEntity.ok().body(new IGenericResponse<Event>(eventService.save(event), 200, ""));

    }

    @PutMapping("update")
    public ResponseEntity<?> updateEvent(@RequestBody Event event) {
        Optional<Event> eventOptional = eventService.findById(event.getId());
        if (eventOptional.isPresent()) {

            return ResponseEntity.ok().body(new IGenericResponse<Event>(eventService.save(event), 200, ""));
        } else {
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found Event"));
        }
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteEvent(@RequestParam("event_id") Long id) {
        Optional<Event> eventOptional = eventService.findById(id);
        if (eventOptional.isPresent()) {
            eventService.deleteById(id);
            return ResponseEntity.ok().body(new HandleExceptionDemo(200, ""));
        } else {
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found Event"));
        }
    }
}