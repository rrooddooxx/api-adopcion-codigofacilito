package com.skravetz.apiadopcioncodigofacilito.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private String username;
    private Boolean enabled;
    private String email;
    private String phone;

}
