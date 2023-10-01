package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.command.Commands;
import pro.sky.telegrambot.command.StartCommand;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    @Autowired
    private TelegramBot telegramBot;

    private final List<Commands> commands;

    public TelegramBotUpdatesListener(List<Commands> commands) {
        this.commands = commands;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);

                commands.stream()
                        .filter(command -> command.ifSuitable(update))
                        .forEach(command -> command.handle(update));

//                switch (update.message().text()) {
//                    case "/start":
//
//                        break;
//
//                }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}