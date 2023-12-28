package fact.it.recipesservice.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeIngredientUtensil {

    private Recipe recipe;

    private List<Ingredient> ingredients;

    private List<Utensil> utensils;
}
