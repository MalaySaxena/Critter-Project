package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.models.Customer;
import com.udacity.jdnd.course3.critter.models.Pet;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetService {

    private PetRepository petRepository;
    private UserService userService;

    PetService(PetRepository petRepository, UserService userService){
        this.petRepository = petRepository;
        this.userService = userService;
    }

    public PetDTO addNewPet(PetDTO petDTO){
        Customer customer = userService.getCustomer((int)petDTO.getOwnerId());
        Pet addedPet = petRepository.save(new Pet(petDTO.getType(), petDTO.getName(),
                        petDTO.getDateOfBirth(), petDTO.getNotes(), customer));

        List<Pet> petList = customer.getPetList();
        if(petList==null)
            petList = new ArrayList<>();

        petList.add(addedPet);
        customer.setPetList(petList);

        userService.addNewCustomer(customer);
        PetDTO petDTO1 = petToDTO(addedPet);
        return petDTO1;
    }

    public PetDTO getPet(Long id){
        return petToDTO(petRepository.findById(id).get());
    }

    public List<PetDTO> getAllPets(){
        List<Pet> petList = (List<Pet>) petRepository.findAll();

        List<PetDTO> allPets = petList
                .stream()
                .map(pet -> petToDTO(pet))
                .collect(Collectors.toList());

        return allPets;
    }

    public List<PetDTO> getAllPetsOfOwner(Long id){
        Customer owner = userService.getCustomer(id.intValue());

        List<Pet> ownedPets = petRepository.findAllByOwner(owner);

        List<PetDTO> petDTOList = new ArrayList<>();

        for(Pet pet:ownedPets){
            PetDTO petDTO = petToDTO(pet);
            petDTO.setOwnerId(pet.getOwner().getId());
            petDTOList.add(petDTO);
        }
        return petDTOList;
    }

    public Customer getOwnerOfPet(Long id){
        Pet pet = petRepository.findById(id).get();
        return pet.getOwner();
    }

    private PetDTO petToDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setOwnerId(pet.getOwner().getId());
        return petDTO;
    }

}
