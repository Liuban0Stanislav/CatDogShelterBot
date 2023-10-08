package pro.sky.telegrambot.services;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendPhoto;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.listener.TelegramBotUpdatesListener;

import static pro.sky.telegrambot.constants.Constants.*;
import static pro.sky.telegrambot.listener.TelegramBotUpdatesListener.isCatChosen;

public class MessageService {
    public static String getMessage(String textMessage){
        //кнопка 2.1.1
        if(textMessage.equals(BUTTON_SHELTER_ABOUT) && isCatChosen()){
            return BTN_REACT_CAT_SHELTER_ABOUT;
        }
        if(textMessage.equals(BUTTON_SHELTER_ABOUT) && !isCatChosen()){
            return BTN_REACT_DOG_SHELTER_ABOUT;
        }
        //кнопка 2.1.2
        if(textMessage.equals(BUTTON_GET_SCHEDULE_ADDRESS) && isCatChosen()){
            return BTN_REACT_CAT_GET_SCHEDULE_ADDRESS;
        }
        if(textMessage.equals(BUTTON_GET_SCHEDULE_ADDRESS) && !isCatChosen()){
            return BTN_REACT_DOG_GET_SCHEDULE_ADDRESS;
        }
        //кнопка 2.1.3
        if(textMessage.equals(BUTTON_MAKING_PASS) && isCatChosen()){
            return BTN_REACT_CAT_MAKING_PASS;
        }
        if(textMessage.equals(BUTTON_MAKING_PASS) && !isCatChosen()){
            return BTN_REACT_DOG_MAKING_PASS;
        }
        //кнопка 2.1.4
        if(textMessage.equals(BUTTON_SAFETY_RULES) && isCatChosen()){
            return BTN_REACT_CAT_SAFETY_RULES;
        }
        if(textMessage.equals(BUTTON_SAFETY_RULES) && !isCatChosen()){
            return BTN_REACT_DOG_SAFETY_RULES;
        }
        return SOMETHING_WENT_WRONG;
    }

    public static SendPhoto sendPhoto(Update update){
        return new SendPhoto(update.message().chat().id(), "https://ledo.pro/wp-content/uploads/2019/12/1491303941_shema_proezda.png");
    }
}
