package com.skravetz.apiadopcioncodigofacilito.infrastructure.database.repositories.entities;

import jakarta.persistence.*;
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
  @JoinColumn(name = "adopter_id")
  private UserEntity adopter;

  @Column(name = "adoption_date")
  private String adoptionDate;

  @ManyToOne
  @JoinColumn(name = "adoption_status_id")
  private AdoptionStatusEntity adoptionStatus;
}
