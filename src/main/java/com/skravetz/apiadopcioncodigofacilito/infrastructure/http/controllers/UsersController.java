package com.skravetz.apiadopcioncodigofacilito.infrastructure.http.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {

  @PostMapping("/users")
  public void createUser() {
    //
  }

  @GetMapping("/users")
  public void getUsers() {
    //
  }

  @GetMapping("/users/{id}")
  public void getUserById(@PathVariable("id") String id) {
    //
    System.out.println(id);
  }
}
