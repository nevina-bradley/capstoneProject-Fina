package com.NevinaBradley.capstone.post;

import com.NevinaBradley.capstone.message.MessageException;
import com.NevinaBradley.capstone.post.model.Post;
import com.NevinaBradley.capstone.post.repo.PostRepo;
import com.NevinaBradley.capstone.post.service.PostServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PostServiceImplTest {
    @Autowired
    private PostServiceImpl postService;

    @MockBean
    private PostRepo postRepo;

    @Test
    public void createPostTest() {
        Post newPost = new Post();
        newPost.setMedia("media");

        when(postRepo.save(newPost)).thenReturn(newPost);

        Post createdPost = postService.createPost(newPost);

        assertNotNull(createdPost);
        assertEquals("media", createdPost.getMedia());
    }

    @Test
    public void getByIdTest() throws Exception {
        Integer id = 1;
        Post post = new Post();
        post.setId(id);
        when(postRepo.findById(id)).thenReturn(Optional.of(post));

        Optional<Post> result = postService.getById(id);

        assertTrue(result.isPresent());
        Post foundPost = result.get();
        assertEquals(id, foundPost.getId());
    }

    @Test
    public void getByIdNotFoundTest() throws Exception {
        BDDMockito.doReturn(Optional.empty()).when(postRepo).findById(ArgumentMatchers.any());
        Assertions.assertThrows(MessageException.class, () -> {
            postService.getById(1);
        });
    }

    @Test
    public void updateUserTest() throws Exception {
        Integer id = 1;

        Post existingPost = new Post();
        existingPost.setId(id);
        existingPost.setMedia("existing media");
        existingPost.setCaption("existing caption");

        Post updatedPost = new Post();
        updatedPost.setMedia("updated media");
        updatedPost.setCaption("updated caption");

        when(postRepo.existsById(id)).thenReturn(true);
        when(postRepo.save(updatedPost)).thenReturn(updatedPost);

        Optional<Post> result = postService.updatePost(id, updatedPost);

        assertTrue(result.isPresent());
        Post updated = result.get();
        assertEquals("updated media", updated.getMedia());
        assertEquals("updated caption", updated.getCaption());
    }

    @Test
    public void deleteUserTest() {
        Integer id = 1;
        when(postRepo.existsById(id)).thenReturn(true);

        boolean result = postService.delete(id);

        assertTrue(result);
    }

    @Test
    public void deleteUserNotFoundTest() {
        Integer id = 1;
        when(postRepo.existsById(id)).thenReturn(false);

        boolean result = postService.delete(id);

        assertFalse(result);
    }
}
