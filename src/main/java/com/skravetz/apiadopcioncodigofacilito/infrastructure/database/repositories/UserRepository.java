package com.skravetz.apiadopcioncodigofacilito.infrastructure.database.repositories;

import com.skravetz.apiadopcioncodigofacilito.infrastructure.database.repositories.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity findByUsername(String username);

}
