package com.skravetz.apiadopcioncodigofacilito.infrastructure.http.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skravetz.apiadopcioncodigofacilito.application.services.PetService;
import com.skravetz.apiadopcioncodigofacilito.domain.app.Pet;
import com.skravetz.apiadopcioncodigofacilito.domain.app.PetType;
import com.skravetz.apiadopcioncodigofacilito.domain.constants.EndpointConstants;
import com.skravetz.apiadopcioncodigofacilito.domain.dto.CreatePetDto;
import com.skravetz.apiadopcioncodigofacilito.domain.exceptions.PetNotFoundException;
import com.skravetz.apiadopcioncodigofacilito.infrastructure.http.handlers.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PetsControllerTests {

    private MockMvc mockMvc;
    private PetService petService;
    private ObjectMapper objectMapper;
    private Pet testPet;
    private CreatePetDto createPetDto;

    @BeforeEach
    void setUp() {
        petService = mock(PetService.class);
        PetsController controller = new PetsController(petService);
        mockMvc = MockMvcBuilders
            .standaloneSetup(controller)
            .setControllerAdvice(new GlobalExceptionHandler()) // Add
            // exception handler
            .build();
        objectMapper = new ObjectMapper();
        testPet = Pet.builder()
                     .id(1L)
                     .name("Max")
                     .type(PetType.CAT)
                     .age(3)
                     .build();

        createPetDto = new CreatePetDto();
        createPetDto.setName("Max");
        createPetDto.setType(PetType.CAT);
        createPetDto.setAge(3);
    }

    @Test
    @DisplayName("Create Pet Registry - Success")
    void createPetRegistry_Success() throws Exception {
        when(petService.createNewPet(any(Pet.class))).thenReturn(testPet);

        mockMvc.perform(post(EndpointConstants.PRIVATE_ROUTE + "/pets")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(createPetDto)))
               .andExpect(status().isCreated())
               .andExpect(content().string("1"));
    }

    @Test
    @DisplayName("Create Pet Registry - Failure")
    void createPetRegistry_Failure() throws Exception {
        when(petService.createNewPet(any(Pet.class)))
            .thenThrow(new IllegalArgumentException("Invalid pet data"));

        mockMvc.perform(post(EndpointConstants.PRIVATE_ROUTE + "/pets")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(createPetDto)))
               .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Get All Pets - Success")
    void getAllPets_Success() throws Exception {
        List<Pet> pets = Collections.singletonList(testPet);
        when(petService.getPets()).thenReturn(pets);

        mockMvc.perform(get(EndpointConstants.PUBLIC_ROUTE + "/pets"))
               .andExpect(status().isOk())
               .andExpect(content().json(objectMapper.writeValueAsString(pets)));
    }

    @Test
    @DisplayName("Get All Pets - Failure")
    void getAllPets_Failure() throws Exception {
        when(petService.getPets())
            .thenThrow(new IllegalStateException("Database error"));

        mockMvc.perform(get(EndpointConstants.PUBLIC_ROUTE + "/pets"))
               .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Get Pet By Id - Success")
    void getPetById_Success() throws Exception {
        when(petService.getPetById(1L)).thenReturn(testPet);

        mockMvc.perform(get(EndpointConstants.PUBLIC_ROUTE + "/pets/1"))
               .andExpect(status().isOk())
               .andExpect(content().json(objectMapper.writeValueAsString(testPet)));
    }

    @Test
    @DisplayName("Get Pet By Id - Not Found")
    void getPetById_NotFound() throws Exception {
        when(petService.getPetById(1L)).thenThrow(new PetNotFoundException(1L));

        mockMvc.perform(get(EndpointConstants.PUBLIC_ROUTE + "/pets/1"))
               .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Modify Pet By Id - Success")
    void modifyPetById_Success() throws Exception {
        when(petService.updatePetById(eq(1L), any(Pet.class))).thenReturn(testPet);

        mockMvc.perform(put(EndpointConstants.PRIVATE_ROUTE + "/pets/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(createPetDto)))
               .andExpect(status().isOk())
               .andExpect(content().json(objectMapper.writeValueAsString(testPet)));
    }

    @Test
    @DisplayName("Modify Pet By Id - Not Found")
    void modifyPetById_NotFound() throws Exception {
        when(petService.updatePetById(eq(1L), any(Pet.class))).thenThrow(new PetNotFoundException(1L));

        mockMvc.perform(put(EndpointConstants.PRIVATE_ROUTE + "/pets/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(createPetDto)))
               .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Delete Pet By Id - Success")
    void deletePetById_Success() throws Exception {
        mockMvc.perform(delete(EndpointConstants.PRIVATE_ROUTE + "/pets/1"))
               .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Delete Pet By Id - Not Found")
    void deletePetById_NotFound() throws Exception {
        doThrow(new PetNotFoundException(1L))
            .when(petService)
            .deletePetById(1L);

        mockMvc.perform(delete(EndpointConstants.PRIVATE_ROUTE + "/pets/1"))
               .andExpect(status().isNotFound());
    }

}
