package com.group.petSitter.domain.pet.repository;


import com.group.petSitter.domain.pet.Pet;
import com.group.petSitter.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet,Long> {
    List<Pet> findPetsByUser(@Param("user")User user);
    Optional<Pet> findPetByPetId(@Param("petId") Long petId);
    Optional<Pet> findPetByUserAndPetId(@Param("user") User user, @Param("petId") Long petId);


}
