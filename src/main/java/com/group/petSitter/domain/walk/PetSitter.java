package com.group.petSitter.domain.walk;

import com.group.petSitter.domain.review.Review;
import com.group.petSitter.domain.user.exception.InvalidUserException;
import com.group.petSitter.global.BaseTimeEntity;
import com.group.petSitter.global.auth.exception.InvalidPasswordException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PetSitter extends BaseTimeEntity {

    private static final Pattern PET_SITTER_NAME_PATTERN = Pattern.compile("^(?=.*[a-z])[a-z0-9]{6,20}$");
    private static final Pattern PET_SITTER_PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-z])(?=.*\\d)[a-z\\d]{8,20}$");
    private static final int ADDRESS_LENGTH = 200;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long petSitterId;

    @Column(nullable = false,unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column
    private String address;

    @OneToMany(mappedBy = "petSitter", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @Builder
    public PetSitter(String username, String password, String address) {
        validatePetSittername(username);
        validatePetSitterPassword(password);
        validatePetSitterAddress(address);

        this.username = username;
        this.password = password;
        this.address = address;
    }

    public void validatePetSittername(String username){
        if(!PET_SITTER_NAME_PATTERN.matcher(username).matches()){
            throw new InvalidUserException("사용자 이름은 영어 소문자 또는 영어 소문자와 숫자 6자 이상, 20자 이하로 구성 되어야 합니다.");
        };
    }

    public void validatePetSitterPassword(String password){
        if(!PET_SITTER_PASSWORD_PATTERN.matcher(password).matches()){
            throw new InvalidPasswordException("패스워드는 영어 소문자, 숫자 8자 이상, 20자 이하로 구성 되어야 합니다.");
        }
    }

    public void validatePetSitterAddress(String address){
        if (ADDRESS_LENGTH > address.length())
            throw new InvalidUserException("주소의 길이는 최대 200자 입니다.");
    }

}
