package de.ackerlog.service;

import de.ackerlog.domain.Nutrient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Nutrient.
 */
public interface NutrientService {

    /**
     * Save a nutrient.
     *
     * @param nutrient the entity to save
     * @return the persisted entity
     */
    Nutrient save(Nutrient nutrient);

    /**
     * Get all the nutrients.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Nutrient> findAll(Pageable pageable);


    /**
     * Get the "id" nutrient.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Nutrient> findOne(Long id);

    /**
     * Delete the "id" nutrient.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
