package fact.it.recipesservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeRequest {

    private String name;

    private String instructions;

    private String recipeUrl;

    private String imageUrl;

    ArrayList<String> ingredients;

    ArrayList<Long> utensils;
}
