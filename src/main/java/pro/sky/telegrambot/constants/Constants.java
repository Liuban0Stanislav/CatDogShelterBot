package pro.sky.telegrambot.constants;

public class Constants {
    /**
     * константы команд
     */
    public static final String COMMAND_START = "/start";
    public static final String COMMAND_OTHER = "/other";

    /**
     * константы начального меню (меню 1)
     */
    public static final String MESSAGE_HELLO = "меню 1: Привет!, выберите в какой приют обратиться.";
    public static final String BUTTON_CAT_SHELTER = "Приют для кошек";
    public static final String BUTTON_DOG_SHELTER = "Приют для собак";

    /**
     * константы меню запроса пользователя (меню 2)
     */
    public static final String MESSAGE_CHOOSE_MENU_OPT = "меню 2: Выберите пункт меню";
    public static final String BUTTON_SHELTER_INFO = "Информация о приюте";
    public static final String BUTTON_HOW_TO_GET_ANIMAL = "Как взять животное";
    public static final String BUTTON_SEND_REPORT = "Прислать отчет";
    public static final String BUTTON_CALL_VOLUNTEER = "Позвать волонтера";

    /**
     * константы меню 2.1 <b>ЭТАПА 1</b>
     */
    public static final String MESSAGE_NEW_USER_CONSULT = "меню 2.1: Консультация нового пользователя";
    public static final String BUTTON_SHELTER_ABOUT = "Рассказ о приюте";
    public static final String BUTTON_GET_SCHEDULE_ADDRESS = "Расписание и адрес";
    public static final String BUTTON_MAKING_PASS = "Оформление пропуска";
    public static final String BUTTON_SAFETY_RULES = "Техника безопасности";
    public static final String BUTTON_CONTACTS = "Оставить свои контакты";

    /**
     * константы меню 2.2 <b>ЭТАПА 2</b>
     */
    public static final String MESSAGE_PET_OWNER_CONSULTING = "меню 2.2: Консультация потенциального хозяина животного из приюта";
    public static final String BUTTON_MEET_PET_RULES = "Знакомство с животным";
    public static final String BUTTON_DOCS_LIST = "Список документов";
    public static final String BUTTON_TRANSPORT_RECOM = "Рекомендации по транспортировке";
    public static final String BUTTON_PUPPY_KITTY_HOME_ARRANGEMENT = "Дом для щенка или котенка";
    public static final String BUTTON_PET_HOME_ARRANGEMENT = "Дом для взрослых животных";
    public static final String BUTTON_DISABLED_PET_RECOM = "Дом для животных инвалидов";
    //только для собак------------------------------------------------------------------------
    public static final String BUTTON_PRIMARY_DOG_CONTACT = "Первичный контакт с собакой";
    public static final String BUTTON_CYNOLOGIST_CONTACT = "Контакты кинологов";
    public static final String BUTTON_REFUSAL_REASON = "Причины отказа";

    /**
     * константы меню 2.3 <b>ЭТАПА 3</b>
     */
    public static final String MESSAGE_PET_LEADING_MENU = "меню 2.3: Меню ведения питомца";
    public static final String BUTTON_CREATE_REPORT = "Создать ежедневный отчет";
    /**
     * другие сообщения
     */
    public static final String SOMETHING_WENT_WRONG = "Что-то пошло не так";

