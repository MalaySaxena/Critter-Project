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

    public Pet addNewPet(PetDTO petDTO){
        Customer customer = userService.getCustomer((int)petDTO.getOwnerId());
        Pet addedPet = petRepository.save(new Pet(petDTO.getType(), petDTO.getName(),
                        petDTO.getDateOfBirth(), petDTO.getNotes(), customer));

        List<Pet> petList = customer.getPetList();

        if(petList==null)
            petList = new ArrayList<>();

        petList.add(addedPet);
        customer.setPetList(petList);
        userService.addNewCustomer(customer);

        return addedPet;
    }

    public Pet getPetDTO(Long id){
        return getPet(id);
    }

    public Pet getPet(Long id){
        return petRepository.findById(id).get();
    }

    public List<Pet> getAllPets(){
        List<Pet> petList = (List<Pet>) petRepository.findAll();
        return petList;
    }

    public List<Pet> getAllPetsByIds(List<Long> ids){
        if(ids == null)
            return null;

        List<Pet> petList = (List<Pet>) petRepository.findAllById(ids);

        return petList;
    }

    public List<Pet> getAllPetsOfOwner(Long id){
        Customer owner = userService.getCustomer(id.intValue());
        List<Pet> ownedPets = petRepository.findAllByOwner(owner);
        return ownedPets;
    }

    public Customer getOwnerOfPet(Long id){
        Pet pet = petRepository.findById(id).get();
        return pet.getOwner();
    }

}
