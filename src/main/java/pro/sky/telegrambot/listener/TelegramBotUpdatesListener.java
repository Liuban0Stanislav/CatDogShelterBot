package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.DeleteMessage;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.keyboards.KeyboardMaker;
import pro.sky.telegrambot.model.Client;
import pro.sky.telegrambot.services.MessageService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static pro.sky.telegrambot.constants.Constants.*;
import static pro.sky.telegrambot.keyboards.KeyboardMaker.*;

@Slf4j
@Service
public class TelegramBotUpdatesListener implements UpdatesListener {
    @Autowired
    private TelegramBot telegramBot;

    private final MessageService messageService;

    /**
     * Это поле используется в методе {@link TelegramBotUpdatesListener#saveContacts(Update, String, String)}
     * для сохранения данных пользователя, которые сам пользователь хочет оставить, переходя по кнопке
     * {@link pro.sky.telegrambot.constants.Constants#BTN_REACT_CONTACTS_MESSAGE}:
     * <br>{@value pro.sky.telegrambot.constants.Constants#BTN_REACT_CONTACTS_MESSAGE}</br>
     */
    private Client client = new Client();

    /**
     * Поле {@link TelegramBotUpdatesListener#messageHistory} хранит историю
     */
    private List<Message> messageHistory = new ArrayList<>();

    /**
     * Флажок {@link TelegramBotUpdatesListener#isCatChosen} используется для ветвления меню, в зависимости от того,
     * какой тип приюта({@value   pro.sky.telegrambot.constants.Constants#BUTTON_CAT_SHELTER} или
     * #{@value  pro.sky.telegrambot.constants.Constants#BUTTON_DOG_SHELTER}) выбран.
     */
    private static boolean isCatChosen;

    public TelegramBotUpdatesListener(MessageService messageService) {
        this.messageService = messageService;
    }

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
                        this.buttonReact(update, messageService.getMessage(BUTTON_SHELTER_ABOUT));
                        break;
                    case BUTTON_GET_SCHEDULE_ADDRESS://меню 2.1.2
                        this.buttonReact(update, messageService.getMessage(BUTTON_GET_SCHEDULE_ADDRESS));
                        break;
                    case BUTTON_MAKING_PASS://меню 2.1.3
                        this.buttonReact(update, messageService.getMessage(BUTTON_MAKING_PASS));
                        break;
                    case BUTTON_SAFETY_RULES://меню 2.1.4
                        this.buttonReact(update, messageService.getMessage(BUTTON_SAFETY_RULES));
                        break;
                    case BUTTON_CONTACTS://меню 2.1.5
                        this.buttonContactsReact(update);
                        getHistory();
                        break;

