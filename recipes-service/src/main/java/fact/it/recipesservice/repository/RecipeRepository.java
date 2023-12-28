package fact.it.recipesservice.repository;

import fact.it.recipesservice.model.Recipe;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

}
