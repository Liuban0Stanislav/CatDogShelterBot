package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegrambot.constants.DocType;
import pro.sky.telegrambot.constants.PetType;
import pro.sky.telegrambot.constants.UpdateStatus;
import pro.sky.telegrambot.model.*;
import pro.sky.telegrambot.repository.*;
import pro.sky.telegrambot.service.AdopterService;
import pro.sky.telegrambot.service.VolunteerService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static pro.sky.telegrambot.constants.Constants.*;
import static pro.sky.telegrambot.constants.DocType.*;

@ExtendWith(MockitoExtension.class)
class TelegramBotUpdatesListenerTest {

    @Mock
    private TelegramBot telegramBot;

    @InjectMocks
    private TelegramBotUpdatesListener telegramBotUpdatesListener;

    @Mock
    private GuestRepository guestRepository;

    @Mock
    private AdopterRepository adopterRepository;

    @Mock
    private AdoptionDocRepository adoptionDocRepository;

    @Mock
    private AdoptionReportRepository adoptionReportRepository;

    @Mock
    private BranchParamsRepository branchParamsRepository;

    @Mock
    private VolunteerService volunteerService;

    @Mock
    private AdopterService adopterService;

    /* Testing '/start' command when it is a new guest (unknown guest). */
    @Test
    public void handleStartCommandNewGuestTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("text_update.json").toURI()));
        Update update = getUpdateMessage(json, "/start");
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567809L);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo(SHELTER_TYPE_SELECT_MSG_TEXT);
    }

    /* Testing '/start' command with the guest who has previously selected CAT shelter.
    *  (and he is already saved in the [guests] table) */
    @Test
    public void handleStartCommandCatShelterGuestTest() throws URISyntaxException, IOException {
        long chatId = 1234567809L;
        Guest guest = new Guest(chatId, Timestamp.valueOf("2023-03-01 14:45:06"), PetType.CAT);

        when(guestRepository.findByChatId(any(Long.class))).thenReturn(guest);

        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("text_update.json").toURI()));
        Update update = getUpdateMessage(json, "/start");
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(chatId);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo(CAT_SHELTER_WELCOME_MSG_TEXT);
    }

    /* Testing '/start' command with the guest who has previously selected DOG shelter.
     *  (and he is already saved in the [guests] table) */
    @Test
    public void handleStartCommandDogShelterGuestTest() throws URISyntaxException, IOException {
        long chatId = 1234567809L;
        Guest guest = new Guest(chatId, Timestamp.valueOf("2023-03-01 14:45:06"), PetType.DOG);

        when(guestRepository.findByChatId(any(Long.class))).thenReturn(guest);

        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("text_update.json").toURI()));
        Update update = getUpdateMessage(json, "/start");
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(chatId);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo(DOG_SHELTER_WELCOME_MSG_TEXT);
    }

    @Test
    public void handleCatShelterSelectTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("data_update.json").toURI()));
        Update update = getUpdateMessage(json, BUTTON_CAT_SHELTER_CALLBACK_TEXT);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, Mockito.times(2)).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567809L);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo(CAT_SHELTER_WELCOME_MSG_TEXT);
    }

    @Test
    public void handleDogShelterSelectTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("data_update.json").toURI()));
        Update update = getUpdateMessage(json, BUTTON_DOG_SHELTER_CALLBACK_TEXT);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, Mockito.times(2)).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567809L);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo(DOG_SHELTER_WELCOME_MSG_TEXT);
    }

    /* Testing Call Volunteer method when guest has no @username defined (his chatId is used in this case). */
    @Test
    public void handleCallVolunteerChatIdTest() throws URISyntaxException, IOException {
        Long volunteerId = 1234567809L;
        String userId = "1234567809";
        Volunteer volunteer = new Volunteer(1, "Vasya", volunteerId, "https://t.me/vasyapupkin");

        when(volunteerService.getRandomVolunteer()).thenReturn(volunteer);

        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("text_update.json").toURI()));
        Update update = getUpdateMessage(json, BUTTON_CALL_VOLUNTEER_TEXT);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(volunteerId);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo(String.format(CONTACT_TELEGRAM_ID_TEXT, userId));
    }

    /* Testing Call Volunteer method when guest's @username is defined. */
    @Test
    public void handleCallVolunteerUsernameTest() throws URISyntaxException, IOException {
        Long volunteerId = 1234567809L;
        String userId = "@vasyapupkin";
        Volunteer volunteer = new Volunteer(1, "Volunteer 1", volunteerId, "https://t.me/volunteer1");

        when(volunteerService.getRandomVolunteer()).thenReturn(volunteer);

        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("text_update_with_username.json").toURI()));
        Update update = getUpdateMessage(json, BUTTON_CALL_VOLUNTEER_TEXT);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(volunteerId);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo(String.format(CONTACT_TELEGRAM_USERNAME_TEXT, userId));
    }

    /* Testing Call Volunteer method when no volunteers in the table (no volunteers defined). */
    @Test
    public void handleCallVolunteerWhenNoVolunteersTest() throws URISyntaxException, IOException {
        Long userId = 1234567809L;

        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("text_update.json").toURI()));
        Update update = getUpdateMessage(json, BUTTON_CALL_VOLUNTEER_TEXT);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(userId);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo(NO_VOLUNTEERS_TEXT);
    }

    /* Testing Share Contact button when it already exists in our database. */
    @Test
    public void handleShareContactWhenItAlreadyExists() throws URISyntaxException, IOException {
        Long chatId = 1122334455L;
        Adopter adopter = new Adopter(1, "Vasya", "Pupkin", "+79101234567", 1122334455, "@vasya_pupkin", 30);

        when(adopterRepository.findByChatId(any(Long.class))).thenReturn(adopter);

        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("contact_update.json").toURI()));
        Update update = getUpdateMessage(json, "");
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(chatId);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo(ADOPTER_ALREADY_EXISTS_TEXT + ' ' + WE_WILL_CALL_YOU_TEXT);
    }

    /* Testing Share Contact button when it is new contact (do not exist in our database). */
    @Test
    public void handleShareContactWhenItDoNotExist() throws URISyntaxException, IOException {
        Long chatId = 1122334455L;
        Guest guest = new Guest(chatId, null, PetType.DOG);
        BranchParams branchParams = new BranchParams();
        branchParams.setProbPeriod(30);

        when(guestRepository.findByChatId(any(Long.class))).thenReturn(guest);
        when(branchParamsRepository.findByPetType(any(PetType.class))).thenReturn(Optional.of(branchParams));

        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("contact_update.json").toURI()));
        Update update = getUpdateMessage(json, "");
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(chatId);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo(SAVE_ADOPTER_SUCCESS_TEXT + ' ' + WE_WILL_CALL_YOU_TEXT);
    }

    /* Testing Share Contact Details button. */
    @Test
    public void handleShareContactDetails() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("data_update.json").toURI()));
        Update update = getUpdateMessage(json, BUTTON_SHARE_CONTACT_CALLBACK_TEXT);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, Mockito.times(2)).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1122334455L);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo(SHARE_CONTACT_MSG_TEXT);
    }
    @Test
    public void handleButtonDocsClickTest() throws URISyntaxException, IOException {

        AdoptionDoc adoptionDoc = new AdoptionDoc(LIST_OF_DOCS, "Паспорт, еще паспорт и еще паспорт");
        when(adoptionDocRepository.findById(any(DocType.class))).thenReturn(Optional.of(adoptionDoc));

        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("data_update.json").toURI()));
        Update update = getUpdateMessage(json, BUTTON_DOCS_FOR_ADOPTION_CALLBACK_TEXT);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, Mockito.times(2)).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567809L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo(adoptionDoc.getDescription());
    }

    @Test
    public void handleButtonInfoMeetingDogClickTest() throws URISyntaxException, IOException {
        telegramBotUpdatesListener.setShelterType(PetType.DOG);
        AdoptionDoc adoptionDoc = new AdoptionDoc(INFO_MEETING_DOG, "Бла бла бла, встреться, обнимите и все такое");
        when(adoptionDocRepository.findById(any(DocType.class))).thenReturn(Optional.of(adoptionDoc));
        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("data_update.json").toURI()));
        Update update = getUpdateMessage(json, BUTTON_RULES_MEETING_ANIMAL_CALLBACK_TEXT);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, Mockito.times(2)).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567809L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo(adoptionDoc.getDescription());
    }

    @Test
    public void handleButtonInfoMeetingCatClickTest() throws URISyntaxException, IOException {
        telegramBotUpdatesListener.setShelterType(PetType.CAT);
        AdoptionDoc adoptionDoc = new AdoptionDoc(INFO_MEETING_CAT, "Бла бла, встретьте кошку");
        when(adoptionDocRepository.findById(any(DocType.class))).thenReturn(Optional.of(adoptionDoc));
        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("data_update.json").toURI()));
        Update update = getUpdateMessage(json, BUTTON_RULES_MEETING_ANIMAL_CALLBACK_TEXT);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, Mockito.times(2)).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567809L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo(adoptionDoc.getDescription());
    }

    @Test
    public void handleButtonRecForTransportClickTest() throws URISyntaxException, IOException {

        AdoptionDoc adoptionDoc = new AdoptionDoc(TRANSPORT_ANIMAL, "Перевозить в теплой коробке из под пиццы");
        when(adoptionDocRepository.findById(any(DocType.class))).thenReturn(Optional.of(adoptionDoc));

        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("data_update.json").toURI()));
        Update update = getUpdateMessage(json, BUTTON_RECOMMENDATIONS_FOR_TRANSPORT_CALLBACK_TEXT);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, Mockito.times(2)).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567809L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo(adoptionDoc.getDescription());
    }

    @Test
    public void handleButtonRecHouseForLittleClickTest() throws URISyntaxException, IOException {

        AdoptionDoc adoptionDoc = new AdoptionDoc(REC_FOR_LITTLE, "Обустроить дом, конец");
        when(adoptionDocRepository.findById(any(DocType.class))).thenReturn(Optional.of(adoptionDoc));

        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("data_update.json").toURI()));
        Update update = getUpdateMessage(json, BUTTON_ARRANGEMENT_FOR_PUPPY_CALLBACK_TEXT);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, Mockito.times(2)).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567809L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo(adoptionDoc.getDescription());
    }

    @Test
    public void handleButtonRecHouseForAdultClickTest() throws URISyntaxException, IOException {

        AdoptionDoc adoptionDoc = new AdoptionDoc(REC_FOR_ADULT, "Обустроить дом получше");
        when(adoptionDocRepository.findById(any(DocType.class))).thenReturn(Optional.of(adoptionDoc));

        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("data_update.json").toURI()));
        Update update = getUpdateMessage(json, BUTTON_ARRANGEMENT_FOR_ADULT_CALLBACK_TEXT);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, Mockito.times(2)).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567809L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo(adoptionDoc.getDescription());
    }

    @Test
    public void handleButtonRecHouseForInvalidClickTest() throws URISyntaxException, IOException {

        AdoptionDoc adoptionDoc = new AdoptionDoc(REC_FOR_DISABLE, "Помочь");
        when(adoptionDocRepository.findById(any(DocType.class))).thenReturn(Optional.of(adoptionDoc));

        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("data_update.json").toURI()));
        Update update = getUpdateMessage(json, BUTTON_ADVICES_FOR_DISABLED_PET_CALLBACK_TEXT);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, Mockito.times(2)).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567809L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo(adoptionDoc.getDescription());
    }

    @Test
    public void handleButtonRecFromKinologsClickTest() throws URISyntaxException, IOException {

        AdoptionDoc adoptionDoc = new AdoptionDoc(KINOLOG_ADVICES, "Тут советы кинолога");
        when(adoptionDocRepository.findById(any(DocType.class))).thenReturn(Optional.of(adoptionDoc));

        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("data_update.json").toURI()));
        Update update = getUpdateMessage(json, BUTTON_ADVICES_FROM_KINOLOG_CALLBACK_TEXT);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, Mockito.times(2)).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567809L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo(adoptionDoc.getDescription());
    }

    @Test
    public void handleButtonRecKinologsClickTest() throws URISyntaxException, IOException {

        AdoptionDoc adoptionDoc = new AdoptionDoc(REC_KINOLOGS, "Кабачок и баклажан");
        when(adoptionDocRepository.findById(any(DocType.class))).thenReturn(Optional.of(adoptionDoc));

        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("data_update.json").toURI()));
        Update update = getUpdateMessage(json, BUTTON_RECOMMENDED_KINOLOGS_CALLBACK_TEXT);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, Mockito.times(2)).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567809L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo(adoptionDoc.getDescription());
    }

    @Test
    public void handleButtonReasonRefusalClickTest() throws URISyntaxException, IOException {
        AdoptionDoc adoptionDoc = new AdoptionDoc(REASONS_REFUSAL, "Бывает");
        when(adoptionDocRepository.findById(any(DocType.class))).thenReturn(Optional.of(adoptionDoc));

        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("data_update.json").toURI()));
        Update update = getUpdateMessage(json, BUTTON_REASONS_FOR_REFUSAL_CALLBACK_TEXT);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, Mockito.times(2)).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567809L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo(adoptionDoc.getDescription());
    }

    @Test
    public void processGettingInformationAboutShelterTest() throws URISyntaxException, IOException {
        telegramBotUpdatesListener.setShelterType(PetType.DOG);
        StringBuilder messageText = new StringBuilder();
        BranchParams branchParams = new BranchParams();
        branchParams.setCity("Москва");
        branchParams.setAddress("ул. Собака Черная, 5");
        branchParams.setWorkHours("9:00-16:00");
        messageText.append("Город: ").append(branchParams.getCity()).append("\n");
        messageText.append("Адрес: ").append(branchParams.getAddress()).append("\n");
        messageText.append("Часы работы: ").append(branchParams.getWorkHours()).append("\n");
        when(branchParamsRepository.findByPetType(PetType.DOG)).thenReturn(Optional.of(branchParams));

        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("data_update.json").toURI()));
        Update update = getUpdateMessage(json, BUTTON_INFO_SHELTER_CALLBACK_TEXT);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, Mockito.times(2)).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567809L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo(messageText.toString());
    }

    @Test
    public void processGettingInformationAboutSecurityTest() throws URISyntaxException, IOException {
        telegramBotUpdatesListener.setShelterType(PetType.DOG);
        BranchParams branchParams = new BranchParams();
        branchParams.setSecurityContact("test security contact");
        when(branchParamsRepository.findByPetType(PetType.DOG)).thenReturn(Optional.of(branchParams));

        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("data_update.json").toURI()));
        Update update = getUpdateMessage(json, BUTTON_INFO_SECURITY_CALLBACK_TEXT);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, Mockito.times(2)).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567809L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo(branchParams.getSecurityContact());
    }

    @Test
    public void processGettingInformationAboutSafetyPrecautionsTest() throws URISyntaxException, IOException {
        telegramBotUpdatesListener.setShelterType(PetType.DOG);
        BranchParams branchParams = new BranchParams();
        branchParams.setSecurityInfo("test security info");
        when(branchParamsRepository.findByPetType(PetType.DOG)).thenReturn(Optional.of(branchParams));

        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("data_update.json").toURI()));
        Update update = getUpdateMessage(json, BUTTON_INFO_SAFETY_PRECAUTIONS_CALLBACK_TEXT);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, Mockito.times(2)).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567809L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo(branchParams.getSecurityInfo());
    }

    @Test
    public void handleButtonAdoptionReportInstructionClick() throws URISyntaxException, IOException {

        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("data_update.json").toURI()));
        Update update = getUpdateMessage(json, BUTTON_REPORT_TEMPLATE_CALLBACK_TEXT);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, Mockito.times(1)).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567809L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo(ADOPTION_REPORT_INSTRUCTION);
    }

    @Test
    public void handleButtonSendAdoptionReportClick() throws URISyntaxException, IOException {

        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("data_update.json").toURI()));
        Update update = getUpdateMessage(json, BUTTON_SEND_REPORT_CALLBACK_TEXT);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, Mockito.times(1)).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567809L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo(PHOTO_WAITING_MESSAGE);
    }

    @Test
    public void handleSaveAdoptionReportDiet() throws URISyntaxException, IOException {
        Long chatId = 1122334455L;
        LocalDate today = LocalDate.now();

        Adopter adopter = new Adopter(1,
                "Vasya",
                "Pupkin",
                "+79101234567",
                1122334455,
                "@vasya_pupkin",
                30);

        AdoptionReport adoptionReport = new AdoptionReport(adopter,
                today,
                null,
                null,
                null,
                null);

        when(adoptionReportRepository.findAdoptionReportByAdopterIdAndReportDate(any(Adopter.class), any(LocalDate.class))).thenReturn(adoptionReport);
        when(adopterRepository.findByChatId(any(Long.class))).thenReturn(adopter);
        when(adopterService.getUpdateStatus(any(Long.class))).thenReturn(UpdateStatus.WAITING_FOR_PET_DIET);

        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("text_update.json").toURI()));
        Update update = getUpdateMessage(json, "qwerty");
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567809L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo(DIET_SAVED_MESSAGE);
    }

    @Test
    public void handleSaveAdoptionReportWellBeing() throws URISyntaxException, IOException {
        Long chatId = 1122334455L;
        LocalDate today = LocalDate.now();

        Adopter adopter = new Adopter(1,
                "Vasya",
                "Pupkin",
                "+79101234567",
                1122334455,
                "@vasya_pupkin",
                30);

        AdoptionReport adoptionReport = new AdoptionReport(adopter,
                today,
                null,
                null,
                null,
                null);

        when(adoptionReportRepository.findAdoptionReportByAdopterIdAndReportDate(any(Adopter.class), any(LocalDate.class))).thenReturn(adoptionReport);
        when(adopterRepository.findByChatId(any(Long.class))).thenReturn(adopter);
        when(adopterService.getUpdateStatus(any(Long.class))).thenReturn(UpdateStatus.WAITING_FOR_WELL_BEING);

        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("text_update.json").toURI()));
        Update update = getUpdateMessage(json, "qwerty");
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567809L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo(WELL_BEING_SAVED_MESSAGE);
    }

    @Test
    public void handleSaveAdoptionReportBehaviorChange() throws URISyntaxException, IOException {
        Long chatId = 1122334455L;
        LocalDate today = LocalDate.now();

        Adopter adopter = new Adopter(1,
                "Vasya",
                "Pupkin",
                "+79101234567",
                1122334455,
                "@vasya_pupkin",
                30);

        AdoptionReport adoptionReport = new AdoptionReport(adopter,
                today,
                null,
                null,
                null,
                null);

        when(adoptionReportRepository.findAdoptionReportByAdopterIdAndReportDate(any(Adopter.class), any(LocalDate.class))).thenReturn(adoptionReport);
        when(adopterRepository.findByChatId(any(Long.class))).thenReturn(adopter);
        when(adopterService.getUpdateStatus(any(Long.class))).thenReturn(UpdateStatus.WAITING_FOR_BEHAVIOR_CHANGE);

        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("text_update.json").toURI()));
        Update update = getUpdateMessage(json, "qwerty");
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567809L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo(BEHAVIOR_CHANGE_SAVED_MESSAGE);
    }
    /* Testing BUTTON_STAGE1_TEXT click with the guest who has selected CAT shelter. */
    @Test
    public void handleCatShelterStage1SelectTest() throws URISyntaxException, IOException {
        telegramBotUpdatesListener.setShelterType(PetType.CAT);

        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("data_update.json").toURI()));
        Update update = getUpdateMessage(json, BUTTON_STAGE1_CALLBACK_TEXT);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, Mockito.times(2)).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567809L);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo(CAT_SHELTER_STAGE1_WELCOME_MSG_TEXT);
    }

    /* Testing BUTTON_STAGE1_TEXT click with the guest who has selected DOG shelter. */
    @Test
    public void handleDogShelterStage1SelectTest() throws URISyntaxException, IOException {
        telegramBotUpdatesListener.setShelterType(PetType.DOG);

        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("data_update.json").toURI()));
        Update update = getUpdateMessage(json, BUTTON_STAGE1_CALLBACK_TEXT);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, Mockito.times(2)).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567809L);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo(DOG_SHELTER_STAGE1_WELCOME_MSG_TEXT);
    }

    /* Testing BUTTON_STAGE2_TEXT click with the guest who has selected CAT shelter. */
    @Test
    public void handleCatShelterStage2SelectTest() throws URISyntaxException, IOException {
        telegramBotUpdatesListener.setShelterType(PetType.CAT);

        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("data_update.json").toURI()));
        Update update = getUpdateMessage(json, BUTTON_STAGE2_CALLBACK_TEXT);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, Mockito.times(2)).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567809L);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo(CAT_SHELTER_STAGE2_WELCOME_MSG_TEXT);
    }

    /* Testing BUTTON_STAGE2_TEXT click with the guest who has selected DOG shelter. */
    @Test
    public void handleDogShelterStage2SelectTest() throws URISyntaxException, IOException {
        telegramBotUpdatesListener.setShelterType(PetType.DOG);

        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("data_update.json").toURI()));
        Update update = getUpdateMessage(json, BUTTON_STAGE2_CALLBACK_TEXT);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, Mockito.times(2)).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567809L);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo(DOG_SHELTER_STAGE2_WELCOME_MSG_TEXT);
    }

    /* Testing BUTTON_STAGE3_TEXT click with the guest who has selected CAT shelter. */
    @Test
    public void handleCatShelterStage3SelectTest() throws URISyntaxException, IOException {
        telegramBotUpdatesListener.setShelterType(PetType.CAT);

        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("data_update.json").toURI()));
        Update update = getUpdateMessage(json, BUTTON_STAGE3_CALLBACK_TEXT);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, Mockito.times(2)).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567809L);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo(CAT_SHELTER_STAGE3_WELCOME_MSG_TEXT);
    }

    /* Testing BUTTON_STAGE3_TEXT click with the guest who has selected DOG shelter. */
    @Test
    public void handleDogShelterStage3SelectTest() throws URISyntaxException, IOException {
        telegramBotUpdatesListener.setShelterType(PetType.DOG);

        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("data_update.json").toURI()));
        Update update = getUpdateMessage(json, BUTTON_STAGE3_CALLBACK_TEXT);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, Mockito.times(2)).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567809L);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo(DOG_SHELTER_STAGE3_WELCOME_MSG_TEXT);
    }

    private Update getUpdateMessage(String json, String replaced) {
        return BotUtils.fromJson(json.replace("%message_text%", replaced), Update.class);
    }
}