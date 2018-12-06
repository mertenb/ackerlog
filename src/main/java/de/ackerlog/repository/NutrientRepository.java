package de.ackerlog.repository;

import de.ackerlog.domain.Nutrient;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Nutrient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NutrientRepository extends JpaRepository<Nutrient, Long> {

    @Query("select nutrient from Nutrient nutrient where nutrient.user.login = ?#{principal.username}")
    List<Nutrient> findByUserIsCurrentUser();

}
