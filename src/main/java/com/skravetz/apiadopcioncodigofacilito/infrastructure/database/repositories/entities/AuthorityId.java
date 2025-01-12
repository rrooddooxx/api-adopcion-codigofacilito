package com.skravetz.apiadopcioncodigofacilito.infrastructure.database.repositories.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityId implements Serializable {
    private String user;
    private String authority;

}
