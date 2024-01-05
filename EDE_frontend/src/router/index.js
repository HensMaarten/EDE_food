import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import RecipeDetailView from "../views/RecipeDetailView.vue";
import EditView from "../views/EditView.vue";
import store from "../store"; // Import your store instance

const routes = [
  {
    path: "/",
    name: "home",
    component: HomeView,
  },
  { path: "/recipes/:id", component: RecipeDetailView },
  {
    path: "/recipes/edit/:id",
    component: EditView,
    meta: { requiresAuth: true }, // Add a meta field to specify authentication requirement
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

router.beforeEach((to, from, next) => {
  // Check if the route requires authentication
  if (to.meta.requiresAuth) {
    // Check if the user is logged in
    if (store.state.loggedIn) {
      // Continue to the route
      next();
    } else {
      // Redirect to the login page or any other page for unauthorized access
      next("/");
    }
  } else {
    // Continue to the route if no authentication is required
    next();
  }
});

export default router;
