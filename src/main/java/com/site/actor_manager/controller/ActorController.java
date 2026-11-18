package com.site.actor_manager.controller;

import com.site.actor_manager.model.Actor;
import com.site.actor_manager.service.ActorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ActorController {

    private final ActorService actorService;

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    // READ ALL
    @GetMapping("/actor")
    public ResponseEntity<List<Actor>> getAllActors() {
        return ResponseEntity.ok(actorService.getAllActors());
    }

    // READ BY ID
    @GetMapping("/actor/{id}")
    public ResponseEntity<Actor> getActorById(@PathVariable Integer id) {
        return ResponseEntity.ok(actorService.getActorById(id));
    }

    // CREATE
    @PostMapping("/actor")
    public ResponseEntity<Actor> createActor(@RequestBody Actor actor) {
        Actor savedActor = actorService.createActor(actor);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedActor);
    }

    // UPDATE
    @PutMapping("/actor/{id}")
    public ResponseEntity<Actor> updateActor(@PathVariable Integer id, @RequestBody Actor updatedActor) {
        return ResponseEntity.ok(actorService.updateActor(id, updatedActor));
    }

    // DELETE
    @DeleteMapping("/actor/{id}")
    public ResponseEntity<String> deleteActor(@PathVariable Integer id) {
        actorService.deleteActor(id);
        return ResponseEntity.ok("Actor with ID " + id + " deleted successfully.");
    }
}