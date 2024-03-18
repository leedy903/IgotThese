package org.example.igotthese.data.dao.impl;

import java.util.List;
import org.example.igotthese.data.dao.DemandSealDao;
import org.example.igotthese.data.entity.DemandSeal;
import org.example.igotthese.data.repository.DemandSealRepository;
import org.springframework.stereotype.Service;

@Service
public class DemandSealDaoImpl implements DemandSealDao {

    private final DemandSealRepository demandSealRepository;

    public DemandSealDaoImpl(DemandSealRepository demandSealRepository) {
        this.demandSealRepository = demandSealRepository;
    }

    @Override
    public DemandSeal saveDemandSeal(DemandSeal demandSeal) {
        return demandSealRepository.save(demandSeal);
    }

    @Override
    public List<DemandSeal> getAllDemandSeal() {
        return demandSealRepository.findAll();
    }

    @Override
    public List<DemandSeal> getDemandSealByPostId(Long postId) {
        return demandSealRepository.findAllByPostId(postId);
    }

    @Override
    public void deleteDemandSealById(Long id) {
        demandSealRepository.deleteById(id);
    }
}
