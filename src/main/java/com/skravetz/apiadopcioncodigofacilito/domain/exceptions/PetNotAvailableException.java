package com.skravetz.apiadopcioncodigofacilito.domain.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Slf4j
public class PetNotAvailableException extends RuntimeException {
    public PetNotAvailableException(Long id) {
        super(String.format("PET WITH ID (%s) IS NOT AVAILABLE FOR ADOPTION!"
            , id));
        log.error("PET WITH ID ({}) IS NOT AVAILABLE FOR ADOPTION!", id);
    }

}
