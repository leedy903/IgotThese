package org.example.igotthese.data.dao;

import java.util.List;
import java.util.Optional;
import org.example.igotthese.data.entity.HoldingSeal;

public interface HoldingSealDao {
    HoldingSeal saveHoldingSeal(HoldingSeal holdingSeal);
    Optional<HoldingSeal> getHoldingSealById(Long id);
    List<HoldingSeal> getHoldingSealsByUserId(Long userId);
    List<HoldingSeal> getHoldingSealsByPokemonId(Long pokemonId);
    void deleteHoldingSealById(Long id);
}
