package org.example.igotthese.data.repository.impl;

import static org.example.igotthese.data.entity.QDemandSeal.demandSeal;
import static org.example.igotthese.data.entity.QPokemon.pokemon;
import static org.example.igotthese.data.entity.QPost.post;
import static org.example.igotthese.data.entity.QSupplySeal.supplySeal;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.example.igotthese.data.dto.PostDto.Response;
import org.example.igotthese.data.dto.PostDto.Search;
import org.example.igotthese.data.entity.Post;
import org.example.igotthese.data.entity.QDemandSeal;
import org.example.igotthese.data.entity.QSupplySeal;
import org.example.igotthese.data.repository.PostQuerydslRepository;

public class PostRepositoryImpl implements PostQuerydslRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public PostRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Response> search(Search search) {
        List<Long> postIds = searchPostIdsByPokemonId(search.getPokemonSearch().getPokemonId());
        System.out.println(postIds);
        return queryFactory
                .select(Projections.bean(Response.class,
                        post.id.as("post_id"),
                        post.user,
                        post.demandSeals,
                        post.supplySeals,
                        post.latitude,
                        post.longitude))
                .from(post.demandSeals, demandSeal)
                .where(post.id.in(postIds))
                .fetch();
    }

    public List<Long> searchPostIdsByPokemonId(Long pokemonId) {
        return queryFactory
                .select(demandSeal.post.id)
                .from(demandSeal)
                .leftJoin(demandSeal.pokemon, pokemon)
                .where(pokemonIdEq(pokemonId))
                .fetch();
    }


    @Override
    public List<Post> findPostsByPokemonId(Long pokemonId) {
        return queryFactory
                .select(post)
                .from(post)
                .leftJoin(post.supplySeals, supplySeal)
                .leftJoin(supplySeal.pokemon, pokemon)
                .where(pokemonIdEq(pokemonId))
                .orderBy(post.modifiedAt.desc())
                .fetch();
    }

    private BooleanExpression pokemonIdEq(Long pokemonIdCond) {
        return pokemonIdCond != null ? pokemon.id.eq(pokemonIdCond) : null;
    }

}
