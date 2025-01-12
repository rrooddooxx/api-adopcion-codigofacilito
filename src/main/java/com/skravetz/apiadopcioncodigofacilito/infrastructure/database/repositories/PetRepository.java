package com.skravetz.apiadopcioncodigofacilito.infrastructure.database.repositories;

import com.skravetz.apiadopcioncodigofacilito.infrastructure.database.repositories.entities.PetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<PetEntity, Long> {
}
