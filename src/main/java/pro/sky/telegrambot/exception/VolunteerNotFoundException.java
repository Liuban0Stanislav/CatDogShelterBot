package pro.sky.telegrambot.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.sky.telegrambot.listener.TelegramBotUpdatesListener;

public class VolunteerNotFoundException extends RuntimeException {

    private int id;
    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    public VolunteerNotFoundException() {
        super();
    }

    public VolunteerNotFoundException(String message) {
        super(message);
        logger.error(message);
    }

    public VolunteerNotFoundException(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
