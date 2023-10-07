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
    public static final String MESSAGE_PET_LEADING_MENU = "меню 2.2: Консультация потенциального хозяина животного из приюта";
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
     * другие сообщения
     */
    public static final String SOMETHING_WENT_WRONG = "Что-то пошло не так";
}
