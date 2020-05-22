package com.rentalscayman.repository;

import com.rentalscayman.domain.UserRegistered;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UserRegistered entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserRegisteredRepository extends JpaRepository<UserRegistered, Long> {}
