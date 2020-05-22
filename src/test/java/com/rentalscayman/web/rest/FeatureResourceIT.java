package com.rentalscayman.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rentalscayman.RentalscaymanApp;
import com.rentalscayman.domain.Feature;
import com.rentalscayman.repository.FeatureRepository;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FeatureResource} REST controller.
 */
@SpringBootTest(classes = RentalscaymanApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FeatureResourceIT {
    private static final Integer DEFAULT_NUMBER_BEDROOMS = 1;
    private static final Integer UPDATED_NUMBER_BEDROOMS = 2;

    private static final Integer DEFAULT_NUMBER_BATHROOM = 1;
    private static final Integer UPDATED_NUMBER_BATHROOM = 2;

    private static final Boolean DEFAULT_FULL_KITCHEN = false;
    private static final Boolean UPDATED_FULL_KITCHEN = true;

    private static final Boolean DEFAULT_ELEVATOR = false;
    private static final Boolean UPDATED_ELEVATOR = true;

    private static final Boolean DEFAULT_PARKING = false;
    private static final Boolean UPDATED_PARKING = true;

    private static final Boolean DEFAULT_AIR_CONDITIONAIR = false;
    private static final Boolean UPDATED_AIR_CONDITIONAIR = true;

    private static final Boolean DEFAULT_BACKYARD = false;
    private static final Boolean UPDATED_BACKYARD = true;

    private static final Boolean DEFAULT_POOL = false;
    private static final Boolean UPDATED_POOL = true;

    private static final Integer DEFAULT_M_2 = 1;
    private static final Integer UPDATED_M_2 = 2;

    @Autowired
    private FeatureRepository featureRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFeatureMockMvc;

    private Feature feature;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Feature createEntity(EntityManager em) {
        Feature feature = new Feature()
            .numberBedrooms(DEFAULT_NUMBER_BEDROOMS)
            .numberBathroom(DEFAULT_NUMBER_BATHROOM)
            .fullKitchen(DEFAULT_FULL_KITCHEN)
            .elevator(DEFAULT_ELEVATOR)
            .parking(DEFAULT_PARKING)
            .airConditionair(DEFAULT_AIR_CONDITIONAIR)
            .backyard(DEFAULT_BACKYARD)
            .pool(DEFAULT_POOL)
            .m2(DEFAULT_M_2);
        return feature;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Feature createUpdatedEntity(EntityManager em) {
        Feature feature = new Feature()
            .numberBedrooms(UPDATED_NUMBER_BEDROOMS)
            .numberBathroom(UPDATED_NUMBER_BATHROOM)
            .fullKitchen(UPDATED_FULL_KITCHEN)
            .elevator(UPDATED_ELEVATOR)
            .parking(UPDATED_PARKING)
            .airConditionair(UPDATED_AIR_CONDITIONAIR)
            .backyard(UPDATED_BACKYARD)
            .pool(UPDATED_POOL)
            .m2(UPDATED_M_2);
        return feature;
    }

    @BeforeEach
    public void initTest() {
        feature = createEntity(em);
    }

    @Test
    @Transactional
    public void createFeature() throws Exception {
        int databaseSizeBeforeCreate = featureRepository.findAll().size();
        // Create the Feature
        restFeatureMockMvc
            .perform(post("/api/features").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feature)))
            .andExpect(status().isCreated());

        // Validate the Feature in the database
        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeCreate + 1);
        Feature testFeature = featureList.get(featureList.size() - 1);
        assertThat(testFeature.getNumberBedrooms()).isEqualTo(DEFAULT_NUMBER_BEDROOMS);
        assertThat(testFeature.getNumberBathroom()).isEqualTo(DEFAULT_NUMBER_BATHROOM);
        assertThat(testFeature.isFullKitchen()).isEqualTo(DEFAULT_FULL_KITCHEN);
        assertThat(testFeature.isElevator()).isEqualTo(DEFAULT_ELEVATOR);
        assertThat(testFeature.isParking()).isEqualTo(DEFAULT_PARKING);
        assertThat(testFeature.isAirConditionair()).isEqualTo(DEFAULT_AIR_CONDITIONAIR);
        assertThat(testFeature.isBackyard()).isEqualTo(DEFAULT_BACKYARD);
        assertThat(testFeature.isPool()).isEqualTo(DEFAULT_POOL);
        assertThat(testFeature.getm2()).isEqualTo(DEFAULT_M_2);
    }

    @Test
    @Transactional
    public void createFeatureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = featureRepository.findAll().size();

        // Create the Feature with an existing ID
        feature.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeatureMockMvc
            .perform(post("/api/features").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feature)))
            .andExpect(status().isBadRequest());

        // Validate the Feature in the database
        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNumberBedroomsIsRequired() throws Exception {
        int databaseSizeBeforeTest = featureRepository.findAll().size();
        // set the field null
        feature.setNumberBedrooms(null);

        // Create the Feature, which fails.

        restFeatureMockMvc
            .perform(post("/api/features").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feature)))
            .andExpect(status().isBadRequest());

        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumberBathroomIsRequired() throws Exception {
        int databaseSizeBeforeTest = featureRepository.findAll().size();
        // set the field null
        feature.setNumberBathroom(null);

        // Create the Feature, which fails.

        restFeatureMockMvc
            .perform(post("/api/features").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feature)))
            .andExpect(status().isBadRequest());

        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFullKitchenIsRequired() throws Exception {
        int databaseSizeBeforeTest = featureRepository.findAll().size();
        // set the field null
        feature.setFullKitchen(null);

        // Create the Feature, which fails.

        restFeatureMockMvc
            .perform(post("/api/features").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feature)))
            .andExpect(status().isBadRequest());

        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFeatures() throws Exception {
        // Initialize the database
        featureRepository.saveAndFlush(feature);

        // Get all the featureList
        restFeatureMockMvc
            .perform(get("/api/features?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feature.getId().intValue())))
            .andExpect(jsonPath("$.[*].numberBedrooms").value(hasItem(DEFAULT_NUMBER_BEDROOMS)))
            .andExpect(jsonPath("$.[*].numberBathroom").value(hasItem(DEFAULT_NUMBER_BATHROOM)))
            .andExpect(jsonPath("$.[*].fullKitchen").value(hasItem(DEFAULT_FULL_KITCHEN.booleanValue())))
            .andExpect(jsonPath("$.[*].elevator").value(hasItem(DEFAULT_ELEVATOR.booleanValue())))
            .andExpect(jsonPath("$.[*].parking").value(hasItem(DEFAULT_PARKING.booleanValue())))
            .andExpect(jsonPath("$.[*].airConditionair").value(hasItem(DEFAULT_AIR_CONDITIONAIR.booleanValue())))
            .andExpect(jsonPath("$.[*].backyard").value(hasItem(DEFAULT_BACKYARD.booleanValue())))
            .andExpect(jsonPath("$.[*].pool").value(hasItem(DEFAULT_POOL.booleanValue())))
            .andExpect(jsonPath("$.[*].m2").value(hasItem(DEFAULT_M_2)));
    }

    @Test
    @Transactional
    public void getFeature() throws Exception {
        // Initialize the database
        featureRepository.saveAndFlush(feature);

        // Get the feature
        restFeatureMockMvc
            .perform(get("/api/features/{id}", feature.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(feature.getId().intValue()))
            .andExpect(jsonPath("$.numberBedrooms").value(DEFAULT_NUMBER_BEDROOMS))
            .andExpect(jsonPath("$.numberBathroom").value(DEFAULT_NUMBER_BATHROOM))
            .andExpect(jsonPath("$.fullKitchen").value(DEFAULT_FULL_KITCHEN.booleanValue()))
            .andExpect(jsonPath("$.elevator").value(DEFAULT_ELEVATOR.booleanValue()))
            .andExpect(jsonPath("$.parking").value(DEFAULT_PARKING.booleanValue()))
            .andExpect(jsonPath("$.airConditionair").value(DEFAULT_AIR_CONDITIONAIR.booleanValue()))
            .andExpect(jsonPath("$.backyard").value(DEFAULT_BACKYARD.booleanValue()))
            .andExpect(jsonPath("$.pool").value(DEFAULT_POOL.booleanValue()))
            .andExpect(jsonPath("$.m2").value(DEFAULT_M_2));
    }

    @Test
    @Transactional
    public void getNonExistingFeature() throws Exception {
        // Get the feature
        restFeatureMockMvc.perform(get("/api/features/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFeature() throws Exception {
        // Initialize the database
        featureRepository.saveAndFlush(feature);

        int databaseSizeBeforeUpdate = featureRepository.findAll().size();

        // Update the feature
        Feature updatedFeature = featureRepository.findById(feature.getId()).get();
        // Disconnect from session so that the updates on updatedFeature are not directly saved in db
        em.detach(updatedFeature);
        updatedFeature
            .numberBedrooms(UPDATED_NUMBER_BEDROOMS)
            .numberBathroom(UPDATED_NUMBER_BATHROOM)
            .fullKitchen(UPDATED_FULL_KITCHEN)
            .elevator(UPDATED_ELEVATOR)
            .parking(UPDATED_PARKING)
            .airConditionair(UPDATED_AIR_CONDITIONAIR)
            .backyard(UPDATED_BACKYARD)
            .pool(UPDATED_POOL)
            .m2(UPDATED_M_2);

        restFeatureMockMvc
            .perform(
                put("/api/features").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(updatedFeature))
            )
            .andExpect(status().isOk());

        // Validate the Feature in the database
        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeUpdate);
        Feature testFeature = featureList.get(featureList.size() - 1);
        assertThat(testFeature.getNumberBedrooms()).isEqualTo(UPDATED_NUMBER_BEDROOMS);
        assertThat(testFeature.getNumberBathroom()).isEqualTo(UPDATED_NUMBER_BATHROOM);
        assertThat(testFeature.isFullKitchen()).isEqualTo(UPDATED_FULL_KITCHEN);
        assertThat(testFeature.isElevator()).isEqualTo(UPDATED_ELEVATOR);
        assertThat(testFeature.isParking()).isEqualTo(UPDATED_PARKING);
        assertThat(testFeature.isAirConditionair()).isEqualTo(UPDATED_AIR_CONDITIONAIR);
        assertThat(testFeature.isBackyard()).isEqualTo(UPDATED_BACKYARD);
        assertThat(testFeature.isPool()).isEqualTo(UPDATED_POOL);
        assertThat(testFeature.getm2()).isEqualTo(UPDATED_M_2);
    }

    @Test
    @Transactional
    public void updateNonExistingFeature() throws Exception {
        int databaseSizeBeforeUpdate = featureRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeatureMockMvc
            .perform(put("/api/features").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feature)))
            .andExpect(status().isBadRequest());

        // Validate the Feature in the database
        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFeature() throws Exception {
        // Initialize the database
        featureRepository.saveAndFlush(feature);

        int databaseSizeBeforeDelete = featureRepository.findAll().size();

        // Delete the feature
        restFeatureMockMvc
            .perform(delete("/api/features/{id}", feature.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
