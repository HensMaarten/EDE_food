package fact.it.ingredientsservice.service;


import fact.it.ingredientsservice.dto.IngredientRequest;
import fact.it.ingredientsservice.dto.IngredientResponse;
import fact.it.ingredientsservice.model.Ingredient;
import fact.it.ingredientsservice.repository.IngredientRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    @PostConstruct
    public void loadData() {
        if(ingredientRepository.count() <= 0){
            Ingredient ingredient = Ingredient.builder()
                    .name("carrot")
                    .measure("stuks")
                    .amount(2)
                    .build();

            Ingredient ingredient2 = Ingredient.builder()
                    .name("steak")
                    .measure("grams")
                    .amount(500)
                    .build();

            ingredientRepository.save(ingredient);
            ingredientRepository.save(ingredient2);
        }
    }


    public Ingredient getIngredientById(String id) {
        Optional<Ingredient> currentOptionalIngredient = ingredientRepository.findById(id);
        return currentOptionalIngredient.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Utensil with " + id + " not found"));
    }

    public Ingredient createIngredient(IngredientRequest ingredientRequest){
        Ingredient ingredient = Ingredient.builder()
                .name(ingredientRequest.getName())
                .measure(ingredientRequest.getMeasure())
                .amount(ingredientRequest.getAmount())
                .build();

        Ingredient savedIngredient = ingredientRepository.save(ingredient);

        return savedIngredient;
    }
    public Ingredient editIngredient(String ingredientId, IngredientRequest ingredientRequest){
        Ingredient currentIngredient = getIngredientById(ingredientId);

        currentIngredient.setName(ingredientRequest.getName());
        currentIngredient.setMeasure(ingredientRequest.getMeasure());
        currentIngredient.setAmount(ingredientRequest.getAmount());

        Ingredient savedIngredient = ingredientRepository.save(currentIngredient);
        return savedIngredient;
    }
    public void deleteIngredient(String ingredientId){
        ingredientRepository.deleteById(ingredientId);
    }

    public List<IngredientResponse> getAllIngredients() {
        List<Ingredient> ingredients = ingredientRepository.findAll();

        return ingredients.stream().map(this::mapToIngredientResponse).toList();
    }

    public List<IngredientResponse> getAllIngredientsById(List<String> ids) {
        List<Ingredient> ingredients = ingredientRepository.findByIdIn(ids);

        return ingredients.stream().map(this::mapToIngredientResponse).toList();
    }

    private IngredientResponse mapToIngredientResponse(Ingredient ingredient) {
        return IngredientResponse.builder()
                .id(ingredient.getId())
                .name(ingredient.getName())
                .measure(ingredient.getMeasure())
                .amount(ingredient.getAmount())
                .build();
    }
}