                    case BUTTON_HOW_TO_GET_ANIMAL://меню 2.2
                        this.buttonReact(update, keyboardCandidateConsult(), MESSAGE_PET_OWNER_CONSULTING);
                        break;
                    case BUTTON_SEND_REPORT: //меню 2.3
                        this.buttonReact(update, keyboardPetLeading(), MESSAGE_PET_LEADING_MENU);
                        break;
                    case BUTTON_CALL_VOLUNTEER: //меню 2.4
                        this.buttonReact(update, BTN_REACT_CALL_VOLUNTEER);
                        break;
                }
            } else if (update.message().text() != null && update.callbackQuery() == null) {
                log.info("блок текстовых сообщений");
                if (update.message().text().equals(COMMAND_START)) {//меню 1
                    log.info("блок команды старт");
                    this.deleteAllPreviousMessages();
                    this.startCommandReact(update);
                }
                //Если пользователь хочет оставить контакты - получение имени
                if (isHistoryContainsText(BTN_REACT_CONTACTS_FIRST_NAME)) {
                    log.info("блок ввода имени");
                    String firstName = this.saveContacts(update, BTN_REACT_CONTACTS_FIRST_NAME, BTN_REACT_CONTACTS_LAST_NAME);
                    client.setFirstName(firstName);
                    log.info("сохранение имени в объект client");
                    log.info("{}", client);
                    getHistory();
                }
                //Если пользователь хочет оставить контакты - получение фамилии
                if(isHistoryContainsText(BTN_REACT_CONTACTS_LAST_NAME) && update.message().text().isEmpty()){
                    log.info("блок ввода фамилии");
                    String lastName = this.saveContacts(update, BTN_REACT_CONTACTS_LAST_NAME, BTN_REACT_CONTACTS_PHONE);
                    client.setLastName(lastName);
                    log.info("{}", client);
                    getHistory();
                }
                //Если пользователь хочет оставить контакты - получение телефона
                if(isHistoryContainsText(BTN_REACT_CONTACTS_PHONE) && !update.message().text().isEmpty()){
                    log.info("блок ввода номера телефона");
                    String phoneNum = this.saveContacts(update, BTN_REACT_CONTACTS_PHONE, BTN_REACT_THANKING);
                    client.setPhone(phoneNum);
                    client.setChatId(update.message().chat().id());
                    client.setTelegramId(update.message().from().id());
                    log.info("{}", client);
                }
            }
        });
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
        Message sentMessageId = telegramBot.execute(message.replyMarkup(keyboard)).message();
        //записываем полученное и отправленное сообщения в историю сообщений
        messageHistory.add(sentMessageId);
    }

    /**
     * Метод создает реакцию на нажатие кнопки на клавиатуре и выводит текстовое сообщение.
     * <br>Описание работы метода: </br>
     * <li>метод находит и присваивает в переменные id чата и id сообщения;</li>
     * <li>метод создает сообщение используя id чата и текст, который подается в метод как параметр <b>textMessage</b>;</li>
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
        Message sentMessageId = telegramBot.execute(message).message();
        //записываем полученное и отправленное сообщения в историю сообщений
        messageHistory.add(sentMessageId);
    }

    /**
     * Метод создает реакцию на нажатие кнопки на клавиатуре и выводит два текстовых сообщения.
     * Одно сообщение с инструкцией о том как заполнять отчет, а второе с формой отчета.
     * <br>Устройство метода см. {@link TelegramBotUpdatesListener#buttonReact(Update, String)}</br>
     *
     * @param update
     */
    public void buttonContactsReact(Update update) {
        log.info("--------------------------------------------");
        log.info("buttonContactsReact Запущен");
        this.buttonReact(update, BTN_REACT_CONTACTS_MESSAGE);
        this.buttonReact(update, BTN_REACT_CONTACTS_FIRST_NAME);
        log.info("--------------------------------------------");
    }
