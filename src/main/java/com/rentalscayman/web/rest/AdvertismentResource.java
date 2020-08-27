package com.rentalscayman.web.rest;

import com.google.common.base.Joiner;
import com.rentalscayman.domain.Advertisment;
import com.rentalscayman.repository.AdvertismentSpecification;
import com.rentalscayman.repository.AdvertismentSpecificationsBuilder;
import com.rentalscayman.security.SecurityUtils;
import com.rentalscayman.service.AdvertismentService;
import com.rentalscayman.service.UserService;
import com.rentalscayman.web.rest.errors.BadRequestAlertException;
import com.rentalscayman.web.util.SearchOperation;
import com.rentalscayman.web.util.SpecSearchCriteria;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * REST controller for managing {@link com.rentalscayman.domain.Advertisment}.
 */
@RestController
@RequestMapping("/api")
public class AdvertismentResource {
    private final Logger log = LoggerFactory.getLogger(AdvertismentResource.class);

    private static final String ENTITY_NAME = "advertisment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdvertismentService advertismentService;

    private final UserService userService;

    public AdvertismentResource(AdvertismentService advertismentService, UserService userService) {
        this.advertismentService = advertismentService;
        this.userService = userService;
    }

    /**
     * {@code POST  /advertisments} : Create a new advertisment.
     *
     * @param advertisment the advertisment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new advertisment, or with status {@code 400 (Bad Request)} if the advertisment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/advertisments")
    public ResponseEntity<Advertisment> createAdvertisment(@Valid @RequestBody Advertisment advertisment) throws URISyntaxException {
        log.debug("REST request to save Advertisment : {}", advertisment);
        if (advertisment.getId() != null) {
            throw new BadRequestAlertException("A new advertisment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        advertisment.setUser(userService.getUserWithAuthorities().orElse(null));
        Advertisment result = advertismentService.save(advertisment);
        return ResponseEntity
            .created(new URI("/api/advertisments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /advertisments} : Updates an existing advertisment.
     *
     * @param advertisment the advertisment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated advertisment,
     * or with status {@code 400 (Bad Request)} if the advertisment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the advertisment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/advertisments")
    public ResponseEntity<Advertisment> updateAdvertisment(@Valid @RequestBody Advertisment advertisment) throws URISyntaxException {
        log.debug("REST request to update Advertisment : {}", advertisment);
        if (advertisment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        advertisment.setUser(userService.getUserWithAuthorities().orElse(null));
        Advertisment result = advertismentService.save(advertisment);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, advertisment.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /advertisments} : get all the advertisments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of advertisments in body.
     */
    @GetMapping("/advertisments")
    public ResponseEntity<List<Advertisment>> getAllAdvertisments(Pageable pageable) {
        log.debug("REST request to get a page of Advertisments");
        AdvertismentSpecification spec = new AdvertismentSpecification(
            new SpecSearchCriteria("active", SearchOperation.EQUALITY, Boolean.TRUE)
        );
        Page<Advertisment> page = advertismentService.findAdsByFilters(spec, pageable);
        //advertismentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /advertisments/:id} : get the "id" advertisment.
     *
     * @param id the id of the advertisment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the advertisment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/advertisments/{id}")
    public ResponseEntity<Advertisment> getAdvertisment(@PathVariable Long id) {
        log.debug("REST request to get Advertisment : {}", id);
        Optional<Advertisment> advertisment = advertismentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(advertisment);
    }

    /**
     * {@code DELETE  /advertisments/:id} : delete the "id" advertisment.
     *
     * @param id the id of the advertisment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/advertisments/{id}")
    public ResponseEntity<Void> deleteAdvertisment(@PathVariable Long id) {
        log.debug("REST request to delete Advertisment : {}", id);

        advertismentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/advertisments/search")
    public ResponseEntity<List<Advertisment>> search(@RequestParam("search") String search, Pageable pageable) {
        AdvertismentSpecificationsBuilder builder = new AdvertismentSpecificationsBuilder();
        String operationSetExper = Joiner.on("|").join(SearchOperation.SIMPLE_OPERATION_SET);
        Pattern pattern = Pattern.compile("(\\p{Punct}?)(\\w+?)(" + operationSetExper + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(5), matcher.group(4), matcher.group(6));
        }

        Specification<Advertisment> spec = builder.build();
        Page<Advertisment> page = advertismentService.findAdsByFilters(spec, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
