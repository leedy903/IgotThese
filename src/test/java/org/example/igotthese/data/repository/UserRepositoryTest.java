package org.example.igotthese.data.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.example.igotthese.common.exception.NoSuchDataException;
import org.example.igotthese.data.entity.HoldingSeal;
import org.example.igotthese.data.entity.Pokemon;
import org.example.igotthese.data.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Commit
class UserRepositoryTest {

    @Autowired UserRepository userRepository;
    @Autowired PokemonRepository pokemonRepository;
    @Autowired HoldingSealRepository holdingSealRepository;

    @Test
    public void testUserSaveNoHolidingSeals() throws Exception {
        //given
        List<HoldingSeal> holdingSeals = new ArrayList<>();
        User user = User.create("user", holdingSeals);
        User savedUser = userRepository.save(user);

        //when
        User findUser = userRepository.findById(savedUser.getId()).get();

        //then
        assertThat(findUser.getId()).isEqualTo(user.getId());
        assertThat(findUser.getUserName()).isEqualTo(user.getUserName());
        assertThat(findUser.getHoldingSeals()).isEqualTo(user.getHoldingSeals());
        assertThat(findUser).isEqualTo(user);
    }

    @Test
    public void testUserSave() throws Exception {
    	//given
        Pokemon pokemon = pokemonRepository.findByPokemonName("pokemon1").get();
        HoldingSeal holdingSeal = HoldingSeal.create(pokemon);
        List<HoldingSeal> holdingSeals = new ArrayList<>();
        holdingSeals.add(holdingSeal);
        User user = User.create("user1", holdingSeals);
        User savedUser = userRepository.save(user);

    	//when
        User findUser = userRepository.findById(savedUser.getId()).get();

    	//then
        assertThat(findUser.getId()).isEqualTo(user.getId());
        assertThat(findUser.getUserName()).isEqualTo(user.getUserName());
        assertThat(findUser.getHoldingSeals()).isEqualTo(user.getHoldingSeals());
        assertThat(findUser).isEqualTo(user);
    }

    @Test
    public void testUserFindByUserName() throws Exception {
    	//given
        Pokemon pokemon = pokemonRepository.findByPokemonName("pokemon1").get();
        HoldingSeal holdingSeal = HoldingSeal.create(pokemon);
        List<HoldingSeal> holdingSeals = new ArrayList<>();
        holdingSeals.add(holdingSeal);
        String userName = "user1";
        User user = User.create(userName, holdingSeals);
        User savedUser = userRepository.save(user);

    	//when
        User foundUser = userRepository.findByUserName(userName).get();

        //then
        assertThat(foundUser).isEqualTo(savedUser);
    }

    @Test
    public void testDeleteUser() throws Exception {
    	//given
    	Long userId = 1L;

    	//when
        userRepository.deleteById(userId);

    	//then
        Optional<User> user = userRepository.findById(userId);
        assertThat(user.isEmpty()).isEqualTo(true);

    }
}