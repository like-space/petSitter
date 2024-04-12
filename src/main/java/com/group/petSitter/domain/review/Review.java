package com.group.petSitter.domain.review;

import com.group.petSitter.domain.petSitter.PetSitter;
import com.group.petSitter.domain.user.User;
import com.group.petSitter.global.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_sitter_id")
    private PetSitter petSitter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String title;

    @Column
    private String content;
}
