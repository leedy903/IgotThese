package org.example.igotthese.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.example.igotthese.data.dto.PostDto;
import org.example.igotthese.data.dto.PostDto.Create;
import org.example.igotthese.data.dto.PostDto.Response;
import org.example.igotthese.data.dto.PostDto.Update;
import org.example.igotthese.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Long> createPost(@RequestBody @Valid Create create) {
        Long postId = postService.savePost(create);
        return ResponseEntity.status(HttpStatus.OK).body(postId);
    }

    @PatchMapping(value = "/edit")
    public ResponseEntity<Long> updatePost(@RequestBody @Valid Update update) {
        Long postId = postService.updatePost(update);
        return ResponseEntity.status(HttpStatus.OK).body(postId);
    }

    @GetMapping(value = "/posts")
    public ResponseEntity<List<PostDto.Response>> gotPosts() {
        List<Response> posts = postService.getPosts();
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    /**
     * deprecated
     * @param search
     * @return
     */
    @PostMapping(value = "/posts-old")
    public ResponseEntity<List<PostDto.Response>> getPostsOld(@RequestBody @Valid PostDto.Search search) {
        List<Response> posts = postService.getPostsOld(search);
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    @PostMapping(value = "/posts")
    public ResponseEntity<List<PostDto.Response>> getPosts(@RequestBody @Valid PostDto.Search search) {
        List<Response> posts = postService.getPosts(search);
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Void> deletePostById(@RequestParam Long id) {
        postService.deletePostById(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
