
## Q1: Dockerfile analysis

The different readme related to this task are here : https://github.com/Themask3d/TFSD/commit/03b671fb49dd6dfb49b297a14130a1c8e5f72a19

But you can use the recent version of this file availble here : https://github.com/Themask3d/TFSD/blob/main/README.md

And the description below that explain how i made this work.

### Analysis of Dockerfile and docker-entrypoint.sh

#### Dockerfile

The Dockerfile given in this assignement is a recipe for creating a Docker image. This image is tailored for running the Eclipse Mosquitto MQTT Broker, a popular message broker used in various IoT and real-time data streaming applications. Let's break down the key components of the Dockerfile:

1. **Base Image:**
   ```FROM alpine:3.18```
   The image is based on Alpine Linux version 3.18, known for its small footprint and security-focused design. This choice suggests an emphasis on creating a lightweight and secure container.

2. **Metadata:**
   ```
   LABEL maintainer="Roger Light <roger@atchoo.org>" \
         description="Eclipse Mosquitto MQTT Broker"
   ```
   These lines provide metadata about the image, indicating the maintainer and a brief description.

3. **Environment Variables:**
    ```
    ENV VERSION=2.0.18 \
        DOWNLOAD_SHA256=d665fe7d0032881b1371a47f34169ee4edab67903b2cd2b4c083822823f4448a \
        GPG_KEYS=A0D6EEA1DCAE49A635A3B2F0779B22DFB3E717B7 \
        LWS_VERSION=4.2.1 \
        LWS_SHA256=842da21f73ccba2be59e680de10a8cce7928313048750eb6ad73b6fa50763c51
    ```

   Several environment variables (`VERSION`, `DOWNLOAD_SHA256`, `GPG_KEYS`, `LWS_VERSION`, `LWS_SHA256`) are declared. These variables store version numbers, SHA256 hashes for verification, and GPG keys for security checks, ensuring that the downloaded files are secure and unaltered.

