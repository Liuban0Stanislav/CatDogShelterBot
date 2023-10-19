package pro.sky.telegrambot.constants;

import com.vdurmont.emoji.EmojiParser;

public class Constants {

    // Emojis
    public final static String EMOJI_SMILEY = EmojiParser.parseToUnicode(":smiley:");
    public final static String EMOJI_WAVE = EmojiParser.parseToUnicode(":wave:");
    public final static String EMOJI_POINT_DOWN = EmojiParser.parseToUnicode(":point_down:");
    public final static String EMOJI_SMILEY_CAT = EmojiParser.parseToUnicode(":smiley_cat:");
    public final static String EMOJI_DOG = EmojiParser.parseToUnicode(":dog:");

    // Information messages
    public final static String SHELTER_TYPE_SELECT_MSG_TEXT = "Привет " + EMOJI_WAVE + " Выберите тип приюта " + EMOJI_POINT_DOWN;
    public final static String CAT_SHELTER_WELCOME_MSG_TEXT = "Вас приветствует приют для кошек. Чем я могу Вам помочь? " + EMOJI_SMILEY_CAT;
    public final static String CAT_SHELTER_STAGE1_WELCOME_MSG_TEXT = "Кошачий приют " + EMOJI_SMILEY_CAT + " Этап 1: Узнать информацию о приюте.";
    public final static String CAT_SHELTER_STAGE2_WELCOME_MSG_TEXT = "Кошачий приют " + EMOJI_SMILEY_CAT + " Этап 2: Как взять кошку из приюта.";
    public final static String CAT_SHELTER_STAGE3_WELCOME_MSG_TEXT = "Кошачий приют " + EMOJI_SMILEY_CAT + " Этап 3: Прислать отчет о питомце.";
    public final static String DOG_SHELTER_STAGE1_WELCOME_MSG_TEXT = "Собачий приют " + EMOJI_DOG + " Этап 1: Узнать информацию о приюте.";
    public final static String DOG_SHELTER_STAGE2_WELCOME_MSG_TEXT = "Собачий приют " + EMOJI_DOG + " Этап 2: Как взять собаку из приюта.";
    public final static String DOG_SHELTER_STAGE3_WELCOME_MSG_TEXT = "Собачий приют " + EMOJI_DOG + " Этап 3: Прислать отчет о питомце.";
    public final static String SHARE_CONTACT_MSG_TEXT = "Нажмите на кнопку " + EMOJI_POINT_DOWN + ", чтобы послать свои контактные данные.";
    public final static String CANCEL_SHARE_CONTACT_MSG_TEXT = "Посылка контактных данных отменена.";
    public final static String CANCEL_SEND_REPORT_MSG_TEXT = "Посылка отчета отменена.";
    public final static String DOG_SHELTER_WELCOME_MSG_TEXT = "Вас приветствует приют для собак. Чем я могу Вам помочь? " + EMOJI_DOG;
    public final static String CONTACT_TELEGRAM_USERNAME_TEXT = "Пожалуйста свяжитесь с пользователем %s. Ему нужна помощь.";
    public final static String CONTACT_TELEGRAM_ID_TEXT = "Пожалуйста свяжитесь с пользователем id %s. Ему нужна помощь.";
    public final static String NO_VOLUNTEERS_TEXT = "На данный момент нет свободных волонтеров.";
    public final static String SAVE_ADOPTER_SUCCESS_TEXT = "Мы записали ваши контактные данные.";
    public final static String ADOPTER_ALREADY_EXISTS_TEXT = "У нас уже есть ваши контактные данные.";
    public final static String WE_WILL_CALL_YOU_TEXT = "Скоро с вами свяжется наш волонтер.";
    public final static String RULES_HOW_TO_MEET_ANIMAL_DOG = "Скоро добавим информацию по собакам";
    public final static String RULES_HOW_TO_MEET_ANIMAL_CAT = "Скоро добавим информацию по кошкам";
    public final static String PHOTO_WAITING_MESSAGE = "Жду фото! ";
    public final static String ADOPTION_REPORT_ALREADY_EXIST = "Отчет уже сделан! ";
    public final static String PHOTO_SAVED_MESSAGE = "Фото сохранено, далее рацион! ";
    public final static String DIET_SAVED_MESSAGE = "Рацион сохранен. Расскажите о самочувствиИ питомца. ";
    public final static String WELL_BEING_SAVED_MESSAGE = "Сохранили! Заметили ли вы изменения в поведении питомца? ";
    public final static String BEHAVIOR_CHANGE_SAVED_MESSAGE = "Отчет сохранен! ";
    public final static String ADOPTION_REPORT_INSTRUCTION =
            "После того как вы нажмете на кнопку - 'прислать отчет', вам нужно будет отправить информацию тремя последовательными сообщениями \n " +
            "Первым сообщение нужно отправить фото питомца. \n" +
            "После фото нужно отправить рацион питомца на сегодня. \n" +
            "Третим сообщением пришлите краткое описание самочувствия питомца. \n" +
            "Последним - четвертым сообщением, отправте информацио о каких либо изменениях в поведении питомца. \n \n " +
            "На этом ежедневный отчет будет сохранен, я оповещу вас сообветствующим сообщением \n" +
            "Не переживайте, во время отправки ежедневного отчета я также буду напоминать что должно содержаться в каждом сообщении! ";
    public final static String TRIAL_PERIOD_IS_OVER_MESSAGE = "Испытательный срок закончился для %s.";
    public final static String ADOPTER_DID_NOT_SEND_REPORT_MESSAGE = "Адоптер %s не прислал отчет за сегодня.";
    public final static String INCOMPLETE_REPORT_PICTURE_MESSAGE = "Адоптер %s прислал неполный отчет: нет фото.";
    public final static String INCOMPLETE_REPORT_DESC_MESSAGE = "Адоптер %s прислал неполный отчет: неполное описание.";
    public final static String PROBATION_APPROVED_MESSAGE = "Поздравляем! Вы успешно прошли испытательный срок и становитесь полноправным хозяином питомца.";
    public final static String PROBATION_REJECTED_MESSAGE = "К сожалению, мы вынуждены сообщить вам о том, что вы не прошли испытательный срок.";
    public final static String INCOMPLETE_REPORT_ADOPTER_MESSAGE =
            "Дорогой усыновитель, мы заметили, что ты заполняешь отчет не так подробно, как необходимо. " +
            "Пожалуйста, подойди ответственнее к этому занятию. " +
            "В противном случае волонтеры приюта будут обязаны самолично проверять условия содержания питомца.";
    public final static String PROBATION_PERIOD_EXTENDED_MESSAGE = "Ваш испытательный срок был продлен до %s дней.";

