package com.skravetz.apiadopcioncodigofacilito.domain.exceptions;

public class PetNotFoundException extends RuntimeException {
    public PetNotFoundException(Long id) {
        super(String.format("PET WITH ID (%s) NOT FOUND!", id));
    }

}
