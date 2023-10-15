package pro.sky.telegrambot.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.Shelter;
import pro.sky.telegrambot.repository.ShelterRepository;

@Service
@Slf4j
public class ShelterService {
    private final ShelterRepository shelterRepository;

    public ShelterService(ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }

    public Shelter createShelter(Shelter shelter) {
        log.info("Creating shelter: {}", shelter);
        return shelterRepository.save(shelter);
    }

    public Shelter findShelter(Long id) {
        if (shelterRepository.findById(id).isPresent()) {
            var findedShelter = shelterRepository.findById(id).get();
            log.info("Shelter: {} found", findedShelter);
            return findedShelter;
        } else {
            throw new RuntimeException();
        }
    }

    public Shelter editShelter(Shelter shelter) {
        log.info("The method editShelter has invoked");
        return shelterRepository.save(shelter);
    }

    public void deleteShelter(Long id) {
        log.info("The method deleteShelter has invoked");
        shelterRepository.deleteById(id);
    }

}
