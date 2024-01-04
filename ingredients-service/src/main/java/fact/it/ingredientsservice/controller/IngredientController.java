package fact.it.ingredientsservice.controller;

import fact.it.ingredientsservice.dto.IngredientRequest;
import fact.it.ingredientsservice.dto.IngredientResponse;
import fact.it.ingredientsservice.model.Ingredient;
import fact.it.ingredientsservice.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
@RequiredArgsConstructor

public class IngredientController {
    private final IngredientService ingredientService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Ingredient> createIngredient
            (@RequestBody IngredientRequest ingredientRequest) {
        Ingredient ingredient = ingredientService.createIngredient(ingredientRequest);

        return ResponseEntity.ok(ingredient);
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
    public ResponseEntity<Ingredient> updateIngredient
            (@PathVariable String id,@RequestBody IngredientRequest ingredientRequest) {
        Ingredient ingredient = ingredientService.editIngredient(id,ingredientRequest);
        return  ResponseEntity.ok(ingredient);
    }
}