//-----------------------------------------------------------------------------------------
    /**
     * Метод принимает имя и фамилию от пользователя. После чего заполняет полученными данными объект {@link Client},
     * Заполненный объект сохраняется в БД, в соответствующей таблице.
     * <br>Алгоритм работы метода следующий:</br>
     * <li>Получаем id и текст предыдущего сообщения.</li>
     * <li>Если id сообщения с текстом {@value  pro.sky.telegrambot.constants.Constants#BTN_REACT_CONTACTS_FIRST_NAME}
     * для имени, а так же с текстом {@value pro.sky.telegrambot.constants.Constants#BTN_REACT_CONTACTS_LAST_NAME}
     * для фамилии отличается от id нынешнего сообщения на 1,
     * значит введенное сообщение несет в себе имя или фамилию пользователя. Это условие содержится в методе
     * {@link TelegramBotUpdatesListener#isMessageEqualsPrevious(Update, String)}.</li>
     * <li>Если условие (из пункта выше) верно
     * ({@link TelegramBotUpdatesListener#isMessageEqualsPrevious(Update, String)} == true),
     * то метод {@link TelegramBotUpdatesListener#nameProcessor(String)}
     * приводит имя или фамилию к надлежащему виду. Удаляет лишние символы, пробелы, делает первую букву заглавной.</li>
     * <li>Далее сеттер присваивает объекту {@link TelegramBotUpdatesListener#client} имя и фамилию.</li>
     * <li>Метод {@link TelegramBotUpdatesListener#deletePreviousMessages(Update, int)} удаляет из чата необходимое
     * количество предыдущих сообщений(количество задается в параметре метода).</li>
     * <li>И наконец, удаленные из чата сообщения так же удаляются из истории сообщений - списка
     * {@link TelegramBotUpdatesListener#messageHistory}.</li>
     *
     * @param update
     * @param preText текст предыдущего сообщения, для сравнения.
     */
    public String saveContacts(Update update, String preText, String nextMessageText) {
        log.info("////////////////////////////////////");
        log.info("saveContacts запущен");
        boolean condition = isMessageEqualsPrevious(update, preText);
        log.info("{}", condition);
        String name = null;
        if (condition) {
            name = update.message().text();
            name = nameProcessor(name);
            log.info("получаем имя или фамилию - {}", name);
            //добавление фамилии, имени или телефона в историю сообщений
            messageHistory.add(update.message());
            sendMessage(update, nextMessageText);


        }
        log.info("////////////////////////////////////");
        return name;
    }

    /**
     * Метод проверяет, был ли в предыдущем сообщении текст подаваемый как параметр.
     * Если такой текст выводился в сообщении, то метод возвращает <b>true</b>.
     * @param update
     * @param preText тест с которым нужно сравнить текст из предыдущего сообщения.
     * @return
     */
    public boolean isMessageEqualsPrevious(Update update, String preText) {
        log.info("isMessageEqualsPrevious запущен");

        Integer currentMessageId = update.message().messageId();
        int previousMessageIndex = messageHistory.size() - 1;
        log.info("предыдущее сообщение = {}", messageHistory.get(previousMessageIndex).text());
        Integer previousMessageId = messageHistory.get(previousMessageIndex).messageId();
        String previousMessageText = messageHistory.get(previousMessageIndex).text();

        log.info("currentMessageId {}", currentMessageId);
        log.info("previousMessageId {}", previousMessageId);
        log.info("Разность = {}", currentMessageId - previousMessageId);

        if (currentMessageId - previousMessageId == 1 && preText.equals(previousMessageText)) {
            return true;
        }
        return false;
    }

    /**
     * Метод приводит имя или фамилию к надлежащему виду.
     * Убирает случайные знаки, убирает пробелы, делает первую букву имени заглавной.
     * <br>Пример: Василий, Петров, Катерина, Дроздова и т.д.</br>
     * @param name как параметр подается имя или фамилия
     * @return - то же слово, но приведенное к формату записи имен и фамилий
     */
    public String nameProcessor(String name){
        log.info("имя или фамилия БЫЛО: {}", name);

        name = name.replace(",", "")
                .replace(".", "")
                .replace(";", "")
                .replace("!", "")
                .trim()
                .toLowerCase();
        name = toUpperCase(name);

        log.info("имя или фамилия СТАЛО: {}", name);
        return name;
    }

    /**
     * Метод делает заглавной первую букву слова.
     * @param word строка класса {@link String}
     * @return строка класса {@link String} но с заглавной первой буквой.
     */
    public static String toUpperCase(String word){
        char[] chars = word.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        word = String.valueOf(chars);
        return word;
    }

    /**
     * Метод отправляет сообщение в чат с заданным текстом.
     * @param update
     * @param textMessage текст который надо отправить в чат.
     */
    private void sendMessage(Update update, String textMessage) {
        log.info("sendMessage Запущен");
        //получение id сообщения и чата
        Long chatId = update.message().chat().id();
        //создание сообщения
        SendMessage message = new SendMessage(chatId, textMessage);
        //бот отправляет сообщение в чат и одновременно записываем в переменную id этого сообщения
        Message sentMessageId = telegramBot.execute(message).message();
        //записываем полученное и отправленное сообщения в историю сообщений
        messageHistory.add(sentMessageId);
    }

    /**
     * Метод удаляет заданное количество предыдущих сообщений
     * @param update
     * @param quantityToDelete количество сообщений которые нужно удалить
     */
    public void deletePreviousMessages(Update update, int quantityToDelete){
        Long chatId = update.message().chat().id();
        for (int i = 2; i <= quantityToDelete; i++) {
            int previousMessageIndex = messageHistory.size() - i;
            Message previousMessage = messageHistory.get(previousMessageIndex);

            deletePreviousMessage(previousMessage, chatId);
            messageHistory.remove(previousMessageIndex);
        }
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
        deleteAllPreviousMessages();
        log.info("+++++++++++++++++++++++++++++++++++++");
        log.info("startCommandReact Запущен");
        //получение id сообщения и чата
        Long chatId = update.message().chat().id();
        log.info("chatId = {}", chatId);
        Integer messageId = update.message().messageId();
        log.info("messageId = {}", messageId);
        //создание сообщения
        SendMessage message = new SendMessage(chatId, MESSAGE_HELLO);
        log.info("start-message = {}", message);
        //бот отправляет сообщение в чат и одновременно записываем в переменную id этого сообщения
        Message sentMessageId = telegramBot.execute(message.replyMarkup(KeyboardMaker.keyboardCatDog())).message();
        //записываем полученное и отправленное сообщения в историю сообщений
        messageHistory.add(update.message());
        messageHistory.add(sentMessageId);
        log.info("+++++++++++++++++++++++++++++++++++++");
    }

    /**
     * Метод для удаления каждого отдельного сообщения, после нажатия следующей кнопки.
     *
     * @param message сообщение
     * @param chatId id чата
     */

    private void deletePreviousMessage(Message message, Long chatId) {
        Integer messageId = message.messageId();
        DeleteMessage deleteMessage = new DeleteMessage(chatId, messageId);
        log.info("сообщение удалено ", telegramBot.execute(deleteMessage));

    }

    /**
     * Метод удаляет всю историю сообщений из чата после ввода команды <i><b>/start</b></i>.
     * <br>В начале нового диалога, очистка чата делает чтение чата более удобным.</br>
     * <br>Как это работает?</br> Метод обращается к мапе {@link TelegramBotUpdatesListener#messageHistory},
     * где записаны id чатов и сообщений всех предыдущих входящих и исходящий сообщений.
     * <br>Далее метод циклом пробегает по мапе с историей сообщений и применяет к каждому класс
     * {@link DeleteMessage}</br>. Таким образом история чата очищается.
     * <br>После окончания работы цикла, очищается и сама мапа {@link TelegramBotUpdatesListener#messageHistory}.</br>
     */
    private void deleteAllPreviousMessages() {
        log.info("deleteAllPreviousMessages запущен");

        for (Message message : messageHistory) {
            Integer messageId = message.messageId();
            Long chatId = message.chat().id();
            DeleteMessage deleteMessage = new DeleteMessage(chatId, messageId);
            log.info("сообщение удалено ", telegramBot.execute(deleteMessage));
        }
        messageHistory.clear();
    }

    /**
     * Метод возвращает <b>true</b>, если заданный текст присутствует в
     * {@link TelegramBotUpdatesListener#messageHistory}.
     * Метод вернет <b>false</b>, если текст не содержится в коллекции.
     * @param text текст с которым, метод сравнивает содержимое сообщений коллекции истории сообщений.
     * @return boolean
     */
    private boolean isHistoryContainsText(String text){
        for(Message message: messageHistory){
            if (message.text().equals(text)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Метод выводит в консоль содержание листа с историей сообщений. Добавляя,
     * этот метод в разные части кода, можно видеть какие сообщения и на каком
     * этапе попадают в лист для дальнейшего возможного использования.
     */
    public void getHistory(){
        messageHistory.stream()
                .map(message -> message.text())
                .forEach(text -> System.out.println(text));
    }

    /**
     * Геттер поля {@link TelegramBotUpdatesListener#isCatChosen}
     *
     * @return boolean - значение поля isCatChosen
     */
    public static boolean isCatChosen() {
        return isCatChosen;
    }
}