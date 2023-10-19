package pro.sky.telegrambot.service;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.exception.BranchNotFoundException;
import pro.sky.telegrambot.model.BranchParams;
import pro.sky.telegrambot.repository.BranchParamsRepository;

@Service
public class BranchParamsService {
    private final BranchParamsRepository branchParamsRepository;

    public BranchParamsService(BranchParamsRepository shelterParamsRepository) {
        this.branchParamsRepository = shelterParamsRepository;
    }

    public BranchParams getBranchById(int id) {
        BranchParams branchParams = branchParamsRepository.findById(id).orElse(null);
        if (branchParams == null) {
            throw new BranchNotFoundException(id);
        }
        return branchParams;
    }

    public BranchParams createBranch(BranchParams branchParams) {
        return branchParamsRepository.save(branchParams);
    }

    public BranchParams editBranch(BranchParams branchParams) {
        if (branchParamsRepository.findById(branchParams.getId()).orElse(null) == null) {
            return null;
        }
        return branchParamsRepository.save(branchParams);
    }
}
