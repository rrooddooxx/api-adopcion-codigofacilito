package com.skravetz.apiadopcioncodigofacilito.infrastructure.http.handlers;

import com.skravetz.apiadopcioncodigofacilito.domain.constants.ErrorCode;
import com.skravetz.apiadopcioncodigofacilito.domain.dto.ErrorResponseDto;
import com.skravetz.apiadopcioncodigofacilito.domain.exceptions.PetNotFoundException;
import com.skravetz.apiadopcioncodigofacilito.domain.exceptions.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponseDto> handleGenericException(Exception exception) {
        String errorMessage = exception.getMessage() != null ?
            exception.getMessage() : "Internal server error";
        ErrorResponseDto responseDto = ErrorResponseDto.builder()
                                                       .message(errorMessage)
                                                       .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                       .errorCode(ErrorCode.ADOP_ERR)
                                                       .timestamp(LocalDateTime.now())
                                                       .build();

        log.error("ERROR IN REQUEST: {} ", errorMessage);

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(responseDto);
    }

    @ExceptionHandler(PetNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponseDto> handlePetNotFoundException(PetNotFoundException exception) {
        String message = exception.getMessage() != null ?
            exception.getMessage() : "Pet not found";
        ErrorResponseDto responseDto = ErrorResponseDto
            .builder()
            .timestamp(LocalDateTime.now())
            .statusCode(HttpStatus.NOT_FOUND.value())
            .message(message)
            .errorCode(ErrorCode.ADOP_PNF)
            .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponseDto> handleUserNotFoundException(UserNotFoundException exception) {
        String message = exception.getMessage() != null ?
            exception.getMessage() : "User not found";
        ErrorResponseDto responseDto = ErrorResponseDto
            .builder()
            .timestamp(LocalDateTime.now())
            .statusCode(HttpStatus.NOT_FOUND.value())
            .message(message)
            .errorCode(ErrorCode.ADOP_UNF)
            .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
    }

}
