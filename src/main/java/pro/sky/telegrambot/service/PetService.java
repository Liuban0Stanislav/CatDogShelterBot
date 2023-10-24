package pro.sky.telegrambot.service;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.exception.PetNotFoundException;
import pro.sky.telegrambot.model.Pet;
import pro.sky.telegrambot.repository.PetRepository;

@Service
public class PetService {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Pet findPet(long id) {
        Pet pet = petRepository.findById(id).orElse(null);
        if (pet == null) {
            throw new PetNotFoundException(id);
        }
        return pet;
    }

    public Pet createPet(Pet pet) {
        return petRepository.save(pet);
    }

    public Pet editPet(Pet pet) {
        if (petRepository.findById(pet.getId()).orElse(null) == null) {
            return null;
        }
        return petRepository.save(pet);
    }
}
