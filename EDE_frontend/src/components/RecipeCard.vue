<template>
  <div class="max-w-xs m-5 rounded overflow-hidden shadow-lg card-bg">

    <img class="w-full max-h-32" v-bind:src="recipe.imageUrl">
    <div class="px-6 py-4">
      <div @click="navigateToDetailPage"
        class=" hover:cursor-pointer font-bold text-xl mb-2 text-center text-neutral-950">{{ recipe.name }}</div>
      <p class="text-base">
        {{ recipe.instructions.slice(0, 100) }} ...
      </p>
    </div>
    <div class=" flex items-stretch px-6 pt-4 pb-2">
      <font-awesome-icon v-if="loggedIn" @click="navigateToEditPage" class="hover:cursor-pointer flex-1 m-3 fa-lg"
        icon="fa-solid fa-file-pen" />
      <font-awesome-icon v-if="loggedIn" @click="deleteRecipe" class="hover:cursor-pointer flex-1 m-3 fa-lg"
        icon="fa-solid fa-trash-can" />
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import { mapActions } from 'vuex'
import axios from 'axios'
export default {
  name: 'RecipeCard',
  props: {
    recipe: Object
  },
  computed: {
    ...mapState(['loggedIn', 'user', 'token']),
  },
  methods: {
    navigateToDetailPage() {
      let url = '/recipes/' + this.recipe.id
      this.$router.push(url);
    },
    navigateToEditPage() {
      let url = '/recipes/edit/' + this.recipe.id
      this.$router.push(url);
    },
    async fetchRecipes() {
      try {
        const response = await axios.get('http://localhost:8083/recipe/all')
        const newRecipes = await response.data;
        this.setRecipes(newRecipes);
      } catch (error) {
        console.error(error);
      }
    },
    async deleteRecipe() {
      try {
        const config = {
          headers: {
            Authorization: `Bearer ${this.token}`,
            'Content-Type': 'application/json'
          },
        }
        const fullUrl = 'http://localhost:8083/recipe/' + this.recipe.id;
        await axios.delete(fullUrl, config)
        this.fetchRecipes();
      } catch (error) {
        console.error(error);
      }
    },
    ...mapActions(['setRecipes']),
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>
.card-bg {
  background-color: #ffd166;
}

@import "../styles/tailwind.css";
</style>
