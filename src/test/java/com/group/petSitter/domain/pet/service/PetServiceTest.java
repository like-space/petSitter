package com.group.petSitter.domain.pet.service;

import com.group.petSitter.domain.coupon.exception.NotFoundCouponException;
import com.group.petSitter.domain.pet.Pet;
import com.group.petSitter.domain.pet.controller.request.UpdatePetRequest;
import com.group.petSitter.domain.pet.exception.NotFoundPetException;
import com.group.petSitter.domain.pet.repository.PetRepository;
import com.group.petSitter.domain.pet.service.request.CreatePetCommand;
import com.group.petSitter.domain.pet.service.request.UpdatePetCommand;
import com.group.petSitter.domain.pet.service.response.FindPetDetailResponse;
import com.group.petSitter.domain.pet.service.response.FindPetsByUserResponse;
import com.group.petSitter.domain.user.User;
import com.group.petSitter.domain.user.repository.UserRepository;
import com.group.petSitter.domain.user.support.UserFixture;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.group.petSitter.domain.pet.support.PetFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class PetServiceTest {

    @InjectMocks
    private PetService petService;

    @Mock
    private PetRepository petRepository;

    @Mock
    private UserRepository userRepository;

    Pet pet;
    User user;

    @BeforeEach
    void setUp(){
        user = UserFixture.user();
        pet = pet(user);
    }

    @Nested
    @DisplayName("savePet 실행 시")
    class SavePetTest{

        @Test
        @DisplayName("반려동물 저장성공")
        void savePet(){
            //given
            CreatePetCommand petCommand = createPetCommand(pet.getUser().getUserId());

            //when
            when(userRepository.findById(any())).thenReturn(Optional.of(user));

            FindPetsByUserResponse findPetsByUserResponse = petService.savePet(petCommand);

            //then
            assertThat(findPetsByUserResponse).usingRecursiveComparison().isEqualTo(findPetsByUserResponse(pet));
        }
    }
    @Nested
    @DisplayName("updatePet 실행 시")
    class UpdatePetTest{

        @Test
        @DisplayName("수정완료")
        void updatePet(){
            //given
            UpdatePetRequest updatePetRequest = updatePetRequest("치즈");
            UpdatePetCommand updatePetCommand = updatePetCommand(pet.getUser().getUserId(), pet.getPetId(), updatePetRequest);

            //when
            when(userRepository.findById(any())).thenReturn(Optional.of(user));
            when(petRepository.findPetByUserAndPetId(any(),any())).thenReturn(Optional.of(pet));
            FindPetDetailResponse findPetDetailResponse = petService.updatePet(updatePetCommand);

            //then
            assertThat(findPetDetailResponse.petName()).isEqualTo(pet.getPetName());
        }

        @Test
        @DisplayName("예외: 해당 반려동물에 권한이 없는 유저 접근하여 수정 시")
        void throwExceptionWhenNotPetOwner() {
            User anotherUser = User.builder()
                    .nickname("김경하")
                    .email("rudgkrk11@naver.com")
                    .build();

            //given
            given(userRepository.findById(any())).willReturn(Optional.of(user));
            given(userRepository.findById(any())).willReturn(Optional.of(anotherUser));

            UpdatePetRequest updatePetRequest = updatePetRequest("치즈");
            UpdatePetCommand updatePetCommand = updatePetCommand(anotherUser.getUserId(), pet.getPetId(), updatePetRequest);

            //when
            Exception exception =
                    catchException(() -> petService.updatePet(updatePetCommand));

            //then
            assertThat(exception).isInstanceOf(NotFoundPetException.class);
        }
    }



}
