package com.skravetz.apiadopcioncodigofacilito.domain.app;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Pet {
    private Long id;
    private String name;
    private PetType type;
    private Integer age;
    private Boolean isAvailable;

}
