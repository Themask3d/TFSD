# TFSD Practical work 5-8 Julien Séailles: Task Management Full Stack Application

All the TP report are available here :

* [TP 5](./TP5.md)

* [TP 6](./TP6.md)

* [TP 7](./TP7.md)

* [TP 8](./TP8.md)

## Description
Developed by Julien Séailles, a student at the Mines de Saint Etienne enrolled in a computer science course, this full-stack application serves as a task management tool. Users can create, manage, and track tasks, with features to add descriptions and update the status for progress tracking. The backend is implemented using Spring Boot with an H2 database, offering robust and flexible data management. The frontend is built with Vue 3, providing a dynamic and responsive user interface.

This project combines and extends functionalities from two open-source projects:
- Frontend forked from: [bezkoder/vue-3-crud](https://github.com/bezkoder/vue-3-crud)
- Backend forked from: [bezkoder/spring-boot-h2-database-crud](https://github.com/bezkoder/spring-boot-h2-database-crud)

The project aims to deliver a hands-on experience in full-stack development, CI/CD, Dockerization, testing, production deployment, and understanding of licensing in software development.

## Core Functionalities
- **Task Creation and Management**: Users can add new tasks with titles and descriptions. Tasks can be updated or deleted as needed.
- **Task Status Tracking**: Each task can be marked as 'Published' or 'Pending', allowing users to track the progress of their tasks.
- **Search Functionality**: Users can search for tasks based on their titles.
- **Responsive Design**: The application is designed to be responsive and user-friendly, adapting to different screen sizes and devices.

## Technologies Used
- **Backend**: Spring Boot, H2 Database
- **Frontend**: Vue 3, Bootstrap for styling
- **API Communication**: Axios for HTTP requests

## Installation

### Prerequisites (Suggested configuration)
- Java Development Kit (JDK 17)
- Node.js (V.21.0.5) and npm (Node Package Manager)
- Docker (optional, for containerization)
- Maven for dependencies management
### Backend Setup
1. Clone the backend repository from the provided GitHub link.
2. Follow the instructions in the `README.md` file to set up the Spring Boot application.

#### Navigate to backend packages
````
cd ./backend/spring-boot-h2-database-crud/
````
#### Install dependencies using maven and launch backend
````
mvn clean install spring-boot:run
````

### Frontend Setup
1. Clone the frontend repository from the provided GitHub link.
2. Follow the `README.md` file for further setup and running the Vue 3 application.

#### Navigate to Frontend packages
````
cd ./frontend/vue-3-crud
````
#### Install dependencies using nvm,node,npm and launch frontend
````
nvm node install
````

````
npm install
````

````
export NODE_OPTIONS=--openssl-legacy-provider
````

````
npm run serve
````

## Usage
After setting up both the backend and frontend, the application is ready for creating and managing tasks. For detailed instructions, refer to the `README.md` files in the frontend and backend directories.

## Usage with docker

### Running Elements Separately in Docker

#### Backend (Spring Boot Application)

1. **Navigate to the Backend Directory**:
   ```
   cd ./backend/spring-boot-h2-database-crud
   ```

2. **Build the Docker Image**:
   ```
   docker build -t my-spring-boot-app .
   ```

3. **Run the Docker Container on a Custom Port**:
   ```
   docker run -e SERVER_PORT=9090 -p 9090:9090 my-spring-boot-app
   ```

   This will start your backend application on port 9090.

#### Frontend (Vue.js Application)

1. **Navigate to the Frontend Directory**:
   ```
   cd ./frontend/vue-3-crud
   ```

2. **Build the Docker Image**:
   ```
   docker build -t my-vue-app .
   ```

3. **Run the Docker Container on Port 8081**:
   ```
   docker run -p 8081:8081 -e PORT=8081 -e BACKEND=http://localhost:9090 my-vue-app
   ```

   This starts the frontend and attempts to connect it to the backend on port 9090.

### Running Backend and Frontend with Docker Compose

#### Build and Run with Docker Compose

1. **Build the Images**:
   Navigate to the root directory where your `docker-compose.yml` file is located and run the following command:
   ```
   docker-compose build
   ```
   This command will build the images for both the backend and frontend services as defined in the Docker Compose file.

2. **Start the Services**:
   ```
   docker-compose up
   ```
   This command will start the backend and frontend services. The frontend should now be able to communicate with the backend through the specified ports (not the case here after 7 hours of search).


## Contributing
Contributions are welcome. Please follow these steps:
1. Fork the repository.
2. Create a new branch for your feature.
3. Commit your changes with clear messages.
4. Push the branch to your fork.
5. Submit a pull request.

Open an issue first for major changes.

## Technical Documentation
- Each component and service in the codebase is documented to explain its functionality and usage.
- Comments are used to clarify complex logic.

## License
Licensed under the [MIT License](LICENSE). See `LICENSE` for details.

## Contact
- Julien Séailles - [Themask3d](https://github.com/Themask3d)
- Project Link: [FULL STACK PROJECT](https://github.com/Themask3d/TFSD)
  
