package com.skravetz.apiadopcioncodigofacilito.application.services;

import com.skravetz.apiadopcioncodigofacilito.domain.app.User;
import com.skravetz.apiadopcioncodigofacilito.domain.exceptions.UserNotFoundException;
import com.skravetz.apiadopcioncodigofacilito.domain.mappers.UserMapper;
import com.skravetz.apiadopcioncodigofacilito.infrastructure.database.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<User> getAllUsers() {
        return userMapper.mapEntitiesToDomain(this.userRepository.findAll());
    }

    public User getUserByUsername(String username) {
        return userMapper.mapEntityToDomain(Objects.requireNonNull(this.userRepository
                                                                       .findById(username)
                                                                       .orElseThrow(() -> new UserNotFoundException("User not found", username))));
    }

    public User createNewUser(User user) {
        return userMapper.mapEntityToDomain(this.userRepository.save(userMapper.mapDomainToEntity(user)));
    }

}
