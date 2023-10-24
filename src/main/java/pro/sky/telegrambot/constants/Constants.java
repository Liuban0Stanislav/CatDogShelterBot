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
            "После того как вы нажмете на кнопку - 'прислать отчет', вам нужно будет отправить информацию тремя последовательными сообщениями \n" +
                    "Первым сообщение нужно отправить фото питомца. \n" +
                    "После фото нужно отправить рацион питомца на сегодня. \n" +
                    "Третим сообщением пришлите краткое описание самочувствия питомца. \n" +
                    "Последним - четвертым сообщением, отправте информацио о каких либо изменениях в поведении питомца. \n \n " +
                    "На этом ежедневный отчет будет сохранен, я оповещу вас сооnветствующим сообщением. \n" +
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
    public final static String BUTTON_RULES_MEETING_ANIMAL_TEXT_ANSWER = "Познакомьте животных с запахом друг друга. " +
            "Для этого потрите щенка тряпкой, а затем положите ее туда, где часто бывает кошка. " +
            "Возможно, такой ход придется проделать несколько раз, пока запах не перестанет " +
            "вызывать беспокойство питомца. Аналогичную процедуру проделайте и с собакой.";
    public final static String BUTTON_DOCS_FOR_ADOPTION_TEXT = "Документы для усыновления";
    public final static String BUTTON_DOCS_FOR_ADOPTION_TEXT_ANSWER = "Паспорт\n" +
            "Прививочный сертификат\n" +
            "Полис ОМС\n" +
            "Письменное согласие от соседей слева, справа, сверху и снизу";
    public final static String BUTTON_INFO_SHELTER_TEXT = "Расписание работы и адрес";
    public final static String BUTTON_INFO_SHELTER_TEXT_ANSWER = "Часы работы приюта:\n " +
            "Пн - Пт с 9:00 до 18:00\n" +
            "Сб с 10:00 до 15:00\n" +
            "\n" +
            "Адрес: 684005, Елизовский район, г.Елизово, ул. Школьная, дом 6, кв. 15";
    ;
    public final static String BUTTON_INFO_SECURITY_TEXT = "Оформление пропуска на машину";
    public final static String BUTTON_INFO_SECURITY_TEXT_ANSWER = "Пропуск можно оформить:\n" +
            "Пн - Пт с 9:00 до 18:00\n" +
            "Сб с 10:00 до 15:00\n" +
            "\n" +
            "Телефон для оформления пропуска на территорию: " +
            "+7(425) 123-45-56";
    public final static String BUTTON_INFO_SAFETY_PRECAUTIONS_TEXT = "Информация о технике безопасности";
    public final static String BUTTON_INFO_SAFETY_PRECAUTIONS_TEXT_ANSWER = "Правила техники безопасности в приюте:\n" +
            "❌В приют не допускаются:\n" +
            "- дети до 14 лет без сопровождения взрослых (родителей, опекунов, представителей с предъявленной рукописной доверенностью);\n" +
            "- лица в состоянии алкогольного или наркотического опьянения;\n" +
            "- лица в агрессивном или неадекватном состоянии.\n" +
            "\n" +
            "❌Для всех посетителей Приюта запрещается:\n" +
            "- проведение фото и видео фиксации без предварительного письменного согласования с администрацией Приюта за 1 рабочего дня до планируемого проведения фото или видео фиксации;\n" +
            "- кормить животных кормами и продуктами;\n" +
            "- посещать блок карантина и изолятор;\n" +
            "- давать животным самостоятельно какие-либо ветеринарные или медицинские препараты;\n" +
            "- осуществлять любые ветеринарные манипуляции с животными;\n" +
            "- находится без сопровождения сотрудника на территории приюта;\n" +
            "- посещать приют со своими животными.";
    public final static String BUTTON_RECOMMENDATIONS_FOR_TRANSPORT_TEXT = "Рекомендации по транспортировке животного";
    public final static String BUTTON_RECOMMENDATIONS_FOR_TRANSPORT_TEXT_ANSWER = "Очень многих кошек и собак, " +
            "особенно в детском возрасте, сильно укачивает в машине даже при плавном ее передвижении. " +
            "У животных начинается сильнейшее слюноотделение, может быть рвота. " +
            "Во избежание столь неприятных сюрпризов не кормите животное хотя бы за два часа до поездки. " +
            "Если особенности породы позволяют, лучше вообще не кормить в этот день.\n" +
            "\n" +
            "Воду, как всегда, можно пить без ограничения. Перед посадкой в машину выгуляйте животное, " +
            "чтобы потом ехать без остановок.\n" +
            "\n" +
            "Очень темпераментное и любопытное животное удобно перевозить в боксе. " +
            "Переноску можно убрать в багажный отсек или поставить в салоне так, " +
            "чтобы при резком торможении она не опрокинулась.";
    public final static String BUTTON_ARRANGEMENT_FOR_PUPPY_TEXT = "Рекомендации по обустройству дома для молодого животного";
    public final static String BUTTON_ARRANGEMENT_FOR_PUPPY_TEXT_ANSWER = "\n" +
            "Практические советы сведут к минимуму риск травм нового члена семьи и сберегут нервы хозяевам.\n" +
            "\n" +
            "Безопасность проводов. Доступ к проводам нужно перекрыть мебелью. Можно прикрепить провода к ножкам " +
            "стола и обклеить скотчем, обмотать оплёткой или спрятать в кабель-каналы. Как вариант – " +
            "убрать провода под ковёр или, наоборот, убрать их повыше. Если провода не получается спрятать, " +
            "нужно обработать их специальными растворами, безвредными для здоровья.\n" +
            "Пора привыкать класть вещи на место. Судьбу всего, что плохо лежит, решит питомец.\n" +
            "Растения в горшках. О некоторых можно уколоться, другие ядовиты. А третьи щенок или " +
            "котёнок может просто раскопать.\n" +
            "Бытовую химию следует убрать повыше и под замок.\n" +
            "Еды в открытом доступе быть не должно. Изюм, кофе, авокадо, шоколад следует прятать. " +
            "Нужно заранее почитать, какие продукты ещё могут быть опасны для питомцев.";
    public final static String BUTTON_ARRANGEMENT_FOR_ADULT_TEXT = "Рекомендации по обустройству дома для взрослого животного";
    public final static String BUTTON_ARRANGEMENT_FOR_ADULT_TEXT_ANSWER = "Содержать домашнее животное, особенно собаку, " +
            "в квартире намного сложнее, чем в частном доме. Они то и дело путаются под ногами, когда мы занимаемся " +
            "уборкой, разносят мусор по всему дому, царапают мебель и мешают спать, запрыгивая на кровать. " +
            "В то же время эти существа являются частью нашей семьи, так почему бы не обустроить для них специально " +
            "отведенное место, где они смогут отдыхать и чувствовать себя хозяевами? Глядишь, и некоторая часть хлопот, " +
            "связанна с ними, разрешится сама собой. Тем более что обустройство места для домашнего питомца " +
            "не требует больших вложений и может быть выполнено своими руками.\n" +
            "\n" +
            "Например, нет ничего проще, чем сшить большую и мягкую напольную подушку, которую можно будет легко " +
            "переносить из комнаты в комнату. Ведь питомцы так любят быть рядом со своими хозяевами и в гостиной, " +
            "пока те смотрят телевизор, и в спальне, когда они отдыхают!";
    public final static String BUTTON_ADVICES_FOR_DISABLED_PET_TEXT = "Рекомендации для животного с ограниченными возможностями";
    public final static String BUTTON_ADVICES_FOR_DISABLED_PET_TEXT_ANSWER = "Уход за слепыми зверями. Если в доме " +
            "содержится любимец с проблемным зрением, рекомендуется " +
            "как можно реже проводить перестановку мебели, так как " +
            "это затруднит его ориентацию в пространстве. " +
            "Также не стоит без особой необходимости менять местоположение " +
            "мисок с водой и кормом, подстилки, лотка. На полу и в пределах " +
            "досягаемости нельзя оставлять вещи, в которых питомец может " +
            "запутаться (например, одежду, полиэтиленовые пакеты-майки и т.д.). " +
            "Доступ к лестнице, камину, нагревательным приборам и т.д. должен быть заблокирован.\n" +
            "\n" +
            "Уход за глухими любимцами. Слабослышащие (или не слышащие вовсе) " +
            "коты в квартире живут практически полноценной жизнью. " +
            "Единственная разница между здоровым животным и инвалидом заключается в том," +
            " что последний не может прийти на зов хозяина. Однако это не помешает " +
            "коту быстро прибежать к миске, как только он учует вкусный запах.\n" +
            "\n" +
            "Глухие собаки, особенно крупных пород, в первую очередь, нуждаются в" +
            " особой дрессировке. В противном случае опасность может грозить " +
            "не только им, но и окружающим. После обучения хозяин сможет " +
            "общаться с питомцем на языке жестов: с помощью фонарика, прикосновений и мимики.\n" +
            "\n" +
            "Уход за питомцами с ограниченной подвижностью. К данной категории " +
            "можно отнести собак и кошек, перенесших травму позвоночника, " +
            "лишившихся конечностей, потерявших чувствительность лап вследствие перенесенных заболеваний и т. д.\n" +
            "\n" +
            "Для таких животных, в первую очередь, необходимо обеспечить " +
            "удобство передвижения по территории постоянного проживания. " +
            "Если питомец волочит заднюю часть туловища, необходимо убрать " +
            "с пола ковры, которые могут препятствовать движению. " +
            "При этом для защиты конечностей от образования мозолей " +
            "потребуется приобрести специальные фиксирующиеся накладки. " +
            "Решить проблему туалета помогут специальные подгузники и одноразовые пеленки.\n" +
            "\n" +
            "В некоторых случаях для собаки или кота можно подобрать " +
            "подходящую инвалидную коляску или ходунки. Такие конструкции " +
            "должны иметь достаточно легкий вес, но при этом не прогибаться " +
            "под массой питомца. Крепления не должны натирать шкуру или вызывать иной дискомфорт.\n" +
            "\n" +
            "Для того чтобы питомец мог самостоятельно преодолевать " +
            "различные препятствия (например, порожки или ступени), " +
            "хозяин может заказать специальные пандусы. Такие конструкции " +
            "чаще всего изготавливаются по индивидуальным размерам. " +
            "Для отделки внешней поверхности используются специальные " +
            "противоскользящие материалы. Надежная фиксация пандусов " +
            "достигается за счет упоров и креплений.\n" +
            "\n" +
            "Важно! Для глухих, слепых или ограниченно подвижных " +
            "животных категорически запрещен свободный выгул.\n" +
            "\n" +
            "Уход за парализованным любимцем. У лежачего пса или " +
            "кота обычно ограничены возможности самогигиены и при " +
            "этом наблюдается недержание мочи и кала. Для решения " +
            "первой проблемы питомца следует мыть не реже одного раза в два-три дня. " +
            "Справиться с дискомфортом от неконтролируемых испражнений помогут подгузники и пеленки.\n" +
            "\n" +
            "Иногда из-за частых водных процедур кожа любимца становится " +
            "сухой и начинает шелушиться. Снять неприятный симптом помогут специальные увлажняющие средства.\n" +
            "\n" +
            "Постоянное нахождение питомца в одной и той же позе может " +
            "привести к образованию пролежней. Для их профилактики " +
            "необходимо использовать специальные защитные повязки и накладки. " +
            "Для крупных животных производители предлагают специальные ортопедические кровати.";
    public final static String BUTTON_ADVICES_FROM_KINOLOG_TEXT = "Советы кинолога по общению с собакой";
    public final static String BUTTON_ADVICES_FROM_KINOLOG_TEXT_ANSWER = "Советы кинолога по общению с собакой: \n" +
            "Приютская собака пуглива. \n" +
            "Обращайтесь с ней ласково! Часто в приюте собаки теряют доверие к человеку и не ждут от него ничего хорошего, поэтому они могут забиваться в угол, зажмуривать глаза и вжимать голову в плечи при попытке нового хозяина ее погладить.  Собака может ложиться на землю и отказаться идти. И, возможно, даже случайно нагадить, в том числе в вашей машине, в которой вы повезете ее в новый дом! На полное восстановление доверия собаки понадобится несколько недель (ветеринарные врачи называют срок в полгода), и вы не имеете права на ошибку: в глазах своего питомца в всегда должны быть Самым Добрым и Самым Любящим Хозяином. Со временем стресс пройдет, и собака заново научится доверять людям. Не стоит в первый же день пытаться растормошить собаку, заставить ее играть или совершать долгие прогулки: животное станет бояться вас еще больше. " +
            "Дайте ей время успокоиться и привыкнуть к новой обстановке – " +
            "она ответит привязанностью на ваше терпение и будет обожать свой дом.";
    public final static String BUTTON_RECOMMENDED_KINOLOGS_TEXT = "Рекомендации по проверенным кинологам";
    public final static String BUTTON_RECOMMENDED_KINOLOGS_TEXT_ANSWER = "Рекомендации по проверенным кинологам: \n" +
            "АСТАНА\n" +
            "КИНОЛОГИЧЕСКИЙ ЦЕНТР К-9" +
            "https://k-9.kz/nursultan_kcentre/lp/";
    public final static String BUTTON_REASONS_FOR_REFUSAL_TEXT = "Причины отказа в усыновлении";
    public final static String BUTTON_REASONS_FOR_REFUSAL_TEXT_ANSWER = "Существует пять причин, по которым чаще всего " +
            "отказывают желающим «усыновить» домашнего любимца:\n" +
            "1 Большое количество животных дома\n" +
            "2 Нестабильные отношения в семье\n" +
            "3 Наличие маленьких детей\n" +
            "4 Съемное жилье\n" +
            "5 Животное в подарок или для работы";

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
