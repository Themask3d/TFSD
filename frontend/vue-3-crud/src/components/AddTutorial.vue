<template>
  <div class="submit-form">
    <div v-if="!submitted">
      <div class="form-group">
        <label for="title">Title</label>
        <input
          type="text"
          class="form-control"
          id="title"
          required
          v-model="tutorial.title"
          name="title"
        />
      </div>

      <div class="form-group">
        <label for="description">Description</label>
        <input
          class="form-control"
          id="description"
          required
          v-model="tutorial.description"
          name="description"
        />
      </div>

      <button @click="saveTutorial" class="btn btn-success">Submit</button>
    </div>

    <div v-else>
      <h4>You submitted successfully!</h4>
      <button class="btn btn-success" @click="newTutorial">Add</button>
    </div>
  </div>
</template>

<script>
import TutorialDataService from "../services/TutorialDataService";
/**
 * Component for adding a new tutorial.
 * Provides form inputs for title and description, and a submit button to save the tutorial.
 */
export default {
  name: "add-tutorial",
  data() {
    /**
     * Data function that holds the state of the component.
     * @returns {Object} The state data of the component.
     */
    return {
     // The tutorial object with properties for binding to form inputs.

      tutorial: {
        id: null,
        title: "",
        description: "",
        published: false
      },
      submitted: false      // Flag to track if the form is submitted.
    };
  },
  methods: {
    /**
     * Saves the tutorial to the database.
     * Sends a POST request to the backend with the tutorial data.
     */
    saveTutorial() {
      var data = {
        title: this.tutorial.title,
        description: this.tutorial.description
      };

      TutorialDataService.create(data)
        .then(response => {
          this.tutorial.id = response.data.id;
          console.log(response.data);
          this.submitted = true;
        })
        .catch(e => {
          console.log(e);
        });
    },

    /**
     * Resets the form to allow entering a new tutorial.
     */
    newTutorial() {
      this.submitted = false;
      this.tutorial = {};
    }
  }
};
</script>

<style>
.submit-form {
  max-width: 300px;
  margin: auto;
}
</style>
