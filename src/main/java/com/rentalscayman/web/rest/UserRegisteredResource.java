package com.rentalscayman.web.rest;

import com.rentalscayman.domain.UserRegistered;
import com.rentalscayman.repository.UserRegisteredRepository;
import com.rentalscayman.repository.UserRepository;
import com.rentalscayman.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing {@link com.rentalscayman.domain.UserRegistered}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class UserRegisteredResource {
    private final Logger log = LoggerFactory.getLogger(UserRegisteredResource.class);

    private static final String ENTITY_NAME = "userRegistered";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserRegisteredRepository userRegisteredRepository;

    private final UserRepository userRepository;

    public UserRegisteredResource(UserRegisteredRepository userRegisteredRepository, UserRepository userRepository) {
        this.userRegisteredRepository = userRegisteredRepository;
        this.userRepository = userRepository;
    }

    /**
     * {@code POST  /user-registereds} : Create a new userRegistered.
     *
     * @param userRegistered the userRegistered to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userRegistered, or with status {@code 400 (Bad Request)} if the userRegistered has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-registereds")
    public ResponseEntity<UserRegistered> createUserRegistered(@RequestBody UserRegistered userRegistered) throws URISyntaxException {
        log.debug("REST request to save UserRegistered : {}", userRegistered);
        if (userRegistered.getId() != null) {
            throw new BadRequestAlertException("A new userRegistered cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (Objects.isNull(userRegistered.getUser())) {
            throw new BadRequestAlertException("Invalid association value provided", ENTITY_NAME, "null");
        }
        Long userId = userRegistered.getUser().getId();
        userRepository.findById(userId).ifPresent(userRegistered::user);
        UserRegistered result = userRegisteredRepository.save(userRegistered);
        return ResponseEntity
            .created(new URI("/api/user-registereds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-registereds} : Updates an existing userRegistered.
     *
     * @param userRegistered the userRegistered to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userRegistered,
     * or with status {@code 400 (Bad Request)} if the userRegistered is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userRegistered couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-registereds")
    public ResponseEntity<UserRegistered> updateUserRegistered(@RequestBody UserRegistered userRegistered) throws URISyntaxException {
        log.debug("REST request to update UserRegistered : {}", userRegistered);
        if (userRegistered.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserRegistered result = userRegisteredRepository.save(userRegistered);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userRegistered.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-registereds} : get all the userRegistereds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userRegistereds in body.
     */
    @GetMapping("/user-registereds")
    @Transactional(readOnly = true)
    public List<UserRegistered> getAllUserRegistereds() {
        log.debug("REST request to get all UserRegistereds");
        return userRegisteredRepository.findAll();
    }

    /**
     * {@code GET  /user-registereds/:id} : get the "id" userRegistered.
     *
     * @param id the id of the userRegistered to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userRegistered, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-registereds/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<UserRegistered> getUserRegistered(@PathVariable Long id) {
        log.debug("REST request to get UserRegistered : {}", id);
        Optional<UserRegistered> userRegistered = userRegisteredRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(userRegistered);
    }

    /**
     * {@code DELETE  /user-registereds/:id} : delete the "id" userRegistered.
     *
     * @param id the id of the userRegistered to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-registereds/{id}")
    public ResponseEntity<Void> deleteUserRegistered(@PathVariable Long id) {
        log.debug("REST request to delete UserRegistered : {}", id);

        userRegisteredRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
