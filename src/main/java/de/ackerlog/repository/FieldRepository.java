package de.ackerlog.repository;

import de.ackerlog.domain.Field;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Field entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FieldRepository extends JpaRepository<Field, Long> {

    @Query("select field from Field field where field.user.login = ?#{principal.username}")
    List<Field> findByUserIsCurrentUser();

}
