package com.example.adambackend.controller.admin;

import com.example.adambackend.entities.Color;
import com.example.adambackend.entities.Event;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.color.ListColorIdDTO;
import com.example.adambackend.payload.event.EventDTO;
import com.example.adambackend.payload.event.EventUpdateDTO;
import com.example.adambackend.payload.event.ListEventId;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.repository.EventRepository;
import com.example.adambackend.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(value = "*", maxAge = 3600)
@RequestMapping("/admin/event")
public class EventController {
    @Autowired
    EventRepository eventService;

    @PostMapping("create")
    public ResponseEntity<?> createEvent(@RequestBody EventDTO eventDTO) {
        try {
            Event event = new Event();
            event.setCreateDate(LocalDateTime.now());
            event.setEventName(eventDTO.getEventName());
            event.setEndTime(eventDTO.getEndTime());
            event.setStartTime(eventDTO.getStartTime());
            event.setIsActive(true);
            event.setIsDelete(false);
            event.setImage(eventDTO.getImage());
            event.setType(eventDTO.getType());
            event.setDescription(eventDTO.getDescription());
            return ResponseEntity.ok().body(new IGenericResponse<Event>(eventService.save(event), 200, ""));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }


    @PutMapping("update")
    public ResponseEntity<?> updateEvent(@RequestBody EventUpdateDTO eventUpdateDTO) {
        try {
            Optional<Event> eventOptional = eventService.findById(eventUpdateDTO.getId());
            if (eventOptional.isPresent()) {
                eventOptional.get().setEventName(eventUpdateDTO.getEventName());
                eventOptional.get().setIsActive(eventUpdateDTO.getIsActive());
                eventOptional.get().setImage(eventUpdateDTO.getImage());

                return ResponseEntity.ok().body(new IGenericResponse<Event>(eventService.save(eventOptional.get()), 200, ""));
            } else {
                return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy Event"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteEvent(@RequestParam("event_id") Integer id) {
        try {
            Optional<Event> eventOptional = eventService.findById(id);
            if (eventOptional.isPresent()) {
                eventService.deleteById(id);
                return ResponseEntity.ok().body(new HandleExceptionDemo(200, ""));
            } else {
                return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy Event"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }
    @GetMapping("findAll")
    public ResponseEntity<?> findAll(@RequestParam(value = "name",required = false)String name) {
        if(name==null){
            return ResponseEntity.ok().body(new IGenericResponse<>(eventService.findAll(), 200, ""));

        } return ResponseEntity.ok().body(new IGenericResponse<>(eventService.findAll(name), 200, ""));
    }
    @DeleteMapping("deleteByListId")
    public ResponseEntity<?> deleteArrayTagId(@RequestBody ListEventId listEventId) {
        try {
            List<Integer> list = listEventId.getListId();

            if (list.size() > 0) {
                for (Integer x : list
                ) {
                    Optional<Event> eventOptional = eventService.findById(x);

                    if (eventOptional.isPresent()) {

                        eventService.updateEventDeleted(x);

                    }
                }
                return ResponseEntity.ok().body(new IGenericResponse<>("", 200, "Thành công"));
            }
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, " Không tìm thấy"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }
}
