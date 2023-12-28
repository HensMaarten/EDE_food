package fact.it.recipesservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeResponse {
    private Long id;
    private String name;

    private String instructions;

    private String recipeUrl;

    private String imageUrl;

    List<String> ingredients;

    List<Long> utensils;
}
