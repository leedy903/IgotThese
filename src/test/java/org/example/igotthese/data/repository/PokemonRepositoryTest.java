package org.example.igotthese.data.repository;

import static org.assertj.core.api.Assertions.*;
import static org.example.igotthese.common.exhandler.ErrorCode.POKEMON_NOT_FOUND;

import org.example.igotthese.common.exception.NoSuchDataException;
import org.example.igotthese.common.exhandler.ErrorCode;
import org.example.igotthese.data.dto.PokemonDto;
import org.example.igotthese.data.entity.Pokemon;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Commit
class PokemonRepositoryTest {

    @Autowired PokemonRepository pokemonRepository;

    @Test
    public void testCreatePokemon() throws Exception {
    	//given
        String pokemonName = "pokemon1";
        Pokemon pokemon = Pokemon.create(pokemonName);

        //when
        Pokemon savedPokemon = pokemonRepository.save(pokemon);

        //then
        assertThat(pokemon).isEqualTo(savedPokemon);
    }

    @Test
    public void testSearchPokemon() throws Exception {
    	//given
        String pokemonName = "pokemon1";
        Pokemon pokemon = Pokemon.create(pokemonName);
        Pokemon savedPokemon = pokemonRepository.save(pokemon);

    	//when
        Pokemon searchedPokemon = pokemonRepository.findByPokemonName(pokemonName)
                .orElseThrow(() -> new NoSuchDataException(POKEMON_NOT_FOUND));

        //then
        assertThat(savedPokemon).isEqualTo(searchedPokemon);
    }
}