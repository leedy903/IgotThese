package org.example.igotthese.config;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.igotthese.data.dao.DemandSealDao;
import org.example.igotthese.data.dao.PokemonDao;
import org.example.igotthese.data.dao.PostDao;
import org.example.igotthese.data.dao.SupplySealDao;
import org.example.igotthese.data.dao.UserDao;
import org.example.igotthese.data.entity.DemandSeal;
import org.example.igotthese.data.entity.Pokemon;
import org.example.igotthese.data.entity.Post;
import org.example.igotthese.data.entity.SupplySeal;
import org.example.igotthese.data.entity.User;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Profile("local")
@Component
@RequiredArgsConstructor
public class PokemonDataLoader {

    private final initPokemonData initPokemonData;

    @PostConstruct
    public void init() {
        initPokemonData.init();
    }

    @Component
    @RequiredArgsConstructor
    static class initPokemonData {
        private final PokemonDao pokemonDao;

        @Transactional
        public void init() {
            List<String> pokemonNames = new ArrayList<>();
            pokemonNames.add("이상해씨");
            pokemonNames.add("이상해풀");
            pokemonNames.add("이상해꽃");
            pokemonNames.add("파이리");
            pokemonNames.add("리자드");
            pokemonNames.add("리자몽");
            pokemonNames.add("꼬부기");
            pokemonNames.add("어니부기");
            pokemonNames.add("거북왕");
            pokemonNames.add("캐터피");
            pokemonNames.add("단데기");
            pokemonNames.add("버터플");
            pokemonNames.add("뿔충이");
            pokemonNames.add("딱충이");
            pokemonNames.add("독침봉");
            pokemonNames.add("구구");
            pokemonNames.add("피죤");
            pokemonNames.add("피죤투");
            pokemonNames.add("꼬렛");
            pokemonNames.add("레트라");
            pokemonNames.add("깨비참");
            pokemonNames.add("깨비드릴조");
            pokemonNames.add("아보");
            pokemonNames.add("아보크");
            pokemonNames.add("피카츄");
            pokemonNames.add("라이츄");

            for (String pokemonName : pokemonNames) {
                Pokemon pokemon = Pokemon.create(pokemonName);
                pokemonDao.savePokemon(pokemon);
            }
        }
    }
}
