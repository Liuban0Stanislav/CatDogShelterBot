package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.command.Commands;
import pro.sky.telegrambot.command.StartCommand;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    @Autowired
    private TelegramBot telegramBot;

//    private final List<Commands> commands;
//
//    public TelegramBotUpdatesListener(List<Commands> commands) {
//        this.commands = commands;
//    }
    Long chatId;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
                    logger.info("Processing update: {}", update);

                    if (update.message() != null && update.message().text().equals("/start")) {
                            this.startCommandReact(update);
                    } else if (update.callbackQuery() != null) {
                        if (update.callbackQuery().data().equals("Приют для кошек")) {
                            logger.info("update.callbackQuery().data().equals({})", update.callbackQuery().data());
                            this.catShelterReact(update);
                        } else if (update.callbackQuery().data().equals("Приют для собак")){
                            logger.info("update.callbackQuery().data().equals({})", update.callbackQuery().data());
                        }
                    }
//            this.getUserData(update);


//                commands.stream()
//                        .filter(command -> command.ifSuitable(update))
//                        .forEach(command -> command.handle(update));

    });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
}

    private void catShelterReact(Update update) {
        logger.info("catShelterReact Запущен");
        InlineKeyboardMarkup replyKeyboardMarkup = new InlineKeyboardMarkup(
                new InlineKeyboardButton("Узнать расписание").callbackData("Узнать расписание"),
                new InlineKeyboardButton("Узнать адреса").callbackData("Узнать адреса")
        );
        logger.info("replyKeyboardMarkup = {}", replyKeyboardMarkup);
        SendMessage message = new SendMessage(chatId, "Это меню рассказывает о адресах и расписании приютов");
        logger.info("message = {}", message);

        boolean isTelegramBotNull;
        if (!telegramBot.equals(null)){
            isTelegramBotNull = false;
        }else {isTelegramBotNull = true;}
        logger.info("telegramBot = {}, является ли телеграмм бот null = {}", telegramBot.toString(), isTelegramBotNull);

        telegramBot.execute(message.replyMarkup(replyKeyboardMarkup));
        logger.info("execute сработал {}", telegramBot.execute(message.replyMarkup(replyKeyboardMarkup)));
    }

    private void startCommandReact(Update update) {
        logger.info("startCommandReact Запущен");
        Long chatId = update.message().chat().id();
        this.chatId = chatId;
        InlineKeyboardMarkup replyKeyboardMarkup = new InlineKeyboardMarkup(
                new InlineKeyboardButton("Приют для кошек").callbackData("Приют для кошек"),
                new InlineKeyboardButton("Приют для собак").callbackData("Приют для собак")
        );
        logger.info("replyKeyboardMarkup = {}", replyKeyboardMarkup);
//                KeyboardButton button1 = new KeyboardButton("Приют для кошек");
//                KeyboardButton button2 = new KeyboardButton("Приют для собак");
//                ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(button1, button2);
        SendMessage message = new SendMessage(chatId, "Привет");
        logger.info("message = {}", message);

        telegramBot.execute(message.replyMarkup(replyKeyboardMarkup));
        logger.info("execute сработал {}", telegramBot.execute(message.replyMarkup(replyKeyboardMarkup)));
        logger.info("__________________________________________________________________________________");
    }

    private void getUserData(Update update){
        String username = update.message().chat().username();
        Long userId = update.message().from().id();
//        logger.info("Имя пользователя - {}\nid пользователя - {}", username, userId);
        System.out.println("username = " + username);
        System.out.println("userId = " + userId);
    }


//
//    private void processCallbackQuery(Update update) {
//        logger.info("Processing callback query: {}", update.callbackQuery());
//        long chatId = update.callbackQuery().message().chat().id();
//        String callbackDataText = update.callbackQuery().data();
//        long userId = update.callbackQuery().from().id();
//
//        sendMessage(this.getReplyMessage(chatId, callbackDataText, userId));
//    }
//    private void sendMessage(SendMessage message) {
//        try {
//            telegramBot.execute(message);
//        } catch (Exception e) {
//            logger.error("Error sending message", e);
//        }
//    }
//    public SendMessage getReplyMessage(long chatId, String callbackDataText, long userId) {
//        return switch (callbackDataText) {
//            case "Информация о боте" -> new SendMessage(chatId, "Этот телеграмм-бот может ответить на вопросы о том, что нужно знать и уметь, " +
//                    "чтобы забрать животное из приюта. Дать информацию о интересующем приюте. " +
//                    "Так же, сюда можно присылать ежедневный отчет о том, как животное приспосабливается к новой обстановке");
//            case "Вернуться к выбору приюта" -> new SendMessage(chatId, "Пожалуйста, выберите приют:").replyMarkup(getShelterTypeKeyboard());
//            default -> new SendMessage(chatId, "Я не понимаю вашу команду. Выберите команду из списка:").replyMarkup(getStartMenuKeyboard());
//        };
//    }
//    public Keyboard getShelterTypeKeyboard() {
//        return new InlineKeyboardMarkup(
//                new InlineKeyboardButton("Приют для кошек" + "\uD83D\uDC08")
//                        .callbackData("Приют для кошек"),
//                new InlineKeyboardButton("Приют для собак" + "\uD83D\uDC15")
//                        .callbackData("Приют для собак"));
//    }
//    public Keyboard getStartMenuKeyboard() {
//        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
//        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("рандомный текст")
//                .callbackData("рандомный текст"));
//        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("рандомный текст")
//                .callbackData("рандомный текст"));
//        return inlineKeyboardMarkup;
//    }
//    private void processUserResponse(Update update) {
//        long chatId = update.message().chat().id();
//        String text = update.message().text();
//
//
//            sendMessage(new SendMessage(chatId, "Спасибо! Ваши контактные данные сохранены."));
//
//    }
}