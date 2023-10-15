package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.model.Animal;

@Repository
public interface AnimalRepository extends JpaRepository<Animal,Long> {
}
