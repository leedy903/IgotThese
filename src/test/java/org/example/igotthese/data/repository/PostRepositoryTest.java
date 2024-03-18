package org.example.igotthese.data.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.example.igotthese.data.dto.PokemonDto;
import org.example.igotthese.data.dto.PostDto.Response;
import org.example.igotthese.data.dto.PostDto.Search;
import org.example.igotthese.data.entity.DemandSeal;
import org.example.igotthese.data.entity.HoldingSeal;
import org.example.igotthese.data.entity.Pokemon;
import org.example.igotthese.data.entity.Post;
import org.example.igotthese.data.entity.SupplySeal;
import org.example.igotthese.data.entity.User;
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
class PostRepositoryTest {
    @Autowired UserRepository userRepository;
    @Autowired PostRepository postRepository;
    @Autowired PokemonRepository pokemonRepository;
    @Autowired HoldingSealRepository holdingSealRepository;

    private final int USER_SIZE = 10;
    private final int POST_SIZE = 10;
    private final int POKEMON_SIZE = 100;
    private final int MAX_SEALS_SIZE = 10;

    @BeforeEach
    public void setUp() {
        for (int i = 1; i <= USER_SIZE; i++) {
            String userName = "user" + i;
            setUserHavingRandomHoldingSeals(userName);
        }
    }

