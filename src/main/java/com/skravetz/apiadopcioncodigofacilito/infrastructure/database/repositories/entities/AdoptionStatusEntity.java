package com.skravetz.apiadopcioncodigofacilito.infrastructure.database.repositories.entities;

import jakarta.persistence.*;

@Entity(name = "adoption_status")
@Table(name = "adoption_status")
public class AdoptionStatusEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "status_name")
  private String statusName;
}
