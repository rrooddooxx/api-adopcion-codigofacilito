package com.skravetz.apiadopcioncodigofacilito.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateAdoptionDto {
    private Long petId;
    private String adopterUsername;

}
