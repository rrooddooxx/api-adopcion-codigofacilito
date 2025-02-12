package com.skravetz.apiadopcioncodigofacilito.domain.app;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String username;
    private String password;
    private Boolean enabled;
    private String email;
    private String phone;

}
