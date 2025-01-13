package com.skravetz.apiadopcioncodigofacilito.domain.mappers;

import com.skravetz.apiadopcioncodigofacilito.domain.app.Adoption;
import com.skravetz.apiadopcioncodigofacilito.domain.app.AdoptionStatus;
import com.skravetz.apiadopcioncodigofacilito.domain.app.NewAdoption;
import com.skravetz.apiadopcioncodigofacilito.domain.dto.CompletedAdoptionDto;
import com.skravetz.apiadopcioncodigofacilito.domain.dto.CreateAdoptionDto;
import com.skravetz.apiadopcioncodigofacilito.infrastructure.database.repositories.entities.AdoptionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AdoptionMapper {

    private final UserMapper userMapper;

    public Adoption mapEntityToDomain(AdoptionEntity adoption) {
        return Adoption.builder()
                       .id(adoption.getId())
                       .adopter(userMapper.mapEntityToDomain(adoption.getAdopter()))
                       .pet(PetMapper.mapSingleEntityToDomain(adoption.getPet()))
                       .adoptionDate(adoption.getAdoptionDate())
                       .adoptionStatus(AdoptionStatus.byKey(adoption
                                                                .getAdoptionStatus()
                                                                .getId()
                                                                .intValue()))
                       .build();
    }

    public List<Adoption> mapEntitiesToDomain(List<AdoptionEntity> adoptions) {
        return adoptions.stream()
                        .map(this::mapEntityToDomain)
                        .collect(Collectors.toList());
    }

    public AdoptionEntity mapDomainToEntity(Adoption adoption) {
        return AdoptionEntity.builder()
                             .id(adoption.getId())
                             .adopter(userMapper.mapDomainToEntity(adoption.getAdopter()))
                             .pet(PetMapper.mapDomainToEntity(adoption.getPet()))
                             .adoptionDate(adoption.getAdoptionDate())
                             .build();
    }

    public NewAdoption mapNewAdoptionDtoToDomain(CreateAdoptionDto newAdoptionDto) {
        return NewAdoption.builder()
                          .adopterUsername(newAdoptionDto.getAdopterUsername())
                          .petId(newAdoptionDto.getPetId())
                          .build();
    }

    public CompletedAdoptionDto mapCompletedAdoptionToDto(Adoption adoption) {
        Map<String, String> adoptionDetails = new HashMap<>();
        adoptionDetails.put("adopter_username", adoption
            .getAdopter()
            .getUsername());
        adoptionDetails.put("adopter_email", adoption.getAdopter().getEmail());

        Map<String, String> petDetails = new HashMap<>();
        petDetails.put("pet_id", adoption.getPet().getId().toString());
        petDetails.put("pet_name", adoption.getPet().getName());

        return CompletedAdoptionDto.builder()
                                   .id(adoption.getId())
                                   .adopterDetails(adoptionDetails)
                                   .petDetails(petDetails)
                                   .adoptionDate(adoption.getAdoptionDate())
                                   .adoptionStatus(adoption
                                                       .getAdoptionStatus())
                                   .build();
    }

    public List<CompletedAdoptionDto> mapListOfCompletedAdoptionsToDtos(List<Adoption> adoptions) {
        return adoptions.stream()
                        .map(this::mapCompletedAdoptionToDto)
                        .collect(Collectors.toList());
    }

}
