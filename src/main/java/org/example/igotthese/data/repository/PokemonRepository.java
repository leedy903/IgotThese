package org.example.igotthese.data.repository;

import java.util.Optional;
import org.example.igotthese.data.entity.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {
    Optional<Pokemon> findByPokemonName(String pokemonName);
}
