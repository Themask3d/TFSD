
# TP 8 CI/CD and automated tests

Since to test the workflow we need to use github, a lot of commits were made so it's kind of a mess but here are example of commits made :

https://github.com/Themask3d/TFSD/commit/521d892074a4f1f7fef7c88e89d0259ba26fd3b8

But i advise looking at the final file : https://github.com/Themask3d/TFSD/blob/main/.github/workflows/releases.yml

## Q1-5: Automating Unit Test, documentation and release

### Introduction

In the pursuit of ensuring consistent code quality and functionality in our Full stack project, we implemented a Continuous Integration (CI) workflow using GitHub Actions. This workflow is designed to automatically execute unit tests upon every commit, providing immediate feedback on the impact of code changes.

### Workflow Configuration

#### GitHub Actions Setup

We started by setting up a GitHub Actions workflow. GitHub Actions is a CI/CD platform that allows us to automate our build, test, and deployment pipeline right within GitHub.

#### Workflow File (`release.yml`)

Here is the content of the file : 

```
name: Continuous Integration and Documentation

on:
  push:
    branches:
      - '*'
    tags:
      - '*'
jobs:
  build-and-test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Set up JDK 17
        uses: actions/setup-java@v2
        with:
          java-version: '17'
          distribution: 'adopt'
      - name: Cache Maven packages
        uses: actions/cache@v2
        with:
          path: ~/.m2
          key: ${{ runner.os }}-m2-${{ hashFiles('**/pom.xml') }}
          restore-keys: ${{ runner.os }}-m2
      - name: Build with Maven
        run: mvn -B package --file pom.xml
        working-directory: ./backend/spring-boot-h2-database-crud
      - name: Run Unit Tests
        run: mvn test
        working-directory: ./backend/spring-boot-h2-database-crud

  

  generate-and-publish-docs:
    if: github.ref == 'refs/heads/master'
    runs-on: ubuntu-latest
    steps:
    - name: Checkout Repository
      uses: actions/checkout@v2

    - name: Set up JDK 17
      uses: actions/setup-java@v2
      with:
        java-version: '17'
        distribution: 'adopt'

    - name: Generate Backend Documentation
      run: |
        cd ./backend/spring-boot-h2-database-crud
        chmod +x ./mvnw
        ./mvnw javadoc:javadoc
    - name: Install Node.js
      uses: actions/setup-node@v2
      with:
        node-version: '14'

    - name: Generate Frontend Documentation
      run: |
        cd ./frontend/vue-3-crud
        npm install
        npm run jsdoc
    - name: Copy Documentation to DOCS Directory
      run: |
        mkdir -p DOCS/backend
        mkdir -p DOCS/frontend
        cp -r ./backend/spring-boot-h2-database-crud/target/site/apidocs/* DOCS/backend/
        cp -r ./frontend/vue-3-crud/docs/* DOCS/frontend/
    - name: Create Central Landing Page
      run: |
        echo '<!DOCTYPE html>
        <html>
        <head>
            <title>TFSD Practical Work 5-8: Task Management Full Stack Application Documentation</title>
        </head>
        <body>
            <h1>TFSD Practical Work 5-8: Task Management Full Stack Application Documentation</h1>
        
            <h2>Introduction</h2>
            <p>Developed by Julien Séailles, this application provides a robust platform for task management. It combines a Spring Boot backend with a Vue 3 frontend, offering a responsive and dynamic user interface.</p>
        
            <h2>Installation and Setup</h2>
            <h3>Backend</h3>
            <ul>
                <li>Clone the backend repository.</li>
                <li>Navigate to the backend directory and install dependencies with Maven.</li>
                <li>Start the Spring Boot application.</li>
                <li>The server starts on <code>http://localhost:8080</code>.</li>
                <li>Detailed setup instructions are available <a href="https://github.com/Themask3d/TFSD/blob/main/backend/spring-boot-h2-database-crud/README.md">here</a>.</li>
            </ul>
        
            <h3>Frontend</h3>
            <ul>
                <li>Clone the frontend repository.</li>
                <li>Navigate to the frontend directory and install dependencies using NPM.</li>
                <li>Start the Vue application with <code>npm run serve</code>.</li>
                <li>Access the frontend at <code>http://localhost:8081</code>.</li>
                <li>Further setup details can be found <a href="https://github.com/Themask3d/TFSD/blob/main/frontend/vue-3-crud/README.md">here</a>.</li>
            </ul>
        
            <h2>Core Functionalities</h2>
            <h3>Backend</h3>
            <ul>
                <li>Provides CRUD operations for task management.</li>
                <li>Utilizes H2 Database for data persistence.</li>
                <li>RESTful API for frontend interaction.</li>
                <li>Explore API details in the <a href="./backend/index.html">Backend API Reference</a>.</li>
            </ul>
        
            <h3>Frontend</h3>
            <ul>
                <li>User-friendly interface for task management.</li>
                <li>Implements real-time data interaction using Axios.</li>
                <li>Responsive design with Bootstrap.</li>
                <li>UI components are detailed <a href="./frontend/index.html">here</a>.</li>
            </ul>
        
            <h2>Technical Documentation</h2>
            <ul>
                <li>Backend code, such as <code>TutorialController.java</code>, can be found <a href="https://github.com/Themask3d/TFSD/blob/main/backend/spring-boot-h2-database-crud/src/main/java/com/bezkoder/spring/jpa/h2/controller/TutorialController.java">here</a>.</li>
                <li>Frontend code, including <code>AddTutorial.vue</code>, is available <a href="https://github.com/Themask3d/TFSD/blob/main/backend/spring-boot-h2-database-crud/src/main/java/com/bezkoder/spring/jpa/h2/model/Tutorial.java">here</a>.</li>
                <li>Examples of API usage and additional resources are in the tests: <a href="https://github.com/Themask3d/TFSD/tree/main/backend/spring-boot-h2-database-crud/src/test/java/com/bezkoder/spring/jpa/h2/controller">here</a>.</li>
            </ul>
        
            <h2>API Usage Examples</h2>
            <ul>
                <li><strong>Create Task</strong>: Endpoint for creating a new task. Example: <code>POST /api/tutorials</code>.</li>
                <li><strong>Update Task</strong>: Update existing tasks. Example: <code>PUT /api/tutorials/{id}</code>.</li>
                <li><strong>Delete Task</strong>: Remove a task. Example: <code>DELETE /api/tutorials/{id}</code>.</li>
            </ul>
        
            <h2>Contributing</h2>
            <p>Contributors are welcome to fork the repository, create feature branches, and submit pull requests. Contribution guidelines are available <a href="https://github.com/Themask3d/TFSD/blob/main/README.md">here</a>.</p>
        
            <h2>License</h2>
            <p>This project is under the MIT License, detailed in the <a href="https://github.com/Themask3d/TFSD/blob/main/LICENSE.md">LICENSE</a> file.</p>
        
            <h2>Contact</h2>
            <p>Julien Séailles: <a href="https://github.com/Themask3d">Themask3d</a></p>
            <p>Project Repository: <a href="https://github.com/Themask3d/TFSD">TFSD Full Stack Project</a></p>
        
            <hr>
        </body>
        </html>' > DOCS/index.html
    - name: Deploy to GitHub Pages
      uses: peaceiris/actions-gh-pages@v3
      with:
        github_token: ${{ secrets.GITHUB_TOKEN }}
        publish_dir: ./DOCS

  create-release:
    needs: build-and-test
    if: startsWith(github.ref, 'refs/tags/')
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      
      - name: Generate Changelog
        id: changelog
        uses: mikepenz/release-changelog-builder-action@v2
        env:
          GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
          
      - name: Create GitHub Release
        id: create_release
        uses: actions/create-release@v1
        env:
          GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
        with:
          tag_name: ${{ github.ref_name }}
          release_name: Release ${{ github.ref_name }}
          body: ${{ steps.changelog.outputs.changelog }}
          draft: false
          prerelease: false
          
      - name: Upload Artifacts
        uses: actions/upload-artifact@v2
        with:
          name: release-artifacts
          path: |
            backend/target/*.jar
            frontend/dist/*
            combined-docs/
```

