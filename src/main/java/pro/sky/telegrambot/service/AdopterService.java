package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.constants.AdopterStatus;
import pro.sky.telegrambot.constants.UpdateStatus;
import pro.sky.telegrambot.exception.AdopterNotFoundException;
import pro.sky.telegrambot.exception.BranchNotFoundException;
import pro.sky.telegrambot.exception.GuestNotFoundException;
import pro.sky.telegrambot.model.*;
import pro.sky.telegrambot.repository.AdopterRepository;
import pro.sky.telegrambot.repository.AdoptionReportRepository;
import pro.sky.telegrambot.repository.BranchParamsRepository;
import pro.sky.telegrambot.repository.GuestRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import static pro.sky.telegrambot.constants.Constants.*;

@Service
public class AdopterService {
    private final AdopterRepository adopterRepository;
    private final AdoptionReportRepository adoptionReportRepository;
    private final GuestRepository guestRepository;
    private final BranchParamsRepository branchParamsRepository;
    private final VolunteerService volunteerService;
    private TelegramBot telegramBot;
    private Logger logger = LoggerFactory.getLogger(AdopterService.class);

    public AdopterService(
            AdopterRepository adopterRepository, AdoptionReportRepository adoptionReportRepository,
            GuestRepository guestRepository, BranchParamsRepository branchParamsRepository,
            VolunteerService volunteerService, TelegramBot telegramBot) {
        this.adopterRepository = adopterRepository;
        this.adoptionReportRepository = adoptionReportRepository;
        this.guestRepository = guestRepository;
        this.branchParamsRepository = branchParamsRepository;
        this.volunteerService = volunteerService;
        this.telegramBot = telegramBot;
    }

    public Adopter createAdopter(Adopter adopter) {
        return adopterRepository.save(adopter);
    }

    public Adopter readAdopter(long id) {
        Adopter adopter = adopterRepository.findById(id).orElse(null);
        if (adopter == null) {
            throw new AdopterNotFoundException(id);
        }
        return adopter;
    }

    public Adopter updateAdopter(Adopter adopter) {
        if (adopterRepository.findById(adopter.getId()).orElse(null) == null) {
            return null;
        }
        return adopterRepository.save(adopter);
    }

    public UpdateStatus getUpdateStatus(long chatId) {
        Adopter adopter = adopterRepository.findByChatId(chatId);
        if (adopter == null) {
            return UpdateStatus.DEFAULT;
        }
        return adopter.getUpdateStatus();
    }

    public void setUpdateStatus(long chatId, UpdateStatus updateStatus) {
        Adopter adopter = adopterRepository.findByChatId(chatId);
        if (adopter == null) {
            throw new AdopterNotFoundException(chatId);
        }
        adopter.setUpdateStatus(updateStatus);
        adopterRepository.save(adopter);
    }

    private void setStatus(Adopter adopter, AdopterStatus status) {
        adopter.setStatus(status);
        adopterRepository.save(adopter);
    }

    /**
     * Check daily adopter reports.
     * Scan {@code [adoption_reports]} table to find incomplete reports or
     * adopters that forget to send the report.
     * It also scans {@code [adopters]} table to find adopters with approved/rejected status.
     * Send appropriate messages to volunteer/adopter. <br>
     * This is scheduled method. Examples of using: <br>
     * {@code @Scheduled(cron = "0 0/1 * * * *")} - runs every minute at 00 sec <br>
     * {@code @Scheduled(cron = "0 0 21 * * *")} - runs every day at 21:00
     *
     * @see Scheduled
     * @see Scheduled#cron()
     */
    @Scheduled(cron = "0 0 21 * * *") // runs every day at 21:00
    public void checkAdopterReports() {
        processAdoptersOnProbation();
        processAdoptersOnProbationExt();
        processAdoptersApproved();
        processAdoptersRejected();
    }

    /**
     * Process adopters on probation to find incomplete or missing reports.
     * Send appropriate message to volunteer.
     */
    private void processAdoptersOnProbation() {
        for (Adopter adopter : adopterRepository.findAdoptersByStatus(AdopterStatus.ON_PROBATION)) {
            processAdoptionReports(adopter);
        }
    }

    /**
     * Process adopters on extended probation to find incomplete or missing reports.
     * Send appropriate message to volunteer.
     */
    private void processAdoptersOnProbationExt() {
        for (Adopter adopter : adopterRepository.findAdoptersByStatus(AdopterStatus.ON_PROBATION_EXT)) {
            processAdoptionReports(adopter);
        }
    }

