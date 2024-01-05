import { createStore } from "vuex";

export default createStore({
  state: {
    loggedIn: false,
    user: null,
    token: null,
    recipes: [],
  },
  getters: {},
  mutations: {
    SET_USER(state, user) {
      state.user = user;
    },
    SET_LOGGEDIN(state, loggedIn) {
      state.loggedIn = loggedIn;
    },
    SET_TOKEN(state, token){
      state.token = token;
    },
    SET_RECIPES(state, recipes){
      state.recipes = recipes;
    },
  },
  actions: {
    setUser(context, user) {
      context.commit("SET_USER", user);
    },
    setLoggedIn(context, loggedIn) {
      context.commit("SET_LOGGEDIN", loggedIn);
    },
    setToken(context, token) {
      context.commit("SET_TOKEN", token);
    },
    setRecipes(context, recipes){
      context.commit("SET_RECIPES", recipes);
    }
  },
  modules: {},
});
