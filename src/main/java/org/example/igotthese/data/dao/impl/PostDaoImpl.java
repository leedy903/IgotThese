package org.example.igotthese.data.dao.impl;

import java.util.List;
import java.util.Optional;
import org.example.igotthese.data.dao.PostDao;
import org.example.igotthese.data.dto.PostDto.Response;
import org.example.igotthese.data.dto.PostDto.Search;
import org.example.igotthese.data.entity.Post;
import org.example.igotthese.data.repository.PostRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class PostDaoImpl implements PostDao {

    PostRepository postRepository;

    public PostDaoImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public List<Post> getAllPost() {
        return postRepository.findAll(Sort.by(Direction.DESC, "modifiedAt"));
    }

    @Override
    public List<Response> getPostResponsesBySearch(Search search) {
        return postRepository.search(search);
    }

    @Override
    public List<Post> getPostsByPoint(String point) {
        return postRepository.findPostsByPoint(point);
    }

    @Override
    public List<Post> getPostsBySearchOld(Long pokemonId, String point) {
        return postRepository.findPostBySearchOld(pokemonId, point);
    }

    @Override
    public List<Post> getPostsBySearch(Long pokemonId, String point) {
        return postRepository.findPostBySearch(pokemonId, point);
    }

    @Override
    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }
}
