package pro.sky.telegrambot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import pro.sky.telegrambot.model.AdoptionReport;
import pro.sky.telegrambot.repository.AdoptionReportRepository;

@Service
public class AdoptionReportService {
    private final AdoptionReportRepository adoptionReportRepository;

    public AdoptionReportService(AdoptionReportRepository adoptionReportRepository) {
        this.adoptionReportRepository = adoptionReportRepository;
    }
    private final Logger logger = LoggerFactory.getLogger(AdoptionReportService.class);

    public AdoptionReport createAdoptionReport(AdoptionReport adoptionReport) {
        return adoptionReportRepository.save(adoptionReport);
    }

    public AdoptionReport readAdoptionReport(long id) {
        logger.debug("Calling method read AdoptionReport (id = {})", id);
        return adoptionReportRepository.findById(id).orElseThrow(() -> new NotFoundException("id not found"));
    }

    public AdoptionReport updateAdoptionReport(long id, AdoptionReport adoptionReport) {
        logger.debug("Calling method update AdoptionReport (id = {})", adoptionReport.getId());
        AdoptionReport oldVoAdoptionReport = readAdoptionReport(id);
        oldVoAdoptionReport.setAdopterId(adoptionReport.getAdopterId());
       // oldVoAdoptionReport.setReportDate(adoptionReport.getReportDate());
        oldVoAdoptionReport.setPetId(adoptionReport.getPetId());
        oldVoAdoptionReport.setPicture(adoptionReport.getPicture());
        oldVoAdoptionReport.setDiet(adoptionReport.getDiet());
        oldVoAdoptionReport.setWellBeing(adoptionReport.getWellBeing());
        oldVoAdoptionReport.setBehaviorChange(adoptionReport.getBehaviorChange());
        return adoptionReportRepository.save(oldVoAdoptionReport);
    }
}
