package com.skravetz.apiadopcioncodigofacilito.domain.dto;

import com.skravetz.apiadopcioncodigofacilito.domain.app.PetType;
import lombok.Data;

@Data
public class CreatePetDto {
    private String name;
    private Integer age;
    private PetType type;
    private Boolean isAvailable;

}
