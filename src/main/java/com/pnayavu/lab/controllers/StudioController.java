package com.pnayavu.lab.controllers;

import com.pnayavu.lab.logging.Logged;
import com.pnayavu.lab.model.Studio;
import com.pnayavu.lab.service.StudioService;
import java.util.NoSuchElementException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/studio")
@CrossOrigin
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
    if (studio.getId() == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not full information");
    }
    Studio savedStudio = studioService.createStudio(studio);
    if (savedStudio == null) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Entity was not saved");
    }
    return savedStudio;
  }

  @Logged
  @PutMapping("")
  public Studio updateStudio(@RequestBody Studio studio) {
    if (studio.getId() == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cant resolve sent entity");
    }
    Studio savedStudio = studioService.updateStudio(studio);
    if (savedStudio == null) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Entity was not saved");
    }
    return savedStudio;
  }

  @Logged
  @DeleteMapping("/{studioId}")
  public String deleteStudio(@PathVariable Long studioId) {
    try {
      studioService.deleteStudioById(studioId);
      return "Deleted studio with id " + studioId;
    } catch (DataIntegrityViolationException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Cannot delete because has reference");
    }
  }
}
