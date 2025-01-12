package com.skravetz.apiadopcioncodigofacilito.domain.mappers;

import com.skravetz.apiadopcioncodigofacilito.domain.app.Pet;
import com.skravetz.apiadopcioncodigofacilito.domain.app.PetType;
import com.skravetz.apiadopcioncodigofacilito.domain.dto.CreatePetDto;
import com.skravetz.apiadopcioncodigofacilito.infrastructure.database.repositories.entities.PetEntity;
import com.skravetz.apiadopcioncodigofacilito.infrastructure.database.repositories.entities.PetTypeEntity;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Builder
@Slf4j
public class PetMapper {

    public static Pet mapSingleEntityToDomain(PetEntity pet) {
        return Pet.builder()
                  .age(pet.getAge())
                  .name(pet.getName())
                  .id(pet.getId())
                  .type(PetType.byKey(pet.getType().getId()))
                  .isAvailable(pet.getIsAvailable())
                  .build();
    }

    public static List<Pet> mapEntitiesToDomain(List<PetEntity> pets) {
        return pets.stream().map(PetMapper::mapSingleEntityToDomain).toList();
    }

    public static PetEntity mapDomainToEntity(Pet pet) {
        log.info("pet in mapper to entity: " + pet);
        return PetEntity.builder()
                        .name(pet.getName())
                        .age(pet.getAge())
                        .type(PetTypeEntity.builder()
                                           .id(pet.getType().getKey())
                                           .type(pet.getType().name())
                                           .build())
                        .isAvailable(pet.getIsAvailable())
                        .build();
    }

    public static Pet mapDtoToDomain(CreatePetDto dto) throws IllegalArgumentException {
        var mapped = Pet.builder();
        if (dto.getName() != null) {
            mapped.name(dto.getName());
        }
        if (dto.getAge() != null) {
            mapped.age(dto.getAge());
        }
        if (dto.getType() != null) {
            mapped.type(PetType.byValue(dto.getType().toString()));
        }
        if (dto.getIsAvailable() != null) {
            mapped.isAvailable(dto.getIsAvailable());
        }

        return mapped.build();
    }

    public static Pet mapUpdateDtoToDomain(Long id, CreatePetDto dto) throws IllegalArgumentException {
        var mapped = Pet.builder();
        mapped.id(id);

        if (dto.getName() != null) {
            mapped.name(dto.getName());
        }
        if (dto.getAge() != null) {
            mapped.age(dto.getAge());
        }
        if (dto.getType() != null) {
            mapped.type(PetType.byValue(dto.getType().toString()));
        }
        if (dto.getIsAvailable() != null) {
            mapped.isAvailable(dto.getIsAvailable());
        }

        return mapped.build();
    }

}