The workflow is defined in a file named `release.yml`, located in the `.github/workflows` directory of our repository. Here's an overview of the file's content and functionalities:

The GitHub Actions workflow script `release.yaml` is meticulously designed to automate several key aspects of the Continuous Integration/Continuous Deployment (CI/CD) pipeline for the TFSD Web application. This automation is crucial for maintaining code quality and streamlining the release process. The script addresses four main requirements as outlined in the assignment:

#### 1. Automate the Execution of Unit Tests on Every Commit (1/5 points)

The `build-and-test` job within the workflow is responsible for this requirement. Whenever a commit is pushed to any branch in the repository, this job is triggered. It performs the following functions:

- **Set up JDK 17**: Initializes the Java Development Kit 17 environment, necessary for building and testing Java applications.
- **Cache Maven Packages**: Optimizes the build process by caching dependencies, reducing build time on subsequent runs.
- **Build with Maven**: Compiles the code and builds the application using Maven, a software project management tool.
- **Run Unit Tests**: Executes unit tests located in the `./backend/spring-boot-h2-database-crud` directory. This step ensures that all tests pass before the changes are merged into the codebase, maintaining the integrity of the application.

#### 2. Automate the Creation of a Release When a Tag is Created (2/5 points)

The `create-release` job meets this requirement. It is conditioned to run only when a new tag is pushed to the repository, ensuring that a release is created only after the tests have passed successfully. The steps involved are:

