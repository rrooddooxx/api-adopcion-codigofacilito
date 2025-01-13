package com.skravetz.apiadopcioncodigofacilito.domain.mappers;

import com.skravetz.apiadopcioncodigofacilito.domain.app.User;
import com.skravetz.apiadopcioncodigofacilito.domain.dto.CreateUserDto;
import com.skravetz.apiadopcioncodigofacilito.domain.dto.UserDto;
import com.skravetz.apiadopcioncodigofacilito.infrastructure.database.repositories.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public User mapEntityToDomain(UserEntity user) {
        return User.builder()
                   .username(user.getUsername())
                   .enabled(user.getEnabled())
                   .email(user.getEmail())
                   .phone(user.getPhone())
                   .build();
    }

    public List<User> mapEntitiesToDomain(List<UserEntity> users) {
        return users.stream()
                    .map(this::mapEntityToDomain)
                    .toList();
    }

    public UserEntity mapDomainToEntity(User user) {
        return UserEntity.builder()
                         .username(user.getUsername())
                         .password(user.getPassword())
                         .enabled(user.getEnabled())
                         .email(user.getEmail())
                         .phone(user.getPhone())
                         .build();
    }

    public User mapDtoToDomain(CreateUserDto newUser) {
        return User.builder()
                   .username(newUser.getUsername())
                   .password(passwordEncoder.encode(newUser.getPassword()))
                   .enabled(newUser.getEnabled())
                   .email(newUser.getEmail())
                   .phone(newUser.getPhone())
                   .build();
    }

    public UserDto mapDomainToUserDto(User user) {
        return UserDto.builder()
                      .username(user.getUsername())
                      .enabled(user.getEnabled())
                      .email(user.getEmail())
                      .phone(user.getPhone())
                      .build();
    }

    public List<UserDto> mapDomainsToUserDtos(List<User> users) {
        return users.stream()
                    .map(this::mapDomainToUserDto)
                    .toList();
    }

}
