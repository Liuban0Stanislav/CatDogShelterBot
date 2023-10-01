package pro.sky.telegrambot.command;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Slf4j
@Component
public class StartCommand implements Commands {
    private TelegramBot bot;

    public StartCommand(TelegramBot bot) {
        this.bot = bot;
    }

    @Override
    public void handle(Update update) {
        log.info("Команда START. Метод handle");

        Long chatId = update.message().chat().id();
        log.debug("chatID = {}", chatId);

        SendMessage message = new SendMessage(chatId, "Вас приветствует бот сети приютов для животных \"CatDogShelter\"");
        log.debug("сформировано сообщение: {}", message);

        bot.execute(message);

    }

    @Override
    public boolean ifSuitable(Update update) {
        log.debug("Команда START. Метод ifSuitable");
//        if(update.message().text().equals("/start")) return true;
//        return false;
        return Optional.of(update)
                .map(Update::message)
                .map(Message::text)
                .map(text -> text.equals("/start"))
                .orElse(false);
    }
}
