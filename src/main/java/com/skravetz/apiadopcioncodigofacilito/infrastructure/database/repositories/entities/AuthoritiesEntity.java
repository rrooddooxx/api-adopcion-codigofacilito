package com.skravetz.apiadopcioncodigofacilito.infrastructure.database.repositories.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@IdClass(AuthorityId.class)
@Entity(name = "authorities")
@Table(name = "authorities", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"username", "authority"})
})
public class AuthoritiesEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username",
        nullable = false)
    private UserEntity user;

    @Id
    @Column(name = "authority", nullable = false, length = 50)
    private String authority;

}
