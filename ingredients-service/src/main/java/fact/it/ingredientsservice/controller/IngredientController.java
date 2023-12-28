package fact.it.ingredientsservice.controller;

import fact.it.ingredientsservice.dto.IngredientRequest;
import fact.it.ingredientsservice.dto.IngredientResponse;
import fact.it.ingredientsservice.model.Ingredient;
import fact.it.ingredientsservice.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
@RequiredArgsConstructor

public class IngredientController {
    private final IngredientService ingredientService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createIngredient
            (@RequestBody IngredientRequest ingredientRequest) {
        ingredientService.createIngredient(ingredientRequest);
    }

    @GetMapping("/getbyids")
    @ResponseStatus(HttpStatus.OK)
    public List<IngredientResponse> getAllIngredientsById
            (@RequestParam List<String> id) {
        return ingredientService.getAllIngredientsById(id);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<IngredientResponse> getAllIngredients() {
        return ingredientService.getAllIngredients();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteIngredient(@PathVariable String id){
        ingredientService.deleteIngredient(id);
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Ingredient updateIngredient
            (@PathVariable String id,@RequestBody IngredientRequest ingredientRequest) {
        ingredientService.editIngredient(id,ingredientRequest);
        return ingredientService.getIngredientById(id);
    }
}
