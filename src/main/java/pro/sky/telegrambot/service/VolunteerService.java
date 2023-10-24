package pro.sky.telegrambot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import pro.sky.telegrambot.exception.VolunteerNotFoundException;
import pro.sky.telegrambot.model.Volunteer;
import pro.sky.telegrambot.repository.VolunteerRepository;

@Service
public class VolunteerService {
    private final VolunteerRepository volunteerRepository;

    private final Logger logger = LoggerFactory.getLogger(VolunteerService.class);

    public VolunteerService(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    public Volunteer createVolunteer(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

    public Volunteer read(long id) {
        logger.debug("Calling method read (id = {})", id);
        return volunteerRepository.findById(id).orElseThrow(() -> new NotFoundException("id not found"));
    }

    public Volunteer updateVolunteer(long id, Volunteer volunteer) {
        logger.debug("Calling method update Volunteer (Id = {})", volunteer.getId());
        Volunteer oldVolunteer = read(id);
        oldVolunteer.setName(volunteer.getName());
        oldVolunteer.setChatId(volunteer.getChatId());
        oldVolunteer.setUsername(volunteer.getUsername());
        return volunteerRepository.save(oldVolunteer);
    }

    public Volunteer getRandomVolunteer() {
        return volunteerRepository
                .getRandomVolunteer()
                .orElseThrow(() -> new VolunteerNotFoundException("At least one volunteer must be present!"));
    }
}
