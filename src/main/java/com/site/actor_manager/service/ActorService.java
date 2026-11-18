package com.site.actor_manager.service;

import com.site.actor_manager.model.Actor;
import com.site.actor_manager.repository.ActorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ActorService {

    private final ActorRepository actorRepository;

    public ActorService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    public List<Actor> getAllActors() {
        return actorRepository.findAll();
    }

    public Actor getActorById(Integer id) {
        return actorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Actor not found with ID: " + id));
    }

    public Actor createActor(Actor actor) {
        actor.setId(null); // Guarantees MySQL inserts a new record
        return actorRepository.save(actor);
    }

    public Actor updateActor(Integer id, Actor updatedActor) {
        Actor existingActor = getActorById(id);
        existingActor.setName(updatedActor.getName());
        existingActor.setMovieName(updatedActor.getMovieName());
        return actorRepository.save(existingActor);
    }

    public void deleteActor(Integer id) {
        Actor existingActor = getActorById(id);
        actorRepository.delete(existingActor);
    }
}