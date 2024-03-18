package org.example.igotthese.service.impl;

import static org.example.igotthese.common.exhandler.ErrorCode.POKEMON_NOT_FOUND;
import static org.example.igotthese.common.exhandler.ErrorCode.POST_NOT_FOUND;
import static org.example.igotthese.common.exhandler.ErrorCode.USER_NOT_FOUND;
import static org.example.igotthese.common.exhandler.ErrorCode.USER_POST_FORBIDDEN;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.igotthese.common.exception.ForbiddenException;
import org.example.igotthese.common.exception.NoSuchDataException;
import org.example.igotthese.data.dao.DemandSealDao;
import org.example.igotthese.data.dao.PokemonDao;
import org.example.igotthese.data.dao.PostDao;
import org.example.igotthese.data.dao.SupplySealDao;
import org.example.igotthese.data.dao.UserDao;
import org.example.igotthese.data.dto.PokemonDto;
import org.example.igotthese.data.dto.PostDto.Create;
import org.example.igotthese.data.dto.PostDto.Response;
import org.example.igotthese.data.dto.PostDto.Search;
import org.example.igotthese.data.dto.PostDto.Update;
import org.example.igotthese.data.entity.DemandSeal;
import org.example.igotthese.data.entity.Pokemon;
import org.example.igotthese.data.entity.Post;
import org.example.igotthese.data.entity.SupplySeal;
import org.example.igotthese.data.entity.User;
import org.example.igotthese.service.PostService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final UserDao userDao;
    private final PostDao postDao;
    private final PokemonDao pokemonDao;
    private final DemandSealDao demandSealDao;
    private final SupplySealDao supplySealDao;

    @Override
    @Transactional
    public Long savePost(Create create) {
        // 유저 검증
        User user = userDao.getUserByUserName(create.getUserName())
                .orElseThrow(() -> new NoSuchDataException(USER_NOT_FOUND));

        // 수요, 공급 N:M Entity 생성
        List<DemandSeal> demandSeals = getDemandSeals(create.getDemandSeals());
        List<SupplySeal> supplySeals = getSupplySeals(create.getSupplySeals());

        // DTO -> Entity
        Post post = create.toEntity(user, demandSeals, supplySeals);
        Post savedPost = postDao.savePost(post);

        // seals save
        demandSeals.stream()
                .forEach(demandSeal -> demandSealDao.saveDemandSeal(demandSeal));
        supplySeals.stream()
                .forEach(supplySeal -> supplySealDao.saveSupplySeal(supplySeal));

        return savedPost.getId();
    }

    @Override
    @Transactional
    public Long updatePost(Update update) {
        // 유저 검증
        userDao.getUserByUserName(update.getUserName())
                .orElseThrow(() -> new NoSuchDataException(USER_NOT_FOUND));

        // 게시판 조회
        Post post = postDao.getPostById(update.getPostId())
                .orElseThrow(() -> new NoSuchDataException(POST_NOT_FOUND));

        // 게시판 검증
        if (!post.getUser().getUserName().equals(update.getUserName())) {
            new ForbiddenException(USER_POST_FORBIDDEN);
        }

        List<DemandSeal> demandSeals = getDemandSeals(update.getDemandSeals());
        List<SupplySeal> supplySeals = getSupplySeals(update.getSupplySeals());

        Double locationX = post.getLongitude();
        Double locationY = post.getLatitude();

        post.change(demandSeals, supplySeals, locationX, locationY);

        // seals save
        post.getDemandSeals().stream()
                .forEach(demandSeal -> demandSealDao.saveDemandSeal(demandSeal));
        post.getSupplySeals().stream()
                .forEach(supplySeal -> supplySealDao.saveSupplySeal(supplySeal));

        return post.getId();
    }

    @Override
    public List<Response> getPosts() {
        List<Post> allPost = postDao.getAllPost();
        List<Response> responses = new ArrayList<>();
        for (Post post : allPost) {
            responses.add(new Response(post));
        }
        return responses;
    }

    @Override
    public List<Response> getPostsOld(Search search) {
        Double latitude = search.getLatitude();
        Double longitude = search.getLongitude();
        String point = getPoint(latitude, longitude);
        List<Post> postsBySearch;
        if (search.getPokemonSearch() == null) {
            postsBySearch = postDao.getPostsByPoint(point);
        } else {
            postsBySearch = postDao.getPostsBySearchOld(search.getPokemonSearch().getPokemonId(), point);
        }
        List<Response> responses = new ArrayList<>();
        for (Post post : postsBySearch) {
            responses.add(new Response(post));
        }
        return responses;
    }

    public List<Response> getPosts(Search search) {
        Double latitude = search.getLatitude();
        Double longitude = search.getLongitude();
        String point = getPoint(latitude, longitude);
        List<Post> postsBySearch;
        if (search.getPokemonSearch() == null) {
            postsBySearch = postDao.getPostsByPoint(point);
        } else {
            postsBySearch = postDao.getPostsBySearch(search.getPokemonSearch().getPokemonId(), point);
        }
        List<Response> responses = new ArrayList<>();
        for (Post post : postsBySearch) {
            responses.add(new Response(post));
        }
        return responses;
    }

    @Override
    @Transactional
    public void deletePostById(Long postId) {
        // 게시판 조회
        Post post = postDao.getPostById(postId).orElseThrow(() -> new NoSuchDataException(POST_NOT_FOUND));
        post.getUser().setPost(null);
        post.getDemandSeals().stream()
                        .forEach(demandSeal -> demandSealDao.deleteDemandSealById(demandSeal.getId()));
        post.getSupplySeals().stream()
                        .forEach(supplySeal -> supplySealDao.deleteSupplySealById(supplySeal.getId()));

        postDao.deletePostById(postId);
    }

    private List<DemandSeal> getDemandSeals(List<PokemonDto.Search> pokemons) {
        List<DemandSeal> demandSeals = new ArrayList<>();
        if (pokemons == null) {
            return demandSeals;
        }
        for (PokemonDto.Search pokemon : pokemons) {
            // 포켓몬 조회
            Pokemon pokemonByPokemonName = pokemonDao.getPokemonByPokemonName(pokemon.getPokemonName())
                    .orElseThrow(() -> new NoSuchDataException(POKEMON_NOT_FOUND));
            DemandSeal demandSeal = DemandSeal.create(pokemonByPokemonName);
            demandSeals.add(demandSeal);
        }
        return demandSeals;
    }

    private List<SupplySeal> getSupplySeals(List<PokemonDto.Search> pokemons) {
        List<SupplySeal> supplySeals = new ArrayList<>();
        if (pokemons == null) {
            return supplySeals;
        }
        for (PokemonDto.Search pokemon : pokemons) {
            // 포켓몬 조회
            Pokemon pokemonByPokemonName = pokemonDao.getPokemonByPokemonName(pokemon.getPokemonName())
                    .orElseThrow(() -> new NoSuchDataException(POKEMON_NOT_FOUND));
            SupplySeal supplySeal = SupplySeal.create(pokemonByPokemonName);
            supplySeals.add(supplySeal);
        }
        return supplySeals;
    }

    private String getPoint(Double latitude, Double longitude) {
        return String.format("POINT(%s %s)", latitude, longitude);
    }

}
