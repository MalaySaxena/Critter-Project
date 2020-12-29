package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.models.Pet;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private PetService petService;

    PetController(PetService petService){
        this.petService = petService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        return petToDTO(petService.addNewPet(petDTO));
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return petToDTO(petService.getPetDTO(petId));
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> petList = petService.getAllPets();

        List<PetDTO> allPets = petList
                .stream()
                .map(pet -> petToDTO(pet))
                .collect(Collectors.toList());

        return allPets;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> ownedPets = petService.getAllPetsOfOwner(ownerId);

        List<PetDTO> petDTOList = new ArrayList<>();

        for(Pet pet:ownedPets){
            PetDTO petDTO = petToDTO(pet);
            petDTO.setOwnerId(pet.getOwner().getId());
            petDTOList.add(petDTO);
        }
        return petDTOList;
    }

    private PetDTO petToDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setOwnerId(pet.getOwner().getId());
        return petDTO;
    }
}
