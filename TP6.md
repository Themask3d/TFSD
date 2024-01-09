

### Project Report: TP6

This was one of the commit of this assignement : https://github.com/Themask3d/TFSD/commit/be4e9e6368a98a517f68db7420fdaa616ee06310#diff-b335630551682c19a781afebcf4d07bf978fb1f8ac04c6bf87428ed5106870f5

and other ones : https://github.com/Themask3d/TFSD/commit/dea2dcc2d50112b6733b82e1eaeb89fa996b6642

full commit of TP5 and 6 : https://github.com/Themask3d/TFSD/commit/33efde5a3d9d995921ce673851d720cb24d11e03

But for a more accurate snapshot of the improvement made and the final result that is of better quality, you can use all the ressources provided below and links.

#### By Julien Séailles

---

#### 1. FOSS License Declaration (0.5/5 points)

**Action Taken:**
- Selected the MIT License, a widely acknowledged and permissive open-source license, for this project. This choice was motivated by the flexibility and openness it offers to users and developers.
- Added the license file to the project's root directory. This file clearly states the permissions granted, including unrestricted use, modification, distribution, and sublicensing of the software.
- The license file explicitly includes a liability waiver and a warranty disclaimer, protecting the author from potential claims while ensuring users are aware of the terms.
- This step ensures legal clarity, encourages open-source collaboration, and respects intellectual property rights.

**Details from the License File:**
- The license grants "free of charge" rights to any person obtaining a copy of the software and its documentation.
- It allows for use, copying, modification, merging, publishing, distributing, sublicensing, and selling of the software.
- The conditions include the inclusion of the copyright notice and permission notice in all copies or substantial portions of the Software.
- Emphasizes that the software is provided "as is" without any warranty of any kind, either expressed or implied.

