package com.bezkoder.spring.jpa.h2.model;

import jakarta.persistence.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Represents a tutorial entity.
 * This class maps to a database table and includes information about a tutorial.
 */
@Entity
@Table(name = "tutorials")
public class Tutorial {

  /**
   * Represents a tutorial id.
   * This is the unique identifier for the tutorial.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  /**
   * Represents a tutorial title.
   * This is the one of the feature for a tutorial.
   */
  @Column(name = "title")
  private String title;

  /**
   * Represents a tutorial description.
   * This is the one of the feature for a tutorial.
   */
  @Column(name = "description")
  private String description;

  /**
   * Represents a tutorial status.
   * This is the one of the information for a tutorial.
   */
  @Column(name = "published")
  private boolean published;

  /**
   * Default constructor for JPA.
   */
  public Tutorial() {

  }

  /**
   * Constructs a new Tutorial with the specified details.
   *
   * @param title The title of the tutorial.
   * @param description The description of the tutorial.
   * @param published The published status of the tutorial.
   */
  public Tutorial(String title, String description, boolean published) {
    this.title = title;
    this.description = description;
    this.published = published;
  }

  /**
   * Gets the ID of the tutorial.
   *
   * @return The ID of the tutorial.
   */
  public long getId() {
    return id;
  }

  /**
   * Gets the title of the tutorial.
   *
   * @return The title of the tutorial.
   */
  public String getTitle() {
    return title;
  }

  /**
   * Sets the title of the tutorial.
   *
   * @param title The new title of the tutorial.
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Gets the description of the tutorial.
   *
   * @return The description of the tutorial.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the description of the tutorial.
   *
   * @param description The new description of the tutorial.
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Checks if the tutorial is published.
   *
   * @return True if the tutorial is published, false otherwise.
   */
  public boolean isPublished() {
    return published;
  }

  /**
   * Sets the published status of the tutorial.
   *
   * @param isPublished The new published status of the tutorial.
   */
  public void setPublished(boolean isPublished) {
    this.published = isPublished;
  }

  /**
   * Returns a string representation of the tutorial.
   *
   * @return A string representation of the tutorial.
   */
  @Override
  public String toString() {
    return "Tutorial [id=" + id + ", title=" + title + ", desc=" + description + ", published=" + published + "]";
  }

}