    // Buttons text
    public final static String BUTTON_CAT_SHELTER_TEXT = "Приют для кошек";
    public final static String BUTTON_DOG_SHELTER_TEXT = "Приют для собак";
    public final static String BUTTON_STAGE1_TEXT = "Узнать информацию о приюте (этап 1)";
    public final static String BUTTON_STAGE2_TEXT = "Как взять питомца из приюта (этап 2)";
    public final static String BUTTON_STAGE3_TEXT = "Прислать отчет о питомце (этап 3)";
    public final static String BUTTON_CALL_VOLUNTEER_TEXT = "Позвать волонтера";
    public final static String BUTTON_MAIN_MENU_TEXT = "Главное меню";
    public final static String BUTTON_SHARE_CONTACT_TEXT = "Отправить контакт";
    public final static String BUTTON_CANCEL_TEXT = "Отменить";
    public final static String BUTTON_SHARE_CONTACT_DETAILS_TEXT = "Оставить свои контактные данные";
    public final static String BUTTON_REPORT_TEMPLATE_TEXT = "Форма ежедневного отчета";
    public final static String BUTTON_SEND_REPORT_TEXT = "Послать отчет";
    public final static String BUTTON_RULES_MEETING_ANIMAL_TEXT = "Правила знакомства с животным";
    public final static String BUTTON_DOCS_FOR_ADOPTION_TEXT = "Документы для усыновления";
    public final static String BUTTON_INFO_SHELTER_TEXT = "Расписание работы / адрес / схема проезда";
    public final static String BUTTON_INFO_SECURITY_TEXT = "Оформление пропуска на машину";
    public final static String BUTTON_INFO_SAFETY_PRECAUTIONS_TEXT = "Информация о технике безопасности";
    public final static String BUTTON_RECOMMENDATIONS_FOR_TRANSPORT_TEXT = "Рекомендации по транспортировке животного";
    public final static String BUTTON_ARRANGEMENT_FOR_PUPPY_TEXT = "Рекомендации по обустройству дома для молодого животного";
    public final static String BUTTON_ARRANGEMENT_FOR_ADULT_TEXT = "Рекомендации по обустройству дома для взрослого животного";
    public final static String BUTTON_ADVICES_FOR_DISABLED_PET_TEXT = "Рекомендации для животного с ограниченными возможностями";
    public final static String BUTTON_ADVICES_FROM_KINOLOG_TEXT = "Советы кинолога по общению с собакой";
    public final static String BUTTON_RECOMMENDED_KINOLOGS_TEXT = "Рекомендации по проверенным кинологам";
    public final static String BUTTON_REASONS_FOR_REFUSAL_TEXT = "Причины отказа в усыновлении";

