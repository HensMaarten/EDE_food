package fact.it.recipesservice.controller;


import fact.it.recipesservice.dto.RecipeRequest;
import fact.it.recipesservice.dto.RecipeResponse;
import fact.it.recipesservice.model.Recipe;
import fact.it.recipesservice.model.RecipeIngredientUtensil;
import fact.it.recipesservice.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipe")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteRecipe(@PathVariable Long id){
        recipeService.deleteRecipe(id);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Recipe> createRecipe
            (@RequestBody RecipeRequest recipeRequest) {
        Recipe recipe = recipeService.createRecipe(recipeRequest);
        return ResponseEntity.ok(recipe);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Recipe> updateRecipe
            (@PathVariable Long id,@RequestBody RecipeRequest recipeRequest) {
        Recipe recipe = recipeService.editRecipe(id,recipeRequest);
        return ResponseEntity.ok(recipe);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<RecipeResponse> getRecipes
            () {
        return recipeService.getAllRecipes();
    }

    @GetMapping("/completeRecipe/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RecipeIngredientUtensil getCompleteRecipe
            (@PathVariable Long id) {
        return recipeService.getCompleteRecipe(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Recipe getRecipe
            (@PathVariable Long id) {
        return recipeService.getRecipeById(id);
    }
}
