package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.model.Volunteer;

import java.util.Optional;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
    @Query(value = "SELECT * FROM volunteers ORDER BY random() LIMIT 1", nativeQuery = true)
    public Optional<Volunteer> getRandomVolunteer();
}
