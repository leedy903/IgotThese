package org.example.igotthese.data.repository;

import java.util.List;
import org.example.igotthese.data.entity.HoldingSeal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HoldingSealRepository extends JpaRepository<HoldingSeal, Long> {
    List<HoldingSeal> findAllByUserId(Long userId);
    List<HoldingSeal> findAllByPokemonId(Long pokemonId);
}