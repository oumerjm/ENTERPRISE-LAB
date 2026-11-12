package com.site.actor_manager.controller;

import com.site.actor_manager.model.Actor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ActorController {

    private List<Actor> actors = new ArrayList<>();

    public ActorController() {
        actors.add(new Actor("John Doe", "The Great Adventures", 1));
        actors.add(new Actor("Baba Yaga", "John Wick", 2));
    }

    // READ ALL
    @GetMapping("/actor")
    public ResponseEntity<List<Actor>> getAllActors() {
        return ResponseEntity.ok(actors);
    }

    // READ BY ID
    @GetMapping("/actor/{id}")
    public ResponseEntity<Actor> getActorById(@PathVariable int id) {
        for (Actor actor : actors) {
            if (actor.getId() == id) {
                return ResponseEntity.ok(actor);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // CREATE
    @PostMapping("/actor")
    public ResponseEntity<Actor> addActor(@RequestBody Actor actor) {
        actors.add(actor);
        return ResponseEntity.status(HttpStatus.CREATED).body(actor);
    }

    // UPDATE
    @PutMapping("/actor/{id}")
    public ResponseEntity<Actor> updateActor(@PathVariable int id, @RequestBody Actor updatedActor) {
        for (Actor actor : actors) {
            if (actor.getId() == id) {
                actor.setName(updatedActor.getName());
                actor.setMovieName(updatedActor.getMovieName());
                return ResponseEntity.ok(actor);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // DELETE
    @DeleteMapping("/actor/{id}")
    public ResponseEntity<String> deleteActor(@PathVariable int id) {
        boolean removed = actors.removeIf(actor -> actor.getId() == id);
        if (removed) {
            return ResponseEntity.ok("Actor with ID " + id + " deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Actor with ID " + id + " not found.");
    }
}