package com.kiwadev.mocktest.services.impl;


import com.kiwadev.mocktest.models.domain.Actor;
import com.kiwadev.mocktest.models.domain.Movie;
import com.kiwadev.mocktest.models.domain.User;
import com.kiwadev.mocktest.models.web.ActorRequestDTO;
import com.kiwadev.mocktest.models.web.ActorResponseDTO;
import com.kiwadev.mocktest.repository.ActorRepository;
import com.kiwadev.mocktest.repository.MovieRepository;
import com.kiwadev.mocktest.services.ActorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ActorServiceImpl implements ActorService {

    private final ActorRepository repository;

    @Override
    public List<ActorResponseDTO> findAll(Pageable pageable) {
        var list =  repository.findAll(pageable);

        return  list.stream().map(Actor::toResponse).toList();
    }

    @Override
    public ActorResponseDTO findById(Long id) {
        var data = repository.findById(id);
        if(data.isEmpty()) throw new RuntimeException("User id : " +id+" not found.");
        return data.get().toResponse();
    }

    @Override
    public ActorResponseDTO save(ActorRequestDTO actor) {
        if(actor.getName().isEmpty()){
            throw new RuntimeException("Data actor is not valid");
        }
        Actor data = new Actor(null, actor.getName());
        return data.toResponse();
    }

    @Override
    public ActorResponseDTO update(Long id, ActorRequestDTO actor) {
        if(actor.getName().isEmpty()){
            throw new RuntimeException("Data actor is not valid");
        }

        return  repository.findById(id)
                .map(existing -> {
                    existing.setName(actor.getName());
                    return  repository.save(existing).toResponse();
                })
                .orElseThrow(() -> new RuntimeException("Actor not found"));
    }

    @Override
    public void delete(Long id) {
        if(!repository.existsById(id))
            throw new RuntimeException("Actor id "+id+" not found");
        repository.deleteById(id);
    }
}
