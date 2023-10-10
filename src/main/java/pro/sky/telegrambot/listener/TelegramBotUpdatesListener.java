package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.DeleteMessage;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.keyboards.KeyboardMaker;
import pro.sky.telegrambot.services.MessageService;

import javax.annotation.PostConstruct;
import java.util.List;

import static pro.sky.telegrambot.constants.Constants.*;
import static pro.sky.telegrambot.keyboards.KeyboardMaker.*;

@Slf4j
@Service
public class TelegramBotUpdatesListener implements UpdatesListener {
    @Autowired
    private TelegramBot telegramBot;

    /**
     * <br>Флажок {@link TelegramBotUpdatesListener#isCatChosen} используется для ветвления меню, в зависимости от того,
     * какой тип приюта({@value   pro.sky.telegrambot.constants.Constants#BUTTON_CAT_SHELTER} или
     * #{@value  pro.sky.telegrambot.constants.Constants#BUTTON_DOG_SHELTER}) выбран.</br>
     */
    private static boolean isCatChosen;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    /**
     * Метод используется дла получения updates. Создания и управления меню бота.
     * <br>Меню создаются с использованием клавиатур класса {@link KeyboardMaker}</br>
     * <br>Константы принадлежат классу {@link pro.sky.telegrambot.constants.Constants}</br>
     *
     * @param updates
     * @return int
     */
    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
//            log.info("Processing update: {}", update);
            log.info("-----------------------------------------------------------------");

            if (update.callbackQuery() != null && update.message() == null) {
                log.info("блок клавиатурных сообщений");
                switch (update.callbackQuery().data()) {
                    case BUTTON_CAT_SHELTER://меню 2
                        this.buttonReact(update, keyboardUserRequest(), MESSAGE_CHOOSE_MENU_OPT);
                        isCatChosen = true;
                        break;
                    case BUTTON_DOG_SHELTER://меню 2
                        this.buttonReact(update, keyboardUserRequest(), MESSAGE_CHOOSE_MENU_OPT);
                        isCatChosen = false;
                        break;
                    case BUTTON_SHELTER_INFO://меню 2.1
                        this.buttonReact(update, keyboardNewUserConsult(), MESSAGE_NEW_USER_CONSULT);
                        break;
                    case BUTTON_SHELTER_ABOUT://меню 2.1.1
                        this.buttonReact(update, MessageService.getMessage(BUTTON_SHELTER_ABOUT));
                        break;
                    case BUTTON_GET_SCHEDULE_ADDRESS://меню 2.1.2
                        this.buttonReact(update, MessageService.getMessage(BUTTON_GET_SCHEDULE_ADDRESS));
                        break;
                    case BUTTON_MAKING_PASS://меню 2.1.3
                        this.buttonReact(update, MessageService.getMessage(BUTTON_MAKING_PASS));
                        break;
                    case BUTTON_SAFETY_RULES://меню 2.1.4
                        this.buttonReact(update, MessageService.getMessage(BUTTON_SAFETY_RULES));
                        break;
                    case BUTTON_CONTACTS://меню 2.1.5
                        this.buttonReact(update, BTN_REACT_CONTACTS_MESSAGE, BTN_REACT_CONTACTS_FORM_EXAMPLE);
                        break;

                    case BUTTON_HOW_TO_GET_ANIMAL://меню 2.2
                        this.buttonReact(update, keyboardCandidateConsult(), MESSAGE_PET_OWNER_CONSULTING);
                        break;
                    case BUTTON_SEND_REPORT: //меню 2.3
                        buttonReact(update, keyboardPetLeading(), MESSAGE_PET_LEADING_MENU);
                        break;
                    case BUTTON_CALL_VOLUNTEER: //меню 2.4
                        this.buttonReact(update, BTN_REACT_CALL_VOLUNTEER);
                        break;
                }
            } else if (update.message().text() != null && update.callbackQuery() == null) {
                log.info("блок текстовых сообщений");
                switch (update.message().text()) {
                    case COMMAND_START://меню 1
                        log.info("блок команды старт");
                        this.startCommandReact(update);
                        break;
                }
                if (update.message() != null && update.message().text().matches("^[A-Za-z0-9()+-\\s:]+(\\n|\\r\\n)*(?!\\/start)")) {
                    log.info("блок регулярки");
                    MessageService.getUserContactsForm(update);
                }
            }
        });
        log.info("isChosenCat = {}", isCatChosen);
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void buttonReact(Update update, Keyboard keyboard, String textMessage) {
        log.info("buttonReact Запущен");
        Long chatId = update.callbackQuery().message().chat().id();
        SendMessage message = new SendMessage(chatId, textMessage);
        telegramBot.execute(message.replyMarkup(keyboard));
//        deletePreviousMessage(update.callbackQuery().message(), chatId);
    }

    private void buttonReact(Update update, String textMessage) {
        log.info("buttonReact Запущен");
        Long chatId = update.callbackQuery().message().chat().id();
        SendMessage message = new SendMessage(chatId, textMessage).parseMode(ParseMode.HTML);
        telegramBot.execute(message);
//        deletePreviousMessage(update.callbackQuery().message(), chatId);
    }

    private void buttonReact(Update update, String textMessage1, String textMessage2) {
        log.info("buttonReact Запущен");
        Long chatId = update.callbackQuery().message().chat().id();
        SendMessage message1 = new SendMessage(chatId, textMessage1).parseMode(ParseMode.HTML);
        SendMessage message2 = new SendMessage(chatId, textMessage2).parseMode(ParseMode.HTML);
        telegramBot.execute(message1);
        telegramBot.execute(message2);
//        telegramBot.execute(MessageService.sendPhoto(update));
//        deletePreviousMessage(update.callbackQuery().message(), chatId);
    }

    private void startCommandReact(Update update) {
        log.info("startCommandReact Запущен");

        Long chatId = update.message().chat().id();
        SendMessage message = new SendMessage(chatId, MESSAGE_HELLO);
        telegramBot.execute(message.replyMarkup(KeyboardMaker.keyboardCatDog()));
    }

    private void deletePreviousMessage(Message message, Long chatId) {
        Integer messageId = message.messageId();
        DeleteMessage deleteMessage = new DeleteMessage(chatId, messageId);
        log.info("сообщение удалено ", telegramBot.execute(deleteMessage));
    }


    public static boolean isCatChosen() {
        return isCatChosen;
    }
}