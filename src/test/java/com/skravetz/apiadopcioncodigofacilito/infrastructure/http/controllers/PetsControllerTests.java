package com.skravetz.apiadopcioncodigofacilito.infrastructure.http.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skravetz.apiadopcioncodigofacilito.application.services.PetService;
import com.skravetz.apiadopcioncodigofacilito.domain.app.Pet;
import com.skravetz.apiadopcioncodigofacilito.domain.app.PetType;
import com.skravetz.apiadopcioncodigofacilito.domain.constants.EndpointConstants;
import com.skravetz.apiadopcioncodigofacilito.domain.dto.CreatePetDto;
import com.skravetz.apiadopcioncodigofacilito.domain.exceptions.PetNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PetsController.class)
class PetsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PetService petService;

    @Autowired
    private ObjectMapper objectMapper;

    private Pet testPet;
    private CreatePetDto createPetDto;

    @BeforeEach
    void setUp() {
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
    void createPetRegistry_Success() throws Exception {
        when(petService.createNewPet(any(Pet.class))).thenReturn(testPet);

        mockMvc.perform(post(EndpointConstants.PRIVATE_ROUTE + "/pets")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(createPetDto)))
               .andExpect(status().isCreated())
               .andExpect(content().string("1"));
    }

    @Test
    void createPetRegistry_Failure() throws Exception {
        when(petService.createNewPet(any(Pet.class))).thenThrow(new RuntimeException());

        mockMvc.perform(post(EndpointConstants.PRIVATE_ROUTE + "/pets")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(createPetDto)))
               .andExpect(status().isBadRequest());
    }

    @Test
    void getAllPets_Success() throws Exception {
        List<Pet> pets = Collections.singletonList(testPet);
        when(petService.getPets()).thenReturn(pets);

        mockMvc.perform(get(EndpointConstants.PUBLIC_ROUTE + "/pets"))
               .andExpect(status().isOk())
               .andExpect(content().json(objectMapper.writeValueAsString(pets)));
    }

    @Test
    void getAllPets_Failure() throws Exception {
        when(petService.getPets()).thenThrow(new RuntimeException());

        mockMvc.perform(get(EndpointConstants.PUBLIC_ROUTE + "/pets"))
               .andExpect(status().isInternalServerError());
    }

    @Test
    void getPetById_Success() throws Exception {
        when(petService.getPetById(1L)).thenReturn(testPet);

        mockMvc.perform(get(EndpointConstants.PUBLIC_ROUTE + "/pets/1"))
               .andExpect(status().isOk())
               .andExpect(content().json(objectMapper.writeValueAsString(testPet)));
    }

    @Test
    void getPetById_NotFound() throws Exception {
        when(petService.getPetById(1L)).thenThrow(new PetNotFoundException(1L));

        mockMvc.perform(get(EndpointConstants.PUBLIC_ROUTE + "/pets/1"))
               .andExpect(status().isNotFound());
    }

    @Test
    void modifyPetById_Success() throws Exception {
        when(petService.updatePetById(eq(1L), any(Pet.class))).thenReturn(testPet);

        mockMvc.perform(put(EndpointConstants.PRIVATE_ROUTE + "/pets/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(createPetDto)))
               .andExpect(status().isOk())
               .andExpect(content().json(objectMapper.writeValueAsString(testPet)));
    }

    @Test
    void modifyPetById_NotFound() throws Exception {
        when(petService.updatePetById(eq(1L), any(Pet.class))).thenThrow(new PetNotFoundException(1L));

        mockMvc.perform(put(EndpointConstants.PRIVATE_ROUTE + "/pets/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(createPetDto)))
               .andExpect(status().isNotFound());
    }

    @Test
    void deletePetById_Success() throws Exception {
        mockMvc.perform(delete(EndpointConstants.PRIVATE_ROUTE + "/pets/1"))
               .andExpect(status().isOk());
    }

    @Test
    void deletePetById_NotFound() throws Exception {
        doThrow(new PetNotFoundException(1L))
            .when(petService)
            .deletePetById(1L);

        mockMvc.perform(delete(EndpointConstants.PRIVATE_ROUTE + "/pets/1"))
               .andExpect(status().isNotFound());
    }

}
