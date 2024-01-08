package com.bezkoder.spring.jpa.h2.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.bezkoder.spring.jpa.h2.model.Tutorial;
import com.bezkoder.spring.jpa.h2.repository.TutorialRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Controller for managing tutorials.
 * Provides CRUD operations and filtering options for tutorials.
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class TutorialController {

  /**
   * Logger for this class.
   */
  private static final Logger LOGGER = LogManager.getLogger(TutorialController.class);

  /**
   * Repository for handling tutorial data operations.
   */
  @SuppressWarnings("checkstyle:VisibilityModifier")
  @Autowired
  TutorialRepository tutorialRepository;

  /**
   * Retrieves all tutorials or filters them by title.
   *
   * @param title Optional title filter.
   * @return ResponseEntity containing the list of tutorials and the HTTP status.
   */
  @GetMapping("/tutorials")
  public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(required = false) String title) {
    LOGGER.debug("Entering getAllTutorials method with title filter: {}", title);
    try {
      List<Tutorial> tutorials = new ArrayList<>();

      if (title == null) {
        tutorialRepository.findAll().forEach(tutorials::add);
      } else {
        tutorialRepository.findByTitleContainingIgnoreCase(title).forEach(tutorials::add);
      }

      if (tutorials.isEmpty()) {
        LOGGER.info("No tutorials found with title filter: {}", title);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      LOGGER.info("Found {} tutorials with title filter: {}", tutorials.size(), title);
      return new ResponseEntity<>(tutorials, HttpStatus.OK);
    } catch (Exception e) {
      LOGGER.error("Error occurred while fetching tutorials with title filter: {}. Error: {}", title, e.toString());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    } finally {
      LOGGER.debug("Exiting getAllTutorials method");
    }
  }

  /**
   * Retrieves a tutorial by its ID.
   *
   * @param id The ID of the tutorial.
   * @return ResponseEntity containing the tutorial and the HTTP status.
   */
  @GetMapping("/tutorials/{id}")
  public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") long id) {
    LOGGER.debug("Entering getTutorialById method with ID: {}", id);
    try {
      Optional<Tutorial> tutorialData = tutorialRepository.findById(id);

      if (tutorialData.isPresent()) {
        LOGGER.info("Found tutorial with ID: {}", id);
        return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
      } else {
        LOGGER.warn("No tutorial found with ID: {}", id);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      LOGGER.error("Error occurred while fetching tutorial by ID {}. Error: {}", id, e.toString());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    } finally {
      LOGGER.debug("Exiting getTutorialById method");
    }
  }

  /**
   * Creates a new tutorial.
   *
   * @param tutorial Tutorial object to be created.
   * @return ResponseEntity containing the created tutorial and the HTTP status.
   */
  @PostMapping("/tutorials")
  public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
    LOGGER.debug("Entering createTutorial method");
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Tutorial data received: {}", tutorial);
    }
    try {
      Tutorial vartutorial = tutorialRepository.save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), false));
      LOGGER.info("Created tutorial with ID: {}", vartutorial.getId());
      return new ResponseEntity<>(vartutorial, HttpStatus.CREATED);
    } catch (Exception e) {
      LOGGER.error("Exception in createTutorial method for title '{}'. Error: {}", tutorial.getTitle(), e.toString());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    } finally {
      LOGGER.debug("Exiting createTutorial method");
    }
  }

  /**
   * Updates an existing tutorial identified by its ID.
   *
   * @param id The ID of the tutorial to update.
   * @param tutorial Updated tutorial information.
   * @return ResponseEntity containing the updated tutorial and the HTTP status.
   */
  @PutMapping("/tutorials/{id}")
  public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial) {
    LOGGER.debug("Entering updateTutorial method with ID: {} and data: {}", id, tutorial);
    try {
      Optional<Tutorial> tutorialData = tutorialRepository.findById(id);

      if (tutorialData.isPresent()) {
        Tutorial tutorialvar = tutorialData.get();
        LOGGER.info("Before update: {}", tutorialvar);
        tutorialvar.setTitle(tutorial.getTitle());
        tutorialvar.setDescription(tutorial.getDescription());
        tutorialvar.setPublished(tutorial.isPublished());
        Tutorial updatedTutorial = tutorialRepository.save(tutorialvar);
        LOGGER.info("Updated tutorial. Before: {}, After: {}", tutorialvar, updatedTutorial);
        return new ResponseEntity<>(updatedTutorial, HttpStatus.OK);
      } else {
        LOGGER.warn("No tutorial found to update with ID: {}", id);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      LOGGER.error("Exception in updateTutorial method for ID '{}'. Error: {}", id, e.toString());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    } finally {
      LOGGER.debug("Exiting updateTutorial method");
    }
  }

  /**
   * Deletes a tutorial by its ID.
   *
   * @param id The ID of the tutorial to delete.
   * @return ResponseEntity with the HTTP status.
   */
  @DeleteMapping("/tutorials/{id}")
  public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
    LOGGER.debug("Entering deleteTutorial method with ID: {}", id);
    try {
      tutorialRepository.deleteById(id);
      LOGGER.info("Deleted tutorial with ID: {}", id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      LOGGER.error("Exception in deleteTutorial method for ID '{}'. Error: {}", id, e.toString());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    } finally {
      LOGGER.debug("Exiting deleteTutorial method");
    }
  }

  /**
   * Deletes all tutorials.
   *
   * @return ResponseEntity with the HTTP status.
   */
  @DeleteMapping("/tutorials")
  public ResponseEntity<HttpStatus> deleteAllTutorials() {
    LOGGER.debug("Entering deleteAllTutorials method");
    try {
      tutorialRepository.deleteAll();
      LOGGER.info("Deleted all tutorials");
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      LOGGER.error("Exception in deleteAllTutorials method. Error: {}", e.toString());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    } finally {
      LOGGER.debug("Exiting deleteAllTutorials method");
    }
  }

  /**
   * Retrieves all published tutorials.
   *
   * @return ResponseEntity containing the list of published tutorials and the HTTP status.
   */
  @GetMapping("/tutorials/published")
  public ResponseEntity<List<Tutorial>> findByPublished() {
    LOGGER.debug("Entering findByPublished method");
    try {
      List<Tutorial> tutorials = tutorialRepository.findByPublished(true);

      if (tutorials.isEmpty()) {
        LOGGER.info("No published tutorials found");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      LOGGER.info("Found {} published tutorials", tutorials.size());
      return new ResponseEntity<>(tutorials, HttpStatus.OK);
    } catch (Exception e) {
      LOGGER.error("Exception in findByPublished method. Error: {}", e.toString());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    } finally {
      LOGGER.debug("Exiting findByPublished method");
    }
  }
}
