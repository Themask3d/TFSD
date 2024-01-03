import axios from "axios";
/**
 * Axios instance configured with base settings for API calls.
 */
export default axios.create({
  baseURL: "http://localhost:8080/api", // Base URL for all requests.
  headers: {
    "Content-type": "application/json" // Set default content type for requests.
  }
});
