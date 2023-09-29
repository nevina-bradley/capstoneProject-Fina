package com.NevinaBradley.capstone.post.service;

import com.NevinaBradley.capstone.message.MessageException;
import com.NevinaBradley.capstone.post.model.Post;
import com.NevinaBradley.capstone.post.repo.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    private PostRepo postRepo;

    @Autowired
    public PostServiceImpl(PostRepo postRepo) {
        this.postRepo = postRepo;
    }

    @Override
    public Post createPost(Post post) { return postRepo.save(post);}

    @Override
    public Optional<Post> getById(Integer id) throws MessageException {
        Optional<Post> post = postRepo.findById(id);
        if(post.isEmpty()){
            throw new MessageException("Could not find post at id " + id);
        }
        return postRepo.findById(id);
    }

    @Override
    public Optional<Post> updatePost(Integer id, Post post) throws Exception {
        if (!postRepo.existsById(id)) {
            throw new Exception("Post not found with ID: " + id);
        }

        post.setId(id);

        Post updatedPost = postRepo.save(post);

        return Optional.of(updatedPost);
    }

    @Override
    public boolean delete(Integer id) {
        if (postRepo.existsById(id)) {
            postRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
