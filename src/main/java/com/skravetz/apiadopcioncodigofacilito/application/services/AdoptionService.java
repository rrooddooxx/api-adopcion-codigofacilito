package com.skravetz.apiadopcioncodigofacilito.application.services;

import com.skravetz.apiadopcioncodigofacilito.domain.app.Adoption;
import com.skravetz.apiadopcioncodigofacilito.domain.app.AdoptionStatus;
import com.skravetz.apiadopcioncodigofacilito.domain.app.NewAdoption;
import com.skravetz.apiadopcioncodigofacilito.domain.exceptions.PetNotAvailableException;
import com.skravetz.apiadopcioncodigofacilito.domain.exceptions.PetNotFoundException;
import com.skravetz.apiadopcioncodigofacilito.domain.exceptions.UserNotFoundException;
import com.skravetz.apiadopcioncodigofacilito.domain.mappers.AdoptionMapper;
import com.skravetz.apiadopcioncodigofacilito.domain.mappers.UserMapper;
import com.skravetz.apiadopcioncodigofacilito.infrastructure.database.repositories.AdoptionRepository;
import com.skravetz.apiadopcioncodigofacilito.infrastructure.database.repositories.AdoptionStatusRepository;
import com.skravetz.apiadopcioncodigofacilito.infrastructure.database.repositories.PetRepository;
import com.skravetz.apiadopcioncodigofacilito.infrastructure.database.repositories.UserRepository;
import com.skravetz.apiadopcioncodigofacilito.infrastructure.database.repositories.entities.AdoptionEntity;
import com.skravetz.apiadopcioncodigofacilito.infrastructure.database.repositories.entities.AdoptionStatusEntity;
import com.skravetz.apiadopcioncodigofacilito.infrastructure.database.repositories.entities.PetEntity;
import com.skravetz.apiadopcioncodigofacilito.infrastructure.database.repositories.entities.UserEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AdoptionService {

    private final AdoptionRepository adoptionRepository;
    private final AdoptionStatusRepository adoptionStatusRepository;
    private final UserMapper userMapper;
    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final AdoptionMapper adoptionMapper;

    public Adoption createAdoptionRegistry(NewAdoption newAdoption) {
        Optional<PetEntity> pet =
            petRepository.findById(newAdoption.getPetId());

        Optional<UserEntity> adopter =
            Optional.ofNullable(userRepository.findByUsername(newAdoption.getAdopterUsername()));

        log.info("status key: {}", AdoptionStatus.SUCCESS
            .getKey()
            .longValue());
        Optional<AdoptionStatusEntity> adoptionStatus =
            adoptionStatusRepository.findById(AdoptionStatus.SUCCESS
                                                  .getKey()
                                                  .longValue());

        if (pet.isEmpty()) {
            throw new PetNotFoundException(newAdoption.getPetId());
        }

        if (adopter.isEmpty()) {
            throw new UserNotFoundException("Adopter not found!",
                                            newAdoption.getAdopterUsername());
        }

        if (!pet.get().getIsAvailable()) {
            throw new PetNotAvailableException(newAdoption.getPetId());
        }

        if (adoptionStatus.isEmpty()) {
            throw new PetNotFoundException(newAdoption.getPetId());
        }

        System.out.println(adoptionStatus.get());

        AdoptionEntity checkedAdoption = AdoptionEntity
            .builder()
            .adopter(adopter.get())
            .pet(pet.get())
            .adoptionDate(LocalDateTime.now().toString())
            .adoptionStatus(adoptionStatus.get())
            .build();

        PetEntity foundPet = pet.get();
        foundPet.setIsAvailable(false);

        petRepository.save(foundPet);

        return adoptionMapper.mapEntityToDomain(adoptionRepository.save(checkedAdoption));
    }

    public List<Adoption> getAdoptions() {
        return adoptionMapper.mapEntitiesToDomain(adoptionRepository.findAll());
    }

}
