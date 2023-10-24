package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.constants.PetType;
import pro.sky.telegrambot.model.BranchParams;

import java.util.Optional;

@Repository
public interface BranchParamsRepository extends JpaRepository<BranchParams, Integer> {

     Optional<BranchParams> findByPetType(PetType petType);
}
