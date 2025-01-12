package com.skravetz.apiadopcioncodigofacilito.infrastructure.http.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdoptionController {

  @PostMapping("/adoptions")
  public void createAdoptionRegistry() {
    //
  }

  @GetMapping
  public void getAdoptions(@RequestParam(value = "status", required = false) String status) {
    //
  }
}
