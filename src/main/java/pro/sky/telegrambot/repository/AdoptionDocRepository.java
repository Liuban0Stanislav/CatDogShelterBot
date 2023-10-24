package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.constants.DocType;
import pro.sky.telegrambot.model.AdoptionDoc;

@Repository
public interface AdoptionDocRepository extends JpaRepository<AdoptionDoc, DocType> {
}
