package org.example.igotthese.data.dao;

import java.util.List;
import org.example.igotthese.data.entity.DemandSeal;

public interface DemandSealDao {
    DemandSeal saveDemandSeal(DemandSeal demandSeal);
    List<DemandSeal> getAllDemandSeal();
    List<DemandSeal> getDemandSealByPostId(Long postId);
    void deleteDemandSealById(Long id);
}
