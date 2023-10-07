package pro.sky.telegrambot.keyboards;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import lombok.extern.slf4j.Slf4j;
import pro.sky.telegrambot.listener.TelegramBotUpdatesListener;

import static pro.sky.telegrambot.constants.Constants.*;

/**
 * Класс для создания клавиатур
 */
@Slf4j
public class KeyboardMaker extends Keyboard {
    /**
     * меню 1. Это стартовое меню, в которе мы попадаем после ввода команды
     * {@value  pro.sky.telegrambot.constants.Constants#COMMAND_START}
     * <br>Метод создает клавиатуру с 2-мя кнопками:</br>
     * <li>{@value  pro.sky.telegrambot.constants.Constants#BUTTON_CAT_SHELTER}</li>
     * <li>{@value  pro.sky.telegrambot.constants.Constants#BUTTON_DOG_SHELTER}</li>
     *
     * @return Keyboard
     */
    public static Keyboard keyboardCatDog() {
        log.info("keyboardCatDog создана, переход по команде {}", COMMAND_START);
        return new InlineKeyboardMarkup(
                new InlineKeyboardButton(BUTTON_CAT_SHELTER).callbackData(BUTTON_CAT_SHELTER),
                new InlineKeyboardButton(BUTTON_DOG_SHELTER).callbackData(BUTTON_DOG_SHELTER)
        );
    }

    /**
     * меню 2. В это меню можно перейти по одной из кнопок:
     * {@value  pro.sky.telegrambot.constants.Constants#BUTTON_CAT_SHELTER} или
     * {@value  pro.sky.telegrambot.constants.Constants#BUTTON_DOG_SHELTER}
     * <br>Метод создает клавиатуру с 4-мя кнопками:</br>
     * <li>{@value  pro.sky.telegrambot.constants.Constants#BUTTON_SHELTER_INFO}</li>
     * <li>{@value  pro.sky.telegrambot.constants.Constants#BUTTON_HOW_TO_GET_ANIMAL}</li>
     * <li>{@value  pro.sky.telegrambot.constants.Constants#BUTTON_SEND_REPORT}</li>
     * <li>{@value  pro.sky.telegrambot.constants.Constants#BUTTON_CALL_VOLUNTEER}</li>
     *
     * @return Keyboard
     */
    public static Keyboard keyboardUserRequest() {
        log.info("keyboardUserRequest создана, переход от кнопок {} или {}", BUTTON_CAT_SHELTER, BUTTON_DOG_SHELTER);
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();

        keyboard.addRow(
                new InlineKeyboardButton(BUTTON_SHELTER_INFO).callbackData(BUTTON_SHELTER_INFO),
                new InlineKeyboardButton(BUTTON_HOW_TO_GET_ANIMAL).callbackData(BUTTON_HOW_TO_GET_ANIMAL));
        keyboard.addRow(
                new InlineKeyboardButton(BUTTON_SEND_REPORT).callbackData(BUTTON_SEND_REPORT),
                new InlineKeyboardButton(BUTTON_CALL_VOLUNTEER).callbackData(BUTTON_CALL_VOLUNTEER));

        return keyboard;
    }

    /**
     * меню 2.1. В это меню переходим по кнопке {@value  pro.sky.telegrambot.constants.Constants#BUTTON_SHELTER_INFO}
     * метод создает клавиатуру с 6-ю кнопками:
     * <li>{@value pro.sky.telegrambot.constants.Constants#BUTTON_SHELTER_ABOUT}</li>
     * <li>{@value pro.sky.telegrambot.constants.Constants#BUTTON_GET_SCHEDULE_ADDRESS}</li>
     * <li>{@value pro.sky.telegrambot.constants.Constants#BUTTON_MAKING_PASS}</li>
     * <li>{@value pro.sky.telegrambot.constants.Constants#BUTTON_SAFETY_RULES}</li>
     * <li>{@value pro.sky.telegrambot.constants.Constants#BUTTON_CONTACTS}</li>
     * <li>{@value pro.sky.telegrambot.constants.Constants#BUTTON_CALL_VOLUNTEER}</li>
     *
     * @return Keyboard
     */
    public static Keyboard keyboardNewUserConsult() {
        log.info("keyboardNewUserConsult создана, переход от кнопки {}", BUTTON_SHELTER_INFO);
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();

        keyboard.addRow(
                new InlineKeyboardButton(BUTTON_SHELTER_ABOUT).callbackData(BUTTON_SHELTER_ABOUT),
                new InlineKeyboardButton(BUTTON_GET_SCHEDULE_ADDRESS).callbackData(BUTTON_GET_SCHEDULE_ADDRESS));
        keyboard.addRow(
                new InlineKeyboardButton(BUTTON_MAKING_PASS).callbackData(BUTTON_MAKING_PASS),
                new InlineKeyboardButton(BUTTON_SAFETY_RULES).callbackData(BUTTON_SAFETY_RULES));
        keyboard.addRow(
                new InlineKeyboardButton(BUTTON_CONTACTS).callbackData(BUTTON_CONTACTS),
                new InlineKeyboardButton(BUTTON_CALL_VOLUNTEER).callbackData(BUTTON_CALL_VOLUNTEER));

        return keyboard;
    }

