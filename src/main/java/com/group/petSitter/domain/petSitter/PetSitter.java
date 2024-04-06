package com.group.petSitter.domain.petSitter;

import com.group.petSitter.domain.pet.Pet;
import com.group.petSitter.domain.pet.PetStatus;
import com.group.petSitter.domain.review.Review;
import com.group.petSitter.global.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PetSitter extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long petSitterId;

    @OneToMany(mappedBy = "petSitter")
    private List<Pet> pets = new ArrayList<>();

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PetStatus status = PetStatus.PENDING;

    @OneToMany(mappedBy = "petSitter", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

}
