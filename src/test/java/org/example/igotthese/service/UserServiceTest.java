package org.example.igotthese.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.example.igotthese.common.exception.DataDuplicationException;
import org.example.igotthese.common.exception.NoSuchDataException;
import org.example.igotthese.data.dto.PokemonDto;
import org.example.igotthese.data.dto.PokemonDto.Search;
import org.example.igotthese.data.dto.UserDto.Create;
import org.example.igotthese.data.dto.UserDto.Response;
import org.example.igotthese.data.dto.UserDto.Update;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
@Transactional
@Commit
class UserServiceTest {

    @Autowired
    UserService userService;

    private final int USER_SIZE = 10;
    private final int POST_SIZE = 10;
    private final int POKEMON_SIZE = 100;
    private final int MAX_SEALS_SIZE = 10;

    @AfterEach
    public void afterEach() {
        List<Response> allUser = userService.getAllUser();
        for (Response user : allUser) {
            userService.deleteUserById(user.getUserId());
        }
    }

    @Test
    public void testCreateUser() throws Exception {
        //given
        String userName = "user";
        Create user = Create.builder()
                .userName(userName)
                .build();

        //when
        Long userId = userService.saveUser(user);
        Response userById = userService.getUserById(userId);

        //then
        assertThat(userById.getUserId()).isEqualTo(userId);
        assertThat(userById.getUserName()).isEqualTo(userName);
    }

    @Test
    public void testCreateUserWithEmptyHoldingSeals() throws Exception {
        //given
        String userName = "user";
        List<PokemonDto.Search> holdingSeals = new ArrayList<>();
        Create user = Create.builder()
                .userName(userName)
                .holdingSeals(holdingSeals)
                .build();

        //when
        Long userId = userService.saveUser(user);
        Response userById = userService.getUserById(userId);

        //then
        assertThat(userById.getUserId()).isEqualTo(userId);
        assertThat(userById.getUserName()).isEqualTo(userName);
    }

    @Test
    public void testCreateUserWithHoldingSeals() throws Exception {
        //given
        List<PokemonDto.Search> holdingSeals = new ArrayList<>();
        Search pokemon1 = Search.builder()
                .pokemonId(1L)
                .pokemonName("pokemon1")
                .build();
        holdingSeals.add(pokemon1);

        Search pokemon2 = Search.builder()
                .pokemonId(2L)
                .pokemonName("pokemon2")
                .build();
        holdingSeals.add(pokemon2);

        String userName = "user";
        Create user = Create.builder()
                .userName(userName)
                .holdingSeals(holdingSeals)
                .build();

        //when
        Long userId = userService.saveUser(user);
        Response foundUser = userService.getUserById(userId);

        //then
        assertThat(foundUser.getUserId()).isEqualTo(userId);
        assertThat(foundUser.getUserName()).isEqualTo(userName);
        assertThat(foundUser.getHoldingSeals().size()).isEqualTo(2);
        
    }

    @Test
    public void testUserSearch() throws Exception {
        //given
        String userName = "user";
        Create user = Create.builder()
                .userName(userName)
                .build();
        Long userId = userService.saveUser(user);

        //when
        Response searchedUser = userService.getUserById(userId);

        //then
        assertThat(searchedUser.getUserId()).isEqualTo(userId);
        assertThat(searchedUser.getUserName()).isEqualTo("user");
    }

    @Test
    public void testUserDoesntExist() throws Exception {
        assertThrows(NoSuchDataException.class, () ->
                userService.getUserByUserName("NOT_FOUND_USER")
        );
    }

