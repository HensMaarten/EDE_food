package fact.it.utensilsservice.repository;


import fact.it.utensilsservice.model.Utensil;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface UtensilsRepository extends JpaRepository<Utensil, Long> {
    List<Utensil> findByIdIn(List<Long> id);


}
