package com.skravetz.apiadopcioncodigofacilito.domain.constants;

public enum ErrorCode {
    ADOP_ERR("ADOP_ERR", "Internal server error"),
    ADOP_PNF("ADOP_PNF", "Pet not found in database"),
    ADOP_UNF("ADOP_UNF", "User not found in database");

    private final String code;
    private final String reason;

    ErrorCode(
        String code, String reason
             ) {
        this.code = code;
        this.reason = reason;
    }
}
