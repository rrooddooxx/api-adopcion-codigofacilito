package com.skravetz.apiadopcioncodigofacilito.infrastructure.database.repositories;

import com.skravetz.apiadopcioncodigofacilito.infrastructure.database.repositories.entities.AdoptionStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdoptionStatusRepository extends JpaRepository<AdoptionStatusEntity,
    Long> {
}
