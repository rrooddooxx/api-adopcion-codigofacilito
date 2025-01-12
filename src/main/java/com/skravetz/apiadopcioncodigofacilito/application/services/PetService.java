package com.skravetz.apiadopcioncodigofacilito.application.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skravetz.apiadopcioncodigofacilito.domain.app.Pet;
import com.skravetz.apiadopcioncodigofacilito.domain.exceptions.PetNotFoundException;
import com.skravetz.apiadopcioncodigofacilito.domain.mappers.PetMapper;
import com.skravetz.apiadopcioncodigofacilito.infrastructure.database.repositories.PetRepository;
import com.skravetz.apiadopcioncodigofacilito.infrastructure.database.repositories.entities.PetEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PetService {

    private final PetRepository petRepository;

    public List<Pet> getPets() {
        return PetMapper.mapEntitiesToDomain(this.petRepository.findAll());
    }

    public Pet getPetById(Long id) throws PetNotFoundException {
        return PetMapper.mapSingleEntityToDomain(this.petRepository
                                                     .findById(id)
                                                     .orElseThrow(() -> new PetNotFoundException(id)));
    }

    public Pet createNewPet(Pet newPet) {
        return PetMapper.mapSingleEntityToDomain(
            this.petRepository.save(PetMapper.mapDomainToEntity(newPet)));
    }

    public Pet updatePetById(Long id, Pet updatedPet) {
        Optional<PetEntity> searchingPet = this.petRepository.findById(id);
        log.info("INTO UPDATE PET.... {}", updatedPet.toString());

        if (searchingPet.isEmpty()) {
            throw new PetNotFoundException(id);
        }

        PetEntity foundPet = searchingPet.get();

        Map<String, Object> mappedUpdatedPet = convertToMap(updatedPet);
        mappedUpdatedPet.forEach((key, value) -> {
            if (value != null) {
                try {
                    Field field = foundPet.getClass().getDeclaredField(key);
                    field.setAccessible(true);
                    field.set(foundPet, value);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    log.error("ERROR ACTUALIZANDO CAMPO EN MASCOTA: {}", key,
                              e);
                }
            }
        });

        return PetMapper.mapSingleEntityToDomain(this.petRepository.save(foundPet));
    }

    public void deletePetById(Long id) throws PetNotFoundException {
        Pet foundPet = getPetById(id);

        if (foundPet == null) {
            throw new PetNotFoundException(id);
        }

        this.petRepository.deleteById(id);
    }

    private Map<String, Object> convertToMap(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(obj, new TypeReference<>() {
        });
    }

}
