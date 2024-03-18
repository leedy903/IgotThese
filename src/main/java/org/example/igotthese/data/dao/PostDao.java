package org.example.igotthese.data.dao;

import java.util.List;
import java.util.Optional;
import org.example.igotthese.data.dto.PostDto.Response;
import org.example.igotthese.data.dto.PostDto.Search;
import org.example.igotthese.data.entity.Post;

public interface PostDao {
    Post savePost(Post post);
    Optional<Post> getPostById(Long id);
    List<Post> getAllPost();
    List<Response> getPostResponsesBySearch(Search search);
    List<Post> getPostsByPoint(String point);

    /**
     * deprecated
     * @param pokemonId
     * @param point
     * @return
     */
    List<Post> getPostsBySearchOld(Long pokemonId, String point);
    List<Post> getPostsBySearch(Long pokemonId, String point);
    void deletePostById(Long id);
}
