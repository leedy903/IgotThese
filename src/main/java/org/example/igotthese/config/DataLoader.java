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
import org.example.igotthese.data.entity.HoldingSeal;
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
public class DataLoader {

    private final initPokemonData initPokemonData;

    @PostConstruct
    public void init() {
        initPokemonData.init();
    }

    @Component
    @RequiredArgsConstructor
    static class initPokemonData {
        private final PokemonDao pokemonDao;
        private final UserDao userDao;
        private final PostDao postDao;
        private final DemandSealDao demandSealDao;
        private final SupplySealDao supplySealDao;

        @Transactional
        public void init() {
            for (Integer i = 1; i <= 100; i++) {
                String pokemonName = "pokemon" + i;
                Pokemon pokemon = Pokemon.create(pokemonName);
                pokemonDao.savePokemon(pokemon);
            }

            for (Integer i = 1; i <= 10000; i++) {
                String userName = "user" + i;
                User user = User.create(userName, new ArrayList<>());
                userDao.saveUser(user);
                List<DemandSeal> demandSeals = getDemandsSeals(getRandomPokemons());
                List<SupplySeal> supplySeals = getSupplySeals(getRandomPokemons());

                Double latitude = getRandomLatitude();
                Double longitude = getRandomLongitude();

                Post post =  Post.create(user, demandSeals, supplySeals, latitude, longitude);
                postDao.savePost(post);
                demandSeals.stream()
                        .forEach(demandSeal -> demandSealDao.saveDemandSeal(demandSeal));
                supplySeals.stream()
                        .forEach(supplySeal -> supplySealDao.saveSupplySeal(supplySeal));
            }
        }

        private Double getRandomLatitude() {
            return Math.random() * 180 - 90.0;
        }

        private Double getRandomLongitude() {
            return Math.random() * 360 - 180.0;
        }

        private List<Long> getRandomIds(int max_size, int max_id) {
            int list_size = (int) (Math.random() * (max_size) + 1);
            List<Long> randomIds = new ArrayList<>();
            while (randomIds.size() < list_size) {
                long id = (long) (Math.random() * max_id + 1);
                if (!randomIds.contains(id)) {
                    randomIds.add(id);
                }
            }
            return randomIds;
        }

        private List<Pokemon> getRandomPokemons() {
            List<Pokemon> pokemons = new ArrayList<>();
            List<Long> randomPokemonIds = getRandomIds(10, 100);

            for (Long randomPokemonId : randomPokemonIds) {
                Pokemon pokemon = pokemonDao.getPokemonById(randomPokemonId).get();
                pokemons.add(pokemon);
            }

            return pokemons;
        }

        private List<DemandSeal> getDemandsSeals(List<Pokemon> pokemons) {
            List<DemandSeal> demandSeals = new ArrayList<>();
            for (Pokemon pokemon : pokemons) {
                DemandSeal demandSeal = DemandSeal.create(pokemon);
                demandSeals.add(demandSeal);
            }
            return demandSeals;
        }

        private List<SupplySeal> getSupplySeals(List<Pokemon> pokemons) {
            List<SupplySeal> supplySeals = new ArrayList<>();
            for (Pokemon pokemon : pokemons) {
                SupplySeal supply = SupplySeal.create(pokemon);
                supplySeals.add(supply);
            }
            return supplySeals;
        }

//        @Transactional
//        public void init() {
//            List<String> pokemonNames = new ArrayList<>();
//            pokemonNames.add("이상해씨");
//            pokemonNames.add("이상해풀");
//            pokemonNames.add("이상해꽃");
//            pokemonNames.add("파이리");
//            pokemonNames.add("리자드");
//            pokemonNames.add("리자몽");
//            pokemonNames.add("꼬부기");
//            pokemonNames.add("어니부기");
//            pokemonNames.add("거북왕");
//            pokemonNames.add("캐터피");
//            pokemonNames.add("단데기");
//            pokemonNames.add("버터플");
//            pokemonNames.add("뿔충이");
//            pokemonNames.add("딱충이");
//            pokemonNames.add("독침봉");
//            pokemonNames.add("구구");
//            pokemonNames.add("피죤");
//            pokemonNames.add("피죤투");
//            pokemonNames.add("꼬렛");
//            pokemonNames.add("레트라");
//            pokemonNames.add("깨비참");
//            pokemonNames.add("깨비드릴조");
//            pokemonNames.add("아보");
//            pokemonNames.add("아보크");
//            pokemonNames.add("피카츄");
//            pokemonNames.add("라이츄");
//
//            for (String pokemonName : pokemonNames) {
//                Pokemon pokemon = Pokemon.create(pokemonName);
//                pokemonDao.savePokemon(pokemon);
//            }
//        }
    }
}
