package org.example.igotthese.data.repository;

import java.util.List;
import org.example.igotthese.data.dto.PostDto;
import org.example.igotthese.data.entity.Post;

public interface PostQuerydslRepository {
    public List<PostDto.Response> search(PostDto.Search search);
    public List<Post> findPostsByPokemonId(Long pokemonId);
}
