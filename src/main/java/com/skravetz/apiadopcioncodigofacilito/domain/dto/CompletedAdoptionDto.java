package com.skravetz.apiadopcioncodigofacilito.domain.dto;

import com.skravetz.apiadopcioncodigofacilito.domain.app.AdoptionStatus;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class CompletedAdoptionDto {
    private Long id;
    private Map<String, String> petDetails;
    private Map<String, String> adopterDetails;
    private String adoptionDate;
    private AdoptionStatus adoptionStatus;

}
