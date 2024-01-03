import { createApp } from 'vue'
import App from './App.vue'
import 'bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'
import router from './router'

/**
 * Initializes and mounts the Vue application.
 */
createApp(App)
    .use(router) // Use Vue Router for navigation.
    .mount('#app'); // Mount the app to the #app element in the DOM.
