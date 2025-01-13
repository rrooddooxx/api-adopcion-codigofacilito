package com.skravetz.apiadopcioncodigofacilito.infrastructure.http.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skravetz.apiadopcioncodigofacilito.application.services.UserService;
import com.skravetz.apiadopcioncodigofacilito.domain.app.User;
import com.skravetz.apiadopcioncodigofacilito.domain.constants.EndpointConstants;
import com.skravetz.apiadopcioncodigofacilito.domain.dto.CreateUserDto;
import com.skravetz.apiadopcioncodigofacilito.domain.dto.UserDto;
import com.skravetz.apiadopcioncodigofacilito.domain.exceptions.UserNotFoundException;
import com.skravetz.apiadopcioncodigofacilito.domain.mappers.UserMapper;
import com.skravetz.apiadopcioncodigofacilito.infrastructure.http.handlers.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UsersControllerTests {

    private MockMvc mockMvc;
    private UserService userService;
    private UserMapper userMapper;
    private ObjectMapper objectMapper;
    private User testUser;
    private UserDto testUserDto;
    private CreateUserDto createUserDto;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        userMapper = mock(UserMapper.class);
        objectMapper = new ObjectMapper();

        UsersController controller = new UsersController(userService,
                                                         userMapper);

        mockMvc = MockMvcBuilders
            .standaloneSetup(controller)
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();

        testUser = User.builder()
                       .username("testuser")
                       .email("test@example.com")
                       .phone("1234567890")
                       .enabled(true)
                       .build();

        testUserDto = UserDto.builder()
                             .username("testuser")
                             .email("test@example.com")
                             .phone("1234567890")
                             .enabled(true)
                             .build();

        createUserDto = CreateUserDto.builder()
                                     .username("testuser")
                                     .password("password123")
                                     .email("test@example.com")
                                     .phone("1234567890")
                                     .enabled(true)
                                     .build();
    }

    @Test
    @DisplayName("Get All Users - Success")
    void getUsers_Success() throws Exception {
        List<User> users = List.of(testUser);
        List<UserDto> userDtos = List.of(testUserDto);

        when(userService.getAllUsers()).thenReturn(users);
        when(userMapper.mapDomainsToUserDtos(users)).thenReturn(userDtos);

        mockMvc.perform(get(EndpointConstants.PUBLIC_ROUTE + "/users")
                            .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().json(objectMapper.writeValueAsString(userDtos)));
    }

    @Test
    @DisplayName("Get All Users - Failure")
    void getUsers_Failure() throws Exception {
        when(userService.getAllUsers())
            .thenThrow(new IllegalStateException("Database error"));

        mockMvc.perform(get(EndpointConstants.PUBLIC_ROUTE + "/users"))
               .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Create User - Success")
    void createUser_Success() throws Exception {
        when(userMapper.mapDtoToDomain(any(CreateUserDto.class))).thenReturn(testUser);
        when(userService.createNewUser(any(User.class))).thenReturn(testUser);
        when(userMapper.mapDomainToUserDto(testUser)).thenReturn(testUserDto);

        mockMvc.perform(post(EndpointConstants.PRIVATE_ROUTE + "/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(createUserDto)))
               .andExpect(status().isOk())
               .andExpect(content().json(objectMapper.writeValueAsString(testUserDto)));
    }

    @Test
    @DisplayName("Create User - Failure")
    void createUser_Failure() throws Exception {
        when(userMapper.mapDtoToDomain(any(CreateUserDto.class))).thenReturn(testUser);
        when(userService.createNewUser(any(User.class)))
            .thenThrow(new IllegalArgumentException("Invalid user data"));

        mockMvc.perform(post(EndpointConstants.PRIVATE_ROUTE + "/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(createUserDto)))
               .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Get User By Id - Success")
    void getUserById_Success() throws Exception {
        String username = "testuser";

        when(userService.getUserByUsername(username)).thenReturn(testUser);
        when(userMapper.mapDomainToUserDto(testUser)).thenReturn(testUserDto);

        mockMvc
            .perform(get(EndpointConstants.PUBLIC_ROUTE + "/users/" + username))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(testUserDto)));
    }

    @Test
    @DisplayName("Get User By Id - Not Found")
    void getUserById_NotFound() throws Exception {
        String username = "nonexistent";

        when(userService.getUserByUsername(username))
            .thenThrow(new UserNotFoundException("User not found", username));

        mockMvc
            .perform(get(EndpointConstants.PUBLIC_ROUTE + "/users/" + username))
            .andExpect(status().isNotFound());
    }

}
