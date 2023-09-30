package pro.sky.telegrambot.command;

import com.pengrad.telegrambot.model.Update;

public interface Commands {
    void handle (Update update);
    boolean ifSuitable (Update update);
}
