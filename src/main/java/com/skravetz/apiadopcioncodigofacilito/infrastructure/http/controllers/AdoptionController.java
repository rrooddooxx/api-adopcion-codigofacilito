package com.skravetz.apiadopcioncodigofacilito.infrastructure.http.controllers;

import com.skravetz.apiadopcioncodigofacilito.application.services.AdoptionService;
import com.skravetz.apiadopcioncodigofacilito.domain.app.Adoption;
import com.skravetz.apiadopcioncodigofacilito.domain.constants.EndpointConstants;
import com.skravetz.apiadopcioncodigofacilito.domain.dto.CompletedAdoptionDto;
import com.skravetz.apiadopcioncodigofacilito.domain.dto.CreateAdoptionDto;
import com.skravetz.apiadopcioncodigofacilito.domain.mappers.AdoptionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdoptionController {

    private final AdoptionService adoptionService;
    private final AdoptionMapper adoptionMapper;

    @PostMapping(EndpointConstants.PRIVATE_ROUTE + "/adoptions")
    public ResponseEntity<Adoption> createAdoptionRegistry(
        @RequestBody CreateAdoptionDto newAdoptionDto
                                                          ) {
        Adoption newAdoption =
            this.adoptionService.createAdoptionRegistry(adoptionMapper.mapNewAdoptionDtoToDomain(newAdoptionDto));
        return ResponseEntity.ok(newAdoption);
    }

    @GetMapping(EndpointConstants.PUBLIC_ROUTE + "/adoptions")
    public ResponseEntity<List<CompletedAdoptionDto>> getAdoptions() {
        List<Adoption> adoptions = this.adoptionService.getAdoptions();
        return ResponseEntity.ok(adoptionMapper.mapListOfCompletedAdoptionsToDtos(adoptions));
    }

}
