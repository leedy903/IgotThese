package org.example.igotthese.service;

import java.util.List;
import org.example.igotthese.data.dto.PostDto;
import org.example.igotthese.data.dto.PostDto.Create;
import org.example.igotthese.data.dto.PostDto.Update;

public interface PostService {
    Long savePost(Create create);
    Long updatePost(Update update);
    List<PostDto.Response> getPosts();

    /**
     * deprecated
     * @param search
     * @return
     */
    List<PostDto.Response> getPostsOld(PostDto.Search search);
    List<PostDto.Response> getPosts(PostDto.Search search);
    void deletePostById(Long postId);
}
