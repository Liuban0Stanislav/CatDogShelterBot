package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.model.Adopter;
import pro.sky.telegrambot.model.AdoptionReport;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AdoptionReportRepository extends JpaRepository<AdoptionReport, Long> {

    public AdoptionReport findAdoptionReportByAdopterIdAndReportDate(Adopter adopter, LocalDate date);

    public List<AdoptionReport> findAdoptionReportsByAdopterId(Adopter adopter);
}
