package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.DeleteMessage;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.Delegate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.keyboards.KeyboardMaker;
import pro.sky.telegrambot.services.MessageService;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static pro.sky.telegrambot.constants.Constants.*;
import static pro.sky.telegrambot.keyboards.KeyboardMaker.*;

@Slf4j
@Service
public class TelegramBotUpdatesListener implements UpdatesListener {
    @Autowired
    private TelegramBot telegramBot;

    /**
     * Поле {@link TelegramBotUpdatesListener#messagesHistory} хранит историю
     */
    private Map<Integer, Long> messagesHistory = new HashMap<>();

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
                        this.deleteAllPreviousMessages();
                        this.startCommandReact(update);
                        break;
                }
//                if (update.message() != null && update.message().text().matches("^[A-Za-z0-9()+-\\s:]+(\\n|\\r\\n)*(?!\\/start)")) {
//                    log.info("блок регулярки");
//                    MessageService.getUserContactsForm(update);
//                }
            }
        });
        log.info("isChosenCat = {}", isCatChosen);
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    /**
     * Метод создает реакцию на нажатие кнопки на клавиатуре и выводит еще одну клавиатуру
     * <br>Описание работы метода: </br>
     * <li>метод находит и присваивает в переменные id чата и id сообщения;</li>
     * <li>метод создает сообщение использую id чата и текст, который подается в метод как параметр;</li>
     * <li>метод отправляет сообщение при помощи метода {@link TelegramBot#execute(BaseRequest)}</li>
     * <li>той же строкой метод присваивает в переменную значение id отправляемого
     * сообщения <i><b>sentMessageId</b></i></li>
     * <li>на последнем этапе в историю сообщений записываются id сообщений, чтобы потом использовать
     * их для очистки истории</li>
     *
     * @param update
     * @param keyboard    задание нужной клавиатуры
     * @param textMessage сообщение над клавиатурой
     */
    private void buttonReact(Update update, Keyboard keyboard, String textMessage) {
        log.info("buttonReact Запущен");
        //получение id сообщения и чата
        Integer messageId = update.callbackQuery().message().messageId();
        Long chatId = update.callbackQuery().message().chat().id();
        //создание сообщения
        SendMessage message = new SendMessage(chatId, textMessage);
        //бот отправляет сообщение в чат и одновременно записываем в переменную id этого сообщения
        Integer sentMessageId = telegramBot.execute(message.replyMarkup(keyboard)).message().messageId();
        //записываем полученное и отправленное сообщения в историю сообщений
        messagesHistory.put(messageId, chatId);
        messagesHistory.put(sentMessageId, chatId);
//        deletePreviousMessage(update.callbackQuery().message(), chatId);
    }

    /**
     * Метод создает реакцию на нажатие кнопки на клавиатуре и выводит текстовое сообщение.
     * <br>Описание работы метода: </br>
     * <li>метод находит и присваивает в переменные id чата и id сообщения;</li>
     * <li>метод создает сообщение использую id чата и текст, который подается в метод как параметр;</li>
     * <li>метод отправляет сообщение при помощи метода {@link TelegramBot#execute(BaseRequest)}</li>
     * <li>той же строкой метод присваивает в переменную значение id отправляемого
     * сообщения <i><b>sentMessageId</b></i></li>
     * <li>на последнем этапе в историю сообщений записываются id сообщений, чтобы потом использовать
     * их для очистки истории</li>
     *
     * @param update
     * @param textMessage Заголовок сообщения
     */
    private void buttonReact(Update update, String textMessage) {
        log.info("buttonReact Запущен");
        //получение id сообщения и чата
        Long chatId = update.callbackQuery().message().chat().id();
        Integer messageId = update.callbackQuery().message().messageId();
        //создание сообщения
        SendMessage message = new SendMessage(chatId, textMessage).parseMode(ParseMode.HTML);
        //бот отправляет сообщение в чат и одновременно записываем в переменную id этого сообщения
        Integer sentMessageId = telegramBot.execute(message).message().messageId();
        //записываем полученное и отправленное сообщения в историю сообщений
        messagesHistory.put(messageId, chatId);
        messagesHistory.put(sentMessageId, chatId);
//        deletePreviousMessage(update.callbackQuery().message(), chatId);
    }

    /**
     * Метод создает реакцию на нажатие кнопки на клавиатуре и выводит два текстовых сообщения.
     * Одно сообщение с инструкцией о том как заполнять отчет, а второе с формой отчета.
     * <br>Устройство метода см. {@link TelegramBotUpdatesListener#buttonReact(Update, String)}</br>
     *
     * @param update
     * @param textMessage1 текст первого сообщения
     * @param textMessage2 текст второго сообщения
     */
    private void buttonReact(Update update, String textMessage1, String textMessage2) {
        log.info("buttonReact Запущен");
        this.buttonReact(update, textMessage1);
        this.buttonReact(update, textMessage2);
    }

    /**
     * Метод реагирует на команду <i><b>/start</b></i> выводом сообщения с клавиатурой для выбора приюта кошек или собак.
     * <br>Описание работы метода: </br>
     * <li>метод находит и присваивает в переменные id чата и id сообщения;</li>
     * <li>метод создает сообщение использую id чата и текст, который подается в метод как параметр;</li>
     * <li>метод отправляет сообщение при помощи метода {@link TelegramBot#execute(BaseRequest)}</li>
     * <li>той же строкой метод присваивает в переменную значение id отправляемого
     * сообщения <i><b>sentMessageId</b></i></li>
     * <li>на последнем этапе в историю сообщений записываются id сообщений, чтобы потом использовать
     * их для очистки истории</li>
     *
     * @param update
     */
    private void startCommandReact(Update update) {
        log.info("startCommandReact Запущен");
        //получение id сообщения и чата
        Long chatId = update.message().chat().id();
        Integer messageId = update.message().messageId();
        //создание сообщения
        SendMessage message = new SendMessage(chatId, MESSAGE_HELLO);
        //бот отправляет сообщение в чат и одновременно записываем в переменную id этого сообщения
        Integer sentMessageId = telegramBot.execute(message.replyMarkup(KeyboardMaker.keyboardCatDog())).message().messageId();
        //записываем полученное и отправленное сообщения в историю сообщений
        messagesHistory.put(messageId, chatId);
        messagesHistory.put(sentMessageId, chatId);
    }

    /**
     * Метод для удаления каждого отдельного сообщения, после нажатия следующей кнопки.
     * @param message
     * @param chatId
     */
    @Deprecated
    private void deletePreviousMessage(Message message, Long chatId) {
        Integer messageId = message.messageId();
        DeleteMessage deleteMessage = new DeleteMessage(chatId, messageId);
        log.info("сообщение удалено ", telegramBot.execute(deleteMessage));

    }

    /**
     * Метод удаляет всю историю сообщений из чата после ввода команды <i><b>/start</b></i>.
     * <br>В начале нового диалога, очистка чата делает чтение чата более удобным.</br>
     * <br>Как это работает?</br> Метод обращается к мапе {@link TelegramBotUpdatesListener#messagesHistory},
     * где записаны id чатов и сообщений всех предыдущих входящих и исходящий сообщений.
     * <br>Далее метод циклом пробегает по мапе с историей сообщений и применяет к каждому класс
     * {@link DeleteMessage}</br>. Таким образом история чата очищается.
     * <br>После окончания работы цикла, очищается и сама мапа {@link TelegramBotUpdatesListener#messagesHistory}.</br>
     */
    private void deleteAllPreviousMessages() {
        log.info("deleteAllPreviousMessages запущен");
        log.info("messagesHistory = {}", messagesHistory.toString());

        for (Map.Entry<Integer, Long> message : messagesHistory.entrySet()) {
            Integer messageId = message.getKey();
            Long chatId = message.getValue();
            DeleteMessage deleteMessage = new DeleteMessage(chatId, messageId);
            log.info("сообщение удалено ", telegramBot.execute(deleteMessage));
        }
        messagesHistory.clear();
    }

    /**
     * Геттер поля {@link TelegramBotUpdatesListener#isCatChosen}
     * @return
     */
    public static boolean isCatChosen() {
        return isCatChosen;
    }
}