package com.NevinaBradley.capstone.post.service;

import com.NevinaBradley.capstone.message.MessageException;
import com.NevinaBradley.capstone.post.model.Post;

import java.util.Optional;

public interface PostService {
    Post createPost(Post post);

    Optional<Post> getById(Integer id) throws Exception, MessageException;

    Optional<Post> updatePost(Integer id, Post post) throws Exception;

    boolean delete(Integer id);
}
