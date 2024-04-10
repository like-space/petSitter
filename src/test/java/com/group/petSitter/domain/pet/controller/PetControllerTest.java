package com.group.petSitter.domain.pet.controller;
import com.group.petSitter.base.BaseControllerTest;
import com.group.petSitter.domain.pet.Pet;
import com.group.petSitter.domain.pet.service.response.FindPetDetailResponse;
import com.group.petSitter.domain.pet.service.response.FindPetsByUserResponse;
import com.group.petSitter.domain.user.User;
import com.group.petSitter.domain.user.support.UserFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.group.petSitter.domain.pet.support.PetFixture.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class PetControllerTest extends BaseControllerTest {

    @Nested
    @DisplayName("반려동물을 등록하는 api 호출시")
    class SavePet {

        @Test
        @DisplayName("성공")
        void savePet() throws Exception {
            // Given
            User user = UserFixture.user();
            Pet pet = pet(user);
            FindPetsByUserResponse findPetsByUserResponse = FindPetsByUserResponse.from(List.of(pet));

            when(petService.savePet(any())).thenReturn(findPetsByUserResponse);

            // when
            ResultActions result = mockMvc.perform(
                    post("/api/v1/pets")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(createPetRequest()))
                            .header(AUTHORIZATION, accessToken));

            // then
            result
                    .andExpect(status().isCreated())
                    .andDo(restDocs.document(
                            relaxedResponseFields(
                                    fieldWithPath("response.pets").type(ARRAY).description("반려동물 리스트"),
                                    fieldWithPath("response.pets[].petName").type(STRING).description("반려동물 이름")
                            )
                    ));
        }
    }

    @Nested
    @DisplayName("반려동물 수정하는 api 호출시")
    class UpdatePet {

        @DisplayName("수정성공")
        @Test
        void upatePet() throws Exception{

            User user = UserFixture.user();
            Pet pet = pet(user);
            pet.updatePet("치즈");

            FindPetDetailResponse findPetDetailResponse = FindPetDetailResponse.from(pet);
            //given
            when(petService.updatePet(anyLong(),any())).thenReturn(findPetDetailResponse);

            // when
            ResultActions result = mockMvc.perform(
                    patch("/api/v1/pets/{petId}",1L)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updatePetRequest("치즈")))
                            .header(AUTHORIZATION, accessToken));

            // then
            result
                    .andExpect(status().isOk())
                    .andDo(restDocs.document(
                            relaxedResponseFields(
                                    fieldWithPath("response.petName").type(STRING).description("반려동물 이름")
                            )
                    ));
        }

    }

    @Nested
    @DisplayName("반려동물 삭제하는 api 호출시")
    class DeletePet {

        @DisplayName("수정성공")
        @Test
        void deletePet() throws Exception{

            User user = UserFixture.user();
            Pet pet = pet(user);
            //given

            // when
            ResultActions result = mockMvc.perform(
                    delete("/api/v1/pets/{petId}",1L)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header(AUTHORIZATION, accessToken));

            // then
            result
                    .andExpect(status().isNoContent())
                    .andDo(restDocs.document(
                            pathParameters(
                                    parameterWithName("petId").description("주문 ID")
                            )
                    ));
        }

    }
}

