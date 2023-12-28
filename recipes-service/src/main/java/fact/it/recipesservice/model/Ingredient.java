package fact.it.recipesservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Ingredient {

    private String id;
    private String name;

    private String measure;
    private int amount;
}
