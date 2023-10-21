package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.*;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetFileResponse;
import com.pengrad.telegrambot.response.SendResponse;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pro.sky.telegrambot.constants.PetType;
import pro.sky.telegrambot.constants.UpdateStatus;
import pro.sky.telegrambot.exception.GuestNotFoundException;
import pro.sky.telegrambot.model.*;
import pro.sky.telegrambot.repository.*;
import pro.sky.telegrambot.service.AdopterService;
import pro.sky.telegrambot.service.VolunteerService;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import static pro.sky.telegrambot.constants.Constants.*;
import static pro.sky.telegrambot.constants.DocType.*;
import static pro.sky.telegrambot.constants.PetType.CAT;
import static pro.sky.telegrambot.constants.PetType.DOG;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private UpdateStatus updateStatus;
    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private TelegramBot telegramBot;

    public PetType getShelterType() {
        return shelterType;
    }

    public void setShelterType(PetType shelterType) {
        this.shelterType = shelterType;
    }

    private PetType shelterType;
    private final GuestRepository guestRepository;
    private final AdopterRepository adopterRepository;
    private final AdoptionDocRepository adoptionDocRepository;
    private final AdoptionReportRepository adoptionReportRepository;
    private final BranchParamsRepository branchParamsRepository;
    private final VolunteerService volunteerService;
    private final AdopterService adopterService;

    public TelegramBotUpdatesListener(TelegramBot telegramBot, GuestRepository guestRepository, AdopterRepository adopterRepository, AdoptionDocRepository adoptionDocRepository, AdoptionReportRepository adoptionReportRepository, BranchParamsRepository branchParamsRepository, VolunteerService volunteerService, AdopterService adopterService) {
        this.telegramBot = telegramBot;
        this.guestRepository = guestRepository;
        this.adopterRepository = adopterRepository;
        this.adoptionDocRepository = adoptionDocRepository;
        this.adoptionReportRepository = adoptionReportRepository;
        this.branchParamsRepository = branchParamsRepository;
        this.volunteerService = volunteerService;
        this.adopterService = adopterService;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {

            logger.info("Processing update: {}", update);

            if (update.message() != null) {
                processMessage(update);
            } else {
                processButtonClick(update);
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void sendMessage(SendMessage message) {
        SendResponse response = telegramBot.execute(message);
        if (response != null && !response.isOk()) {
            logger.warn("Message was not sent: {}, error code: {}", message, response.errorCode());
        }
    }

    /**
     * Creates buttons for the shelter type selection message (reply to the /start command)
     *
     * @return {@code InlineKeyboardMarkup}
     */
    private InlineKeyboardMarkup createButtonsShelterTypeSelect() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_CAT_SHELTER_TEXT).callbackData(BUTTON_CAT_SHELTER_CALLBACK_TEXT));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_DOG_SHELTER_TEXT).callbackData(BUTTON_DOG_SHELTER_CALLBACK_TEXT));
        return inlineKeyboardMarkup;
    }

    /**
     * Creates buttons for the reply message to the shelter type selection (Stage 0)
     *
     * @return {@code InlineKeyboardMarkup}
     */
    private InlineKeyboardMarkup createButtonsStage0() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_STAGE1_TEXT).callbackData(BUTTON_STAGE1_CALLBACK_TEXT));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_STAGE2_TEXT).callbackData(BUTTON_STAGE2_CALLBACK_TEXT));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_STAGE3_TEXT).callbackData(BUTTON_STAGE3_CALLBACK_TEXT));
        return inlineKeyboardMarkup;
    }

    private InlineKeyboardMarkup createButtonsStage1() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_INFO_SHELTER_TEXT).callbackData(BUTTON_INFO_SHELTER_CALLBACK_TEXT));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_INFO_SECURITY_TEXT).callbackData(BUTTON_INFO_SECURITY_CALLBACK_TEXT));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_INFO_SAFETY_PRECAUTIONS_TEXT).callbackData(BUTTON_INFO_SAFETY_PRECAUTIONS_CALLBACK_TEXT));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_SHARE_CONTACT_DETAILS_TEXT).callbackData(BUTTON_SHARE_CONTACT_CALLBACK_TEXT));
        return inlineKeyboardMarkup;
    }

    private InlineKeyboardMarkup createButtonsStage2() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_RULES_MEETING_ANIMAL_TEXT).callbackData(BUTTON_RULES_MEETING_ANIMAL_CALLBACK_TEXT));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_DOCS_FOR_ADOPTION_TEXT).callbackData(BUTTON_DOCS_FOR_ADOPTION_CALLBACK_TEXT));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_RECOMMENDATIONS_FOR_TRANSPORT_TEXT).callbackData(BUTTON_RECOMMENDATIONS_FOR_TRANSPORT_CALLBACK_TEXT));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_ARRANGEMENT_FOR_PUPPY_TEXT).callbackData(BUTTON_ARRANGEMENT_FOR_PUPPY_CALLBACK_TEXT));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_ARRANGEMENT_FOR_ADULT_TEXT).callbackData(BUTTON_ARRANGEMENT_FOR_ADULT_CALLBACK_TEXT));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_ADVICES_FOR_DISABLED_PET_TEXT).callbackData(BUTTON_ADVICES_FOR_DISABLED_PET_CALLBACK_TEXT));
        if (shelterType.equals(DOG)) {
            inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_ADVICES_FROM_KINOLOG_TEXT).callbackData(BUTTON_ADVICES_FROM_KINOLOG_CALLBACK_TEXT));
            inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_RECOMMENDED_KINOLOGS_TEXT).callbackData(BUTTON_RECOMMENDED_KINOLOGS_CALLBACK_TEXT));
        }
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_REASONS_FOR_REFUSAL_TEXT).callbackData(BUTTON_REASONS_FOR_REFUSAL_CALLBACK_TEXT));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_SHARE_CONTACT_DETAILS_TEXT).callbackData(BUTTON_SHARE_CONTACT_CALLBACK_TEXT));
        return inlineKeyboardMarkup;
    }

    private InlineKeyboardMarkup createButtonsStage3() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_REPORT_TEMPLATE_TEXT).callbackData(BUTTON_REPORT_TEMPLATE_CALLBACK_TEXT));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_SEND_REPORT_TEXT).callbackData(BUTTON_SEND_REPORT_CALLBACK_TEXT));
        return inlineKeyboardMarkup;
    }

    private InlineKeyboardMarkup createButtonsSendReport() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_CANCEL_TEXT).callbackData(BUTTON_CANCEL_SEND_REPORT_CALLBACK_TEXT));
        return inlineKeyboardMarkup;
    }

    private ReplyKeyboardMarkup createRequestContactKeyboardButton() {
        KeyboardButton keyboardButton1 = new KeyboardButton(BUTTON_SHARE_CONTACT_TEXT);
        KeyboardButton keyboardButton2 = new KeyboardButton(BUTTON_CANCEL_TEXT);
        keyboardButton1.requestContact(true);
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(keyboardButton1, keyboardButton2);
        replyKeyboardMarkup.resizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    private ReplyKeyboardMarkup createMainMenuKeyboardButtons() {
        KeyboardButton keyboardButton1 = new KeyboardButton(BUTTON_MAIN_MENU_TEXT);
        KeyboardButton keyboardButton2 = new KeyboardButton(BUTTON_CALL_VOLUNTEER_TEXT);
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(keyboardButton1, keyboardButton2);
        replyKeyboardMarkup.resizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    /**
     * Process messages from user.
     *
     * @param update user input (can be text, button click, emoji, sticker, etc.)
     *               but process only messages with {@code message()} defined.
     */
    private void processMessage(Update update) {
        if (update.message().contact() != null) {
            // Save new adopter contacts in our db
            saveAdopter(update);
            return;
        }

        long chatId = update.message().chat().id();

        // Read updateStatus for the current adopter
        updateStatus = adopterService.getUpdateStatus(chatId);

        if (updateStatus == UpdateStatus.WAITING_FOR_PET_PICTURE) {
            saveAdoptionReportPhoto(update);
            adopterService.setUpdateStatus(chatId, UpdateStatus.WAITING_FOR_PET_DIET);
            return;
        }
        if (updateStatus == UpdateStatus.WAITING_FOR_PET_DIET) {
            saveAdoptionReportDiet(update);
            adopterService.setUpdateStatus(chatId, UpdateStatus.WAITING_FOR_WELL_BEING);
            return;
        }
        if (updateStatus == UpdateStatus.WAITING_FOR_WELL_BEING) {
            saveAdoptionReportWellBeing(update);
            adopterService.setUpdateStatus(chatId, UpdateStatus.WAITING_FOR_BEHAVIOR_CHANGE);
            return;
        }
        if (updateStatus == UpdateStatus.WAITING_FOR_BEHAVIOR_CHANGE) {
            saveAdoptionReportBehaviorChange(update);
            adopterService.setUpdateStatus(chatId, UpdateStatus.DEFAULT);
            return;
        }

        if (update.message().text() == null) {
            // For stickers incomeMsgText is null
            return;
        }

        switch (update.message().text()) {
            case "/start", BUTTON_MAIN_MENU_TEXT:
                processStartCommand(update);
                break;
            case BUTTON_CALL_VOLUNTEER_TEXT:
                // Call a volunteer
                callVolunteer(update);
                break;
            case BUTTON_CANCEL_TEXT:
                cancelShareContact(update);
                break;
        }
    }

    private void cancelShareContact(Update update) {
        long chatId = update.message().chat().id();
        SendMessage message = new SendMessage(chatId, CANCEL_SHARE_CONTACT_MSG_TEXT);
        sendMessage(message.replyMarkup(createMainMenuKeyboardButtons()));
    }

    private void cancelSendReport(long chatId) {
        updateStatus = adopterService.getUpdateStatus(chatId);
        if (updateStatus != UpdateStatus.WAITING_FOR_PET_PICTURE) {
            return;
        }

        adopterService.setUpdateStatus(chatId, UpdateStatus.DEFAULT);

        Adopter adopterId = adopterRepository.findByChatId(chatId);
        LocalDate date = LocalDate.now();
        AdoptionReport adoptionReport = adoptionReportRepository.findAdoptionReportByAdopterIdAndReportDate(adopterId, date);
        if (adoptionReport != null) {
            adoptionReportRepository.delete(adoptionReport);
        }

        SendMessage message = new SendMessage(chatId, CANCEL_SEND_REPORT_MSG_TEXT);
        sendMessage(message);
    }

    /**
     * Process button clicks from user.
     *
     * @param update user input (can be text, button click, emoji, sticker, etc.)
     *               but process only button clicks with {@code callbackData()} defined.
     * @see InlineKeyboardButton#callbackData()
     */
    private void processButtonClick(Update update) {
        CallbackQuery callbackQuery = update.callbackQuery();
        if (callbackQuery != null) {
            long chatId = callbackQuery.message().chat().id();
            switch (callbackQuery.data()) {
                case BUTTON_CAT_SHELTER_CALLBACK_TEXT:
                    // Cat shelter selected
                    sendButtonClickMessage(chatId, BUTTON_CAT_SHELTER_TEXT);
                    processCatShelterClick(chatId);
                    break;
                case BUTTON_DOG_SHELTER_CALLBACK_TEXT:
                    // Dog shelter selected
                    sendButtonClickMessage(chatId, BUTTON_DOG_SHELTER_TEXT);
                    processDogShelterClick(chatId);
                    break;
                case BUTTON_STAGE1_CALLBACK_TEXT:
                    // General info about the shelter (stage 1)
                    sendButtonClickMessage(chatId, BUTTON_STAGE1_TEXT);
                    processStage1Click(chatId);
                    break;
                case BUTTON_STAGE2_CALLBACK_TEXT:
                    // How to adopt a dog/cat (stage 2)
                    sendButtonClickMessage(chatId, BUTTON_STAGE2_TEXT);
                    processStage2Click(chatId, update);
                    break;
                case BUTTON_STAGE3_CALLBACK_TEXT:
                    // Send a follow-up report (stage 3)
                    sendButtonClickMessage(chatId, BUTTON_STAGE3_TEXT);
                    processStage3Click(chatId);
                    break;
                case BUTTON_REPORT_TEMPLATE_CALLBACK_TEXT:
                    SendMessage instructionMessage = new SendMessage(chatId, ADOPTION_REPORT_INSTRUCTION);
                    sendMessage(instructionMessage);
                    break;
                case BUTTON_SEND_REPORT_CALLBACK_TEXT:
                    saveAdoptionReport(chatId);
                    break;
                case BUTTON_SHARE_CONTACT_CALLBACK_TEXT:
                    // Share your contact details
                    sendButtonClickMessage(chatId, BUTTON_SHARE_CONTACT_DETAILS_TEXT);
                    shareContact(update);
                    break;
                case BUTTON_INFO_SHELTER_CALLBACK_TEXT:
                    // Safety information
                    sendButtonClickMessage(chatId, BUTTON_INFO_SHELTER_TEXT);
                    processGettingInformationAboutShelter(chatId);
                    break;
                case BUTTON_INFO_SECURITY_CALLBACK_TEXT:
                    // Obtaining security contacts
                    sendButtonClickMessage(chatId, BUTTON_INFO_SECURITY_TEXT);
                    processGettingInformationAboutSecurity(chatId);
                    break;
                case BUTTON_INFO_SAFETY_PRECAUTIONS_CALLBACK_TEXT:
                    // Obtaining Safety Instructions
                    sendButtonClickMessage(chatId, BUTTON_INFO_SAFETY_PRECAUTIONS_TEXT);
                    processGettingInformationAboutSafetyPrecautions(chatId);
                    break;
                case BUTTON_RULES_MEETING_ANIMAL_CALLBACK_TEXT:
                    // Instruction how to meet animal first time
                    sendButtonClickMessage(chatId, BUTTON_RULES_MEETING_ANIMAL_TEXT);
                    processInfoMeetingClick(chatId);
                    break;
                case BUTTON_DOCS_FOR_ADOPTION_CALLBACK_TEXT:
                    // List of required docs
                    sendButtonClickMessage(chatId, BUTTON_DOCS_FOR_ADOPTION_TEXT);
                    processListOfDocsClick(chatId);
                    break;
                case BUTTON_RECOMMENDATIONS_FOR_TRANSPORT_CALLBACK_TEXT:
                    // Recommendation how to transport animals
                    sendButtonClickMessage(chatId, BUTTON_RECOMMENDATIONS_FOR_TRANSPORT_TEXT);
                    processTransportAnimal(chatId);
                    break;
                case BUTTON_ARRANGEMENT_FOR_PUPPY_CALLBACK_TEXT:
                    //  Arrangement for little animal in house
                    sendButtonClickMessage(chatId, BUTTON_ARRANGEMENT_FOR_PUPPY_TEXT);
                    processRecForLittle(chatId);
                    break;
                case BUTTON_ARRANGEMENT_FOR_ADULT_CALLBACK_TEXT:
                    // Arrangement for adult animal in house
                    sendButtonClickMessage(chatId, BUTTON_ARRANGEMENT_FOR_ADULT_TEXT);
                    processRecForAdult(chatId);
                    break;
                case BUTTON_ADVICES_FOR_DISABLED_PET_CALLBACK_TEXT:
                    // Advices how to be with disable animals
                    sendButtonClickMessage(chatId, BUTTON_ADVICES_FOR_DISABLED_PET_TEXT);
                    processRecForDisable(chatId);
                    break;
                case BUTTON_ADVICES_FROM_KINOLOG_CALLBACK_TEXT:
                    // Advices from kinolog
                    sendButtonClickMessage(chatId, BUTTON_ADVICES_FROM_KINOLOG_TEXT);
                    processKinologAdvices(chatId);
                    break;
                case BUTTON_RECOMMENDED_KINOLOGS_CALLBACK_TEXT:
                    // List of recommended kinologs
                    sendButtonClickMessage(chatId, BUTTON_RECOMMENDED_KINOLOGS_TEXT);
                    processRecKinologs(chatId);
                    break;
                case BUTTON_REASONS_FOR_REFUSAL_CALLBACK_TEXT:
                    // Reasons why we can refuse you
                    sendButtonClickMessage(chatId, BUTTON_REASONS_FOR_REFUSAL_TEXT);
                    processReasonsRefusal(chatId);
                    break;
                case BUTTON_CANCEL_SEND_REPORT_CALLBACK_TEXT:
                    // Cancel Send Report
                    cancelSendReport(chatId);
                    break;
            }
        }
    }

    private void shareContact(Update update) {
        long chatId = update.callbackQuery().from().id();
        SendMessage message = new SendMessage(chatId, SHARE_CONTACT_MSG_TEXT);
        // Adding buttons
        message.replyMarkup(createRequestContactKeyboardButton());
        sendMessage(message);
    }

    private void processStartCommand(Update update) {
        long chatId = update.message().chat().id();
        Guest guest = guestRepository.findByChatId(chatId);
        if (guest == null) {
            sendShelterTypeSelectMessage(chatId);
        } else {
            shelterType = guest.getLastMenu();
            if (shelterType == null) {
                sendShelterTypeSelectMessage(chatId);
                return;
            }
            switch (shelterType) {
                case DOG:
                    sendStage0Message(chatId, DOG_SHELTER_WELCOME_MSG_TEXT);
                    break;
                case CAT:
                    sendStage0Message(chatId, CAT_SHELTER_WELCOME_MSG_TEXT);
                    break;
                default:
                    sendShelterTypeSelectMessage(chatId);
            }
        }
    }

    private void processCatShelterClick(long chatId) {
        shelterType = PetType.CAT;
        saveGuest(chatId, shelterType);
        sendStage0Message(chatId, CAT_SHELTER_WELCOME_MSG_TEXT);
    }

    private void processDogShelterClick(long chatId) {
        shelterType = DOG;
        saveGuest(chatId, shelterType);
        sendStage0Message(chatId, DOG_SHELTER_WELCOME_MSG_TEXT);
    }

    private void sendShelterTypeSelectMessage(long chatId) {
        SendMessage message = new SendMessage(chatId, SHELTER_TYPE_SELECT_MSG_TEXT);
        // Adding buttons
        message.replyMarkup(createButtonsShelterTypeSelect());
        sendMessage(message);
    }

    private void sendStage0Message(long chatId, String messageText) {
        SendMessage message = new SendMessage(chatId, messageText);
        // Remove all buttons
        //message.replyMarkup(new ReplyKeyboardRemove());
        // Adding buttons
        message.replyMarkup(createButtonsStage0());
        sendMessage(message);
    }

    /**
     * Processing request: General info about the shelter (stage 1)
     *
     * @param chatId
     */
    private void processStage1Click(long chatId) {
        if (shelterType == null) {
            return;
        }
        String messageText = null;
        switch (shelterType) {
            case DOG:
                messageText = DOG_SHELTER_STAGE1_WELCOME_MSG_TEXT;
                break;
            case CAT:
                messageText = CAT_SHELTER_STAGE1_WELCOME_MSG_TEXT;
                break;
        }
        Message message1 = new Message();

        SendMessage message = new SendMessage(chatId, messageText);
        // Adding buttons
        message.replyMarkup(createButtonsStage1());
        sendMessage(message);
    }

    /**
     * Processing request: How to adopt a dog/cat (stage 2)
     *
     * @param chatId
     */
    private void processStage2Click(long chatId, Update update) {
        if (shelterType == null) {
            return;
        }
        String messageText = switch (shelterType) {
            case DOG -> DOG_SHELTER_STAGE2_WELCOME_MSG_TEXT;
            case CAT -> CAT_SHELTER_STAGE2_WELCOME_MSG_TEXT;
        };
        SendMessage message = new SendMessage(chatId, messageText);
        // Adding buttons
        message.replyMarkup(createButtonsStage2());

        sendMessage(message);
    }

    private void processInfoMeetingClick(long chatId) {
        if (shelterType == null) {
            return;
        }
        AdoptionDoc doc = null;
        switch (shelterType) {
            case DOG:
                doc = adoptionDocRepository.findById(INFO_MEETING_DOG).orElse(null);
                break;
            case CAT:
                doc = adoptionDocRepository.findById(INFO_MEETING_CAT).orElse(null);
                break;
        }
        if (doc != null) {
            String messageText = doc.getDescription();
            SendMessage message = new SendMessage(chatId, messageText);
            sendMessage(message);
        }
    }

    private void processListOfDocsClick(long chatId) {
        AdoptionDoc doc = adoptionDocRepository.findById(LIST_OF_DOCS).orElse(null);
        if (doc != null) {
            String messageText = doc.getDescription();
            SendMessage message = new SendMessage(chatId, messageText);
            sendMessage(message);
        }
    }

    private void processTransportAnimal(long chatId) {
        AdoptionDoc doc = adoptionDocRepository.findById(TRANSPORT_ANIMAL).orElse(null);
        if (doc != null) {
            String messageText = doc.getDescription();
            SendMessage message = new SendMessage(chatId, messageText);
            sendMessage(message);
        }
    }

    private void processRecForLittle(long chatId) {
        AdoptionDoc doc = adoptionDocRepository.findById(REC_FOR_LITTLE).orElse(null);
        if (doc != null) {
            String messageText = doc.getDescription();
            SendMessage message = new SendMessage(chatId, messageText);
            sendMessage(message);
        }
    }

    private void processRecForAdult(long chatId) {
        AdoptionDoc doc = adoptionDocRepository.findById(REC_FOR_ADULT).orElse(null);
        if (doc != null) {
            String messageText = doc.getDescription();
            SendMessage message = new SendMessage(chatId, messageText);
            sendMessage(message);
        }
    }

    private void processRecForDisable(long chatId) {
        AdoptionDoc doc = adoptionDocRepository.findById(REC_FOR_DISABLE).orElse(null);
        if (doc != null) {
            String messageText = doc.getDescription();
            SendMessage message = new SendMessage(chatId, messageText);
            sendMessage(message);
        }
    }

    private void processKinologAdvices(long chatId) {
        AdoptionDoc doc = adoptionDocRepository.findById(KINOLOG_ADVICES).orElse(null);
        if (doc != null) {
            String messageText = doc.getDescription();
            SendMessage message = new SendMessage(chatId, messageText);
            sendMessage(message);
        }
    }

    private void processRecKinologs(long chatId) {
        AdoptionDoc doc = adoptionDocRepository.findById(REC_KINOLOGS).orElse(null);
        if (doc != null) {
            String messageText = doc.getDescription();
            SendMessage message = new SendMessage(chatId, messageText);
            sendMessage(message);
        }
    }

    private void processReasonsRefusal(long chatId) {
        AdoptionDoc doc = adoptionDocRepository.findById(REASONS_REFUSAL).orElse(null);
        if (doc != null) {
            String messageText = doc.getDescription();
            SendMessage message = new SendMessage(chatId, messageText);
            sendMessage(message);
        }
    }

    /**
     * Processing request: Send a follow-up report (stage 3)
     *
     * @param chatId
     */
    private void processStage3Click(long chatId) {
        if (shelterType == null) {
            return;
        }
        String messageText = null;
        switch (shelterType) {
            case DOG:
                messageText = DOG_SHELTER_STAGE3_WELCOME_MSG_TEXT;
                break;
            case CAT:
                messageText = CAT_SHELTER_STAGE3_WELCOME_MSG_TEXT;
                break;
        }
        SendMessage message = new SendMessage(chatId, messageText);
        // Adding buttons
        message.replyMarkup(createButtonsStage3());
        sendMessage(message);
    }

    /**
     * Sends technical message that the button has been clicked.
     * Can be disabled if it is not needed.
     *
     * @param chatId  sends message to this chat
     * @param message the message itself
     */
    private void sendButtonClickMessage(long chatId, String message) {
        sendMessage(new SendMessage(chatId, message));
    }

    /**
     * Generates and sends message to volunteer from volunteers table.
     * If {@code @username} of the guest is defined it mentions him by {@code @username}.
     * Otherwise, it mentions him by {@code chat_id}.
     * If volunteers table is empty - sends {@code NO_VOLUNTEERS_TEXT} message.
     *
     * @param update 'Call a volunteer' button click.
     */
    private void callVolunteer(Update update) {
        String userId = ""; // guest's chat_id or username
        long chatId = 0; // volunteer's chat_id
        userId += update.message().from().id();
        logger.info("UserId = {}", userId);
        Volunteer volunteer = volunteerService.getRandomVolunteer();
        if (volunteer == null) {
            // Guest chat_id. Send message to the guest.
            chatId = Long.parseLong(userId);
            SendMessage message = new SendMessage(chatId, NO_VOLUNTEERS_TEXT);
            sendMessage(message);
        } else {
            // Volunteer chat_id. Send message to volunteer.
            chatId = volunteer.getChatId();
            if (update.message().from().username() != null) {
                userId = "@" + update.message().from().username();
                SendMessage message = new SendMessage(chatId, String.format(CONTACT_TELEGRAM_USERNAME_TEXT, userId));
                sendMessage(message);
            } else {
                SendMessage message = new SendMessage(chatId, String.format(CONTACT_TELEGRAM_ID_TEXT, userId));
                sendMessage(message);
            }
        }
    }

    private void saveGuest(long chatId, PetType lastMenu) {
        Guest guest = guestRepository.findByChatId(chatId);
        if (guest == null) {
            guest = new Guest(chatId, new Timestamp(System.currentTimeMillis()), lastMenu);
        } else {
            guest.setLastMenu(lastMenu);
        }
        guestRepository.save(guest);
    }

    /**
     * Save new adopter into {@code [adopters]} table.
     * Default probation period for adopter equals the selected shelter probation period,
     * i.e. by default {@code [adopters].[prob_extend] = [branch_params].[prob_period]}.
     * The {@code [adopters].[prob_extend]} can be increased later by volunteer if needed.
     *
     * @param update
     */
    private void saveAdopter(Update update) {
        if (update.message().contact() != null) {
            String firstName = update.message().contact().firstName();
            String lastName = update.message().contact().lastName();
            String phone1 = update.message().contact().phoneNumber();
            String username = update.message().chat().username();
            long chatId = update.message().chat().id();
            int probExtendDefault = 30; //default

            Adopter adopter = adopterRepository.findByChatId(chatId);
            if (adopter == null) {
                Guest guest = guestRepository.findByChatId(chatId);
                if (guest == null) {
                    throw new GuestNotFoundException(chatId);
                }
                adopter = new Adopter(firstName, lastName, phone1, chatId, username, probExtendDefault);
                adopter.setUpdateStatus(UpdateStatus.DEFAULT);
                adopterRepository.save(adopter);
                SendMessage message = new SendMessage(chatId, SAVE_ADOPTER_SUCCESS_TEXT + ' ' + WE_WILL_CALL_YOU_TEXT);
                sendMessage(message.replyMarkup(createMainMenuKeyboardButtons()));
            } else {
                SendMessage message = new SendMessage(chatId, ADOPTER_ALREADY_EXISTS_TEXT + ' ' + WE_WILL_CALL_YOU_TEXT);
                sendMessage(message.replyMarkup(createMainMenuKeyboardButtons()));
            }
        }
    }

    private void saveAdoptionReport(long chatId) {
        Adopter adopterId = adopterRepository.findByChatId(chatId);
        LocalDate date = LocalDate.now();

        AdoptionReport adoptionReport = adoptionReportRepository.findAdoptionReportByAdopterIdAndReportDate(adopterId, date);
        if (adoptionReport == null) {
            adoptionReport = new AdoptionReport(adopterId, date, null, null, null, null);
            adoptionReportRepository.save(adoptionReport);
            adopterService.setUpdateStatus(chatId, UpdateStatus.WAITING_FOR_PET_PICTURE);
            SendMessage requestPhotoMessage = new SendMessage(chatId, PHOTO_WAITING_MESSAGE);
            requestPhotoMessage.replyMarkup(createButtonsSendReport());
            sendMessage(requestPhotoMessage);
        } else {
            SendMessage message = new SendMessage(chatId, ADOPTION_REPORT_ALREADY_EXIST);
            sendMessage(message);
        }
    }

    private void saveAdoptionReportPhoto(Update update) {
        long chatId = update.message().chat().id();
        Adopter adopter = adopterRepository.findByChatId(chatId);
        LocalDate date = LocalDate.now();
        AdoptionReport adoptionReport = adoptionReportRepository.findAdoptionReportByAdopterIdAndReportDate(adopter, date);
        if (update.message().photo() != null) {
            byte[] image = getPhoto(update);
            adoptionReport.setPicture(image);
            adoptionReportRepository.save(adoptionReport);
            SendMessage savePhotoMessage = new SendMessage(chatId, PHOTO_SAVED_MESSAGE);
            sendMessage(savePhotoMessage);
        }
    }

    private void saveAdoptionReportDiet(Update update) {
        long chatId = update.message().chat().id();
        Adopter adopter = adopterRepository.findByChatId(chatId);
        LocalDate date = LocalDate.now();
        AdoptionReport adoptionReport = adoptionReportRepository.findAdoptionReportByAdopterIdAndReportDate(adopter, date);
        String diet = adoptionReport.getDiet();
        if (diet == null) {
            String newDiet = update.message().text();
            adoptionReport.setDiet(newDiet);
            adoptionReportRepository.save(adoptionReport);
            SendMessage saveDietMessage = new SendMessage(chatId, DIET_SAVED_MESSAGE);
            sendMessage(saveDietMessage);
        }
    }

    private void saveAdoptionReportWellBeing(Update update) {
        long chatId = update.message().chat().id();
        Adopter adopter = adopterRepository.findByChatId(chatId);
        LocalDate date = LocalDate.now();
        AdoptionReport adoptionReport = adoptionReportRepository.findAdoptionReportByAdopterIdAndReportDate(adopter, date);
        String wellBeing = adoptionReport.getWellBeing();
        if (wellBeing == null) {
            String newWellBeing = update.message().text();
            adoptionReport.setWellBeing(newWellBeing);
            adoptionReportRepository.save(adoptionReport);
            SendMessage saveWellBeingMessage = new SendMessage(chatId, WELL_BEING_SAVED_MESSAGE);
            sendMessage(saveWellBeingMessage);
        }
    }

    private void saveAdoptionReportBehaviorChange(Update update) {
        long chatId = update.message().chat().id();
        Adopter adopter = adopterRepository.findByChatId(chatId);
        LocalDate date = LocalDate.now();
        AdoptionReport adoptionReport = adoptionReportRepository.findAdoptionReportByAdopterIdAndReportDate(adopter, date);
        String behaviorChane = adoptionReport.getBehaviorChange();
        if (behaviorChane == null) {
            String newBehaviorChane = update.message().text();
            adoptionReport.setBehaviorChange(newBehaviorChane);
            adoptionReportRepository.save(adoptionReport);
            SendMessage saveBehaviorChangeMessage = new SendMessage(chatId, BEHAVIOR_CHANGE_SAVED_MESSAGE);
            sendMessage(saveBehaviorChangeMessage);
        }
    }

    private void processGettingInformationAboutShelter(long chatId) {
        if (shelterType == null) {
            return;
        }
        StringBuilder messageText = new StringBuilder();
        switch (shelterType) {
            case DOG:
                BranchParams dogParams = branchParamsRepository.findByPetType(DOG).orElse(null);
                if (dogParams != null) {
                    messageText.append("Город: ").append(dogParams.getCity()).append("\n");
                    messageText.append("Адрес: ").append(dogParams.getAddress()).append("\n");
                    messageText.append("Часы работы: ").append(dogParams.getWorkHours()).append("\n");
                }
                break;
            case CAT:
                BranchParams catParams = branchParamsRepository.findByPetType(CAT).orElse(null);
                if (catParams != null) {
                    messageText.append("Город: ").append(catParams.getCity()).append("\n");
                    messageText.append("Адрес: ").append(catParams.getAddress()).append("\n");
                    messageText.append("Часы работы: ").append(catParams.getWorkHours()).append("\n");
                }
                break;
        }
        SendMessage message = new SendMessage(chatId, messageText.toString());
        sendMessage(message);
    }

    private void processGettingInformationAboutSecurity(long chatId) {
        if (shelterType == null) {
            return;
        }
        StringBuilder messageText = new StringBuilder();
        switch (shelterType) {
            case DOG:
                BranchParams dogParams = branchParamsRepository.findByPetType(DOG).orElse(null);
                if (dogParams != null) {
                    messageText.append(dogParams.getSecurityContact());
                }
                break;
            case CAT:
                BranchParams catParams = branchParamsRepository.findByPetType(CAT).orElse(null);
                if (catParams != null) {
                    messageText.append(catParams.getSecurityContact());
                }
                break;
        }
        SendMessage message = new SendMessage(chatId, messageText.toString());
        sendMessage(message);
    }

    private void processGettingInformationAboutSafetyPrecautions(long chatId) {
        if (shelterType == null) {
            return;
        }
        StringBuilder messageText = new StringBuilder();
        switch (shelterType) {
            case DOG:
                BranchParams dogParams = branchParamsRepository.findByPetType(DOG).orElse(null);
                if (dogParams != null) {
                    messageText.append(dogParams.getSecurityInfo());
                }
                break;
            case CAT:
                BranchParams catParams = branchParamsRepository.findByPetType(CAT).orElse(null);
                if (catParams != null) {
                    messageText.append(catParams.getSecurityInfo());
                }
                break;
        }
        SendMessage message = new SendMessage(chatId, messageText.toString());
        sendMessage(message);
    }

    public byte[] getPhoto(Update update) {
        if (update.message().photo() != null) {
            PhotoSize[] photoSizes = update.message().photo();
            for (PhotoSize photoSize : photoSizes) {
                GetFile getFile = new GetFile(photoSize.fileId());
                GetFileResponse getFileResponse = telegramBot.execute(getFile);
                if (getFileResponse.isOk()) {
                    File file = getFileResponse.file();
                    String extension = StringUtils.getFilenameExtension(file.filePath());
                    try {
                        byte[] image = telegramBot.getFileContent(file);
                        return image;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return null;
    }
}

