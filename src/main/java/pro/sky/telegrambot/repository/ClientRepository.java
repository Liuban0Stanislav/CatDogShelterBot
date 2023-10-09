package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {
}
