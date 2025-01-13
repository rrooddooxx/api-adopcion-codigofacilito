package com.skravetz.apiadopcioncodigofacilito.domain.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Slf4j
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message, String username) {
        super(message);
        log.error("User ({}) not found: " + message, username);
    }

}
