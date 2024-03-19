package com.pnayavu.lab.controllers;

import com.pnayavu.lab.entity.Studio;
import com.pnayavu.lab.service.StudioService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/studio")
public class StudioController {
    StudioService studioService;
    public StudioController(StudioService studioService) {
        this.studioService = studioService;
    }
    @GetMapping(value = "/{studioId}", produces = "application/json")
    public Studio getStudioById(@PathVariable Long studioId) {
        try {
            return studioService.getStudioById(studioId);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No studio found");
        }
    }
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Studio postStudio(@RequestBody Studio studio) {
        return studioService.createStudio(studio);
    }

    @PutMapping("")
    public Studio updateStudio(@RequestBody Studio studio) {
        return studioService.updateStudio(studio);
    }

    @DeleteMapping("/{studioId}")
    public void deleteStudio(@PathVariable Long studioId) {
        if(getStudioById(studioId) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        try {
            studioService.deleteStudioById(studioId);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot delete because has reference");
        }
    }
}