    /**
     * Меню 2.2. В это меню переходим по кнопке {@value  pro.sky.telegrambot.constants.Constants#BUTTON_HOW_TO_GET_ANIMAL}
     * <br>Метод создает клавиатуру с 6-ю кнопками для обоих видов животных:</br>
     * <li>{@value pro.sky.telegrambot.constants.Constants#BUTTON_MEET_PET_RULES}</li>
     * <li>{@value pro.sky.telegrambot.constants.Constants#BUTTON_DOCS_LIST}</li>
     * <li>{@value pro.sky.telegrambot.constants.Constants#BUTTON_TRANSPORT_RECOM}</li>
     * <li>{@value pro.sky.telegrambot.constants.Constants#BUTTON_PUPPY_KITTY_HOME_ARRANGEMENT}</li>
     * <li>{@value pro.sky.telegrambot.constants.Constants#BUTTON_PET_HOME_ARRANGEMENT}</li>
     * <li>{@value pro.sky.telegrambot.constants.Constants#BUTTON_DISABLED_PET_RECOM}</li>
     * <br>и дополнительными 5-ю пунктами меню только для собак:</br>
     * <li>{@value pro.sky.telegrambot.constants.Constants#BUTTON_PRIMARY_DOG_CONTACT}</li>
     * <li>{@value pro.sky.telegrambot.constants.Constants#BUTTON_CYNOLOGIST_CONTACT}</li>
     * <li>{@value pro.sky.telegrambot.constants.Constants#BUTTON_REFUSAL_REASON}</li>
     * <li>{@value pro.sky.telegrambot.constants.Constants#BUTTON_CONTACTS}</li>
     * <li>{@value pro.sky.telegrambot.constants.Constants#BUTTON_CALL_VOLUNTEER}</li>
     * Метод делает различие между кошками и собаками опираясь на значение флажка {@link TelegramBotUpdatesListener#isChosenCat()}
     * @return Keyboard
     */
    public static Keyboard keyboardCandidateConsult() {
        log.info("keyboardCandidateConsult создана");
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();

        keyboard.addRow(new InlineKeyboardButton(BUTTON_MEET_PET_RULES).callbackData(BUTTON_MEET_PET_RULES));
        keyboard.addRow(new InlineKeyboardButton(BUTTON_DOCS_LIST).callbackData(BUTTON_DOCS_LIST));
        keyboard.addRow(new InlineKeyboardButton(BUTTON_TRANSPORT_RECOM).callbackData(BUTTON_TRANSPORT_RECOM));
        keyboard.addRow(new InlineKeyboardButton(BUTTON_PUPPY_KITTY_HOME_ARRANGEMENT).callbackData(BUTTON_PUPPY_KITTY_HOME_ARRANGEMENT));
        keyboard.addRow(new InlineKeyboardButton(BUTTON_PET_HOME_ARRANGEMENT).callbackData(BUTTON_PET_HOME_ARRANGEMENT));
        keyboard.addRow(new InlineKeyboardButton(BUTTON_DISABLED_PET_RECOM).callbackData(BUTTON_DISABLED_PET_RECOM));

        if (!TelegramBotUpdatesListener.isChosenCat()) {
            keyboard.addRow(new InlineKeyboardButton(BUTTON_PRIMARY_DOG_CONTACT).callbackData(BUTTON_PRIMARY_DOG_CONTACT));
            keyboard.addRow(new InlineKeyboardButton(BUTTON_CYNOLOGIST_CONTACT).callbackData(BUTTON_CYNOLOGIST_CONTACT));
            keyboard.addRow(new InlineKeyboardButton(BUTTON_REFUSAL_REASON).callbackData(BUTTON_REFUSAL_REASON));
            keyboard.addRow(new InlineKeyboardButton(BUTTON_CONTACTS).callbackData(BUTTON_CONTACTS));
            keyboard.addRow(new InlineKeyboardButton(BUTTON_CALL_VOLUNTEER).callbackData(BUTTON_CALL_VOLUNTEER));
        }
        return keyboard;
    }
}
