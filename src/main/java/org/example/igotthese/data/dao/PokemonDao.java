package org.example.igotthese.data.dao;

import java.util.Optional;
import org.example.igotthese.data.entity.Pokemon;

public interface PokemonDao {
    Pokemon savePokemon(Pokemon pokemon);
    Optional<Pokemon> getPokemonById(Long id);
    Optional<Pokemon> getPokemonByPokemonName(String pokemonName);
}
