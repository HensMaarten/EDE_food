package fact.it.recipesservice.service;

import fact.it.recipesservice.dto.RecipeRequest;
import fact.it.recipesservice.dto.RecipeResponse;
import fact.it.recipesservice.model.Ingredient;
import fact.it.recipesservice.model.Recipe;
import fact.it.recipesservice.model.RecipeIngredientUtensil;
import fact.it.recipesservice.model.Utensil;
import fact.it.recipesservice.repository.RecipeRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final WebClient webClient;

    @Value("${ingredientservice.baseurl}")
    private String ingredientServiceBaseUrl;

    @Value("${utensilservice.baseurl}")
    private String utensilServiceBaseUrl;

    @PostConstruct
    public void loadData() {
        if(recipeRepository.count() <= 0){
            Recipe recipe = new Recipe();
            recipe.setName("Croque Monsieur");
            recipe.setInstructions(
                    """
                            Leg op een sneetje brood een plakje kaas vervolgens een plakje hesp en daarna opnieuw een plakje kaas. Bedek met het tweede sneetje brood.\s
                            Bak de croque in het croque-apparaat, de oven of de pan tot de kaas mooi gesmolten is en het brood goudbruin gebakken is.\s""");
            recipe.setRecipeUrl("https://njam.tv/recepten/croque-monsieur");
            recipe.setImageUrl("https://images-2.schellywood.be/thumbnail/full/82831/croque-monsieur.jpg");
            ArrayList<Long> longList = new ArrayList<>();
            ArrayList<String> stringList = new ArrayList<>();
            stringList.add("655df60cf8ffc62dd4dc4c39");
            stringList.add("655df60cf8ffc62dd4dc4c3a");
            longList.add(1L);
            longList.add(2L);
            recipe.setIngredients(stringList);
            recipe.setUtensils(longList);
            recipeRepository.save(recipe);
        }
    }

    public List<RecipeResponse> getAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();

        return recipes.stream().map(this::mapToRecipeResponse).toList();
    }

    public Recipe getRecipeById(Long id) {
        Optional<Recipe> currentOptionalRecipe = recipeRepository.findById(id);
        return currentOptionalRecipe.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe with " + id + " not found"));
    }

    public RecipeIngredientUtensil getCompleteRecipe(Long id) {
        Recipe recipe = getRecipeById(id);
        RecipeIngredientUtensil completeRecipe = new RecipeIngredientUtensil();
        List<Utensil> utensils = webClient.get().uri("http://" + utensilServiceBaseUrl + "/api/utensil/getUtensils"
                ,uriBuilder -> uriBuilder.queryParam("id",recipe.getUtensils()).build())
                .retrieve()
                .bodyToFlux(Utensil.class)
                .collectList().block();

        List<Ingredient> ingredients = webClient.get().uri("http://" + ingredientServiceBaseUrl + "/api/ingredients/getbyids"
                        ,uriBuilder -> uriBuilder.queryParam("id",recipe.getIngredients()).build())
                .retrieve()
                .bodyToFlux(Ingredient.class)
                .collectList().block();
        completeRecipe.setRecipe(recipe);
        completeRecipe.setUtensils(utensils);
        completeRecipe.setIngredients(ingredients);

        return completeRecipe;
    }


    public void createRecipe(RecipeRequest recipeRequest){
        Recipe recipe = Recipe.builder()
                .name(recipeRequest.getName())
                .instructions(recipeRequest.getInstructions())
                .recipeUrl(recipeRequest.getRecipeUrl())
                .imageUrl(recipeRequest.getImageUrl())
                .ingredients(recipeRequest.getIngredients())
                .utensils(recipeRequest.getUtensils())
                .build();

        recipeRepository.save(recipe);
    }

    public void editRecipe(Long recipeId, RecipeRequest recipeRequest){
        Recipe currentRecipe = getRecipeById(recipeId);

        currentRecipe.setName(recipeRequest.getName());
        currentRecipe.setInstructions(recipeRequest.getInstructions());
        currentRecipe.setRecipeUrl(recipeRequest.getRecipeUrl());
        currentRecipe.setImageUrl(recipeRequest.getImageUrl());
        currentRecipe.setIngredients(recipeRequest.getIngredients());
        currentRecipe.setUtensils(recipeRequest.getUtensils());

        recipeRepository.save(currentRecipe);
    }
    public void deleteRecipe(Long recipeId){
        recipeRepository.deleteById(recipeId);
    }

    private RecipeResponse mapToRecipeResponse(Recipe recipe) {
        return RecipeResponse.builder()
                .id(recipe.getId())
                .name(recipe.getName())
                .instructions(recipe.getInstructions())
                .recipeUrl(recipe.getRecipeUrl())
                .imageUrl(recipe.getImageUrl())
                .ingredients(recipe.getIngredients())
                .utensils(recipe.getUtensils())
                .build();
    }

}
