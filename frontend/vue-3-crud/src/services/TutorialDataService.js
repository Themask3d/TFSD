import http from "../http-common";
/**
 * Service for handling tutorial data operations.
 * Communicates with the backend API for CRUD operations.
 */
class TutorialDataService {
  /**
     * Retrieves all tutorials.
     * @returns {Promise} A promise object representing the response from the API.
     */
  getAll() {
    return http.get("/tutorials");
  }
  /**
     * Retrieves a tutorial by its ID.
     * @param {number} id - The ID of the tutorial to retrieve.
     * @returns {Promise} A promise object representing the response from the API.
     */

  get(id) {
    return http.get(`/tutorials/${id}`);
  }

  /**
     * Creates a new tutorial.
     * @param {Object} data - The tutorial data to create.
     * @returns {Promise} A promise object representing the response from the API.
     */

  create(data) {
    return http.post("/tutorials", data);
  }

  /**
     * Updates a tutorial by its ID.
     * @param {number} id - The ID of the tutorial to update.
     * @param {Object} data - The updated tutorial data.
     * @returns {Promise} A promise object representing the response from the API.
     */

  update(id, data) {
    return http.put(`/tutorials/${id}`, data);
  }

  /**
     * Deletes a tutorial by its ID.
     * @param {number} id - The ID of the tutorial to delete.
     * @returns {Promise} A promise object representing the response from the API.
     */

  delete(id) {
    return http.delete(`/tutorials/${id}`);
  }

  /**
     * Deletes all tutorials.
     * @returns {Promise} A promise object representing the response from the API.
     */

  deleteAll() {
    return http.delete(`/tutorials`);
  }

  /**
     * Finds tutorials by title.
     * @param {string} title - The title to search for.
     * @returns {Promise} A promise object representing the response from the API.
     */

  findByTitle(title) {
    return http.get(`/tutorials?title=${title}`);
  }
}

export default new TutorialDataService();
