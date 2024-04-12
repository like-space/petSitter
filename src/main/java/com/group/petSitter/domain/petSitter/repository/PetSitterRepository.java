package com.group.petSitter.domain.petSitter.repository;

import com.group.petSitter.domain.petSitter.PetSitter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PetSitterRepository extends JpaRepository<PetSitter,Long> {
    boolean existsByUsername(String username);

    Optional<PetSitter> findByUsername(String username);

}
