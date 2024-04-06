package com.group.petSitter.domain.pet;

import com.group.petSitter.global.BaseTimeEntity;
import com.group.petSitter.domain.user.User;
import com.group.petSitter.domain.petSitter.PetSitter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

}
