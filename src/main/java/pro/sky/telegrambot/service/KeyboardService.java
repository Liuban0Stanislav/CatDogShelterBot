package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.constants.PetType;

import static pro.sky.telegrambot.constants.Constants.*;
import static pro.sky.telegrambot.constants.Constants.BUTTON_CALL_VOLUNTEER_TEXT;
import static pro.sky.telegrambot.constants.PetType.DOG;
@Service
public class KeyboardService {


    /**
     * Creates buttons for the shelter type selection message (reply to the /start command)
     *
     * @return {@code InlineKeyboardMarkup}
     */
    public InlineKeyboardMarkup createButtonsShelterTypeSelect() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_CAT_SHELTER_TEXT).callbackData(BUTTON_CAT_SHELTER_CALLBACK_TEXT));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_DOG_SHELTER_TEXT).callbackData(BUTTON_DOG_SHELTER_CALLBACK_TEXT));
        return inlineKeyboardMarkup;
    }

    /**
     * Creates buttons for the reply message to the shelter type selection (Stage 0)
     *
     * @return {@code InlineKeyboardMarkup}
     */
    public InlineKeyboardMarkup createButtonsStage0() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_STAGE1_TEXT).callbackData(BUTTON_STAGE1_CALLBACK_TEXT));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_STAGE2_TEXT).callbackData(BUTTON_STAGE2_CALLBACK_TEXT));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_STAGE3_TEXT).callbackData(BUTTON_STAGE3_CALLBACK_TEXT));
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup createButtonsStage1() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_INFO_SHELTER_TEXT).callbackData(BUTTON_INFO_SHELTER_CALLBACK_TEXT));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_INFO_SECURITY_TEXT).callbackData(BUTTON_INFO_SECURITY_CALLBACK_TEXT));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_INFO_SAFETY_PRECAUTIONS_TEXT).callbackData(BUTTON_INFO_SAFETY_PRECAUTIONS_CALLBACK_TEXT));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_SHARE_CONTACT_DETAILS_TEXT).callbackData(BUTTON_SHARE_CONTACT_CALLBACK_TEXT));
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup createButtonsStage2(PetType shelterType) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_RULES_MEETING_ANIMAL_TEXT).callbackData(BUTTON_RULES_MEETING_ANIMAL_CALLBACK_TEXT));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_DOCS_FOR_ADOPTION_TEXT).callbackData(BUTTON_DOCS_FOR_ADOPTION_CALLBACK_TEXT));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_RECOMMENDATIONS_FOR_TRANSPORT_TEXT).callbackData(BUTTON_RECOMMENDATIONS_FOR_TRANSPORT_CALLBACK_TEXT));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_ARRANGEMENT_FOR_PUPPY_TEXT).callbackData(BUTTON_ARRANGEMENT_FOR_PUPPY_CALLBACK_TEXT));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_ARRANGEMENT_FOR_ADULT_TEXT).callbackData(BUTTON_ARRANGEMENT_FOR_ADULT_CALLBACK_TEXT));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_ADVICES_FOR_DISABLED_PET_TEXT).callbackData(BUTTON_ADVICES_FOR_DISABLED_PET_CALLBACK_TEXT));
        if (shelterType.equals(DOG)) {
            inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_ADVICES_FROM_KINOLOG_TEXT).callbackData(BUTTON_ADVICES_FROM_KINOLOG_CALLBACK_TEXT));
            inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_RECOMMENDED_KINOLOGS_TEXT).callbackData(BUTTON_RECOMMENDED_KINOLOGS_CALLBACK_TEXT));
        }
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_REASONS_FOR_REFUSAL_TEXT).callbackData(BUTTON_REASONS_FOR_REFUSAL_CALLBACK_TEXT));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_SHARE_CONTACT_DETAILS_TEXT).callbackData(BUTTON_SHARE_CONTACT_CALLBACK_TEXT));
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup createButtonsStage3() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_REPORT_TEMPLATE_TEXT).callbackData(BUTTON_REPORT_TEMPLATE_CALLBACK_TEXT));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_SEND_REPORT_TEXT).callbackData(BUTTON_SEND_REPORT_CALLBACK_TEXT));
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup createButtonsSendReport() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_CANCEL_TEXT).callbackData(BUTTON_CANCEL_SEND_REPORT_CALLBACK_TEXT));
        return inlineKeyboardMarkup;
    }

    public ReplyKeyboardMarkup createRequestContactKeyboardButton() {
        KeyboardButton keyboardButton1 = new KeyboardButton(BUTTON_SHARE_CONTACT_TEXT);
        KeyboardButton keyboardButton2 = new KeyboardButton(BUTTON_CANCEL_TEXT);
        keyboardButton1.requestContact(true);
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(keyboardButton1, keyboardButton2);
        replyKeyboardMarkup.resizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup createMainMenuKeyboardButtons() {
        KeyboardButton keyboardButton1 = new KeyboardButton(BUTTON_MAIN_MENU_TEXT);
        KeyboardButton keyboardButton2 = new KeyboardButton(BUTTON_CALL_VOLUNTEER_TEXT);
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(keyboardButton1, keyboardButton2);
        replyKeyboardMarkup.resizeKeyboard(true);
        return replyKeyboardMarkup;
    }
}
