<template>
  <div class="home">
    <p class="text-center text-3xl m-4">Flavor Fiesta</p>
    <div class="absolute top-0 right-0 m-5">
      <LoginComponent v-if="!loggedIn" />
      <UserComponent v-if="loggedIn" />
    </div>
    <div class=" col-span-4  grid grid-cols-3 gap-4" id="cardContainer">
      <RecipeCard v-for="recipe in recipes" :key="recipe.id" :recipe=recipe />
    </div>
    <font-awesome-icon @click="navigateToAddPage" v-if="loggedIn"
      class="hover:cursor-pointer fa-2xl m-5 absolute top-0 left-0" icon="fa-solid fa-circle-plus" />
  </div>
</template>

<script>
// @ is an alias to /src
import RecipeCard from '@/components/RecipeCard.vue';
import LoginComponent from '@/components/LoginComponent.vue';
import UserComponent from '@/components/UserComponent.vue';
import axios from 'axios'
import { mapState } from 'vuex'
import { mapActions } from 'vuex'

export default {
  name: 'HomeView',
  components: {
    RecipeCard,
    LoginComponent,
    UserComponent
  },
  computed: {
    ...mapState(['loggedIn', 'user', 'recipes']),
  },
  methods: {
    async fetchRecipes() {
      try {
        const response = await axios.get('http://localhost:8083/recipe/all')
        const newRecipes = await response.data;
        this.setRecipes(newRecipes);
      } catch (error) {
        console.error(error);
      }
    },
    navigateToAddPage() {
      this.$router.push('/recipes/edit/0');
    },
    ...mapActions(['setRecipes']),
  },
  async mounted() {
    this.fetchRecipes()
  },
}
</script>
