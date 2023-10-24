package pro.sky.telegrambot.service;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.constants.DocType;
import pro.sky.telegrambot.exception.AdoptionDocNotFoundException;
import pro.sky.telegrambot.model.AdoptionDoc;
import pro.sky.telegrambot.repository.AdoptionDocRepository;

@Service
public class AdoptionDocService {

    private final AdoptionDocRepository adoptionDocRepository;
    public AdoptionDocService(AdoptionDocRepository adoptionDocRepository) {
        this.adoptionDocRepository = adoptionDocRepository;
    }

    public AdoptionDoc createAdoptionDoc(AdoptionDoc adoptionDoc) {
        return adoptionDocRepository.save(adoptionDoc);
    }

    public AdoptionDoc readAdoptionDoc(DocType id) {
        AdoptionDoc adoptionDoc = adoptionDocRepository.findById(id).orElse(null);
        if (adoptionDoc == null) {
            throw new AdoptionDocNotFoundException(id.ordinal());
        }
        return adoptionDoc;
    }

    public AdoptionDoc updateAdoptionDoc(AdoptionDoc adoptionDoc) {
        if (adoptionDocRepository.findById(adoptionDoc.getId()).orElse(null) == null) {
            return null;
        }
        return adoptionDocRepository.save(adoptionDoc);
    }


}
