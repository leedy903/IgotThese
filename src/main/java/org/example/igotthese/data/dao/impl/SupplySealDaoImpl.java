package org.example.igotthese.data.dao.impl;

import java.util.List;
import org.example.igotthese.data.dao.SupplySealDao;
import org.example.igotthese.data.entity.SupplySeal;
import org.example.igotthese.data.repository.SupplySealRepository;
import org.springframework.stereotype.Service;

@Service
public class SupplySealDaoImpl implements SupplySealDao {

    private final SupplySealRepository supplySealRepository;

    public SupplySealDaoImpl(SupplySealRepository supplySealRepository) {
        this.supplySealRepository = supplySealRepository;
    }

    @Override
    public SupplySeal saveSupplySeal(SupplySeal supplySeal) {
         return supplySealRepository.save(supplySeal);
    }

    @Override
    public List<SupplySeal> getAllSupplySeal() {
        return supplySealRepository.findAll();
    }

    @Override
    public List<SupplySeal> getSupplySealByPostId(Long postId) {
        return supplySealRepository.findAllByPostId(postId);
    }

    @Override
    public void deleteSupplySealById(Long id) {
        supplySealRepository.deleteById(id);
    }
}
