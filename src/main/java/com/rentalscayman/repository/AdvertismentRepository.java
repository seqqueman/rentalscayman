package com.rentalscayman.repository;

import com.rentalscayman.domain.Advertisment;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the Advertisment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdvertismentRepository extends JpaRepository<Advertisment, Long>, JpaSpecificationExecutor<Advertisment> {
    @Query("select advertisment from Advertisment advertisment where advertisment.user.login = ?#{principal.username}")
    List<Advertisment> findByUserIsCurrentUser();
}