    @Test
    public void testUserSearchWithHoldingSeals() throws Exception {
        //given
        String userName = "user";
        List<Search> holdingSeals = getRandomPokemonSearches();
        Create user = Create.builder()
                .userName(userName)
                .holdingSeals(holdingSeals)
                .build();

        Long userId = userService.saveUser(user);

        //when
        Response searchedUser = userService.getUserById(userId);

        //then
        List<String> holdingSealsPokemonNames = holdingSeals.stream()
                .map(search -> search.getPokemonName())
                .collect(Collectors.toList());

        List<String> searchedPokemonNames = searchedUser.getHoldingSeals().stream()
                .map(holdingSealResponse -> holdingSealResponse.getPokemonName())
                .collect(Collectors.toList());

        assertThat(searchedUser.getUserId()).isEqualTo(userId);
        assertThat(searchedUser.getUserName()).isEqualTo(userName);
        assertThat(searchedUser.getHoldingSeals().size()).isEqualTo(holdingSeals.size());
        assertThat(searchedPokemonNames).isEqualTo(holdingSealsPokemonNames);
    }


    @Test
    public void testDuplicateUserNameCreate() throws Exception {
        //given
        String userName = "user";
        Create user = Create.builder()
                .userName(userName)
                .build();

        //when
        userService.saveUser(user);

        //then
        assertThrows(DataDuplicationException.class, () -> {
            userService.saveUser(user);
        });
    }

    @Test
    public void testUpdateUserName() throws Exception {
        //given
        String userName = "user";
        Create user = Create.builder()
                .userName(userName)
                .build();

        Long savedUserId = userService.saveUser(user);

        //when
        String newUserName = "newUserName";
        Update updatedUser = Update.builder()
                .userId(savedUserId)
                .userName(newUserName)
                .build();

        Long updatedUserId = userService.updateUser(updatedUser);
        Response userById = userService.getUserById(updatedUserId);

        //then
        assertThat(updatedUserId).isEqualTo(savedUserId);
        assertThat(userById.getUserName()).isEqualTo(newUserName);
    }

    @Test
    public void testUpdateHoldingSeals() throws Exception {
        //given
        String userName = "user";
        List<Search> pokemons = getRandomPokemonSearches();
        Create user = Create.builder()
                .userName(userName)
                .holdingSeals(pokemons)
                .build();

        Long savedUserId = userService.saveUser(user);

        //when
        List<Search> newPokemons = getRandomPokemonSearches();
        List<String> newPokemonNames = newPokemons.stream()
                .map(search -> search.getPokemonName())
                .collect(Collectors.toList());

        Update updateUser = Update.builder()
                .userId(savedUserId)
                .userName(userName)
                .holdingSeals(newPokemons)
                .build();

        Long updatedUserId = userService.updateUser(updateUser);

        //then
        Response updatedUser = userService.getUserById(updatedUserId);
        List<String> updatedPokemonNames = updatedUser.getHoldingSeals().stream()
                .map(holdingSealResponse -> holdingSealResponse.getPokemonName())
                .collect(Collectors.toList());

        updatedPokemonNames.stream()
                        .forEach(updatedPokemonName -> log.info("updatedPokemonName={}", updatedPokemonName));

        assertThat(updatedUserId).isEqualTo(savedUserId);
        assertThat(updatedPokemonNames).isEqualTo(newPokemonNames);
    }

    @Test
    public void testDeleteUser() throws Exception {
    	//given
        String userName = "user";
        Create user = Create.builder()
                .userName(userName)
                .build();

        Long savedUserId = userService.saveUser(user);

    	//when
        userService.deleteUserById(savedUserId);

    	//then
        assertThrows(NoSuchDataException.class, () ->
            userService.getUserById(savedUserId)
        );
    }

    @Test
    public void testDeleteDoesntExistUser() throws Exception {
        //given

        //when
        Long userId = 1L;
        userService.deleteUserById(userId);

        List<Response> allUser = userService.getAllUser();

        //then
        assertThat(allUser.size()).isEqualTo(0);
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

    private List<PokemonDto.Search> getRandomPokemonSearches() {
        List<PokemonDto.Search> pokemonSearches = new ArrayList<>();
        List<Long> randomPokemonIds = getRandomIds(MAX_SEALS_SIZE, POKEMON_SIZE);

        for (Long randomPokemonId : randomPokemonIds) {
            Search pokemon = Search.builder()
                    .pokemonId(randomPokemonId)
                    .pokemonName("pokemon" + randomPokemonId)
                    .build();
            pokemonSearches.add(pokemon);
        }

        return pokemonSearches;
    }

}