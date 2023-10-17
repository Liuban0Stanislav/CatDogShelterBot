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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.keyboards.KeyboardMaker;
import pro.sky.telegrambot.model.Client;
import pro.sky.telegrambot.services.MessageService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
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

    private final MessageService messageService;

    /**
     * Это поле используется для сохранения данных пользователя, которые сам пользователь
     * хочет оставить, переходя по кнопке
     * {@link pro.sky.telegrambot.constants.Constants#BTN_REACT_CONTACTS_MESSAGE}:
     * <br>{@value pro.sky.telegrambot.constants.Constants#BTN_REACT_CONTACTS_MESSAGE}</br>
     * Перед сохранением в БД.
     */
//    private Client client = new Client();
    private Map<Client, List<Message>> clients = new HashMap<>();

//    /**
//     * Поле {@link TelegramBotUpdatesListener#messageHistory} хранит историю
//     */
//    private List<Message> messageHistory = new ArrayList<>();

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
            log.info("Processing update: {}", update);
//            log.info("-----------------------------------------------------------------");

            if (update.callbackQuery() != null && update.message() == null) {
                log.info("блок клавиатурных сообщений");
                switch (update.callbackQuery().data()) {
                    case BUTTON_CAT_SHELTER://меню 2
                        this.buttonReact(update, keyboardUserRequest(), MESSAGE_CHOOSE_MENU_OPT);
                        isCatChosen = true;
                        //запоминает кошачий или собачий приют выбрал клиент
                        findClientByChatId(update.callbackQuery().message()).setClientСhoice(isCatChosen);
                        break;
                    case BUTTON_DOG_SHELTER://меню 2
                        this.buttonReact(update, keyboardUserRequest(), MESSAGE_CHOOSE_MENU_OPT);
                        isCatChosen = false;
                        //запоминает кошачий или собачий приют выбрал клиент
                        findClientByChatId(update.callbackQuery().message()).setClientСhoice(isCatChosen);
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
//                        log.info("{}", getHistoryList(update));
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
                    clients.put(new Client(), new ArrayList<>(List.of(update.message())));
                    this.deleteAllPreviousMessages();
                    this.startCommandReact(update);

                }
                //Если пользователь хочет оставить контакты - получение имени
                if (isMessageEqualsPrevious(update, BTN_REACT_CONTACTS_FIRST_NAME)) {
                    log.info("блок ввода имени");
                    String firstName = this.saveContacts(update, BTN_REACT_CONTACTS_LAST_NAME);
                    //для перезаписи ключа требуется сначала сохранить значение
                    List<Message> clientsList = clients.get(findClientByChatId(update.message()));
                    //сохраняем ключ в отдельной переменной
                    Client client = getClient(update, clients);
                    //дополняем объект ключа новым полем с именем
                    client.setFirstName(firstName);
                    log.info("{}", clients.put(client, clientsList));
                    log.info("{}", getHistoryList(update));
                }
                //Если пользователь хочет оставить контакты - получение фамилии
                if (isMessageEqualsPrevious(update, BTN_REACT_CONTACTS_LAST_NAME)) {
                    log.info("блок ввода фамилии");
                    String lastName = this.saveContacts(update, BTN_REACT_CONTACTS_PHONE);
                    //для перезаписи ключа требуется сначала сохранить значение
                    List<Message> clientsList = clients.get(findClientByChatId(update.message()));
                    //сохраняем ключ в отдельной переменной
                    Client client = getClient(update, clients);
                    //дополняем объект ключа новым полем с фамилией
                    client.setFirstName(lastName);
                    log.info("{}", clients.put(client, clientsList));
                    log.info("{}", getHistoryList(update));
                }
                //Если пользователь хочет оставить контакты - получение телефона
                if (isMessageEqualsPrevious(update, BTN_REACT_CONTACTS_PHONE)) {
                    log.info("блок ввода номера телефона");
                    String phoneNum = this.saveContacts(update, BTN_REACT_THANKING);

                    //для перезаписи ключа требуется сначала сохранить значение
                    List<Message> clientsList = clients.get(findClientByChatId(update.message()));
                    //сохраняем ключ в отдельной переменной
                    Client client = getClient(update, clients);
                    //дополняем объект ключа новым полем с фамилией
                    client.setFirstName(phoneNum);
                    log.info("{}", clients.put(client, clientsList));
                    log.info("{}", clients.get(findClientByChatId(update.message())));
                    log.info("{}", getHistoryList(update));
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    /**
     * Метод достает из мапы ключ - объект client, чтобы в последствии можно было использовать его для перезаписи ключа.
     * @param update
     * @param clientsMap
     * @return объект client
     */
    public Client getClient(Update update, Map<Client, List<Message>> clientsMap){
        for(Map.Entry<Client, List<Message>> entry: clientsMap.entrySet()){
            if(update.message().chat().id().equals(entry.getKey().getChatId())){
                return entry.getKey();
            }
        }
        return null;
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
//        messageHistory.add(sentMessageId);
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
        addHistory(sentMessageId);
    }

    /**
     * Метод создает реакцию на нажатие кнопки ({@value  pro.sky.telegrambot.constants.Constants#BUTTON_CONTACTS})
     * на клавиатуре и выводит два текстовых сообщения с инструкциями.
     * <br>Так же см. описание метода {@link TelegramBotUpdatesListener#buttonReact(Update, String)}</br>
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
     * Метод принимает имя и фамилию или телефон от пользователя.
     * После чего заполняет полученными данными объект {@link Client}.
     * <br>Алгоритм работы метода следующий:</br>
     * <li>Получаем имя, фамилию или телефон из update.</li>
     * <li>Метод {@link TelegramBotUpdatesListener#nameProcessor(String)}
     * приводит имя или фамилию к надлежащему виду. Удаляет лишние символы, пробелы, делает первую букву заглавной.</li>
     * <li>Сообщение с имененм, фамилией или номером телефона добавляется в историю сообщений
     * {@link TelegramBotUpdatesListener#clients}.</li>
     * <li>Метод отправляет пользователю сообщение со следующей инструкцией.</li>
     *
     * @param update          отсюда, берем имя, фамилию или номер телефона
     * @param nextMessageText текст следующего сообщения.
     */
    public String saveContacts(Update update, String nextMessageText) {
        log.info("////////////////////////////////////");
        log.info("saveContacts запущен");
        String name = null;
        //приведение имени к заданному виду перед сохранением в БД
        name = update.message().text();
        name = nameProcessor(name);
        log.info("получаем имя или фамилию - {}", name);
        //добавление фамилии, имени или телефона в историю сообщений
        addHistory(update.message());
        //отправка следующего сообщения с инструкциями по вводу контактов
        sendMessage(update, nextMessageText);
        log.info("////////////////////////////////////");
        return name;
    }

    /**
     * Метод проверяет, был ли в предыдущем сообщении текст подаваемый как параметр.
     * Если такой текст выводился в сообщении, то метод возвращает <b>true</b>.
     * В условии разность равная 1 означает, что сообщение имеющее заданный текст
     * было именно предпоследним.
     *
     * @param update
     * @param preText тест с которым нужно сравнить текст из предыдущего сообщения.
     * @return
     */
    public boolean isMessageEqualsPrevious(Update update, String preText) {
        log.info("isMessageEqualsPrevious запущен");

        Integer currentMessageId = update.message().messageId();
        int previousMessageIndex = getHistoryList(update).size() - 1;
        log.info("предыдущее сообщение = {}", getHistoryList(update).get(previousMessageIndex).text());
        Integer previousMessageId = getHistoryList(update).get(previousMessageIndex).messageId();
        String previousMessageText = getHistoryList(update).get(previousMessageIndex).text();

        log.info("currentMessageId {}", currentMessageId);
        log.info("previousMessageId {}", previousMessageId);
        log.info("Разность = {}", currentMessageId - previousMessageId);

        return preText.equals(previousMessageText) && currentMessageId - previousMessageId == 1;
    }


    /**
     * Метод приводит имя или фамилию к надлежащему виду.
     * Убирает случайные знаки, убирает пробелы, делает первую букву имени заглавной.
     * <br>Пример: Василий, Петров, Катерина, Дроздова и т.д.</br>
     *
     * @param name как параметр подается имя или фамилия
     * @return - то же слово, но приведенное к формату записи имен и фамилий
     */
    public String nameProcessor(String name) {
        log.info("имя или фамилия БЫЛО: {}", name);

        name = name.replace(",", "")
                .replace(".", "")
                .replace(";", "")
                .replace("!", "")
                .trim()
                .toLowerCase();
        name = StringUtils.capitalize(name);

        log.info("имя или фамилия СТАЛО: {}", name);
        return name;
    }

    /**
     * Метод отправляет сообщение в чат с заданным текстом.
     *
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
        getHistoryList(update).add(sentMessageId);
    }

    /**
     * Метод удаляет заданное количество предыдущих сообщений
     *
     * @param update
     * @param quantityToDelete количество сообщений которые нужно удалить
     */
    @Deprecated
    public void deletePreviousMessages(Update update, int quantityToDelete) {
        Long chatId = update.message().chat().id();
        for (int i = 2; i <= quantityToDelete; i++) {
            int previousMessageIndex = getHistoryList(update).size() - i;
            Message previousMessage = getHistoryList(update).get(previousMessageIndex);

            deletePreviousMessage(previousMessage, chatId);
            getHistoryList(update).remove(previousMessageIndex);
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
        //добавляем в client chatId, telegramId
        for (Map.Entry<Client, List<Message>> entry: clients.entrySet()) {
            entry.getKey().setChatId(chatId);
            entry.getKey().setTelegramId(update.message().from().id());
        }
        //записываем полученное и отправленное сообщения в историю сообщений
        getHistoryList(update).add(update.message());
        getHistoryList(update).add(sentMessageId);

        log.info("+++++++++++++++++++++++++++++++++++++");
    }

    /**
     * Метод для удаления сообщения.
     *
     * @param message сообщение
     * @param chatId  id чата
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
     * <br>Как это работает?</br> Метод обращается к мапе {@link TelegramBotUpdatesListener#getHistoryList(Update)},
     * где записаны id чатов и сообщений всех предыдущих входящих и исходящий сообщений.
     * <br>Далее метод циклом пробегает по мапе с историей сообщений и применяет к каждому класс
     * {@link DeleteMessage}</br>. Таким образом история чата очищается.
     * <br>После окончания работы цикла, очищается и сама мапа {@link TelegramBotUpdatesListener#getHistoryList(Update)}.</br>
     */
    private void deleteAllPreviousMessages() {
        log.info("deleteAllPreviousMessages запущен");

        for (Map.Entry<Client, List<Message>> entry: clients.entrySet()) {
            List<Message> messageHistory = entry.getValue();
            for (Message message : messageHistory) {
                Long chatId = message.chat().id();
                Integer messageId = message.messageId();
                DeleteMessage deleteMessage = new DeleteMessage(chatId, messageId);
                log.info("сообщение удалено ", telegramBot.execute(deleteMessage));
            }
            messageHistory.clear();
        }
    }

    /**
     * Метод ищет в мапе {@link TelegramBotUpdatesListener#clients} клиента с заданным <b>chatId</b>.
     *
     * @param message
     * @return клиента, который соответствует <b>chatId</b>.
     */
    public Client findClientByChatId(Message message) {
        for (Map.Entry<Client, List<Message>> entry : clients.entrySet()) {
            if (message.chat().id().equals(entry.getKey().getChatId())){
                return entry.getKey();
            }
        }
        // Если не найдено соответствующего клиента, можно вернуть null.
        return null;
    }

    /**
     * Метод добавляет <b>message</b> в историю сообщений конкретного клиента по его <b>chatId</b>.
     *
     * @param message
     */
    public void addHistory(Message message) {
        for (Map.Entry<Client, List<Message>> entry : clients.entrySet()) {
            if(message.chat().id().equals(entry.getKey().getChatId())){
                entry.getValue().add(message);
            }
        }
    }

    /**
     * Метод возвращает коллекцию с историей сообщений для текущего пользователя.
     *
     * @param update
     * @return список message
     */
    public List<Message> getHistoryList(Update update) {
        for (Map.Entry<Client, List<Message>> entry : clients.entrySet()) {
            Long chatId = (update.message().text() != null)? update.message().chat().id() : update.callbackQuery().message().chat().id();
            if(chatId.equals(entry.getKey().getChatId())){
                return entry.getValue();
            }
        }
        return null;
    }

//    /**
//     * Метод возвращает <b>true</b>, если заданный текст присутствует в
//     * {@link TelegramBotUpdatesListener#messageHistory}.
//     * Метод вернет <b>false</b>, если текст не содержится в коллекции.
//     *
//     * @param text текст с которым, метод сравнивает содержимое сообщений коллекции истории сообщений.
//     * @return boolean
//     */
//    @Deprecated
//    private boolean isHistoryContainsText(String text) {
//        for (Message message : messageHistory) {
//            if (message.text().equals(text)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    /**
//     * Метод выводит в консоль содержание листа с историей сообщений. Добавляя,
//     * этот метод в разные части кода, можно видеть какие сообщения и на каком
//     * этапе попадают в лист для дальнейшего возможного использования.
//     */
//    public void getHistory() {
//        messageHistory.stream()
//                .map(message -> message.text())
//                .forEach(text -> System.out.println(text));
//    }

    /**
     * Геттер поля {@link TelegramBotUpdatesListener#isCatChosen}
     *
     * @return boolean - значение поля isCatChosen
     */
    public static boolean isCatChosen() {
        return isCatChosen;
    }
}