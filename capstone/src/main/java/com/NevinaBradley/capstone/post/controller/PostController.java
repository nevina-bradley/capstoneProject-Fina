package com.NevinaBradley.capstone.post.controller;

import com.NevinaBradley.capstone.post.model.Post;
import com.NevinaBradley.capstone.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {
    private PostService postService;

    @Autowired
    public PostController(PostService postService) { this.postService = postService; }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        Post createdPost = postService.createPost(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getById(@PathVariable("id") Integer id) {
        try {
            Optional<Post> post = postService.getById(id);

            if (post.isPresent()) {
                return ResponseEntity.ok(post.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Integer id, @RequestBody Post updatePost) throws Exception {
        Optional<Post> updatedPost = postService.updatePost(id, updatePost);
        if (updatedPost.isPresent()) {
            return ResponseEntity.ok(updatedPost.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        boolean deleted = postService.delete(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
