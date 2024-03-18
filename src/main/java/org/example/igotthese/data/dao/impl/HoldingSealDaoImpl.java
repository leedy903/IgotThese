package org.example.igotthese.data.dao.impl;

import java.util.List;
import java.util.Optional;
import org.example.igotthese.data.dao.HoldingSealDao;
import org.example.igotthese.data.entity.HoldingSeal;
import org.example.igotthese.data.repository.HoldingSealRepository;
import org.springframework.stereotype.Service;

@Service
public class HoldingSealDaoImpl implements HoldingSealDao {
    private final HoldingSealRepository holdingSealRepository;

    public HoldingSealDaoImpl(HoldingSealRepository holdingSealRepository) {
        this.holdingSealRepository = holdingSealRepository;
    }

    @Override
    public HoldingSeal saveHoldingSeal(HoldingSeal holdingSeal) {
        return holdingSealRepository.save(holdingSeal);
    }

    @Override
    public Optional<HoldingSeal> getHoldingSealById(Long id) {
        return holdingSealRepository.findById(id);
    }

    @Override
    public List<HoldingSeal> getHoldingSealsByUserId(Long userId) {
        return holdingSealRepository.findAllByUserId(userId);
    }

    @Override
    public List<HoldingSeal> getHoldingSealsByPokemonId(Long pokemonId) {
        return holdingSealRepository.findAllByPokemonId(pokemonId);
    }

    @Override
    public void deleteHoldingSealById(Long id) {
        holdingSealRepository.deleteById(id);
    }

}
