package com.skravetz.apiadopcioncodigofacilito.domain.app;

import lombok.Getter;

@Getter
public enum AdoptionStatus {
  SUCCESS(4, "Success"),
  NOT_AVAILABLE(5, "Failed"),
  PENDING(6, "Pending");

  private final Integer key;
  private final String status;

  AdoptionStatus(Integer key, String status) {
    this.key = key;
    this.status = status;
  }

  public static AdoptionStatus byKey(Integer key) throws IllegalArgumentException {
    for (AdoptionStatus status : AdoptionStatus.values()) {
      if (status.getKey().equals(key)) {
        return status;
      }
    }
    throw new IllegalArgumentException("Invalid key");
  }

  public static AdoptionStatus byValue(String value) throws IllegalArgumentException {
    for (AdoptionStatus status : AdoptionStatus.values()) {
      if (status.getStatus().equals(value)) {
        return status;
      }
    }
    throw new IllegalArgumentException("Invalid value");
  }
}
