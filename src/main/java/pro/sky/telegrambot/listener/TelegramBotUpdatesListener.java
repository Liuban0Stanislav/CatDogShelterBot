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
public class TelegramBotUpdatesListener implements UpdatesListener{

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    @Autowired
    private TelegramBot telegramBot;

//    private final List<Commands> commands;
//
//    public TelegramBotUpdatesListener(List<Commands> commands) {
//        this.commands = commands;
//    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);

            InlineKeyboardButton inlineKeyboardButton2 =  new InlineKeyboardButton("Приют для кошек").callbackData("для кошек");
            InlineKeyboardButton inlineKeyboardButton3 =  new InlineKeyboardButton("Приют для собак").callbackData("для кошек");

            if (update.message().text().equals( "/start")) {
                InlineKeyboardMarkup replyKeyboardMarkup = new InlineKeyboardMarkup(
                       inlineKeyboardButton2,
                        inlineKeyboardButton3
                );

//                KeyboardButton button1 = new KeyboardButton("Приют для кошек");
//                KeyboardButton button2 = new KeyboardButton("Приют для собак");
//                ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(button1, button2);
                SendMessage message = new SendMessage(update.message().chat().id(), "Привет");
                telegramBot.execute(message.replyMarkup(replyKeyboardMarkup));
            }
            logger.info("update.callbackQuery().data() = {}", update.callbackQuery().data());

            if (update.callbackQuery().data().equals("Приют для кошек")){
                InlineKeyboardMarkup replyKeyboardMarkup = new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Узнать расписание").callbackData("расписание"),
                        new InlineKeyboardButton("Узнать адреса").callbackData("адреса")
                );

//                KeyboardButton button1 = new KeyboardButton("Узнать расписание");
//                KeyboardButton button2 = new KeyboardButton("Узнать адреса");
//                ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(button1, button2);
                SendMessage message = new SendMessage(update.message().chat().id(), "Это меню рассказывает о адресах и расписании приютов");
                telegramBot.execute(message.replyMarkup(replyKeyboardMarkup));
            }

//                commands.stream()
//                        .filter(command -> command.ifSuitable(update))
//                        .forEach(command -> command.handle(update));




//                switch (update.message().text()) {
//                    case "/start":
//
//                        break;
//
//                }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }


}