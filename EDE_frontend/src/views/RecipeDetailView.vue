<template>
    <div class="m-8">
        <p class="text-center text-3xl m-4"> {{ recipe.name }}</p>
        <font-awesome-icon @click="navigateToHome" 
            class="hover:cursor-pointer fa-2xl m-5 absolute top-0 left-0" icon="fa-solid fa-house" />
        <div class="flex justify-center items-center">
            <img class="max-w-96 max-h-96" v-bind:src="recipe.imageUrl" />
        </div>
        <div class="grid grid-cols-2 gap-4">
            <div>
                <p class="font-bold">Ingrediënten:</p>
                <ul class="ml-4 list-disc">
                    <li v-for="ingredient in ingredients" :key="ingredient.id">
                        {{ ingredient.amount }} {{ ingredient.measure }} {{ ingredient.name }}
                    </li>
                </ul>
            </div>

            <div>
                <p class="font-bold">Keukengerei:</p>
                <ul class="ml-4 list-disc">
                    <li v-for="utensil in utensils" :key="utensil.id">
                        {{ utensil.name }}
                        <font-awesome-icon class="ml-5 fa-xl" v-if="utensil.requiresElectricity"
                            icon="fa-solid fa-plug-circle-check" />
                        <font-awesome-icon class="ml-5 fa-xl" v-if="!utensil.requiresElectricity"
                            icon="fa-solid fa-plug-circle-xmark" />
                    </li>
                </ul>
            </div>
        </div>
        <p class="font-bold">Bereidingswijze:</p>
        <p> {{ recipe.instructions }}</p>
    </div>
</template>
  
<script>
// @ is an alias to /src
import axios from 'axios'

export default {
    name: 'RecipeDetailView',
    data() {
        return {
            recipe: {},
            utensils: [],
            ingredients: []
        }
    },
    components: {
    },
    methods: {
        async fetchRecipe() {
            try {
                const id = this.$route.params.id
                const fullUrl = 'http://localhost:8083/recipe/complete/' + id;
                const response = await axios.get(fullUrl)
                const completeRecipe = await response.data;
                this.recipe = completeRecipe.recipe;
                this.utensils = completeRecipe.utensils;
                this.ingredients = completeRecipe.ingredients;
            } catch (error) {
                console.error(error);
            }
        },
        navigateToHome() {
            this.$router.push('/');
        }
    },
    mounted() {
        this.fetchRecipe();
    }
}
</script>