package com.NevinaBradley.capstone.post;

import com.NevinaBradley.capstone.post.controller.PostController;
import com.NevinaBradley.capstone.post.model.Post;
import com.NevinaBradley.capstone.post.service.PostService;
import com.NevinaBradley.capstone.users.model.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class PostControllerTest {

    @Autowired
    private PostController postController;

    @MockBean
    private PostService postService;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
    }

    @Test
    public void createPostTest() throws Exception {
        Post newPost = new Post();
        newPost.setMedia("new_media_url");
        newPost.setCaption("This is a new post caption");

        when(postService.createPost(newPost)).thenReturn(newPost);

        mockMvc.perform(post("/api/v1/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newPost)))
                .andExpect(status().isCreated());
    }

    @Test
    public void getByIdFoundTest() throws Exception {
        Integer id = 1;

        Post mockPost = new Post();
        mockPost.setId(1);
        mockPost.setMedia("test_media_url");
        mockPost.setCaption("This is a test post caption");

        Optional<Post> mockPostOptional = Optional.of(mockPost);
        BDDMockito.when(postService.getById(mockPost.getId())).thenReturn(mockPostOptional);

        mockMvc.perform(get("/api/v1/post/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.media").value("test_media_url"))
                .andExpect(jsonPath("$.caption").value("This is a test post caption"));
    }

    @Test
    public void getByIdNotFoundTest() throws Exception {
        Integer id = 2;

        when(postService.getById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/post/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updatePostTest() throws Exception {
        Integer id = 1;

        Post existingPost = new Post();
        existingPost.setId(id);
        existingPost.setMedia("existing_media_url");
        existingPost.setCaption("This is an existing post caption");
        Users users = new Users();
        users.setId(1);
        users.setUsername("existinguser");

        Post updatedPost = new Post();
        updatedPost.setMedia("updated_media_url");
        updatedPost.setCaption("This is an updated post caption");

        when(postService.updatePost(any(Integer.class), any(Post.class))).thenReturn(Optional.of(updatedPost));

        mockMvc.perform(put("/api/v1/post/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedPost)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.media").value("updated_media_url"))
                .andExpect(jsonPath("$.caption").value("This is an updated post caption"));
    }

    @Test
    public void deletePostSuccessTest() throws Exception {
        Integer id = 1;

        when(postService.delete(id)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/post/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deletePostNotFoundTest() throws Exception {
        Integer postId = 2;

        when(postService.delete(postId)).thenReturn(false);

        mockMvc.perform(delete("/api/v1/post/{id}", postId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    private String asJsonString(Object obj) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}
