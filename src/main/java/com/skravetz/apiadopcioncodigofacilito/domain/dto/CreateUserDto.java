package com.skravetz.apiadopcioncodigofacilito.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserDto {
    private String username;
    private String password;
    private Boolean enabled;
    private String email;
    private String phone;

}