    // Buttons callback text
    public final static String BUTTON_CAT_SHELTER_CALLBACK_TEXT = "button_Cat_Shelter_clicked";
    public final static String BUTTON_DOG_SHELTER_CALLBACK_TEXT = "button_Dog_Shelter_clicked";
    public final static String BUTTON_STAGE1_CALLBACK_TEXT = "button_Stage1_clicked";
    public final static String BUTTON_STAGE2_CALLBACK_TEXT = "button_Stage2_clicked";
    public final static String BUTTON_STAGE3_CALLBACK_TEXT = "button_Stage3_clicked";
    public final static String BUTTON_CALL_VOLUNTEER_CALLBACK_TEXT = "button_CallVolunteer_clicked";
    public final static String BUTTON_SHARE_CONTACT_CALLBACK_TEXT = "button_ShareContact_clicked";
    public final static String BUTTON_REPORT_TEMPLATE_CALLBACK_TEXT = "button_ReportTemplate_clicked";
    public final static String BUTTON_SEND_REPORT_CALLBACK_TEXT = "button_SendReport_clicked";
    public final static String BUTTON_RULES_MEETING_ANIMAL_CALLBACK_TEXT = "button_rulesMeetingAnimal_clicked";
    public final static String BUTTON_DOCS_FOR_ADOPTION_CALLBACK_TEXT = "button_docsForAdoption_clicked";
    public final static String BUTTON_RECOMMENDATIONS_FOR_TRANSPORT_CALLBACK_TEXT = "button_recForTransport_clicked";
    public final static String BUTTON_ARRANGEMENT_FOR_PUPPY_CALLBACK_TEXT = "button_arrangForLittle_clicked";
    public final static String BUTTON_ARRANGEMENT_FOR_ADULT_CALLBACK_TEXT = "button_arrangForAdult_clicked";
    public final static String BUTTON_ADVICES_FOR_DISABLED_PET_CALLBACK_TEXT = "button_advicesForDisable_clicked";
    public final static String BUTTON_ADVICES_FROM_KINOLOG_CALLBACK_TEXT = "button_advicesFromKinolog_clicked";
    public final static String BUTTON_RECOMMENDED_KINOLOGS_CALLBACK_TEXT = "button_recommendedKinologs_clicked";
    public final static String BUTTON_REASONS_FOR_REFUSAL_CALLBACK_TEXT = "button_reasonsRefusal_clicked";
    public final static String BUTTON_INFO_SHELTER_CALLBACK_TEXT = "button_Info_Shelter_clicked";
    public final static String BUTTON_INFO_SECURITY_CALLBACK_TEXT = "button_Info_Security_clicked";
    public final static String BUTTON_INFO_SAFETY_PRECAUTIONS_CALLBACK_TEXT = "button_Info_Safety_Precautions_clicked";
    public final static String BUTTON_CANCEL_SEND_REPORT_CALLBACK_TEXT = "button_Cancel_Send_Report_clicked";

    // REST endpoint urls
    public final static String LOCALHOST_URL = "http://localhost:";
    public final static String BRANCHPARAMS_URL = "/pet-shelter/params";
    public final static String PET_URL = "/pet-shelter/pet";
    public final static String VOLUNTEER_URL = "/pet-shelter/volunteer";
    public final static String BREED_URL = "/pet-shelter/breed";
    public final static String ADOPTION_REPORT_URL = "/pet-shelter/adoptionReport";
    public final static String ADOPTIONDOC_URL = "/pet-shelter/doc";
    public final static String ADOPTER_URL = "/pet-shelter/adopter";

}
