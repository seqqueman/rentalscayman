package com.rentalscayman.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rentalscayman.RentalscaymanApp;
import com.rentalscayman.domain.User;
import com.rentalscayman.domain.UserRegistered;
import com.rentalscayman.repository.UserRegisteredRepository;
import com.rentalscayman.repository.UserRepository;
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
 * Integration tests for the {@link UserRegisteredResource} REST controller.
 */
@SpringBootTest(classes = RentalscaymanApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserRegisteredResourceIT {
    private static final Boolean DEFAULT_ADVERTISER = false;
    private static final Boolean UPDATED_ADVERTISER = true;

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMBER_OF_ADS = 1;
    private static final Integer UPDATED_NUMBER_OF_ADS = 2;

    @Autowired
    private UserRegisteredRepository userRegisteredRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserRegisteredMockMvc;

    private UserRegistered userRegistered;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserRegistered createEntity(EntityManager em) {
        UserRegistered userRegistered = new UserRegistered()
            .advertiser(DEFAULT_ADVERTISER)
            .phone(DEFAULT_PHONE)
            .numberOfAds(DEFAULT_NUMBER_OF_ADS);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        userRegistered.setUser(user);
        return userRegistered;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserRegistered createUpdatedEntity(EntityManager em) {
        UserRegistered userRegistered = new UserRegistered()
            .advertiser(UPDATED_ADVERTISER)
            .phone(UPDATED_PHONE)
            .numberOfAds(UPDATED_NUMBER_OF_ADS);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        userRegistered.setUser(user);
        return userRegistered;
    }

    @BeforeEach
    public void initTest() {
        userRegistered = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserRegistered() throws Exception {
        int databaseSizeBeforeCreate = userRegisteredRepository.findAll().size();
        // Create the UserRegistered
        restUserRegisteredMockMvc
            .perform(
                post("/api/user-registereds")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userRegistered))
            )
            .andExpect(status().isCreated());

        // Validate the UserRegistered in the database
        List<UserRegistered> userRegisteredList = userRegisteredRepository.findAll();
        assertThat(userRegisteredList).hasSize(databaseSizeBeforeCreate + 1);
        UserRegistered testUserRegistered = userRegisteredList.get(userRegisteredList.size() - 1);
        assertThat(testUserRegistered.isAdvertiser()).isEqualTo(DEFAULT_ADVERTISER);
        assertThat(testUserRegistered.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testUserRegistered.getNumberOfAds()).isEqualTo(DEFAULT_NUMBER_OF_ADS);

        // Validate the id for MapsId, the ids must be same
        assertThat(testUserRegistered.getId()).isEqualTo(testUserRegistered.getUser().getId());
    }

    @Test
    @Transactional
    public void createUserRegisteredWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userRegisteredRepository.findAll().size();

        // Create the UserRegistered with an existing ID
        userRegistered.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserRegisteredMockMvc
            .perform(
                post("/api/user-registereds")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userRegistered))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserRegistered in the database
        List<UserRegistered> userRegisteredList = userRegisteredRepository.findAll();
        assertThat(userRegisteredList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void updateUserRegisteredMapsIdAssociationWithNewId() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);
        int databaseSizeBeforeCreate = userRegisteredRepository.findAll().size();

        // Add a new parent entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();

        // Load the userRegistered
        UserRegistered updatedUserRegistered = userRegisteredRepository.findById(userRegistered.getId()).get();
        // Disconnect from session so that the updates on updatedUserRegistered are not directly saved in db
        em.detach(updatedUserRegistered);

        // Update the User with new association value
        updatedUserRegistered.setUser(user);

        // Update the entity
        restUserRegisteredMockMvc
            .perform(
                put("/api/user-registereds")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUserRegistered))
            )
            .andExpect(status().isOk());

        // Validate the UserRegistered in the database
        List<UserRegistered> userRegisteredList = userRegisteredRepository.findAll();
        assertThat(userRegisteredList).hasSize(databaseSizeBeforeCreate);
        UserRegistered testUserRegistered = userRegisteredList.get(userRegisteredList.size() - 1);
        // Validate the id for MapsId, the ids must be same
        // Uncomment the following line for assertion. However, please note that there is a known issue and uncommenting will fail the test.
        // Please look at https://github.com/jhipster/generator-jhipster/issues/9100. You can modify this test as necessary.
        // assertThat(testUserRegistered.getId()).isEqualTo(testUserRegistered.getUser().getId());
    }

    @Test
    @Transactional
    public void getAllUserRegistereds() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList
        restUserRegisteredMockMvc
            .perform(get("/api/user-registereds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userRegistered.getId().intValue())))
            .andExpect(jsonPath("$.[*].advertiser").value(hasItem(DEFAULT_ADVERTISER.booleanValue())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].numberOfAds").value(hasItem(DEFAULT_NUMBER_OF_ADS)));
    }

    @Test
    @Transactional
    public void getUserRegistered() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get the userRegistered
        restUserRegisteredMockMvc
            .perform(get("/api/user-registereds/{id}", userRegistered.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userRegistered.getId().intValue()))
            .andExpect(jsonPath("$.advertiser").value(DEFAULT_ADVERTISER.booleanValue()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.numberOfAds").value(DEFAULT_NUMBER_OF_ADS));
    }

    @Test
    @Transactional
    public void getNonExistingUserRegistered() throws Exception {
        // Get the userRegistered
        restUserRegisteredMockMvc.perform(get("/api/user-registereds/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserRegistered() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        int databaseSizeBeforeUpdate = userRegisteredRepository.findAll().size();

        // Update the userRegistered
        UserRegistered updatedUserRegistered = userRegisteredRepository.findById(userRegistered.getId()).get();
        // Disconnect from session so that the updates on updatedUserRegistered are not directly saved in db
        em.detach(updatedUserRegistered);
        updatedUserRegistered.advertiser(UPDATED_ADVERTISER).phone(UPDATED_PHONE).numberOfAds(UPDATED_NUMBER_OF_ADS);

        restUserRegisteredMockMvc
            .perform(
                put("/api/user-registereds")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUserRegistered))
            )
            .andExpect(status().isOk());

        // Validate the UserRegistered in the database
        List<UserRegistered> userRegisteredList = userRegisteredRepository.findAll();
        assertThat(userRegisteredList).hasSize(databaseSizeBeforeUpdate);
        UserRegistered testUserRegistered = userRegisteredList.get(userRegisteredList.size() - 1);
        assertThat(testUserRegistered.isAdvertiser()).isEqualTo(UPDATED_ADVERTISER);
        assertThat(testUserRegistered.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testUserRegistered.getNumberOfAds()).isEqualTo(UPDATED_NUMBER_OF_ADS);
    }

    @Test
    @Transactional
    public void updateNonExistingUserRegistered() throws Exception {
        int databaseSizeBeforeUpdate = userRegisteredRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserRegisteredMockMvc
            .perform(
                put("/api/user-registereds")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userRegistered))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserRegistered in the database
        List<UserRegistered> userRegisteredList = userRegisteredRepository.findAll();
        assertThat(userRegisteredList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserRegistered() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        int databaseSizeBeforeDelete = userRegisteredRepository.findAll().size();

        // Delete the userRegistered
        restUserRegisteredMockMvc
            .perform(delete("/api/user-registereds/{id}", userRegistered.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserRegistered> userRegisteredList = userRegisteredRepository.findAll();
        assertThat(userRegisteredList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
