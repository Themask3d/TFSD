import { createWebHistory, createRouter } from "vue-router";

// Route definitions for the application.
const routes =  [
  {
    path: "/",
    alias: "/tutorials",
    name: "tutorials",
    component: () => import("./components/TutorialsList") // Lazy-load TutorialsList component.
  },
  {
    path: "/tutorials/:id",
    name: "tutorial-details",
    component: () => import("./components/Tutorial") // Lazy-load Tutorial component.
  },
  {
    path: "/add",
    name: "add",
    component: () => import("./components/AddTutorial") // Lazy-load AddTutorial component.
  }
];

/**
 * Router instance for the application.
 * Manages navigation between components based on the browser URL.
 */

const router = createRouter({
  history: createWebHistory(), // Use browser history.
  routes, // Route configurations.
});

export default router;