package com.pnayavu.lab.controllers;

import com.pnayavu.lab.entity.Studio;
import com.pnayavu.lab.service.StudioService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/studio")
public class StudioController {
    StudioService studioService;
    public StudioController(StudioService studioService) {
        this.studioService = studioService;
    }
    @GetMapping("/{studioId}")
    public Studio getStudioById(@PathVariable Long studioId) {
       return studioService.getStudioById(studioId);
    }
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Studio postStudio(@RequestBody Studio studio) {
        return studioService.createStudio(studio);
    }

    @PatchMapping("")
    public Studio updateStudio(@RequestBody Studio studio) {
        return studioService.updateStudio(studio);
    }

    @DeleteMapping("/{studioId}")
    public void deleteStudio(@PathVariable Long studioId) {
        if(getStudioById(studioId) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        studioService.deleteStudioById(studioId);
    }
}
