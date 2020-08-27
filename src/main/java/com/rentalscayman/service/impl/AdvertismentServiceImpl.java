package com.rentalscayman.service.impl;

import com.rentalscayman.domain.Advertisment;
import com.rentalscayman.repository.AdvertismentRepository;
import com.rentalscayman.service.AdvertismentService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Advertisment}.
 */
@Service
@Transactional
public class AdvertismentServiceImpl implements AdvertismentService {
    private final Logger log = LoggerFactory.getLogger(AdvertismentServiceImpl.class);

    private final AdvertismentRepository advertismentRepository;

    public AdvertismentServiceImpl(AdvertismentRepository advertismentRepository) {
        this.advertismentRepository = advertismentRepository;
    }

    /**
     * Save a advertisment.
     *
     * @param advertisment the entity to save.
     * @return the persisted entity.
     */
    @Override
    @Transactional(readOnly = false)
    public Advertisment save(Advertisment advertisment) {
        log.debug("Request to save Advertisment : {}", advertisment);
        return advertismentRepository.save(advertisment);
    }

    /**
     * Get all the advertisments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Advertisment> findAll(Pageable pageable) {
        log.debug("Request to get all Advertisments");
        return advertismentRepository.findAll(pageable);
    }

    /**
     * Get one advertisment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Advertisment> findOne(Long id) {
        log.debug("Request to get Advertisment : {}", id);
        return advertismentRepository.findById(id);
    }

    /**
     * Delete the advertisment by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Advertisment : {}", id);

        advertismentRepository.deleteById(id);
    }

    @Override
    public Page<Advertisment> findAdsByFilters(Specification<Advertisment> specs, Pageable pages) {
        return advertismentRepository.findAll(specs, pages);
    }
}
