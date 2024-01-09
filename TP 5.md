# Detailed Web Project Report about TP 5

Here is the commit after this task : https://github.com/Themask3d/TFSD/commit/0538048fd70d53c44f9275df8d2547a1d22cf180

But since this was improved afterwards, you can directly use the file available here, to see the final work done : https://github.com/Themask3d/TFSD/tree/main/backend

## Introduction

This report presents a comprehensive overview of the enhancements made to the Spring Boot-based web application (I will not work on the vue.js part for this assignement), particularly focusing on the aspects of logging, testing, and static code analysis. Each section explicitly references the modified files and the specific changes implemented.

### Project Context

The application is designed for managing tutorials and is built using Spring Boot with JPA and H2 database. The primary functionalities include CRUD operations exposed via RESTful APIs.

## 1. Implementation of Logging (1/5 Points)

Logging is instrumental for tracking the application's behavior, facilitating effective debugging and monitoring. In order to do it, i have used Log4j2 to log every interactions with the backend.

### Logging Details

- **Files Modified for Logging**:
  - `TutorialController.java`: Integrated Log4j2 for logging various levels of information.
  - `Tutorial.java`: Logging added for data model operations.

- **Key Implementations**:
  - **Logger Initialization**: Used `LogManager.getLogger()` to initialize loggers in both files.
  - **Logging Levels**: Incorporated DEBUG, INFO, WARN, and ERROR levels. 
    - Example: In `TutorialController.java`, DEBUG logs entry into methods, INFO logs successful operations, WARN logs when tutorials are not found, and ERROR logs exceptions.
  - **Practical Use**: Logs help in tracing the application's flow and quickly identifying issues, particularly in `TutorialController.java` where most of the application's business logic resides.

These files can be found in the [MAIN REPOSITORY](./backend/spring-boot-h2-database-crud/src/main/java/com/bezkoder/spring/jpa/h2)

You also have access to example files of logging [HERE](./backend/spring-boot-h2-database-crud/app-log.log)

I have tried to make the logging it as normalized as possible in order to be able to read it easily.

## 2. Unit and Integration Tests (2/5 Points)

Testing is crucial for ensuring the application's functionality and stability.

### Unit Tests

- **Files Involved**:
  - `TutorialControllerTest.java`: Contains unit tests for `TutorialController`.
  
- **Testing Frameworks Used**: JUnit for testing and Mockito for mocking.
- **Coverage and Examples**:
  - The tests encompass all CRUD operations and potential edge cases.
  - For instance, `getAllTutorials_WithTitleFilter_ReturnsNonEmptyList` and `createTutorial_WithValidTutorial_ReturnsCreated` validate API responses and error handling.

### Integration Tests

- **Files Involved**:
  - `TutorialControllerIntegrationTest.java`: Implements integration testing for the application.

- **Testing Environment**: Utilizes Spring Boot Test with a random port to simulate a real environment.
- **Scope and Example**:
  - Tests validate the complete flow from HTTP requests to database interactions.
  - Test cases like `testCreateTutorial` and `testGetAllTutorials` assure that the REST endpoints and underlying database logic function as intended.

These files can be found in the [TEST REPOSITORY](./backend/spring-boot-h2-database-crud/src/test/java/com/bezkoder/spring/jpa/h2)

## 3. Static Code Analysis Configuration (2/5 Points)

Static code analysis is essential for maintaining code quality and consistency.

### Checkstyle

- **File Configured**: `pom.xml` and a custom `checkstyle.xml`.
- **Checkstyle.xml Overview:**
    - Basics: Sets standard rules for code formatting and structure.
    - Modules:
        - FileTabCharacter and LineLength outside TreeWalker for basic formatting.
        - Within TreeWalker, modules like JavadocMethod, ConstantName, MethodLength, and others enforce Java coding conventions.
        - Custom Rules: For instance, LineLength is set to a maximum of 160 characters, and MethodLength is capped at 150 lines.
- **Integration and Execution**:
  - Integrated into the Maven build lifecycle.
  - Executes during the `validate` phase, ensuring adherence to defined coding standards.

### Error Prone

- **File Configured**: `pom.xml`.
- **Implementation Details**:
  - Added as part of the Maven Compiler plugin configuration.
  - Enhances Java compilation to identify common coding mistakes.

The pom.xml file is available [HERE](./backend/spring-boot-h2-database-crud/pom.xml)

## Conclusion

In conclusion, the project has been significantly enhanced with the integration of systematic logging, comprehensive testing (both unit and integration), and rigorous static code analysis. These implementations not only improve the project's reliability and maintainability but also align it with professional software development standards. The explicit referencing of modified files and the detailed explanation of each implementation underscore the thoughtful and meticulous approach taken in this project.

Thank you for the time you have taken to go through this assignement. 

I hope that i will meet the requirements. 

Sincerly, Julien Séailles
