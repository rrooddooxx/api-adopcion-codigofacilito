package com.skravetz.apiadopcioncodigofacilito.domain.app;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public enum PetType {
    DOG(1, "Dog"),
    CAT(2, "Cat"),
    BIRD(3, "Bird"),
    REPTILE(4, "Reptile"),
    RODENT(5, "Rodent"),
    MOLLUSK(6, "Mollusk"),
    FISH(7, "Fish"),
    AMPHIBIAN(8, "Amphibian"),
    INVERTEBRATE(9, "Invertebrate"),
    ARACHNID(10, "Arachnid"),
    INSECT(11, "Insect"),
    MAMMAL(12, "Mammal"),
    CRUSTACEAN(13, "Crustacean"),
    OTHER(14, "Other");

    private final String name;
    private final Integer key;

    PetType(Integer key, String name) {
        this.name = name;
        this.key = key;
    }

    public static PetType byKey(Integer key) {
        for (PetType type : PetType.values()) {
            if (type.getKey().equals(key)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid key");
    }

    public static PetType byValue(String value) throws IllegalArgumentException {
        for (PetType type : PetType.values()) {
            if (type.getName().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid value");
    }
}
