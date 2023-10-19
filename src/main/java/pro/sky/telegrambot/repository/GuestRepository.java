package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.model.Guest;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
    public Guest findByChatId(long chatId);
}
