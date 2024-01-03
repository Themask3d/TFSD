---

# TFSD Practical work 5-8 Julien Séailles: Backend Part

## Backend Overview
The backend of the Task Management Application is developed with Spring Boot and utilizes an H2 database. It is responsible for handling all CRUD operations related to task management.

## Core Functionalities
- **CRUD Operations**: Create, Read, Update, and Delete tasks.
- **Data Persistence**: Utilizes H2 Database for storing task information.
- **RESTful API**: Provides API endpoints for interaction with the frontend.

## Project Structure
- **Controllers**: `TutorialController.java` for handling HTTP requests.
- **Models**: `Tutorial.java` representing the data model.
- **Repositories**: `TutorialRepository.java` for database interactions.
- **Tests**: Integration and unit tests to ensure functionality.

## Installation and Setup
1. **Clone the Repository**
2. **Navigate to Backend Directory**: `cd ./backend/spring-boot-h2-database-crud/`
3. **Install Dependencies and Launch Backend**:
   ```
   mvn clean install spring-boot:run
   ```

## API Documentation
API endpoints and their usage are documented in `./backend/Technical documentation/index.html`. [View Documentation](../Technical documentation/index.html)

## Testing
Contains unit and integration tests to ensure API functionality and reliability.
---