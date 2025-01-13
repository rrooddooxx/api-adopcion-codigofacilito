package com.skravetz.apiadopcioncodigofacilito.infrastructure.http.controllers;

import com.skravetz.apiadopcioncodigofacilito.application.services.UserService;
import com.skravetz.apiadopcioncodigofacilito.domain.app.User;
import com.skravetz.apiadopcioncodigofacilito.domain.constants.EndpointConstants;
import com.skravetz.apiadopcioncodigofacilito.domain.dto.CreateUserDto;
import com.skravetz.apiadopcioncodigofacilito.domain.dto.UserDto;
import com.skravetz.apiadopcioncodigofacilito.domain.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UsersController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping(EndpointConstants.PUBLIC_ROUTE + "/users")
    public ResponseEntity<List<UserDto>> getUsers() {
        List<User> userList = userService.getAllUsers();
        return ResponseEntity.ok(userMapper.mapDomainsToUserDtos(userList));
    }

    @PostMapping(EndpointConstants.PRIVATE_ROUTE + "/users")
    public ResponseEntity<UserDto> createUser(
        @RequestBody CreateUserDto newUserDto
                                             ) {
        User newUser =
            this.userService.createNewUser(userMapper.mapDtoToDomain(newUserDto));
        return ResponseEntity.ok(userMapper.mapDomainToUserDto(newUser));
    }

    @GetMapping(EndpointConstants.PUBLIC_ROUTE + "/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") String id) {
        User foundUser = this.userService.getUserByUsername(id);
        return ResponseEntity.ok(userMapper.mapDomainToUserDto(foundUser));
    }

}