4. **Dependencies and Building:**

    * **Setting Shell Options and installing Build Dependencies:**
    ```
    RUN set -x && \
    apk --no-cache add --virtual build-deps \
        build-base \
        cmake \
        cjson-dev \
        gnupg \
        libressl-dev \
        linux-headers \
        util-linux-dev && \
    ```
    - `set -x`: This command activates debugging in the shell, making it print each command before executing it. This is useful for troubleshooting and understanding the build process.
    - `apk --no-cache add --virtual build-deps`: Installs necessary packages for building the software. 
        - `build-base`: A meta-package that includes essential development tools like GCC.
        - `cmake`: A cross-platform build system.
        - `cjson-dev`: Development files for cJSON, a lightweight JSON parser in C.
        - `gnupg`: GnuPG for cryptographic operations.
        - `libressl-dev`: Development files for LibreSSL, a version of the SSL/TLS protocol forked from OpenSSL.
        - `linux-headers`: Kernel headers needed for compiling modules like device drivers.
        - `util-linux-dev`: Development files for various essential utilities.

    * **Downloading and Verifying libwebsockets:**
    ```
    wget https://github.com/warmcat/libwebsockets/archive/v${LWS_VERSION}.tar.gz -O /tmp/lws.tar.gz && \
    echo "$LWS_SHA256  /tmp/lws.tar.gz" | sha256sum -c - && \
    ```
    - The script downloads a specific version of `libwebsockets` (as specified in `LWS_VERSION`), a lightweight C library for creating web servers, which is necessary for Mosquitto's WebSocket support.
    - `echo "$LWS_SHA256  /tmp/lws.tar.gz" | sha256sum -c -`: Verifies the integrity of the downloaded file using SHA256 checksums to ensure it hasn't been tampered with.

    * **Building libwebsockets:**
    ```
    mkdir -p /build/lws && \
    tar --strip=1 -xf /tmp/lws.tar.gz -C /build/lws && \
    rm /tmp/lws.tar.gz && \
    cd /build/lws && \
    cmake . \
        -DCMAKE_BUILD_TYPE=MinSizeRel \
        -DCMAKE_INSTALL_PREFIX=/usr \
        -DDISABLE_WERROR=ON \
        -DLWS_IPV6=ON \
        -DLWS_WITHOUT_BUILTIN_GETIFADDRS=ON \
        -DLWS_WITHOUT_CLIENT=ON \
        -DLWS_WITHOUT_EXTENSIONS=ON \
        -DLWS_WITHOUT_TESTAPPS=ON \
        -DLWS_WITH_EXTERNAL_POLL=ON \
        -DLWS_WITH_HTTP2=OFF \
        -DLWS_WITH_SHARED=OFF \
        -DLWS_WITH_ZIP_FOPS=OFF \
        -DLWS_WITH_ZLIB=OFF && \
    make -j "$(nproc)" && \
    rm -rf /root/.cmake && \
    ```
    - `mkdir -p /build/lws` creates a directory for the build.
    - The tar command extracts the `libwebsockets` source code.
    - `cmake .` along with various `-D` options configures the build environment. These options disable unnecessary components (like test apps and built-in getifaddrs) and optimize the build for a smaller size (`MinSizeRel`).

    * **Downloading, Verifying, and Building Mosquitto:**
    ```
    wget https://mosquitto.org/files/source/mosquitto-${VERSION}.tar.gz -O /tmp/mosq.tar.gz && \
    echo "$DOWNLOAD_SHA256  /tmp/mosq.tar.gz" | sha256sum -c - && \
    wget https://mosquitto.org/files/source/mosquitto-${VERSION}.tar.gz.asc -O /tmp/mosq.tar.gz.asc && \
    export GNUPGHOME="$(mktemp -d)" && \
    found=''; \
    for server in \
        hkps://keys.openpgp.org \
        hkp://keyserver.ubuntu.com:80 \
        pgp.mit.edu \
    ; do \
        echo "Fetching GPG key $GPG_KEYS from $server"; \
        gpg --keyserver "$server" --keyserver-options timeout=10 --recv-keys "$GPG_KEYS" && found=yes && break; \
    done; \
    test -z "$found" && echo >&2 "error: failed to fetch GPG key $GPG_KEYS" && exit 1; \
    gpg --batch --verify /tmp/mosq.tar.gz.asc /tmp/mosq.tar.gz && \
    gpgconf --kill all && \
    rm -rf "$GNUPGHOME" /tmp/mosq.tar.gz.asc && \
    mkdir -p /build/mosq && \
    tar --strip=1 -xf /tmp/mosq.tar.gz -C /build/mosq && \
    rm /tmp/mosq.tar.gz && \
    make -C /build/mosq -j "$(nproc)" \
        CFLAGS="-Wall -O2 -I/build/lws/include -I/build" \
        LDFLAGS="-L/build/lws/lib" \
        WITH_ADNS=no \
        WITH_DOCS=no \
        WITH_SHARED_LIBRARIES=yes \
        WITH_SRV=no \
        WITH_STRIP=yes \
        WITH_TLS_PSK=no \
        WITH_WEBSOCKETS=yes \
        prefix=/usr \
        binary && \
    ```
    - Similar steps are followed for Mosquitto: downloading the source, verifying its integrity, and then building it.
    - GPG keys are fetched for additional security verification of the downloaded tarball (`mosquitto-${VERSION}.tar.gz`).
    - `make -C /build/mosq -j "$(nproc)"` compiles Mosquitto with specified flags (`CFLAGS` and `LDFLAGS`) and configurations. The use of `nproc` optimizes the build process by using all available CPU cores.

    * **Final Setup Steps:**
    ```
    addgroup -S -g 1883 mosquitto 2>/dev/null && \
    adduser -S -u 1883 -D -H -h /var/empty -s /sbin/nologin -G mosquitto -g mosquitto mosquitto 2>/dev/null && \
    mkdir -p /mosquitto/config /mosquitto/data /mosquitto/log && \
    install -d /usr/sbin/ && \
    install -s -m755 /build/mosq/client/mosquitto_pub /usr/bin/mosquitto_pub && \
    install -s -m755 /build/mosq/client/mosquitto_rr /usr/bin/mosquitto_rr && \
    install -s -m755 /build/mosq/client/mosquitto_sub /usr/bin/mosquitto_sub && \
    install -s -m644 /build/mosq/lib/libmosquitto.so.1 /usr/lib/libmosquitto.so.1 && \
    install -s -m755 /build/mosq/src/mosquitto /usr/sbin/mosquitto && \
    install -s -m755 /build/mosq/apps/mosquitto_ctrl/mosquitto_ctrl /usr/bin/mosquitto_ctrl && \
    install -s -m755 /build/mosq/apps/mosquitto_passwd/mosquitto_passwd /usr/bin/mosquitto_passwd && \
    install -s -m755 /build/mosq/plugins/dynamic-security/mosquitto_dynamic_security.so /usr/lib/mosquitto_dynamic_security.so && \
    install -m644 /build/mosq/mosquitto.conf /mosquitto/config/mosquitto.conf && \
    install -Dm644 /build/lws/LICENSE /usr/share/licenses/libwebsockets/LICENSE && \
    install -Dm644 /build/mosq/epl-v20 /usr/share/licenses/mosquitto/epl-v20 && \
    install -Dm644 /build/mosq/edl-v10 /usr/share/licenses/mosquitto/edl-v10 && \
    chown -R mosquitto:mosquitto /mosquitto && \
    apk --no-cache add \
        ca-certificates \
        cjson \
        libressl && \
    apk del build-deps && \
    rm -rf /build
    ```
    - Creation of the `mosquitto` user and group to run the service non-root.
    - Installation of Mosquitto binaries and libraries into the container, as well as configuration and license files.
    - Setting ownership of the `/mosquitto` directory to the `mosquitto` user and group for security reasons.
    - Addition of runtime dependencies like `ca-certificates`, `cjson`, and `libressl`.
    - Removal of build dependencies and cleaning up the build directory to reduce the image size.
    
