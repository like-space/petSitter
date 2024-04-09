package com.group.petSitter.domain.pet.repository;


import com.group.petSitter.domain.pet.Pet;
import com.group.petSitter.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet,Long> {
    @Query("select p from Pet p where p.user = :user")
    List<Pet> findPetsByUser(@Param("user")User user);

    @Query("select p from Pet p where p.petId = :petId")
    Pet findPetByPetId(@Param("petId")Long petId);

}
