package com.NevinaBradley.capstone.users;

import com.NevinaBradley.capstone.users.controller.UsersController;
import com.NevinaBradley.capstone.users.model.Users;
import com.NevinaBradley.capstone.users.service.UsersService;
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
public class UsersControllerTest {

    @Autowired
    private UsersController usersController;

    @MockBean
    private UsersService usersService;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(usersController).build();
    }

    @Test
    public void createUserTest() throws Exception {
        Users newUsers = new Users();
        newUsers.setUsername("newuser");
        newUsers.setPassword("password");
        newUsers.setDisplay_name("New User");

        when(usersService.createUser(newUsers)).thenReturn(newUsers);

        mockMvc.perform(post("/api/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newUsers)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("newuser"))
                .andExpect(jsonPath("$.password").value("password"))
                .andExpect(jsonPath("$.display_name").value("New User"));
    }

    @Test
    public void getByUsernameFoundTest() throws Exception {
        String username = "testuser";

        Users mockUsers = new Users();
        mockUsers.setId(1);
        mockUsers.setUsername("username");
        mockUsers.setDisplay_name("Test User");

        Optional<Users> mockUsersOptional = Optional.of(mockUsers);
        BDDMockito.when(usersService.getByUsername(username)).thenReturn(mockUsersOptional);

        mockMvc.perform(get("/api/v1/user/search/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("username"))
                .andExpect(jsonPath("$.display_name").value("Test User"));
    }

    @Test
    public void getByUsernameNotFoundTest() throws Exception {
        String username = "nonexistentuser";

        when(usersService.getByUsername(username)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/user/search/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateUserTest() throws Exception {
        Integer id = 1;

        Users existingUsers = new Users();
        existingUsers.setId(id);
        existingUsers.setUsername("existinguser");
        existingUsers.setPassword("oldpassword");
        existingUsers.setDisplay_name("Existing User");

        Users updatedUsers = new Users();
        updatedUsers.setUsername("updateduser");
        updatedUsers.setPassword("newpassword");
        updatedUsers.setDisplay_name("Updated User");

        when(usersService.getById(any(Integer.class))).thenReturn(Optional.of(existingUsers));
        when(usersService.updateUser(any(Integer.class), any(Users.class))).thenReturn(Optional.of(updatedUsers));

        mockMvc.perform(put("/api/v1/user/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedUsers)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("updateduser"))
                .andExpect(jsonPath("$.display_name").value("Updated User"));
    }

    @Test
    public void deleteUserSuccessTest() throws Exception {
        Integer userId = 1;

        when(usersService.delete(userId)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/user/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteUserNotFoundTest() throws Exception {
        Integer userId = 2;

        when(usersService.delete(userId)).thenReturn(false);

        mockMvc.perform(delete("/api/v1/user/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    private String asJsonString(Object obj) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}
