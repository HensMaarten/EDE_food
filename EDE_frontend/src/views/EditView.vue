<template>
    <div class="m-10">
        <h1 class="mb-5 mt-5">Bewerk Recept </h1>
        <div class="mb-5">
            <label for="name" class="block mb-2 text-sm font-medium">Naam van het recept</label>
            <input type="text" id="name" v-model="recipe.name"
                class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 dark:shadow-sm-light"
                required>
        </div>
        <div class="mb-5">
            <label for="instructions" class="block mb-2 text-sm font-medium">Bereidingswijze</label>
            <textarea id="instructions" rows="8" v-model="recipe.instructions"
                class="block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"></textarea>
        </div>
        <div class="mb-5">
            <label for="recipeUrl" class="block mb-2 text-sm font-medium">Link naar het recept</label>
            <input type="url" id="recipeUrl" v-model="recipe.recipeUrl"
                class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 dark:shadow-sm-light"
                required>
        </div>
        <div class="mb-5">
            <label for="imageUrl" class="block mb-2 text-sm font-medium">Link naar de afbeelding</label>
            <input type="url" id="imageUrl" v-model="recipe.imageUrl"
                class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 dark:shadow-sm-light"
                required>
        </div>
        <div class="grid gap-6 mb-6 md:grid-cols-4">
            <div>
                <label for="ingredientName" class="block mb-2 text-sm font-medium">Naam ingredient</label>
                <input type="text" id="ingredientName" v-model="newIngredient.name"
                    class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 dark:shadow-sm-light">
            </div>
            <div>
                <label for="measure" class="block mb-2 text-sm font-medium">Meeteenheid</label>
                <input type="text" id="measure" v-model="newIngredient.measure"
                    class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 dark:shadow-sm-light">
            </div>
            <div>
                <label for="amount" class="block mb-2 text-sm font-medium">Hoeveelheid</label>
                <input type="number" id="amount" v-model="newIngredient.amount"
                    class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 dark:shadow-sm-light">
            </div>
            <div>
                <button @click="addIngredient"
                    class=" mt-7 text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                    Voeg Ingredient toe</button>
            </div>
        </div>
        <ul class=" mb-5 ml-7">
            <div class=" m-3 flex flex-row" v-for="ingredient in ingredients" :key="ingredient.id">
                <li class="list-disc"> {{ ingredient.amount }} {{
                    ingredient.measure }} {{ ingredient.name }}
                </li>
                <font-awesome-icon @click="deleteIngredient(ingredient.id)"
                    class="hover:cursor-pointer absolute right-20  fa-xl" icon="fa-solid fa-trash-can" />

            </div>
        </ul>
        <div class="grid gap-6 mb-6 md:grid-cols-3">
            <div>
                <label for="utensilName" class="block mb-2 text-sm font-medium">Naam van het keukengereedschap</label>
                <input type="text" id="utensilName" v-model="newUtensil.name"
                    class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 dark:shadow-sm-light">
            </div>
            <div>
                <div>
                    <label for="utensilName" class="block mb-2 text-sm font-medium">Gebruikt Elektriciteit</label>
                    <label class=" mt-2 relative inline-flex items-center cursor-pointer">
                        <input type="checkbox" value="" class="sr-only peer" v-model="newUtensil.requiresElectricity">
                        <div
                            class="w-11 h-6 bg-gray-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300 dark:peer-focus:ring-blue-800 rounded-full peer dark:bg-gray-700 peer-checked:after:translate-x-full rtl:peer-checked:after:-translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:start-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all dark:border-gray-600 peer-checked:bg-blue-600">
                        </div>
                    </label>
                </div>
            </div>
            <div>
                <button @click="addUtensil"
                    class=" mt-7 text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                    Voeg Keukengereedschap toe</button>
            </div>
        </div>
        <ul class=" mb-5 ml-7">
            <div class=" m-5 flex flex-row" v-for="utensil in utensils" :key="utensil.id">
                <li class=" list-disc"> {{ utensil.name }}
                </li>
                <font-awesome-icon class="ml-5 fa-xl" v-if="utensil.requiresElectricity"
                    icon="fa-solid fa-plug-circle-check" />
                <font-awesome-icon class="ml-5 fa-xl" v-if="!utensil.requiresElectricity"
                    icon="fa-solid fa-plug-circle-xmark" />
                <font-awesome-icon @click="deleteUtensil(utensil.id)" class="hover:cursor-pointer absolute right-20  fa-xl"
                    icon="fa-solid fa-trash-can" />

            </div>

        </ul>
        <button @click="editRecipe"
            class="text-white mb-5 bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
            Voeg Recept toe</button>
    </div>
</template>
<script>
import axios from 'axios'
import { mapState } from 'vuex'
import { mapActions } from 'vuex'

