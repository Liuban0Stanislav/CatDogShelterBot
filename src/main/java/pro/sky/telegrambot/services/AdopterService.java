package pro.sky.telegrambot.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.Adopter;
import pro.sky.telegrambot.repository.AdopterRepository;

@Service
public class AdopterService {
    private static final Logger logger = LoggerFactory.getLogger(AdopterService.class);
    private final AdopterRepository adopterRepository;

    public AdopterService(AdopterRepository adopterRepository) {
        this.adopterRepository = adopterRepository;
    }

    public Adopter createAdopter(Adopter adopter) {
        logger.info("Creating adopter: {}", adopter);
        return adopterRepository.save(adopter);
    }

    public Adopter findAdopter(Long id) {
        if (adopterRepository.findById(id).isPresent()) {
            var findedAdopter = adopterRepository.findById(id).get();
            logger.info("Adopter: {} found", findedAdopter);
            return findedAdopter;
        } else {
            throw new RuntimeException();
        }
    }

    public Adopter editAdopter(Adopter adopter) {
        logger.info("The method editAdopter has invoked");
        return adopterRepository.save(adopter);
    }

    public void deleteAdopter(Long id) {
        logger.info("The method deleteAdopter has invoked");
        adopterRepository.deleteById(id);
    }

}
