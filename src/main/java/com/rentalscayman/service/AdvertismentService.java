package com.rentalscayman.service;

import com.rentalscayman.domain.Advertisment;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Advertisment}.
 */
public interface AdvertismentService {
    /**
     * Save a advertisment.
     *
     * @param advertisment the entity to save.
     * @return the persisted entity.
     */
    Advertisment save(Advertisment advertisment);

    /**
     * Get all the advertisments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Advertisment> findAll(Pageable pageable);

    /**
     * Get the "id" advertisment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Advertisment> findOne(Long id);

    /**
     * Delete the "id" advertisment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