export default {
    name: 'EditView',
    data() {
        return {
            recipe: {
                name: "",
                instructions: "",
                recipeUrl: "",
                imageUrl: "",
                ingredients: [],
                utensils: []
            },
            newIngredient: {},
            newUtensil: {
                name: "",
                requiresElectricity: false
            },
            ingredients: [],
            utensils: []
        }
    },
    computed: {
        ...mapState(['token']),
    },
    methods: {
        async fetchRecipe(id) {
            try {
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
        async fetchRecipes() {
            try {
                const response = await axios.get('http://localhost:8083/recipe/all')
                const newRecipes = await response.data;
                this.setRecipes(newRecipes);
                console.log(newRecipes)
            } catch (error) {
                console.error(error);
            }
        },
        async editRecipe() {
            const id = this.$route.params.id
            const config = {
                headers: {
                    Authorization: `Bearer ${this.token}`,
                    'Content-Type': 'application/json'
                },
            }
            if (id != 0) {
                const fullUrl = 'http://localhost:8083/recipe/' + id;
                axios.put(fullUrl, this.recipe, config)
            }
            else {
                const fullUrl = 'http://localhost:8083/recipe';
                axios.post(fullUrl, this.recipe, config)
            }
            this.fetchRecipes().then(setTimeout(() => {
                this.$router.push('/')
            }, 50));

        },
        async addIngredient() {
            if (this.newIngredient.name != "", this.newIngredient.measure != "", this.newIngredient.amount != 0) {
                const config = {
                    headers: {
                        Authorization: `Bearer ${this.token}`,
                        'Content-Type': 'application/json'
                    },
                }
                const fullUrl = 'http://localhost:8083/ingredients';
                const savedingredient = await axios.post(fullUrl, this.newIngredient, config)
                this.recipe.ingredients.push(savedingredient.data.id);
                this.ingredients.push(savedingredient.data)
                const recipeId = this.$route.params.id
                if (recipeId != 0) {
                    const fullRecipeUrl = 'http://localhost:8083/recipe/' + recipeId
                    await axios.put(fullRecipeUrl, this.recipe, config)
                }
                this.newIngredient = {};
            }
        },
        async deleteIngredient(ingredientId) {
            const config = {
                headers: {
                    Authorization: `Bearer ${this.token}`,
                    'Content-Type': 'application/json'
                },
            }

            const fullUrl = 'http://localhost:8083/ingredients/' + ingredientId;
            await axios.delete(fullUrl, config)
            const index = this.ingredients.findIndex(ingredient => ingredient.id === ingredientId);
            if (index !== -1) {
                this.ingredients.splice(index, 1);
            }
            const indexRecipeArray = this.recipe.ingredients.findIndex(ingredient => ingredient.id === ingredientId);
            if (indexRecipeArray !== -1) {
                this.recipe.ingredients.splice(indexRecipeArray, 1);
            }
            const recipeId = this.$route.params.id
            if (recipeId != 0) {
                const fullRecipeUrl = 'http://localhost:8083/recipe/' + recipeId
                await axios.put(fullRecipeUrl, this.recipe, config)
            }
        },
        async addUtensil() {
            if (this.newUtensil.name != "") {
                const config = {
                    headers: {
                        Authorization: `Bearer ${this.token}`,
                        'Content-Type': 'application/json'
                    },
                }
                const fullUrl = 'http://localhost:8083/utensil';
                const savedUtensil = await axios.post(fullUrl, this.newUtensil, config)
                this.recipe.utensils.push(savedUtensil.data.id);
                this.utensils.push(savedUtensil.data)
                const recipeId = this.$route.params.id
                if (recipeId != 0) {
                    const fullRecipeUrl = 'http://localhost:8083/recipe/' + recipeId
                    await axios.put(fullRecipeUrl, this.recipe, config)
                }
                this.newUtensil = {
                    name: "",
                    requiresElectricity: false
                };
            }
        },
        async deleteUtensil(utensilId) {
            const config = {
                headers: {
                    Authorization: `Bearer ${this.token}`,
                    'Content-Type': 'application/json'
                },
            }

            const fullUrl = 'http://localhost:8083/utensil/' + utensilId;
            await axios.delete(fullUrl, config)
            const index = this.utensils.findIndex(utensil => utensil.id === utensilId);
            if (index !== -1) {
                this.utensils.splice(index, 1);
            }
            const indexRecipeArray = this.recipe.utensils.findIndex(utensil => utensil.id === utensilId);
            if (indexRecipeArray !== -1) {
                this.recipe.utensils.splice(indexRecipeArray, 1);
            }
            const recipeId = this.$route.params.id

            if (recipeId != 0) {
                const fullRecipeUrl = 'http://localhost:8083/recipe/' + recipeId
                await axios.put(fullRecipeUrl, this.recipe, config)
            }

        },
        ...mapActions(['setRecipes']),

    },
    mounted() {
        const id = this.$route.params.id
        if (id != 0) {
            this.fetchRecipe(id);
        }
    }
}
</script>
<style></style>