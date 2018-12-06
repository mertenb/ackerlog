package de.ackerlog.service.impl;

import de.ackerlog.service.NutrientService;
import de.ackerlog.domain.Nutrient;
import de.ackerlog.repository.NutrientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Nutrient.
 */
@Service
@Transactional
public class NutrientServiceImpl implements NutrientService {

    private final Logger log = LoggerFactory.getLogger(NutrientServiceImpl.class);

    private final NutrientRepository nutrientRepository;

    public NutrientServiceImpl(NutrientRepository nutrientRepository) {
        this.nutrientRepository = nutrientRepository;
    }

    /**
     * Save a nutrient.
     *
     * @param nutrient the entity to save
     * @return the persisted entity
     */
    @Override
    public Nutrient save(Nutrient nutrient) {
        log.debug("Request to save Nutrient : {}", nutrient);
        return nutrientRepository.save(nutrient);
    }

    /**
     * Get all the nutrients.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Nutrient> findAll(Pageable pageable) {
        log.debug("Request to get all Nutrients");
        return nutrientRepository.findAll(pageable);
    }


    /**
     * Get one nutrient by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Nutrient> findOne(Long id) {
        log.debug("Request to get Nutrient : {}", id);
        return nutrientRepository.findById(id);
    }

    /**
     * Delete the nutrient by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Nutrient : {}", id);
        nutrientRepository.deleteById(id);
    }
}
