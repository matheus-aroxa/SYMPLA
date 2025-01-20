package com.miromori.project_sympla_entrega_2.controllers;

import com.miromori.project_sympla_entrega_2.models.Feedback;
import com.miromori.project_sympla_entrega_2.models.User;
import com.miromori.project_sympla_entrega_2.repositories.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/feedbacks")
public class FeedbackController {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @GetMapping
    public ResponseEntity<List<Feedback>> findAll(){
        return ResponseEntity.ok(feedbackRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Feedback>> findById(@PathVariable Long id){
        return ResponseEntity.ok(feedbackRepository.findById(id));
    }

    @PostMapping
    public ResponseEntity<Feedback> save(@RequestBody Feedback feedback){
        return ResponseEntity.ok(feedbackRepository.save(feedback));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Feedback> delete(@PathVariable Long id){
        Feedback feedback = feedbackRepository.findById(id).orElse(null);
        feedbackRepository.deleteById(id);
        return ResponseEntity.ok(feedback);
    }
}
