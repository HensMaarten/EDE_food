package fact.it.ingredientsservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "ingredient")
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
