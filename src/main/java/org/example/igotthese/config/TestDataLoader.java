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

@Profile("load_test")
@Component
@RequiredArgsConstructor
public class TestDataLoader {

    private final initData initData;

    @PostConstruct
    public void init() {
        initData.init();
    }

    @Component
    @RequiredArgsConstructor
    static class initData {
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
    }
}
