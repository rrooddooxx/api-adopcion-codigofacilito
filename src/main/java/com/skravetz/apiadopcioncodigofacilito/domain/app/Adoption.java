package com.skravetz.apiadopcioncodigofacilito.domain.app;

import com.skravetz.apiadopcioncodigofacilito.infrastructure.database.repositories.entities.AdoptionStatusEntity;
import com.skravetz.apiadopcioncodigofacilito.infrastructure.database.repositories.entities.PetEntity;
import com.skravetz.apiadopcioncodigofacilito.infrastructure.database.repositories.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Adoption {
  private Long id;
  private PetEntity pet;
  private UserEntity adopter;
  private String adoptionDate;
  private AdoptionStatusEntity adoptionStatus;
}
