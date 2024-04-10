package com.group.petSitter.domain.pet;

import com.group.petSitter.domain.user.support.UserFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PetTest {

    private static final String petName = "버터";

    @Nested
    @DisplayName("Pet 등록 시")
    class NewUserTest {

        @DisplayName("성공")
        @Test
        void success() {
            //given
            //when
            Pet pet = Pet.builder()
                    .petName(petName)
                    .petStatus(PetStatus.PENDING)
                    .user(UserFixture.user())
                    .build();

            //then
            assertThat(pet.getPetName()).isEqualTo(petName);
        }
    }
}
