package com.example.adambackend.controller.admin;

import com.example.adambackend.entities.Event;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.event.EventDTO;
import com.example.adambackend.payload.event.EventUpdateDTO;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(value = "*", maxAge = 3600)
@RequestMapping("admin/event")
public class EventController {
    @Autowired
    EventService eventService;

    @PostMapping("create")
    public ResponseEntity<?> createEvent(@RequestBody EventDTO eventDTO) {
        Event event = new Event();
        event.setCreateDate(eventDTO.getCreateDate());
        event.setEventName(eventDTO.getEventName());
        event.setEndTime(eventDTO.getEndTime());
        event.setStartTime(eventDTO.getStartTime());
        event.setIsActive(true);
        event.setIsDelete(false);
        event.setImage(eventDTO.getImage());
        event.setType(eventDTO.getType());
        event.setDescription(eventDTO.getDescription());
        return ResponseEntity.ok().body(new IGenericResponse<Event>(eventService.save(event), 200, ""));

    }

    //    private Integer id;
//    private String eventName;
//    private LocalDateTime startTime;
//    private LocalDateTime endTime;
//    private String description;
//    private Boolean type;
//    private String image;
    @PutMapping("update")
    public ResponseEntity<?> updateEvent(@RequestBody EventUpdateDTO eventUpdateDTO) {
        Optional<Event> eventOptional = eventService.findById(eventUpdateDTO.getId());
        if (eventOptional.isPresent()) {
            eventOptional.get().setEventName(eventUpdateDTO.getEventName());
            eventOptional.get().setDescription(eventUpdateDTO.getDescription());
            eventOptional.get().setImage(eventUpdateDTO.getImage());
            eventOptional.get().setType(eventUpdateDTO.getType());
            eventOptional.get().setStartTime(eventUpdateDTO.getStartTime());
            eventOptional.get().setEndTime(eventUpdateDTO.getEndTime());
            return ResponseEntity.ok().body(new IGenericResponse<Event>(eventService.save(eventOptional.get()), 200, ""));
        } else {
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy Event"));
        }
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteEvent(@RequestParam("event_id") Integer id) {
        Optional<Event> eventOptional = eventService.findById(id);
        if (eventOptional.isPresent()) {
            eventService.deleteById(id);
            return ResponseEntity.ok().body(new HandleExceptionDemo(200, ""));
        } else {
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy Event"));
        }
    }
}
