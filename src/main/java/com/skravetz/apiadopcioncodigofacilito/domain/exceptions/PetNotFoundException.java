package com.skravetz.apiadopcioncodigofacilito.domain.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Slf4j
public class PetNotFoundException extends RuntimeException {
    public PetNotFoundException(Long id) {
        super(String.format("PET WITH ID (%s) NOT FOUND!", id));
        log.error("PET WITH ID ({}) NOT FOUND!", id);
    }

}