5. **Volumes:**
   ```
   VOLUME ["/mosquitto/data", "/mosquitto/log"]
   ```
   Designates persistent storage for Mosquitto data and log directories, ensuring data persistence across container restarts.

6. **Networking:**
   ```
   EXPOSE 1883
   ```
   Exposes port 1883, the default port for MQTT, making it accessible to other services and networks.

7. **Entry Point and Default Command:**
    ```
    ENTRYPOINT ["/docker-entrypoint.sh"]
    CMD ["/usr/sbin/mosquitto", "-c", "/mosquitto/config/mosquitto.conf"]
    ```
    Sets the container to execute `docker-entrypoint.sh` on startup, and the default command runs the Mosquitto broker with the provided configuration.

#### docker-entrypoint.sh

This shell script is executed every time the container starts. It performs the following functions:

1. **Permission Setting:**
```
#!/bin/ash
set -e

# Set permissions
user="$(id -u)"
if [ "$user" = '0' ]; then
	[ -d "/mosquitto" ] && chown -R mosquitto:mosquitto /mosquitto || true
fi```
   Checks if the script is executed as the root user (`id -u` equals '0'). If so, it changes the ownership of the `/mosquitto` directory to the `mosquitto` user and group. This step is crucial for maintaining proper file permissions, especially for volume-mounted directories.

2. **Execution Delegation:**
```
exec "$@"
```
   `exec "$@"` passes control to the CMD specified in the Dockerfile (i.e., starting the Mosquitto broker), ensuring that Mosquitto becomes the main process in the container.

This Dockerfile and the accompanying `docker-entrypoint.sh` script create a secure, efficient, and lightweight environment for running the Eclipse Mosquitto MQTT Broker. The Dockerfile meticulously builds Mosquitto from source, ensuring all components are up-to-date and secure, while the entrypoint script manages runtime permissions, enhancing the container's security posture. The use of Alpine Linux as the base image underlines a commitment to minimalism and security.

## Q2: Dockerfile for backend:

## Dockerization of the Spring Boot Application

In the endeavor to encapsulate and streamline the deployment of our task management application, a Dockerfile has been meticulously crafted for the backend component. This Dockerfile plays a crucial role in building a Docker image from the Spring Boot application's `.jar` file, with an added emphasis on configurability and flexibility.

