package com.skravetz.apiadopcioncodigofacilito.domain.app;

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
    private Pet pet;
    private User adopter;
    private String adoptionDate;
    private AdoptionStatus adoptionStatus;

}
