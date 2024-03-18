package org.example.igotthese.service;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.assertj.core.api.Assertions;
import org.example.igotthese.data.dto.BookDto;
import org.example.igotthese.data.dto.BookDto.Ranker;
import org.example.igotthese.data.dto.PokemonDto;
import org.example.igotthese.data.dto.PokemonDto.HoldingSealResponse;
import org.example.igotthese.data.dto.PokemonDto.Search;
import org.example.igotthese.data.dto.UserDto.Create;
import org.example.igotthese.data.dto.UserDto.Response;
import org.example.igotthese.data.dto.UserDto.Update;
import org.example.igotthese.data.entity.Pokemon;
import org.example.igotthese.data.repository.PokemonRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Commit
class BookServiceTest {

    @Autowired UserService userService;
    @Autowired BookService bookService;
    @Autowired PokemonRepository pokemonRepository;

    private final int USER_SIZE = 20;
    private final int POKEMON_SIZE = 100;
    private final int MAX_SEALS_SIZE = 10;

    @BeforeEach
    public void setUp() {
        for (int i = 1; i <= USER_SIZE; i++) {
            String userName = "user" + i;
            List<Search> randomHoldingSeals = getRandomHoldingSeals();
            Create user = Create.builder()
                    .userName(userName)
                    .holdingSeals(randomHoldingSeals)
                    .build();
            userService.saveUser(user);
        }
    }

    @AfterEach
    public void afterEach() {
        List<Response> allUser = userService.getAllUser();
        for (Response user : allUser) {
            userService.deleteUserById(user.getUserId());
        }
    }

    @Test
    public void testGetBookByUserId() throws Exception {
    	//given
        Update user = Update.builder()
                .userId(1L)
                .userName("user1")
                .holdingSeals(getMaxHoldingSeals())
                .build();
        Long userId = userService.updateUser(user);

        //when
        BookDto.Response bookByUser = bookService.getBookByUserId(userId);

    	//then
        List<String> userBookPokemonNames = bookByUser.getMyBook().getHoldingSeals().stream()
                .map(holdingSealResponse -> holdingSealResponse.getPokemonName())
                .collect(Collectors.toList());
        List<String> userHoldingSeals = user.getHoldingSeals().stream()
                .map(holdingSeal -> holdingSeal.getPokemonName())
                .collect(Collectors.toList());

        Ranker ranker = bookByUser.getRankers().get(0);

        assertThat(userBookPokemonNames).isEqualTo(userHoldingSeals);
        assertThat(ranker.getUserId()).isEqualTo(user.getUserId());
        assertThat(ranker.getUserName()).isEqualTo(user.getUserName());
        assertThat(ranker.getCount()).isEqualTo(user.getHoldingSeals().size());
    }

    private List<PokemonDto.Search> getMaxHoldingSeals() {
        List<Pokemon> pokemons = getMaxPokemons();
        List<Search> holdingSeals = pokemons.stream()
                .map(pokemon -> new Search(pokemon))
                .collect(Collectors.toList());
        return holdingSeals;
    }

    private List<PokemonDto.Search> getRandomHoldingSeals() {
        List<Pokemon> pokemons = getRandomPokemons();
        List<Search> holdingSeals = pokemons.stream()
                .map(pokemon -> new Search(pokemon))
                .collect(Collectors.toList());
        return holdingSeals;
    }

    private List<Pokemon> getMaxPokemons() {
        List<Pokemon> pokemons = new ArrayList<>();
        for (Long pokemonId = 1L; pokemonId <= 100L; pokemonId++) {
            Pokemon pokemon = pokemonRepository.findById(pokemonId).get();
            pokemons.add(pokemon);
        }
        return pokemons;
    }

    private List<Pokemon> getRandomPokemons() {
        List<Pokemon> pokemons = new ArrayList<>();
        List<Long> randomPokemonIds = getRandomIds(MAX_SEALS_SIZE, POKEMON_SIZE);

        for (Long randomPokemonId : randomPokemonIds) {
            Pokemon pokemon = pokemonRepository.findById(randomPokemonId).get();
            pokemons.add(pokemon);
        }

        return pokemons;
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

}