- **Changelog Generation**: Utilizes the `mikepenz/release-changelog-builder-action` to automatically generate a changelog from the git history since the last tag. This provides a concise summary of changes and new features in the release.
- **GitHub Release Creation**: Uses the `actions/create-release` action to create a new release on GitHub. The release includes the tag name, release name, and the dynamically generated changelog.
- **Artifact Upload**: Artifacts such as compiled JAR files from the backend and frontend distribution files are uploaded with the release, making them readily available for deployment or distribution.

#### 3. Automatically Generate the Changelog Based on Git History (1/5 points)

This functionality is embedded within the `create-release` job. The `release-changelog-builder-action` automatically generates a changelog based on commits and pull requests merged since the last tag. This automation enhances the visibility of changes for developers and stakeholders.

#### 4. Automate the Publication of Documentation on the Master Branch Only (1/5 points)

The `generate-and-publish-docs` job addresses this requirement. It is specifically designed to trigger only when changes are pushed to the `master` branch. The key steps include:

- **Generation of Backend and Frontend Documentation**: Runs scripts to generate Javadoc for the backend and JsDoc for the frontend.
- **Creation of a Central Landing Page**: A custom HTML landing page is dynamically created, providing a centralized access point to all documentation.
- **Deployment to GitHub Pages**: Utilizes `peaceiris/actions-gh-pages` to publish the compiled documentation to GitHub Pages, making the documentation publicly accessible.

You can see the documentation [here](https://themask3d.github.io/TFSD/)

In summary, the `release.yaml` GitHub Actions workflow script for the TFSD Web application effectively automates critical aspects of the development pipeline. From ensuring code quality with automated unit tests on every commit, to streamlining the release process with automated changelog generation and artifact publishing, and finally ensuring up-to-date documentation on the master branch, this workflow significantly enhances the efficiency and reliability of the development process.

After having created the file i thoroughly tested it in order to see if we match the inteded behaviour:

## Testing Procedure for GitHub Actions Workflow in "TFSD" Repository

As part of the evaluation of the continuous integration pipeline implemented in the "TFSD" repository, a comprehensive testing procedure was conducted. This procedure aimed to verify the correct functioning of the GitHub Actions workflows under various scenarios, including code commits, tag creation, and branch merging.

### Step-by-Step Testing Process 

1. **Repository Cloning**:
   The "TFSD" repository was cloned to a local environment using the command:
   ```
   git clone https://github.com/Themask3d/TFSD.git
   cd TFSD
   ```

2. **Branch Creation for Testing**:
   A new branch named `test-branch` was created to ensure that the main branch remains unaffected during the testing process:
   ```
   git checkout -b test-branch
   ```

3. **Implementation of Minor Code Changes**:
   A minor modification was introduced to create a new commit. This step is crucial for simulating a typical development change:
   ```
   echo "// Test change" >> testfile.txt
   ```

4. **Committing and Pushing Changes**:
   The changes were committed and pushed to the remote repository, which triggered the `build-and-test` job in the workflow:
   ```
   git add testfile.txt
   git commit -m "Test: minor change"
   git push origin test-branch
   ```

Here we should only run unit tests : https://github.com/Themask3d/TFSD/actions/runs/7454307903

5. **Tag Creation for Release Simulation**:
   A tag `v1.0-test` was created and pushed to simulate a release. This action was intended to trigger workflows associated with releases:
   ```
   git tag -a v1.0-test -m "Test tag v1.0"
   git push origin v1.0-test
   ```

Here we should have unit test and release : https://github.com/Themask3d/TFSD/actions/runs/7454321603

6. **Optional: Merging into Master Branch (here mine is main)**:
   The `test-branch` was optionally merged into the master branch to test any master branch-specific workflows, such as documentation deployment:
   ```
   git checkout main
   git merge test-branch
   git push
   ```

Here we should only have docs and unit tests : https://github.com/Themask3d/TFSD/actions/runs/7454397538

7. **Workflow Monitoring**:
   The GitHub Actions tab in the repository was monitored after each push to observe the execution of workflows.

8. **Output Verification**:
   - The logs were reviewed to confirm the successful execution of unit tests.
   - The GitHub Pages site was checked for updates in documentation.
   - The "Releases" section was inspected to verify the creation of a new release with the appropriate artifacts and changelog.

9. **Repository Clean-Up**:
   Post-testing, the test branch and tag were removed to maintain repository cleanliness:
   ```
   git branch -d test-branch
   git push origin --delete test-branch
   git push origin --delete v1.0-test
   ```

### Conclusion

This methodical approach ensured a thorough examination of the continuous integration process. The tests conducted provided valuable insights into the functionality and robustness of the implemented GitHub Actions workflows, ensuring that the "TFSD" repository adheres to best practices in automated software development processes.

Thank you for the time you have taken to go through this work. i hope that you enjoyed it.