    /**
     * константы реакций на кнопки из меню 2.1 <b>ЭТАПА 1</b>
     */
    public static final String BTN_REACT_CALL_VOLUNTEER = "Волонтер: Анжелика\n"  +
                                                            "+7(963)-832-32-39";
    public static final String BTN_REACT_CAT_SHELTER_ABOUT = "<b>Котофеево</b>\n" +
            "Мы в ответе за тех, кого приручили!\n" +
            "Крупнейший приют для кошек на дальнем востоке.\n" +
            "Более 300 кошек на попечении!\n" +
            "✅ Лечим\n" +
            "✅ Социализируем\n" +
            "✅ Находим дом ( уже более 1000 домашних \uD83D\uDC31)\n" +
            "✅ Проводим Уроки доброты\n" +
            "✅ Организуем акции - раздачи животных\n" +
            "✅ Перенимаем опыт лучших приютов и организаций помощи животным в стране и мире";
    public static final String BTN_REACT_DOG_SHELTER_ABOUT = "<b>ПРИЮТ «АРИСТОКРАТ»</b>\n " +
            "Приморская общественная организация «Кинологический и " +
            "фелинологический клуб (ПОО КиФК ) «Аристократ»» (далее клуб «Аристократ») – это социально-ориентированная " +
            "некоммерческая организация, ведущая свою деятельность порядка 7 (семи) лет которая направлена " +
            "на формирование ответственного отношения к домашним и бездомным животным в обществе. " +
            "Клуб \"Аристократ\" зарегистрирован в качестве юридического лица 27 сентября 2013г.";
    public static final String BTN_REACT_CAT_GET_SCHEDULE_ADDRESS = "<b>Часы работы приюта:</b>\n " +
            "Пн - Пт с 9:00 до 18:00\n" +
            "Сб с 10:00 до 15:00\n" +
            "\n" +
            "Адрес: 684005, Елизовский район, г.Елизово, ул. Школьная, дом 6, кв. 15";
    public static final String BTN_REACT_DOG_GET_SCHEDULE_ADDRESS = "<b>Часы работы приюта:</b>\n " +
            "Пн - Пт с 8:00 до 21:00\n" +
            "Сб, Вс с 11:00 до 17:00\n" +
            "\n" +
            "Адрес: 695687, Анучинский район, с.Таёжка, ул.Новая, 24а";
    public static final String BTN_REACT_CAT_MAKING_PASS = "<b>Пропуск можно оформить:</b>\n" +
            "Пн - Пт с 9:00 до 18:00\n" +
            "Сб с 10:00 до 15:00\n" +
            "\n" +
            "Телефон для оформления пропуска на территорию: +7(425) 123-45-56";
    public static final String BTN_REACT_DOG_MAKING_PASS = "<b>Пропуск можно оформить:</b>\n " +
            "Пн - Пт с 8:00 до 21:00\n" +
            "Сб, Вс с 11:00 до 17:00\n" +
            "\n" +
            "Телефон для оформления пропуска на территорию:\n" +
            "+7(963) 654-43-21";
    public static final String BTN_REACT_CAT_SAFETY_RULES = "<b>Правила техники безопасности в приюте:</b>\n" +
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
    public static final String BTN_REACT_DOG_SAFETY_RULES = "<b>Правила техники безопасности в приюте:</b>\n " +
            "В приют не допускаются❌:\n" +
            "\n" +
            "- дети до 16 лет без сопровождения взрослых (родителей, опекунов, представителей с предъявленной рукописной доверенностью);\n" +
            "- лица в состоянии алкогольного или наркотического опьянения;\n" +
            "- лица в агрессивном или неадекватном состоянии.\n" +
            "- лица, не имеющие официальный документ, подтверждающие проведение необходимых антирабических прививок с действующим сроком их действия;\n" +
            "- лица, не представившие документ об отсутствии медицинских противопоказаний на посещение приюта для непродуктивных животных проявляющих немотивированную агрессию;\n" +
            "- лица с неподтвержденным статусом волонтера приюта;\n" +
            "- лица, не имеющие пропуск, утвержденный и подписанный уполномоченным представителем администрации приюта.";
    public static final String BTN_REACT_CONTACTS_MESSAGE = "<b>Дорогой друг!</b>\n" +
                                                            "Если ты хочешь, чтобы с тобой связались,\n" +
                                                            "ты можешь оставить нам свои контакты.\n" +
                                                            "Скопируй форму из сообщения ниже,\n" +
                                                            "заполни и отправь ее нам.";
    public static final String BTN_REACT_CONTACTS_FIRST_NAME = "Ведите ваше имя в сообщении ниже.";
    public static final String BTN_REACT_CONTACTS_LAST_NAME = "Ведите вашу фамилию.";
    public static final String BTN_REACT_CONTACTS_PHONE = "Ведите ваш номер телефона.\nв формате: +7(ХХХ)ХХХ-ХХ-ХХ";

}
