package com.skravetz.apiadopcioncodigofacilito.infrastructure.http.controllers;

import com.skravetz.apiadopcioncodigofacilito.application.services.PetService;
import com.skravetz.apiadopcioncodigofacilito.domain.app.Pet;
import com.skravetz.apiadopcioncodigofacilito.domain.constants.EndpointConstants;
import com.skravetz.apiadopcioncodigofacilito.domain.dto.CreatePetDto;
import com.skravetz.apiadopcioncodigofacilito.domain.exceptions.PetNotFoundException;
import com.skravetz.apiadopcioncodigofacilito.domain.mappers.PetMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PetsController {

    private final PetService petService;

    @PostMapping(EndpointConstants.PRIVATE_ROUTE + "/pets")
    public ResponseEntity<Long> createPetRegistry(
        @RequestBody CreatePetDto createPetDto
                                                 ) {
        try {
            log.info("CREATING NEW PET, WITH PAYLOAD: " + createPetDto);
            Pet newPet =
                petService.createNewPet(PetMapper.mapDtoToDomain(createPetDto));

            log.info("NEW PET SUCCESSFULLY CREATED!");
            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newPet.getId());
        } catch (Exception err) {
            log.error("ERROR CREATING PET: {}", err.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(EndpointConstants.PUBLIC_ROUTE + "/pets")
    public ResponseEntity<List<Pet>> getAllPets() {
        try {
            List<Pet> pets = petService.getPets();
            return ResponseEntity.ok(pets);
        } catch (Exception err) {
            log.error("ERROR RETRIEVING PETS: {}", err.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(EndpointConstants.PUBLIC_ROUTE + "/pets/{id}")
    public ResponseEntity<Pet> getPetById(@PathVariable("id") Long id) {
        try {
            log.info("LOOKING FOR PET WITH ID: " + id);
            Pet foundPet = petService.getPetById(id);
            return ResponseEntity.ok(foundPet);
        } catch (PetNotFoundException err) {
            log.error("ERROR RETRIEVING PET WITH ID {}", id);
            return ResponseEntity.notFound().build();
        } catch (Exception err) {
            log.error("ERROR RETRIEVING PET WITH ID {}, MESSAGE: {}", id,
                      err.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping(EndpointConstants.PRIVATE_ROUTE + "/pets/{id}")
    public ResponseEntity<Pet> modifyPetById(
        @PathVariable("id") Long id,
        @RequestBody CreatePetDto createPetDto
                                            ) {
        try {
            Pet updatedPet = petService.updatePetById(id,
                                                      PetMapper.mapUpdateDtoToDomain(id, createPetDto));
            return ResponseEntity.ok(updatedPet);
        } catch (PetNotFoundException err) {
            log.error("ERROR RETRIEVING PET WITH ID {}", id);
            return ResponseEntity.notFound().build();
        } catch (Exception err) {
            log.error("ERROR: {}", err.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping(EndpointConstants.PRIVATE_ROUTE + "/pets/{id}")
    public ResponseEntity<Object> deletePetById(@PathVariable("id") Long id) {
        try {
            petService.deletePetById(id);
            return ResponseEntity.ok().build();
        } catch (PetNotFoundException err) {
            log.error("ERROR RETRIEVING PET WITH ID {}", id);
            return ResponseEntity.notFound().build();
        } catch (Exception err) {
            log.error("ERROR: {}", err.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

}
