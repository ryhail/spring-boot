package com.pnayavu.lab.controllers;

import com.pnayavu.lab.entity.Studio;
import com.pnayavu.lab.logging.Logged;
import com.pnayavu.lab.service.StudioService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

@RestController
@RequestMapping("/studio")
public class StudioController {
    StudioService studioService;
    public StudioController(StudioService studioService) {
        this.studioService = studioService;
    }
    @GetMapping(value = "/{studioId}", produces = "application/json")
    @Logged
    public Studio getStudioById(@PathVariable Long studioId) {
        try {
            return studioService.getStudioById(studioId);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No studio found");
        }
    }
    @Logged
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Studio postStudio(@RequestBody Studio studio) {
        if(Stream.of(studio).allMatch(null))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cant resolve sent entity");
        Studio savedStudio = studioService.createStudio(studio);
        if(savedStudio == null)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Entity was not saved");
        return savedStudio;
    }
    @Logged
    @PutMapping("")
    public Studio updateStudio(@RequestBody Studio studio) {
        if(studio.getId() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cant resolve sent entity");
        Studio savedStudio = studioService.updateStudio(studio);
        if(savedStudio == null)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Entity was not saved");
        return savedStudio;
    }
    @Logged
    @DeleteMapping("/{studioId}")
    public void deleteStudio(@PathVariable Long studioId) {
        if(getStudioById(studioId) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime with such id not found");
        }
        try {
            studioService.deleteStudioById(studioId);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot delete because has reference");
        }
    }
}
