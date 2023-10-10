package pro.sky.telegrambot.services;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendPhoto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

import static pro.sky.telegrambot.constants.Constants.*;
import static pro.sky.telegrambot.listener.TelegramBotUpdatesListener.isCatChosen;
@Slf4j
@Service
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
        //кнопка 2.1.5
        if(textMessage.equals(BUTTON_CONTACTS)){
            return BTN_REACT_CONTACTS_MESSAGE;
        }

        return SOMETHING_WENT_WRONG;
    }

    public static String getUserContactsForm(Update update){
        log.info("getUserContactsForm");
        //кнопка 2.1.5
       boolean isFirstNameFormExist = update.message().text().contains("имя:");
       boolean isLastNameFormExist = update.message().text().contains("фамилия:");
       boolean isPhoneFormExist = update.message().text().contains("телефон:");
       log.info("имя: {}\nфамилия: {}\nтелефон: {}",isFirstNameFormExist, isLastNameFormExist, isPhoneFormExist);
       return"jr";
    }



    public static SendPhoto sendPhoto(Update update){
        return new SendPhoto(update.message().chat().id(), "https://ledo.pro/wp-content/uploads/2019/12/1491303941_shema_proezda.png");
    }
}
