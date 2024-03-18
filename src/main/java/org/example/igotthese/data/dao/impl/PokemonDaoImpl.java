package org.example.igotthese.data.dao.impl;

import java.util.Optional;
import org.example.igotthese.data.dao.PokemonDao;
import org.example.igotthese.data.entity.Pokemon;
import org.example.igotthese.data.repository.PokemonRepository;
import org.springframework.stereotype.Service;

@Service
public class PokemonDaoImpl implements PokemonDao {

    private final PokemonRepository pokemonRepository;

    public PokemonDaoImpl(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    @Override
    public Pokemon savePokemon(Pokemon pokemon) {
        return pokemonRepository.save(pokemon);
    }

    @Override
    public Optional<Pokemon> getPokemonById(Long id) {
        return pokemonRepository.findById(id);
    }

    @Override
    public Optional<Pokemon> getPokemonByPokemonName(String pokemonName) {
        return pokemonRepository.findByPokemonName(pokemonName);
    }
}
