package com.group.petSitter.domain.pet;

import com.group.petSitter.domain.walk.PetSitter;
import com.group.petSitter.global.BaseTimeEntity;
import com.group.petSitter.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Pet extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long petId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String petName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_sitter_id")
    private PetSitter petSitter;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PetStatus petStatus;

    @Builder
    public Pet(User user, String petName, PetSitter petSitter,PetStatus petStatus) {
        this.user = user;
        this.petName = petName;
        this.petSitter = petSitter;
        this.petStatus = petStatus;
    }

    public void updatePet(String petName){
        this.petName = petName;
    }

}
