package de.ackerlog.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.ackerlog.domain.Nutrient;
import de.ackerlog.service.NutrientService;
import de.ackerlog.web.rest.errors.BadRequestAlertException;
import de.ackerlog.web.rest.util.HeaderUtil;
import de.ackerlog.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Nutrient.
 */
@RestController
@RequestMapping("/api")
public class NutrientResource {

    private final Logger log = LoggerFactory.getLogger(NutrientResource.class);

    private static final String ENTITY_NAME = "nutrient";

    private final NutrientService nutrientService;

    public NutrientResource(NutrientService nutrientService) {
        this.nutrientService = nutrientService;
    }

    /**
     * POST  /nutrients : Create a new nutrient.
     *
     * @param nutrient the nutrient to create
     * @return the ResponseEntity with status 201 (Created) and with body the new nutrient, or with status 400 (Bad Request) if the nutrient has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/nutrients")
    @Timed
    public ResponseEntity<Nutrient> createNutrient(@Valid @RequestBody Nutrient nutrient) throws URISyntaxException {
        log.debug("REST request to save Nutrient : {}", nutrient);
        if (nutrient.getId() != null) {
            throw new BadRequestAlertException("A new nutrient cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Nutrient result = nutrientService.save(nutrient);
        return ResponseEntity.created(new URI("/api/nutrients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /nutrients : Updates an existing nutrient.
     *
     * @param nutrient the nutrient to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated nutrient,
     * or with status 400 (Bad Request) if the nutrient is not valid,
     * or with status 500 (Internal Server Error) if the nutrient couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/nutrients")
    @Timed
    public ResponseEntity<Nutrient> updateNutrient(@Valid @RequestBody Nutrient nutrient) throws URISyntaxException {
        log.debug("REST request to update Nutrient : {}", nutrient);
        if (nutrient.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Nutrient result = nutrientService.save(nutrient);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nutrient.getId().toString()))
            .body(result);
    }

    /**
     * GET  /nutrients : get all the nutrients.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of nutrients in body
     */
    @GetMapping("/nutrients")
    @Timed
    public ResponseEntity<List<Nutrient>> getAllNutrients(Pageable pageable) {
        log.debug("REST request to get a page of Nutrients");
        Page<Nutrient> page = nutrientService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/nutrients");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /nutrients/:id : get the "id" nutrient.
     *
     * @param id the id of the nutrient to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nutrient, or with status 404 (Not Found)
     */
    @GetMapping("/nutrients/{id}")
    @Timed
    public ResponseEntity<Nutrient> getNutrient(@PathVariable Long id) {
        log.debug("REST request to get Nutrient : {}", id);
        Optional<Nutrient> nutrient = nutrientService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nutrient);
    }

    /**
     * DELETE  /nutrients/:id : delete the "id" nutrient.
     *
     * @param id the id of the nutrient to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/nutrients/{id}")
    @Timed
    public ResponseEntity<Void> deleteNutrient(@PathVariable Long id) {
        log.debug("REST request to delete Nutrient : {}", id);
        nutrientService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
