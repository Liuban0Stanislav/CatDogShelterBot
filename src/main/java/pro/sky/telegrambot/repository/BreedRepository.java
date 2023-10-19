package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.model.Breed;


@Repository
public interface BreedRepository extends JpaRepository<Breed, Long> {

}