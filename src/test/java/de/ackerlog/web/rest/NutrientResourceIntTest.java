package de.ackerlog.web.rest;

import de.ackerlog.AckerlogApp;

import de.ackerlog.domain.Nutrient;
import de.ackerlog.domain.User;
import de.ackerlog.domain.Field;
import de.ackerlog.repository.NutrientRepository;
import de.ackerlog.service.NutrientService;
import de.ackerlog.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static de.ackerlog.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the NutrientResource REST controller.
 *
 * @see NutrientResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AckerlogApp.class)
public class NutrientResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_N = 1;
    private static final Integer UPDATED_N = 2;

    private static final Integer DEFAULT_P = 1;
    private static final Integer UPDATED_P = 2;

    private static final Integer DEFAULT_K = 1;
    private static final Integer UPDATED_K = 2;

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    @Autowired
    private NutrientRepository nutrientRepository;

    @Autowired
    private NutrientService nutrientService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNutrientMockMvc;

    private Nutrient nutrient;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NutrientResource nutrientResource = new NutrientResource(nutrientService);
        this.restNutrientMockMvc = MockMvcBuilders.standaloneSetup(nutrientResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nutrient createEntity(EntityManager em) {
        Nutrient nutrient = new Nutrient()
            .name(DEFAULT_NAME)
            .n(DEFAULT_N)
            .p(DEFAULT_P)
            .k(DEFAULT_K)
            .note(DEFAULT_NOTE);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        nutrient.setUser(user);
        // Add required entity
        Field field = FieldResourceIntTest.createEntity(em);
        em.persist(field);
        em.flush();
        nutrient.setField(field);
        return nutrient;
    }

    @Before
    public void initTest() {
        nutrient = createEntity(em);
    }

    @Test
    @Transactional
    public void createNutrient() throws Exception {
        int databaseSizeBeforeCreate = nutrientRepository.findAll().size();

        // Create the Nutrient
        restNutrientMockMvc.perform(post("/api/nutrients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nutrient)))
            .andExpect(status().isCreated());

        // Validate the Nutrient in the database
        List<Nutrient> nutrientList = nutrientRepository.findAll();
        assertThat(nutrientList).hasSize(databaseSizeBeforeCreate + 1);
        Nutrient testNutrient = nutrientList.get(nutrientList.size() - 1);
        assertThat(testNutrient.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testNutrient.getN()).isEqualTo(DEFAULT_N);
        assertThat(testNutrient.getP()).isEqualTo(DEFAULT_P);
        assertThat(testNutrient.getK()).isEqualTo(DEFAULT_K);
        assertThat(testNutrient.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createNutrientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nutrientRepository.findAll().size();

        // Create the Nutrient with an existing ID
        nutrient.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNutrientMockMvc.perform(post("/api/nutrients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nutrient)))
            .andExpect(status().isBadRequest());

        // Validate the Nutrient in the database
        List<Nutrient> nutrientList = nutrientRepository.findAll();
        assertThat(nutrientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = nutrientRepository.findAll().size();
        // set the field null
        nutrient.setName(null);

        // Create the Nutrient, which fails.

        restNutrientMockMvc.perform(post("/api/nutrients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nutrient)))
            .andExpect(status().isBadRequest());

        List<Nutrient> nutrientList = nutrientRepository.findAll();
        assertThat(nutrientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNutrients() throws Exception {
        // Initialize the database
        nutrientRepository.saveAndFlush(nutrient);

        // Get all the nutrientList
        restNutrientMockMvc.perform(get("/api/nutrients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nutrient.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].n").value(hasItem(DEFAULT_N)))
            .andExpect(jsonPath("$.[*].p").value(hasItem(DEFAULT_P)))
            .andExpect(jsonPath("$.[*].k").value(hasItem(DEFAULT_K)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())));
    }
    
    @Test
    @Transactional
    public void getNutrient() throws Exception {
        // Initialize the database
        nutrientRepository.saveAndFlush(nutrient);

        // Get the nutrient
        restNutrientMockMvc.perform(get("/api/nutrients/{id}", nutrient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nutrient.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.n").value(DEFAULT_N))
            .andExpect(jsonPath("$.p").value(DEFAULT_P))
            .andExpect(jsonPath("$.k").value(DEFAULT_K))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNutrient() throws Exception {
        // Get the nutrient
        restNutrientMockMvc.perform(get("/api/nutrients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNutrient() throws Exception {
        // Initialize the database
        nutrientService.save(nutrient);

        int databaseSizeBeforeUpdate = nutrientRepository.findAll().size();

        // Update the nutrient
        Nutrient updatedNutrient = nutrientRepository.findById(nutrient.getId()).get();
        // Disconnect from session so that the updates on updatedNutrient are not directly saved in db
        em.detach(updatedNutrient);
        updatedNutrient
            .name(UPDATED_NAME)
            .n(UPDATED_N)
            .p(UPDATED_P)
            .k(UPDATED_K)
            .note(UPDATED_NOTE);

        restNutrientMockMvc.perform(put("/api/nutrients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNutrient)))
            .andExpect(status().isOk());

        // Validate the Nutrient in the database
        List<Nutrient> nutrientList = nutrientRepository.findAll();
        assertThat(nutrientList).hasSize(databaseSizeBeforeUpdate);
        Nutrient testNutrient = nutrientList.get(nutrientList.size() - 1);
        assertThat(testNutrient.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNutrient.getN()).isEqualTo(UPDATED_N);
        assertThat(testNutrient.getP()).isEqualTo(UPDATED_P);
        assertThat(testNutrient.getK()).isEqualTo(UPDATED_K);
        assertThat(testNutrient.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingNutrient() throws Exception {
        int databaseSizeBeforeUpdate = nutrientRepository.findAll().size();

        // Create the Nutrient

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNutrientMockMvc.perform(put("/api/nutrients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nutrient)))
            .andExpect(status().isBadRequest());

        // Validate the Nutrient in the database
        List<Nutrient> nutrientList = nutrientRepository.findAll();
        assertThat(nutrientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNutrient() throws Exception {
        // Initialize the database
        nutrientService.save(nutrient);

        int databaseSizeBeforeDelete = nutrientRepository.findAll().size();

        // Get the nutrient
        restNutrientMockMvc.perform(delete("/api/nutrients/{id}", nutrient.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Nutrient> nutrientList = nutrientRepository.findAll();
        assertThat(nutrientList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Nutrient.class);
        Nutrient nutrient1 = new Nutrient();
        nutrient1.setId(1L);
        Nutrient nutrient2 = new Nutrient();
        nutrient2.setId(nutrient1.getId());
        assertThat(nutrient1).isEqualTo(nutrient2);
        nutrient2.setId(2L);
        assertThat(nutrient1).isNotEqualTo(nutrient2);
        nutrient1.setId(null);
        assertThat(nutrient1).isNotEqualTo(nutrient2);
    }
}
