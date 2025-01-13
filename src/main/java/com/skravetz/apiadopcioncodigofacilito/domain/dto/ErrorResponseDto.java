package com.skravetz.apiadopcioncodigofacilito.domain.dto;

import com.skravetz.apiadopcioncodigofacilito.domain.constants.ErrorCode;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponseDto {
    private LocalDateTime timestamp;
    private String message;
    private Integer statusCode;
    private ErrorCode errorCode;

}
