package pro.sky.telegrambot.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.Animal;
import pro.sky.telegrambot.repository.AnimalRepository;

@Service
@Slf4j
public class AnimalService {
    private final AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    public Animal createAnimal(Animal animal) {
        log.info("Creating adopter: {}", animal);
        return animalRepository.save(animal);
    }

    public Animal findAnimal(Long id) {
        if (animalRepository.findById(id).isPresent()) {
            var findedAnimal = animalRepository.findById(id).get();
            log.info("Animal: {} found", findedAnimal);
            return findedAnimal;
        } else {
            throw new RuntimeException();
        }
    }

    public Animal editAnimal(Animal animal) {
        log.info("The method editAnimal has invoked");
        return animalRepository.save(animal);
    }

    public void deleteAnimal(Long id) {
        log.info("The method deleteAnimal has invoked");
        animalRepository.deleteById(id);
    }

}