### Dockerfile Composition

The Dockerfile is constructed with the following specifications, designed to optimize the deployment and operation of our application:

```
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
```

### Prerequisites for Dockerization

To ensure a smooth deployment process, verify the installation of Docker on your system with the following command:

```
docker -v
```

### Executing the Dockerization Process

1. **Accessing the Backend Directory:**
   Begin by navigating to the backend directory of our application:

   ```
   cd ./backend/spring-boot-h2-database-crud
   ```

2. **Building the Docker Image:**
   Use this command to create a Docker image named `my-spring-boot-app`, which encompasses our application:

   ````
   docker build -t my-spring-boot-app .
   ```

3. **Running the Docker Container with Configurable Port:**
   The following command launches the application in a Docker container, with the ability to configure the port:

   for example to run on port 9090:
   ```
   docker run -e SERVER_PORT=9090 -p 9090:9090 my-springboot-app
   ```

   And on 8080:
   ```
   docker run -e SERVER_PORT=8080 -p 8080:8080 my-springboot-app
   ```


The Dockerization of our Spring Boot application signifies a significant advancement in our deployment strategy. This approach not only enhances the portability and consistency of the application across various environments but also introduces an element of configurability, allowing the deployment port to be dynamically set. The adoption of containerization technology underscores our commitment to modern, efficient, and scalable application deployment methodologies.

## Q3: Dockerfile for the Frontend

### Dockerization of the Vue.js Application

In our continuous efforts to streamline and modernize our application deployment, we have now Dockerized the frontend of our task management application using Vue.js. The process involves creating a Dockerfile, a Nginx configuration template, and an entrypoint script, ensuring that our frontend is not only containerized but also highly configurable, particularly in terms of the port on which it runs.

I first navigated toward the directory : 

```
cd ./frontend/vue-3-crud
```

Dockerfile Composition
The Dockerfile for the frontend is constructed with a two-stage build process:

Then i created the following dockerfile : 

```
# Build stage
FROM node:16 as build-stage
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY ./ .
RUN npm run build

# Production Stage
FROM nginx:alpine
COPY --from=build-stage /app/dist /usr/share/nginx/html
COPY nginx.conf.template /etc/nginx/conf.d/default.conf.template
COPY docker-entrypoint.sh /usr/local/bin/
RUN chmod +x /usr/local/bin/docker-entrypoint.sh
ENTRYPOINT ["docker-entrypoint.sh"]
```

Key Steps:

The initial stage uses a Node.js environment to build the Vue.js application.
The second stage uses Nginx to serve the static files generated by the build process.

### Nginx Configuration Template
A nginx.conf.template file was created to facilitate the use of environment variables for configuration:

```
server {
    listen       ${PORT};
    server_name  localhost;

    root   /usr/share/nginx/html;
    index  index.html;

    location / {
        try_files $uri $uri/ =404;
    }
}

```

This template allows for dynamic configuration of the Nginx server, particularly the listening port, which is vital for deployment flexibility.

The created a .dockerignore file

```
**/node_modules
**/dist
```

Docker Entrypoint Script
The docker-entrypoint.sh script enables the dynamic setting of the port using the envsubst command:

````
#!/bin/sh

# Replace the PORT in the Nginx configuration
envsubst '$PORT' < /etc/nginx/conf.d/default.conf.template > /etc/nginx/conf.d/default.conf

# Function to replace backend URL and display the file name if a replacement was made
replace_backend_url() {
    local file=$1
    if sed -i 's|http://localhost:8080|http://localhost:'"$BACKEND"'|g' "$file"; then
        echo "Replaced Backend URLs in $file"
    fi
}

replace_frontend_url() {
    local file=$1
    if sed -i 's|http://localhost:8081|http://localhost:'"$PORT"'|g' "$file"; then
        echo "Replaced Frontend URLs in $file"
    fi
}

# Loop through all .js and .map files in the dist/js directory
for file in /usr/share/nginx/html/js/*.js  /usr/share/nginx/html/js/*.map; do
  replace_backend_url "$file"
done

# Loop through all .js and .map files in the dist/js directory
for file in /usr/share/nginx/html/js/*.js /usr/share/nginx/html/js/*.map; do
  replace_frontend_url "$file"
done

echo "Backend running on http://localhost:${BACKEND}"
echo "Frontend running on http://localhost:${PORT}"

# Start Nginx
exec nginx -g 'daemon off;'
```


