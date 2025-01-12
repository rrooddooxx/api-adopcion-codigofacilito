package com.skravetz.apiadopcioncodigofacilito.infrastructure.database.repositories;

import com.skravetz.apiadopcioncodigofacilito.infrastructure.database.repositories.entities.AdoptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdoptionRepository extends JpaRepository<AdoptionEntity,
    Long> {
}