The license is available [HERE](https://github.com/Themask3d/TFSD/blob/main/LICENSE)

#### 2. Canonical README File (1/5 points)

**Action Taken:**
- Composed a detailed and structured README file, following canonical guidelines. The README serves as the first point of contact for users and developers, offering an overview of the project and guiding them through installation, setup, and usage.
- The README begins with a succinct description of the project, positioning it as a full-stack task management application developed by Julien Séailles for a computer science course. This sets the context and introduces the purpose of the project.
- Detailed the project's core functionalities, such as task creation, management, status tracking, and a responsive design that adapts to different devices. This section gives users a clear understanding of what the application offers.
- Outlined the technological stack: Spring Boot for the backend, Vue 3 for the frontend, H2 Database for data management, Bootstrap for styling, and Axios for API communication. This information is crucial for developers interested in the technical specifics.
- Provided comprehensive installation instructions for both the backend and frontend, including prerequisites and step-by-step guidance. This ensures users can easily set up the application.
- Described the usage of Docker for running the application, offering an alternative for users who prefer containerized environments.
- Included guidelines for contributing to the project, encouraging open-source collaboration and detailing the process for submitting changes.
- Added contact information and a link to the project repository, facilitating communication and access to the full project.

**Details from the README File:**
- Emphasizes the project's derivation from two open-source projects, highlighting the importance of community contributions in software development.
- Discusses the installation prerequisites, such as JDK, Node.js, npm, Docker, and Maven, providing clarity on the setup requirements.
- Specific commands for setting up the backend and frontend, such as `mvn clean install spring-boot:run` and `npm run serve`, are included, making the setup process transparent and replicable.
- Usage instructions are clear and concise, guiding users on how to effectively use the application for task management.
- The README also addresses the usage of the application with Docker, detailing the steps to build and run the Docker images and containers for both the backend and frontend, demonstrating an understanding of modern deployment practices.

I created one readme in the root project : https://github.com/Themask3d/TFSD/blob/main/README.md

But also for each part of the project :

back: https://github.com/Themask3d/TFSD/blob/main/backend/spring-boot-h2-database-crud/README.md

front: https://github.com/Themask3d/TFSD/blob/main/frontend/vue-3-crud/README.md

#### 3. Technical Documentation (1/5 points)

**Action Taken:**

- For the backend, I integrated Javadoc to create comprehensive documentation. This involved adding Javadoc comments above each class, method, and field in the code. These comments provide detailed explanations of the code's functionality, parameters, and return values, making it easier for other developers to understand and use the code effectively.

- In the frontend, I employed JSDoc with a Vue plugin to document the JavaScript and Vue components. This was achieved by inserting JSDoc comments, which begin with `/**` and end with `*/`, above functions, classes, and other significant elements in the JavaScript files. The JSDoc comments are structured to clearly define the purpose and usage of each element.

- To generate the backend documentation, I executed specific commands within the root directory of the backend part of the project. This process involved using the standard Javadoc toolset to scan the source code and produce a set of HTML pages that represent the documentation.

- For the frontend documentation, I first installed JSDoc globally using the command `npm install -g jsdoc`. I then generated the documentation using `jsdoc -c jsdoc.json`. The `jsdoc.json` configuration file was tailored to the needs of a Vue.js project, specifying plugins like `jsdoc-vuejs` and setting source inclusion patterns to cover both `.vue` and `.js` files.

- The configuration details in `jsdoc.json` were critical for ensuring that the documentation correctly included and formatted information from Vue components. The options specified in the file directed JSDoc to recursively go through the source files and output the documentation to the `./docs` directory.

- Both sets of documentation are comprehensive and user-friendly, providing clear explanations of how each part of the application works. They include information on usage, parameters, return values, and in some cases, examples, making them great resources for anyone looking to understand or contribute to the project.

- These documents were initially snapshots capturing the state of the project at a specific development stage. However, with the implementation of automated CI/CD pipelines in TP8, these documents are now continuously updated and made available on the GitHub Pages site at `https://themask3d.github.io/TFSD/`. This ensures that the documentation remains up-to-date with the latest code changes, providing an always-current reference for developers.

#### 4. Manual Release Creation (1/5 points)

**Action Taken:**

- **Ensured Code Stability:** Prior to the release, I confirmed that all new features were fully integrated and tested in the main branch. This step is crucial to guarantee the reliability and stability of the software.

- **Tagging the Release with Semantic Versioning:** Using Git, I tagged the release as `v1.0.0`. This version number follows the semantic versioning format of MAJOR.MINOR.PATCH. It symbolizes a significant milestone in the development of the project, marking its initial release.

- **Writing Detailed Release Notes:** The release notes were carefully prepared to be clear and informative. They included:
    - **New Features:** Detailed descriptions of new functionalities added to both the frontend and backend, emphasizing the enhancements and improvements made since the last version.
    - **Bug Fixes:** A list of bugs fixed in this release, showcasing the project's ongoing efforts to improve quality and reliability.
    - **Breaking Changes:** Highlighted any changes that could affect existing functionality, ensuring that users are aware of any significant modifications that might impact their use of the software.
    - **Acknowledgments:** Mentioned contributors and acknowledged any significant assistance or guidance received during the development of this version.

- **Publishing the Release on GitHub:**
    - Navigated to the "Releases" section of the GitHub repository.
    - Clicked "Draft a new release" and selected the `v1.0.0` tag.
    - Filled in the release title as "Initial Release v1.0.0" and added the detailed release notes in the description.
    - Optionally, attached binary files or source code archives, if necessary.
    - Published the release, making it officially available to users and marking a significant milestone in the project's lifecycle.

The release is available here : https://github.com/Themask3d/TFSD/releases/tag/v1.0.0

#### 5. Canonical Documentation (1.5/5 points)

**Action Taken:**

- **Compilation of Extensive Documentation:** I compiled a comprehensive set of canonical documentation, which includes a detailed project overview, installation guides, usage instructions, and technical details for both the frontend and backend components.

- **Methodologies for Generating Documentation:** The process of generating Javadoc and JSDoc documentation as described before.

- **Centralized Information Repository:** All documentation has been made accessible on GitHub Pages at `https://themask3d.github.io/TFSD/`. This platform was chosen for its ease of access and integration with the GitHub workflow. It enables continuous updating of the documentation, keeping it synchronized with the latest developments in the project.

- **Continuous Integration:** Automated CI/CD pipelines were set up to ensure that the documentation on GitHub Pages remains current with any code changes. This automation reflects a commitment to maintaining up-to-date and reliable documentation, a crucial aspect of any professional software project.

Because the two documentation were separated (generated using different plugins, i have been able to merge them using a index.html file that is at the basis of the github-pages : https://github.com/Themask3d/TFSD/tree/gh-pages the index is available here : https://github.com/Themask3d/TFSD/blob/gh-pages/index.html)

At first during the commit their was only this index page but after TP8 their is now the extensive documentation available.

#### Conclusion

Throughout this project, meticulous attention was given to each requirement: declaring a FOSS License, crafting a canonical README, adding in-depth technical documentation, creating a manual release, and compiling comprehensive canonical documentation. This endeavor not only satisfies the academic criteria of the project but also exemplifies a structured and professional approach to software development, documentation, and open-source contribution. The project stands as a testament to thorough planning, execution, and dedication to quality and open-source ethos.
