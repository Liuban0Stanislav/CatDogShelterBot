package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.model.Adopter;

@Repository
public interface AdopterRepository extends JpaRepository<Adopter,Long> {
}
