package fact.it.ingredientsservice.repository;

import fact.it.ingredientsservice.model.Ingredient;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IngredientRepository extends MongoRepository<Ingredient, String> {

    List<Ingredient> findByIdIn(List<String> id);
}
