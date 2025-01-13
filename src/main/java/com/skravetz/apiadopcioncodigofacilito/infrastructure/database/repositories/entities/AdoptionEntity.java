package com.skravetz.apiadopcioncodigofacilito.infrastructure.database.repositories.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "adoption")
@Table(name = "adoption")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdoptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private PetEntity pet;

    @ManyToOne
    @JoinColumn(name = "adopter_id", referencedColumnName = "username")
    private UserEntity adopter;

    @Column(name = "adoption_date")
    private String adoptionDate;

    @ManyToOne
    @JoinColumn(name = "adoption_status_id")
    private AdoptionStatusEntity adoptionStatus;

}
