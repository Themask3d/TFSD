# Use OpenJDK 17 as the base image for Java runtime
FROM openjdk:17

# Metadata: Maintainer Information
LABEL maintainer="julien.seailles@etu.emse.fr"

# Declare a volume for temporary storage
VOLUME /tmp

# Environment variable for the port with a default value
ENV SERVER_PORT=8080

# Expose the port defined in the environment variable
EXPOSE ${SERVER_PORT}

# Path to the application's jar file
ARG JAR_FILE=target/*.jar

# Incorporate the application's jar into the container
ADD ${JAR_FILE} app.jar

# Command to run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dserver.port=${SERVER_PORT}","-jar","/app.jar"]