This script is pivotal in ensuring that the environment variable PORT is effectively used to configure the Nginx server at runtime.


### Building and Running the Docker Image

1. Building the Docker Image:
    The image is built using the command:

    ```
    docker build -t my-vue-app .
    ```

2. Running the Docker Container:
    The container can be run on the default port (8081) or any other port as desired:

    ```
    docker run -p 8081:8081 -e PORT=8081 -e BACKEND=http://localhost:8080 my-vue-app
    ```

    To launch it on another port it works like this:

    ```
    docker run -p 8082:8082 -e PORT=8082 -e BACKEND=http://localhost:8080 my-vue-app
    ```
    Launching on another port for the frontend will create all the links but the connexion with database and CRUD features won't work (i did not find a solution for it). But if you launch the backend on another port there is no problem on port 8081 for frontend.


If you want to try in 2 terminal you can run the following sequence : 

```
cd ./backend/spring-boot-h2-database-crud
docker build -t my-spring-boot-app .
docker run -e SERVER_PORT=9090 -p 9090:9090 my-springboot-app
```

This will launch backend on port 9090, now you need to launch front on 8081 in another terminal:

```
cd ./frontend/vue-3-crud
docker build -t my-vue-app .
docker run -p 8081:8081 -e PORT=8081 -e BACKEND=http://localhost:9090 my-vue-app
```

Now you can play with the app.


The Dockerization of our Vue.js frontend represents a significant stride in our deployment approach. This method not only ensures the uniformity of our application across various environments but also introduces an element of configurability and scalability. Through Docker, we are able to deploy our application efficiently, maintaining consistency and flexibility, particularly in a microservices architecture.

After spending 7 hours and a half trying to understand why i did not find the reason and a workaround why i cannot use the app properly when running backend wherever i want (this works), running the front wherever i want (it works) but i cannot make them communicate properly if not on port 8081. Hence i am unable to run the muticontainer docker.

Since i haven't designed the code, it's just a loss of time for me to find out how i can correct this. During this course i have asked if i could do it in another language (python) but this was rejected. Because of that i have used the fork that was given below the todo's. this was hard to run (because all the dependencies that evolved for such an old project (2017)) when trying to follow the requirements of the assignement a lot of issues link to the fact that i have never done java before + this code was designed using old code and frameworks that i have never heard about.

This assignement would have been much more usefull if it was in a programming language that i have choosen, on a project that was far more intrusting than just trying to stick pieces together.

## Question 4: Running all at once 

For this i created .env file containing the port for front and back.

.env:

```
BACKEND_PORT=8080
FRONTEND_PORT=8081
```

```
version: '3.8'
services:
  backend:
    build:
      context: ./backend/spring-boot-h2-database-crud
      dockerfile: Dockerfile
    ports:
      - "${BACKEND_PORT}:${BACKEND_PORT}"
    environment:
      - SERVER_PORT=${BACKEND_PORT}
    volumes:
      - ./backend/spring-boot-h2-database-crud:/app

  frontend:
    build:
      context: ./frontend/vue-3-crud
      dockerfile: Dockerfile
    ports:
      - "${FRONTEND_PORT}:${FRONTEND_PORT}"
    environment:
      - PORT=${FRONTEND_PORT}
      - BACKEND=http://backend:${BACKEND_PORT}
```

after this i have used :

```
docker-compose build
```

and then i have used : 

```
docker-compose up
```

The app launches, the backend too but unable to communicate for no reasons that i have found (should be the same issue than the frontend and backend not communicating when not on localhost:8081). I have already lost more than 7 hours dealing with these issues. I am starting to get mad so i will stop here.

Thank you for the time you have taken to go through this work, the next steps of the assignement are part of the project on my github.
