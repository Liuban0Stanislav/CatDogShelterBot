package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.constants.AdopterStatus;
import pro.sky.telegrambot.model.Adopter;

import java.util.List;

public interface AdopterRepository extends JpaRepository<Adopter, Long> {
    public Adopter findByChatId(long chatId);

    List<Adopter> findAdoptersByStatus(AdopterStatus status);
}
