package org.example.igotthese.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.example.igotthese.data.dto.PokemonDto;
import org.example.igotthese.data.dto.PostDto.Create;
import org.example.igotthese.data.dto.PostDto.Response;
import org.example.igotthese.data.dto.UserDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class PostServiceTest {
    @Autowired UserService userService;
    @Autowired PostService postService;

    private final int USER_SIZE = 10;
    private final int POST_SIZE = 10;
    private final int POKEMON_SIZE = 100;
    private final int MAX_SEALS_SIZE = 10;

    @BeforeEach
    public void setUp() {
        for (int i = 1; i <= USER_SIZE; i++) {
            String userName = "user" + i;
            UserDto.Create user = UserDto.Create.builder()
                    .userName(userName)
                    .build();
            userService.saveUser(user);
        }
    }

    @AfterEach
    public void afterEach() {
        List<UserDto.Response> allUser = userService.getAllUser();
        for (UserDto.Response user : allUser) {
            userService.deleteUserById(user.getUserId());
        }
    }


    @Test
    public void testPostCreate() throws Exception {
    	//given
        String userName = "user1";
        List<PokemonDto.Search> demandSeals = new ArrayList<>();
        List<PokemonDto.Search> supplySeals = new ArrayList<>();
        Double latitude = 38.1325;
        Double longitude = 124.1992;

        Create post = Create.builder()
                .userName(userName)
                .demandSeals(demandSeals)
                .supplySeals(supplySeals)
                .latitude(latitude)
                .longitude(longitude)
                .build();

        //when
        Long postId = postService.savePost(post);
        List<Response> posts = postService.getPosts();

        //then
        assertThat(posts.get(0).getPostId()).isEqualTo(postId);
    }






}