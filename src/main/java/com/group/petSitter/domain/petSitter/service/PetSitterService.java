package com.group.petSitter.domain.petSitter.service;


import com.group.petSitter.domain.petSitter.repository.PetSitterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PetSitterService {

    private PetSitterRepository petSitterRepository;

}
