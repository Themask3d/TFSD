package com.bezkoder.spring.jpa.h2.controller;

import com.bezkoder.spring.jpa.h2.SpringBootJpaH2Application;
import com.bezkoder.spring.jpa.h2.model.Tutorial;
import com.bezkoder.spring.jpa.h2.repository.TutorialRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the TutorialController.
 * These tests start the application on a random port and use TestRestTemplate
 * to perform HTTP requests and assert the responses.
 */
@SpringBootTest(classes = SpringBootJpaH2Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TutorialControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TutorialRepository tutorialRepository;

    @LocalServerPort
    private int port;

    /**
     * Constructs the base URL for the API based on the random server port.
     * @return The root URL for the API.
     */
    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    /**
     * Sets up the test environment before each test.
     * Adds a default tutorial to the repository.
     */
    @BeforeEach
    void setup() {
        Tutorial testTutorial = new Tutorial("Test", "Test Description", false);
        tutorialRepository.save(testTutorial);
    }

    /**
     * Cleans up the test environment after each test.
     * Removes all tutorials from the repository.
     */
    @AfterEach
    void tearDown() {
        tutorialRepository.deleteAll();
    }

    /**
     * Test the creation of a new tutorial via HTTP POST request.
     * It should return HttpStatus.CREATED.
     */
    @Test
    void testCreateTutorial() {
        Tutorial tutorial = new Tutorial("New Tutorial", "New Description", false);
        ResponseEntity<Tutorial> postResponse = restTemplate.postForEntity(getRootUrl() + "/api/tutorials", tutorial, Tutorial.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
        assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());
    }

    /**
     * Test the retrieval of all tutorials via HTTP GET request.
     * It should return HttpStatus.OK.
     */
    @Test
    void testGetAllTutorials() {
        ResponseEntity<String> response = restTemplate.getForEntity(getRootUrl() + "/api/tutorials", String.class);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /**
     * Test the retrieval of a tutorial by its ID via HTTP GET request.
     * It should return HttpStatus.OK.
     */
    @Test
    void testGetTutorialById() {
        Tutorial tutorial = tutorialRepository.findAll().iterator().next();
        ResponseEntity<Tutorial> response = restTemplate.getForEntity(getRootUrl() + "/api/tutorials/" + tutorial.getId(), Tutorial.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    /**
     * Test the update of a tutorial via HTTP PUT request.
     * It should return HttpStatus.OK.
     */
    @Test
    void testUpdateTutorial() {
        long id = tutorialRepository.findAll().iterator().next().getId();
        Tutorial tutorial = restTemplate.getForObject(getRootUrl() + "/api/tutorials/" + id, Tutorial.class);
        tutorial.setTitle("Updated Title");
        tutorial.setDescription("Updated Description");

        restTemplate.put(getRootUrl() + "/api/tutorials/" + id, tutorial);

        Tutorial updatedTutorial = restTemplate.getForObject(getRootUrl() + "/api/tutorials/" + id, Tutorial.class);
        assertNotNull(updatedTutorial);
        assertEquals("Updated Title", updatedTutorial.getTitle());
    }

    /**
     * Test the deletion of a tutorial via HTTP DELETE request.
     * It should return HttpStatus.NO_CONTENT.
     */
    @Test
    void testDeleteTutorial() {
        long id = tutorialRepository.findAll().iterator().next().getId();
        restTemplate.delete(getRootUrl() + "/api/tutorials/" + id);

        Tutorial deletedTutorial = restTemplate.getForObject(getRootUrl() + "/api/tutorials/" + id, Tutorial.class);
        assertNull(deletedTutorial);
    }

}
