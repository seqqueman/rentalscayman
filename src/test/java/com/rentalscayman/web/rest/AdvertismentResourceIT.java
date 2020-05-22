package com.rentalscayman.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rentalscayman.RentalscaymanApp;
import com.rentalscayman.domain.Advertisment;
import com.rentalscayman.domain.enumeration.PropertyType;
import com.rentalscayman.domain.enumeration.TypeAdvertisment;
import com.rentalscayman.repository.AdvertismentRepository;
import com.rentalscayman.service.AdvertismentService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link AdvertismentResource} REST controller.
 */
@SpringBootTest(classes = RentalscaymanApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AdvertismentResourceIT {
    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATE_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_MODIFIED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final TypeAdvertisment DEFAULT_TYPE_AD = TypeAdvertisment.FOR_RENT;
    private static final TypeAdvertisment UPDATED_TYPE_AD = TypeAdvertisment.FOR_SELL;

    private static final PropertyType DEFAULT_PROPERTY_TYPE = PropertyType.NEWDEVELOPMENT;
    private static final PropertyType UPDATED_PROPERTY_TYPE = PropertyType.HOME;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    @Autowired
    private AdvertismentRepository advertismentRepository;

    @Autowired
    private AdvertismentService advertismentService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdvertismentMockMvc;

    private Advertisment advertisment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Advertisment createEntity(EntityManager em) {
        Advertisment advertisment = new Advertisment()
            .description(DEFAULT_DESCRIPTION)
            .createAt(DEFAULT_CREATE_AT)
            .modifiedAt(DEFAULT_MODIFIED_AT)
            .typeAd(DEFAULT_TYPE_AD)
            .propertyType(DEFAULT_PROPERTY_TYPE)
            .active(DEFAULT_ACTIVE)
            .price(DEFAULT_PRICE)
            .reference(DEFAULT_REFERENCE);
        return advertisment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Advertisment createUpdatedEntity(EntityManager em) {
        Advertisment advertisment = new Advertisment()
            .description(UPDATED_DESCRIPTION)
            .createAt(UPDATED_CREATE_AT)
            .modifiedAt(UPDATED_MODIFIED_AT)
            .typeAd(UPDATED_TYPE_AD)
            .propertyType(UPDATED_PROPERTY_TYPE)
            .active(UPDATED_ACTIVE)
            .price(UPDATED_PRICE)
            .reference(UPDATED_REFERENCE);
        return advertisment;
    }

    @BeforeEach
    public void initTest() {
        advertisment = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdvertisment() throws Exception {
        int databaseSizeBeforeCreate = advertismentRepository.findAll().size();
        // Create the Advertisment
        restAdvertismentMockMvc
            .perform(
                post("/api/advertisments").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(advertisment))
            )
            .andExpect(status().isCreated());

        // Validate the Advertisment in the database
        List<Advertisment> advertismentList = advertismentRepository.findAll();
        assertThat(advertismentList).hasSize(databaseSizeBeforeCreate + 1);
        Advertisment testAdvertisment = advertismentList.get(advertismentList.size() - 1);
        assertThat(testAdvertisment.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAdvertisment.getCreateAt()).isEqualTo(DEFAULT_CREATE_AT);
        assertThat(testAdvertisment.getModifiedAt()).isEqualTo(DEFAULT_MODIFIED_AT);
        assertThat(testAdvertisment.getTypeAd()).isEqualTo(DEFAULT_TYPE_AD);
        assertThat(testAdvertisment.getPropertyType()).isEqualTo(DEFAULT_PROPERTY_TYPE);
        assertThat(testAdvertisment.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testAdvertisment.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testAdvertisment.getReference()).isEqualTo(DEFAULT_REFERENCE);
    }

    @Test
    @Transactional
    public void createAdvertismentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = advertismentRepository.findAll().size();

        // Create the Advertisment with an existing ID
        advertisment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdvertismentMockMvc
            .perform(
                post("/api/advertisments").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(advertisment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Advertisment in the database
        List<Advertisment> advertismentList = advertismentRepository.findAll();
        assertThat(advertismentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = advertismentRepository.findAll().size();
        // set the field null
        advertisment.setDescription(null);

        // Create the Advertisment, which fails.

        restAdvertismentMockMvc
            .perform(
                post("/api/advertisments").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(advertisment))
            )
            .andExpect(status().isBadRequest());

        List<Advertisment> advertismentList = advertismentRepository.findAll();
        assertThat(advertismentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = advertismentRepository.findAll().size();
        // set the field null
        advertisment.setCreateAt(null);

        // Create the Advertisment, which fails.

        restAdvertismentMockMvc
            .perform(
                post("/api/advertisments").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(advertisment))
            )
            .andExpect(status().isBadRequest());

        List<Advertisment> advertismentList = advertismentRepository.findAll();
        assertThat(advertismentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModifiedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = advertismentRepository.findAll().size();
        // set the field null
        advertisment.setModifiedAt(null);

        // Create the Advertisment, which fails.

        restAdvertismentMockMvc
            .perform(
                post("/api/advertisments").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(advertisment))
            )
            .andExpect(status().isBadRequest());

        List<Advertisment> advertismentList = advertismentRepository.findAll();
        assertThat(advertismentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeAdIsRequired() throws Exception {
        int databaseSizeBeforeTest = advertismentRepository.findAll().size();
        // set the field null
        advertisment.setTypeAd(null);

        // Create the Advertisment, which fails.

        restAdvertismentMockMvc
            .perform(
                post("/api/advertisments").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(advertisment))
            )
            .andExpect(status().isBadRequest());

        List<Advertisment> advertismentList = advertismentRepository.findAll();
        assertThat(advertismentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPropertyTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = advertismentRepository.findAll().size();
        // set the field null
        advertisment.setPropertyType(null);

        // Create the Advertisment, which fails.

        restAdvertismentMockMvc
            .perform(
                post("/api/advertisments").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(advertisment))
            )
            .andExpect(status().isBadRequest());

        List<Advertisment> advertismentList = advertismentRepository.findAll();
        assertThat(advertismentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = advertismentRepository.findAll().size();
        // set the field null
        advertisment.setActive(null);

        // Create the Advertisment, which fails.

        restAdvertismentMockMvc
            .perform(
                post("/api/advertisments").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(advertisment))
            )
            .andExpect(status().isBadRequest());

        List<Advertisment> advertismentList = advertismentRepository.findAll();
        assertThat(advertismentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = advertismentRepository.findAll().size();
        // set the field null
        advertisment.setPrice(null);

        // Create the Advertisment, which fails.

        restAdvertismentMockMvc
            .perform(
                post("/api/advertisments").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(advertisment))
            )
            .andExpect(status().isBadRequest());

        List<Advertisment> advertismentList = advertismentRepository.findAll();
        assertThat(advertismentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReferenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = advertismentRepository.findAll().size();
        // set the field null
        advertisment.setReference(null);

        // Create the Advertisment, which fails.

        restAdvertismentMockMvc
            .perform(
                post("/api/advertisments").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(advertisment))
            )
            .andExpect(status().isBadRequest());

        List<Advertisment> advertismentList = advertismentRepository.findAll();
        assertThat(advertismentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAdvertisments() throws Exception {
        // Initialize the database
        advertismentRepository.saveAndFlush(advertisment);

        // Get all the advertismentList
        restAdvertismentMockMvc
            .perform(get("/api/advertisments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(advertisment.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createAt").value(hasItem(DEFAULT_CREATE_AT.toString())))
            .andExpect(jsonPath("$.[*].modifiedAt").value(hasItem(DEFAULT_MODIFIED_AT.toString())))
            .andExpect(jsonPath("$.[*].typeAd").value(hasItem(DEFAULT_TYPE_AD.toString())))
            .andExpect(jsonPath("$.[*].propertyType").value(hasItem(DEFAULT_PROPERTY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)));
    }

    @Test
    @Transactional
    public void getAdvertisment() throws Exception {
        // Initialize the database
        advertismentRepository.saveAndFlush(advertisment);

        // Get the advertisment
        restAdvertismentMockMvc
            .perform(get("/api/advertisments/{id}", advertisment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(advertisment.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.createAt").value(DEFAULT_CREATE_AT.toString()))
            .andExpect(jsonPath("$.modifiedAt").value(DEFAULT_MODIFIED_AT.toString()))
            .andExpect(jsonPath("$.typeAd").value(DEFAULT_TYPE_AD.toString()))
            .andExpect(jsonPath("$.propertyType").value(DEFAULT_PROPERTY_TYPE.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE));
    }

    @Test
    @Transactional
    public void getNonExistingAdvertisment() throws Exception {
        // Get the advertisment
        restAdvertismentMockMvc.perform(get("/api/advertisments/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdvertisment() throws Exception {
        // Initialize the database
        advertismentService.save(advertisment);

        int databaseSizeBeforeUpdate = advertismentRepository.findAll().size();

        // Update the advertisment
        Advertisment updatedAdvertisment = advertismentRepository.findById(advertisment.getId()).get();
        // Disconnect from session so that the updates on updatedAdvertisment are not directly saved in db
        em.detach(updatedAdvertisment);
        updatedAdvertisment
            .description(UPDATED_DESCRIPTION)
            .createAt(UPDATED_CREATE_AT)
            .modifiedAt(UPDATED_MODIFIED_AT)
            .typeAd(UPDATED_TYPE_AD)
            .propertyType(UPDATED_PROPERTY_TYPE)
            .active(UPDATED_ACTIVE)
            .price(UPDATED_PRICE)
            .reference(UPDATED_REFERENCE);

        restAdvertismentMockMvc
            .perform(
                put("/api/advertisments")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAdvertisment))
            )
            .andExpect(status().isOk());

        // Validate the Advertisment in the database
        List<Advertisment> advertismentList = advertismentRepository.findAll();
        assertThat(advertismentList).hasSize(databaseSizeBeforeUpdate);
        Advertisment testAdvertisment = advertismentList.get(advertismentList.size() - 1);
        assertThat(testAdvertisment.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAdvertisment.getCreateAt()).isEqualTo(UPDATED_CREATE_AT);
        assertThat(testAdvertisment.getModifiedAt()).isEqualTo(UPDATED_MODIFIED_AT);
        assertThat(testAdvertisment.getTypeAd()).isEqualTo(UPDATED_TYPE_AD);
        assertThat(testAdvertisment.getPropertyType()).isEqualTo(UPDATED_PROPERTY_TYPE);
        assertThat(testAdvertisment.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testAdvertisment.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testAdvertisment.getReference()).isEqualTo(UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void updateNonExistingAdvertisment() throws Exception {
        int databaseSizeBeforeUpdate = advertismentRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdvertismentMockMvc
            .perform(
                put("/api/advertisments").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(advertisment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Advertisment in the database
        List<Advertisment> advertismentList = advertismentRepository.findAll();
        assertThat(advertismentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAdvertisment() throws Exception {
        // Initialize the database
        advertismentService.save(advertisment);

        int databaseSizeBeforeDelete = advertismentRepository.findAll().size();

        // Delete the advertisment
        restAdvertismentMockMvc
            .perform(delete("/api/advertisments/{id}", advertisment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Advertisment> advertismentList = advertismentRepository.findAll();
        assertThat(advertismentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
