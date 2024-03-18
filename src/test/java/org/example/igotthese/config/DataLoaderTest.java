package org.example.igotthese.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.igotthese.data.dao.PokemonDao;
import org.example.igotthese.data.entity.Pokemon;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Profile("test")
@Component
@RequiredArgsConstructor
class DataLoaderTest {
    private final initPokemonTestData initPokemonTestData;

    @PostConstruct
    public void init() {
        initPokemonTestData.init();
    }

    @Component
    @RequiredArgsConstructor
    static class initPokemonTestData {
        private final PokemonDao pokemonDao;

        @Transactional
        public void init() {
            for (int i = 1; i <= 100; i++) {
                String pokemonName = "pokemon" + i;
                Pokemon pokemon = Pokemon.create(pokemonName);
                pokemonDao.savePokemon(pokemon);
            }
        }
    }
}