    private void processAdoptionReports(Adopter adopter) {
        List<AdoptionReport> adoptionReports = adoptionReportRepository.findAdoptionReportsByAdopterId(adopter);
        String fullName = adopter.getFirstName() + " " + adopter.getLastName();

        // In case there is no reports from the adopter yet
        if (adoptionReports.size() == 0) {
            sendMessageToVolunteer(String.format(ADOPTER_DID_NOT_SEND_REPORT_MESSAGE, fullName));
            return;
        }

        Long adoptionDay = adoptionReports
                .stream()
                .map(adoptionReport -> adoptionReport.getReportDate())
                .min(LocalDate::compareTo).get().toEpochDay();

        AdoptionReport lastAdoptionReport = adoptionReports
                .stream()
                .max(Comparator.comparing(AdoptionReport::getReportDate)).get();

        Long lastAdoptionReportDay = lastAdoptionReport.getReportDate().toEpochDay();
        long today = LocalDate.now().toEpochDay();

        if (today - adoptionDay >= adopter.getProbExtend()) {
            sendMessageToVolunteer(String.format(TRIAL_PERIOD_IS_OVER_MESSAGE, fullName));

        } else {
            if (lastAdoptionReportDay == null || today - lastAdoptionReportDay >= 1) {
                sendMessageToVolunteer(String.format(ADOPTER_DID_NOT_SEND_REPORT_MESSAGE, fullName));

            } else if (lastAdoptionReport.getPicture() == null) {
                sendMessageToVolunteer(String.format(INCOMPLETE_REPORT_PICTURE_MESSAGE, fullName));
                sendMessageToAdopter(adopter, INCOMPLETE_REPORT_ADOPTER_MESSAGE);

            } else if (lastAdoptionReport.getDiet() == null || lastAdoptionReport.getWellBeing() == null
                    || lastAdoptionReport.getBehaviorChange() == null) {
                sendMessageToVolunteer(String.format(INCOMPLETE_REPORT_DESC_MESSAGE, fullName));
                sendMessageToAdopter(adopter, INCOMPLETE_REPORT_ADOPTER_MESSAGE);
            }
            checkExtendedProbPeriod(adopter);
        }
    }

    /**
     * Process approved adopters. Send notification message to them.
     */
    private void processAdoptersApproved() {
        for (Adopter adopter : adopterRepository.findAdoptersByStatus(AdopterStatus.APPROVED)) {
            if (sendMessageToAdopter(adopter, PROBATION_APPROVED_MESSAGE)) {
                setStatus(adopter, AdopterStatus.VERIFIED);
            }
        }
    }

    /**
     * Process rejected adopters. Send notification message to them.
     */
    private void processAdoptersRejected() {
        for (Adopter adopter : adopterRepository.findAdoptersByStatus(AdopterStatus.REJECTED)) {
            if (sendMessageToAdopter(adopter, PROBATION_REJECTED_MESSAGE)) {
                setStatus(adopter, AdopterStatus.NOT_VERIFIED);
            }
        }
    }

    /**
     * Send message to random volunteer
     *
     * @param msgText message text
     */
    private void sendMessageToVolunteer(String msgText) {
        if (msgText == null) {
            return;
        }
        Volunteer volunteer = volunteerService.getRandomVolunteer();
        if (volunteer != null) {
            long chatId = volunteer.getChatId();
            SendMessage message = new SendMessage(chatId, msgText);
            sendMessage(message);
        }
    }

    /**
     * Send message to adopter
     *
     * @param adopter adopter to send message to
     * @param msgText message text
     */
    private boolean sendMessageToAdopter(Adopter adopter, String msgText) {
        if (msgText == null) {
            return false;
        }
        long chatId = adopter.getChatId();
        SendMessage message = new SendMessage(chatId, msgText);
        return sendMessage(message);
    }

    private void checkExtendedProbPeriod(Adopter adopter) {
        if (adopter.getStatus() != AdopterStatus.ON_PROBATION) {
            return;
        }
        Guest guest = guestRepository.findByChatId(adopter.getChatId());
        if (guest == null) {
            throw new GuestNotFoundException(
                    "name: " + adopter.getFirstName() + " " + adopter.getLastName() + ", chatId: " + adopter.getChatId());
        }
        BranchParams branchParams = branchParamsRepository.findByPetType(guest.getLastMenu()).orElse(null);
        if (branchParams == null) {
            throw new BranchNotFoundException();
        }
        if (adopter.getProbExtend() > branchParams.getProbPeriod()) {
            if (sendMessageToAdopter(adopter, String.format(PROBATION_PERIOD_EXTENDED_MESSAGE, adopter.getProbExtend()))) {
                setStatus(adopter, AdopterStatus.ON_PROBATION_EXT);
            }
        }
    }

    private boolean sendMessage(SendMessage message) {
        SendResponse response = telegramBot.execute(message);
        if (response != null && !response.isOk()) {
            logger.warn("Message was not sent: {}, error code: {}", message, response.errorCode());
            return false;
        }
        return true;
    }
}