    @AfterEach
    public void afterEach() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.getPost() != null) {
                postRepository.deleteById(user.getPost().getId());
                user.setPost(null);
            }
        }
    }

    @Test
    public void testPostSave() throws Exception {
    	//given
        User user1 = userRepository.findByUserName("user1").get();

        List<DemandSeal> demandSeals = getDemandsSeals(getPokemonsBySequence(6, 8));
        List<SupplySeal> supplySeals = getSupplySeals(getPokemonsBySequence(2, 5));

        Double latitude = 1.1;
        Double longitude = 2.2;

        //when
        Post post = Post.create(user1, demandSeals, supplySeals, latitude, longitude);
        Post savedPost = postRepository.save(post);

    	//then
        assertThat(post.getId()).isEqualTo(savedPost.getId());
    }

    @Test
    public void testRandomPokemonPostSave() throws Exception {
        //given
        User user1 = userRepository.findByUserName("user1").get();

        //when
        Post post = createRandomPostByUser(user1);
        Post savedPost = postRepository.save(post);

        //then
        assertThat(post.getId()).isEqualTo(savedPost.getId());
    }

    @Test
    public void testEveryUserCreateRandomPostAndFindAll() throws Exception {
        //given
        List<User> users = userRepository.findAll();

        for (User user : users) {
            Post post = createRandomPostByUser(user);
            postRepository.save(post);
        }

        //when
        List<Post> searchedPosts = postRepository.findAll();

        //then 개수 확인
        assertThat(searchedPosts.size()).isEqualTo(users.size());
    }

    @Test
    public void testPostFind() throws Exception {
        //given
        User user1 = userRepository.findByUserName("user1").get();

        List<DemandSeal> demandSeals = getDemandsSeals(getPokemonsBySequence(6, 8));
        List<SupplySeal> supplySeals = getSupplySeals(getPokemonsBySequence(2, 5));

        Double latitude = 1.1;
        Double longitude = 2.2;

        Post post = Post.create(user1, demandSeals, supplySeals, latitude, longitude);
        Post savedPost = postRepository.save(post);

        //when
        Long searchPokemonId = 3L;
        Pokemon searchPokemon = pokemonRepository.findById(searchPokemonId).get();
        List<Post> postsByPokemonId = postRepository.findPostsByPokemonId(searchPokemonId);

        //then
        for (Post postByPokemonId : postsByPokemonId) {
            List<SupplySeal> supplySealsByPokemonId = postByPokemonId.getSupplySeals();
            List<Pokemon> searchedPokemons = supplySealsByPokemonId.stream()
                    .map(supplySeal -> supplySeal.getPokemon())
                    .collect(Collectors.toList());
            assertThat(searchedPokemons).contains(searchPokemon);
        }
    }

    @Test
    public void testPostSearch() throws Exception {
        //given
        User user1 = userRepository.findByUserName("user1").get();

        List<DemandSeal> demandSeals = getDemandsSeals(getPokemonsBySequence(6, 8));
        List<SupplySeal> supplySeals = getSupplySeals(getPokemonsBySequence(2, 5));

        Double latitude = 1.1;
        Double longitude = 2.2;

        Post post = Post.create(user1, demandSeals, supplySeals, latitude, longitude);
        Post savedPost = postRepository.save(post);

        //when
        Pokemon pokemon = pokemonRepository.findById(3L).get();
        PokemonDto.Search pokemonSearch = PokemonDto.Search.builder()
                .pokemonId(pokemon.getId())
                .pokemonName(pokemon.getPokemonName())
                .build();

        Search postSearch = Search.builder()
                .pokemonSearch(pokemonSearch)
                .latitude(1.1)
                .longitude(2.2)
                .build();
        List<Response> searchedPosts = postRepository.search(postSearch);

        //then
        for (Response searchedPost : searchedPosts) {
            List<PokemonDto.SupplySealResponse> searchedSupplySeals = searchedPost.getSupplySeals();
            List<Long> searchedPokemonIds = searchedSupplySeals.stream()
                    .map(searchedSupplySeal -> searchedSupplySeal.getPokemonId())
                    .collect(Collectors.toList());
            assertThat(searchedPokemonIds).contains(3L);
        }
    }
    
    @Test
    public void testPostUpdate() throws Exception {
        //given
        User user1 = userRepository.findByUserName("user1").get();
        Post post = createRandomPostByUser(user1);
        Post savedPost = postRepository.save(post);

        //when
        List<Pokemon> newPokemons1 = getPokemonsBySequence(6, 8);
        List<Pokemon> newPokemons2 = getPokemonsBySequence(2, 5);

        List<DemandSeal> demandSeals = getDemandsSeals(newPokemons1);
        List<SupplySeal> supplySeals = getSupplySeals(newPokemons2);

        Double newLatitude = 1.1;
        Double newLongitude = 2.2;

        savedPost.change(demandSeals, supplySeals, newLatitude, newLongitude);

        //then
        System.out.println("savedPost = " + savedPost);
        List<DemandSeal> updatedDemandSeals = savedPost.getDemandSeals();
        List<Pokemon> updatedPokemons1 = updatedDemandSeals.stream()
                .map(demandSeal -> demandSeal.getPokemon())
                .collect(Collectors.toList());
        assertThat(updatedPokemons1).isEqualTo(newPokemons1);

        List<SupplySeal> updatedSupplySeals = savedPost.getSupplySeals();
        List<Pokemon> updatedPokemons2 = updatedSupplySeals.stream()
                .map(supplySeal -> supplySeal.getPokemon())
                .collect(Collectors.toList());
        assertThat(updatedPokemons2).isEqualTo(newPokemons2);
    }

    @Test
    public void testPostUpdate2Times() throws Exception {
        //given
        User user1 = userRepository.findByUserName("user1").get();
        Post post = createRandomPostByUser(user1);
        Post savedPost = postRepository.save(post);

        //when

        List<Pokemon> newPokemons1 = getPokemonsBySequence(3, 7);
        List<Pokemon> newPokemons2 = getPokemonsBySequence(1, 4);

        List<DemandSeal> demandSeals = getDemandsSeals(newPokemons1);
        List<SupplySeal> supplySeals = getSupplySeals(newPokemons2);

        Double newLatitude = 1.1;
        Double newLongitude = 2.2;

        savedPost.change(demandSeals, supplySeals, newLatitude, newLongitude);

        newPokemons1 = getPokemonsBySequence(6, 8);
        newPokemons2 = getPokemonsBySequence(2, 5);

        demandSeals = getDemandsSeals(newPokemons1);
        supplySeals = getSupplySeals(newPokemons2);

        newLatitude = 1.12;
        newLongitude = 2.23;

        savedPost.change(demandSeals, supplySeals, newLatitude, newLongitude);

        //then
        System.out.println("savedPost = " + savedPost);
        List<DemandSeal> updatedDemandSeals = savedPost.getDemandSeals();
        List<Pokemon> updatedPokemons1 = updatedDemandSeals.stream()
                .map(demandSeal -> demandSeal.getPokemon())
                .collect(Collectors.toList());
        assertThat(updatedPokemons1).isEqualTo(newPokemons1);

        List<SupplySeal> updatedSupplySeals = savedPost.getSupplySeals();
        List<Pokemon> updatedPokemons2 = updatedSupplySeals.stream()
                .map(supplySeal -> supplySeal.getPokemon())
                .collect(Collectors.toList());
        assertThat(updatedPokemons2).isEqualTo(newPokemons2);
    }

    @Test
    public void testPostSearch2() throws Exception {
    	//given
        List<User> users = userRepository.findAll();

        //when
        for (User user : users) {
            Post post = createRandomPostByUser(user);
            postRepository.save(post);
        }

    	//then
        for (long i = 1L; i < 11L; i++) {
            Long pokemonId = i;
            String randomPoint = getRandomPoint();
            List<Post> postBySearches = postRepository.findPostBySearch(pokemonId, randomPoint);

            if (postBySearches.size() < 2) continue;
            System.out.println("postBySearches.size() = " + postBySearches.size());
            System.out.println("postBySearches = " + postBySearches);
            for (Post postBySearch : postBySearches) {

                System.out.println("postBySearch = " + postBySearch);
            }
        }


    }


    private User createUserByUserName(String userName) {
        User user = User.create(userName, new ArrayList<>());
        return userRepository.save(user);
    }

    private void setUserHavingHoldingSeals(String userName, int from, int to) {
        Optional<User> userOptional = userRepository.findByUserName(userName);
        if (userOptional.isEmpty()) {
            User user = createUserByUserName(userName);
            List<HoldingSeal> holdingSeals = getHoldingSeals(getPokemonsBySequence(from, to));
            user.changeHoldingSeals(holdingSeals);
        }
    }

    private void setUserHavingRandomHoldingSeals(String userName) {
        Optional<User> userOptional = userRepository.findByUserName(userName);
        if (userOptional.isEmpty()) {
            User user = createUserByUserName(userName);
            List<HoldingSeal> holdingSeals = getHoldingSeals(getRandomPokemons());
            user.changeHoldingSeals(holdingSeals);
        }
    }

    private Post createRandomPostByUser(User user) {
        if (user.getPost() != null) {
            return postRepository.findById(user.getPost().getId()).get();
        }
        List<DemandSeal> demandSeals = getDemandsSeals(getRandomPokemons());
        List<SupplySeal> supplySeals = getSupplySeals(getRandomPokemons());

        Double latitude = getRandomLatitude();
        Double longitude = getRandomLongitude();

        return Post.create(user, demandSeals, supplySeals, latitude, longitude);
    }

    private Double getRandomLatitude() {
        return Math.random() * 180 - 90.0;
    }

    private Double getRandomLongitude() {
        return Math.random() * 360 - 180.0;
    }

    private String getPoint(Double latitude, Double longitude) {
        return String.format("POINT(%s %s)", latitude, longitude);
    }

    private String getRandomPoint() {
        Double latitude = getRandomLatitude();
        Double longitude = getRandomLongitude();
        return String.format("POINT(%s %s)", latitude, longitude);
    }

    private List<Pokemon> getPokemonsBySequence(int from, int to) {
        List<Pokemon> pokemons = new ArrayList<>();
        for (int i = from; i < to; i++) {
            String pokemonName = "pokemon" + i;
            Pokemon pokemon = pokemonRepository.findByPokemonName(pokemonName).get();
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

    private List<Pokemon> getRandomPokemons() {
        List<Pokemon> pokemons = new ArrayList<>();
        List<Long> randomPokemonIds = getRandomIds(MAX_SEALS_SIZE, POKEMON_SIZE);

        for (Long randomPokemonId : randomPokemonIds) {
            Pokemon pokemon = pokemonRepository.findById(randomPokemonId).get();
            pokemons.add(pokemon);
        }

        return pokemons;
    }

    private List<HoldingSeal> getHoldingSeals(List<Pokemon> pokemons) {
        List<HoldingSeal> holdingSeals = new ArrayList<>();
        for (Pokemon pokemon : pokemons) {
            HoldingSeal holdingSeal = HoldingSeal.create(pokemon);
            holdingSeals.add(holdingSeal);
        }
        return holdingSeals;
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