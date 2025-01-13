package com.skravetz.apiadopcioncodigofacilito.infrastructure.http.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skravetz.apiadopcioncodigofacilito.application.services.AdoptionService;
import com.skravetz.apiadopcioncodigofacilito.domain.app.Adoption;
import com.skravetz.apiadopcioncodigofacilito.domain.app.NewAdoption;
import com.skravetz.apiadopcioncodigofacilito.domain.app.Pet;
import com.skravetz.apiadopcioncodigofacilito.domain.app.User;
import com.skravetz.apiadopcioncodigofacilito.domain.constants.EndpointConstants;
import com.skravetz.apiadopcioncodigofacilito.domain.dto.CompletedAdoptionDto;
import com.skravetz.apiadopcioncodigofacilito.domain.dto.CreateAdoptionDto;
import com.skravetz.apiadopcioncodigofacilito.domain.mappers.AdoptionMapper;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdoptionControllerTest {

    private MockMvc mockMvc;
    private AdoptionService adoptionService;
    private AdoptionMapper adoptionMapper;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        adoptionService = mock(AdoptionService.class);
        adoptionMapper = mock(AdoptionMapper.class);
        objectMapper = new ObjectMapper();

        AdoptionController controller =
            new AdoptionController(adoptionService, adoptionMapper);

        mockMvc = MockMvcBuilders
            .standaloneSetup(controller)
            .build();
    }

    @Test
    @DisplayName("Create Adoption Registry - Success")
    void createAdoptionRegistry_Success() throws Exception {
        CreateAdoptionDto dto = CreateAdoptionDto.builder()
                                                 .petId(1L)
                                                 .adopterUsername("testUser")
                                                 .build();

        NewAdoption newAdoption = NewAdoption.builder()
                                             .petId(1L)
                                             .adopterUsername("testUser")
                                             .build();

        Adoption adoption = Adoption.builder()
                                    .id(1L)
                                    .adopter(new User())
                                    .pet(new Pet())
                                    .adoptionDate("2023-12-25")
                                    .build();

        when(adoptionMapper.mapNewAdoptionDtoToDomain(any(CreateAdoptionDto.class)))
            .thenReturn(newAdoption);
        when(adoptionService.createAdoptionRegistry(any(NewAdoption.class)))
            .thenReturn(adoption);

        mockMvc.perform(post(EndpointConstants.PRIVATE_ROUTE + "/adoptions")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(dto)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1L))
               .andExpect(jsonPath("$.adoptionDate").value("2023-12-25"));
    }

    @Test
    @DisplayName("Get Adoptions - Success")
    void getAdoptions_Success() throws Exception {
        Adoption adoption = Adoption.builder()
                                    .id(1L)
                                    .adopter(new User())
                                    .pet(new Pet())
                                    .adoptionDate("2023-12-25")
                                    .build();

        CompletedAdoptionDto dto = CompletedAdoptionDto.builder()
                                                       .id(1L)
                                                       .adoptionDate("2023-12-25")
                                                       .build();

        when(adoptionService.getAdoptions())
            .thenReturn(List.of(adoption));
        when(adoptionMapper.mapListOfCompletedAdoptionsToDtos(List.of(adoption)))
            .thenReturn(List.of(dto));

        mockMvc.perform(get(EndpointConstants.PUBLIC_ROUTE + "/adoptions")
                            .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].id").value(1L))
               .andExpect(jsonPath("$[0].adoptionDate").value("2023-12-25"));
    }

}
