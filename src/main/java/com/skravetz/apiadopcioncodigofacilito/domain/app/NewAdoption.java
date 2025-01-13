package com.skravetz.apiadopcioncodigofacilito.domain.app;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewAdoption {
    private Long id;
    private Long petId;
    private String adopterUsername;
    private LocalDateTime adoptionDate;
    private AdoptionStatus adoptionStatus;

}
