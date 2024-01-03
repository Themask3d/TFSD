package com.bezkoder.spring.jpa.h2.controller;

import com.bezkoder.spring.jpa.h2.model.Tutorial;
import com.bezkoder.spring.jpa.h2.repository.TutorialRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the TutorialController class.
 * These tests use Mockito to mock the TutorialRepository and test the functionality
 * of TutorialController in isolation.
 */
@ExtendWith(MockitoExtension.class)
public class TutorialControllerTest {

    @Mock
    private TutorialRepository tutorialRepository;

    @InjectMocks
    private TutorialController tutorialController;

    private Tutorial tutorial;

    /**
     * Setup method to initialize common objects before each test.
     */
    @BeforeEach
    void setUp() {
        tutorial = new Tutorial("Java", "Java Description", true);
    }

    /**
     * Test for getAllTutorials method with a title filter.
     * It should return a non-empty list when tutorials are found.
     */
    @Test
    void getAllTutorials_WithTitleFilter_ReturnsNonEmptyList() {
        when(tutorialRepository.findByTitleContainingIgnoreCase("Java")).thenReturn(Arrays.asList(tutorial));
        ResponseEntity<List<Tutorial>> response = tutorialController.getAllTutorials("Java");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
        assertEquals(1, response.getBody().size());
    }

    /**
     * Test for getAllTutorials method without a title filter.
     * It should return an empty list.
     */
    @Test
    void getAllTutorials_WithoutTitleFilter_ReturnsEmptyList() {
        when(tutorialRepository.findAll()).thenReturn(Collections.emptyList());
        ResponseEntity<List<Tutorial>> response = tutorialController.getAllTutorials(null);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertTrue(response.getBody() == null || response.getBody().isEmpty());
    }


    /**
     * Test the getTutorialById method with an existing ID.
     * It should return a response with a non empty body.
     */
    @Test
    void getTutorialById_WithExistingId_ReturnsTutorial() {
        when(tutorialRepository.findById(1L)).thenReturn(Optional.of(tutorial));
        ResponseEntity<Tutorial> response = tutorialController.getTutorialById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Java", response.getBody().getTitle());
    }

    /**
     * Test the getTutorialById method with a non-existing ID.
     * It should return HttpStatus.NOT_FOUND.
     */
    @Test
    void getTutorialById_WithNonExistingId_ReturnsNotFound() {
        when(tutorialRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseEntity<Tutorial> response = tutorialController.getTutorialById(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    /**
     * Test the createTutorial method with a valid tutorial object.
     * It should return HttpStatus.CREATED.
     */
    @Test
    void createTutorial_WithValidTutorial_ReturnsCreated() {
        when(tutorialRepository.save(any(Tutorial.class))).thenReturn(tutorial);
        ResponseEntity<Tutorial> response = tutorialController.createTutorial(new Tutorial("Spring", "Spring Description", false));
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    /**
     * Test the updateTutorial method with an existing ID.
     * It should update the tutorial and return HttpStatus.OK.
     */
    @Test
    void updateTutorial_WithExistingId_ReturnsUpdatedTutorial() {
        when(tutorialRepository.findById(1L)).thenReturn(Optional.of(tutorial));
        when(tutorialRepository.save(any(Tutorial.class))).thenReturn(tutorial);
        ResponseEntity<Tutorial> response = tutorialController.updateTutorial(1L, new Tutorial("Updated Title", "Updated Description", true));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated Title", response.getBody().getTitle());
    }

    /**
     * Test the updateTutorial method with a non-existing ID.
     * It should return HttpStatus.NOT_FOUND.
     */
    @Test
    void updateTutorial_WithNonExistingId_ReturnsNotFound() {
        when(tutorialRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseEntity<Tutorial> response = tutorialController.updateTutorial(1L, new Tutorial("Updated Title", "Updated Description", true));
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    /**
     * Test the deleteTutorial method with an existing ID.
     * It should delete the tutorial and return HttpStatus.NO_CONTENT.
     */
    @Test
    void deleteTutorial_WithExistingId_ReturnsNoContent() {
        doNothing().when(tutorialRepository).deleteById(1L);

        ResponseEntity<HttpStatus> response = tutorialController.deleteTutorial(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(tutorialRepository).deleteById(1L);
    }

    /**
     * Test the deleteTutorial method with a non-existing ID.
     * It should return HttpStatus.INTERNAL_SERVER_ERROR.
     */
    @Test
    void deleteTutorial_WithNonExistingId_ReturnsNotFound() {
        doThrow(new RuntimeException("Tutorial not found")).when(tutorialRepository).deleteById(1L);
        ResponseEntity<HttpStatus> response = tutorialController.deleteTutorial(1L);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }



    /**
     * Test the findByPublished method.
     * It should return a list of published tutorials and HttpStatus.OK.
     */
    @Test
    void findByPublished_WhenCalled_ReturnsList() {
        when(tutorialRepository.findByPublished(true)).thenReturn(Arrays.asList(tutorial));
        ResponseEntity<List<Tutorial>> response = tutorialController.findByPublished();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }
}
