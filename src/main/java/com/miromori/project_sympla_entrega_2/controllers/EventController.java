package com.miromori.project_sympla_entrega_2.controllers;

import com.miromori.project_sympla_entrega_2.models.Event;
import com.miromori.project_sympla_entrega_2.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/events")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @GetMapping
    public List<Event> findAll(){
        return eventRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Event> findById(@PathVariable Long id){
        return eventRepository.findById(id);
    }

    @PostMapping
    public ResponseEntity<Event> save(@RequestBody Event event){
        return ResponseEntity.ok(eventRepository.save(event));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Event> delete(@PathVariable Long id){
        Event event = eventRepository.findById(id).orElse(null);
        eventRepository.deleteById(id);
        return ResponseEntity.ok(event);
    }

    public Optional<Event> findByName(String name){
        return eventRepository.findByName(name);
    }
}
