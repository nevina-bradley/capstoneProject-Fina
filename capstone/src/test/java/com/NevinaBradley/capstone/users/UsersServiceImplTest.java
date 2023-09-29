package com.NevinaBradley.capstone.users;

import com.NevinaBradley.capstone.message.MessageException;
import com.NevinaBradley.capstone.users.model.Users;
import com.NevinaBradley.capstone.users.repo.UsersRepo;
import com.NevinaBradley.capstone.users.service.UsersServiceImpl;
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
public class UsersServiceImplTest {

    @Autowired
    private UsersServiceImpl usersService;

    @MockBean
    private UsersRepo usersRepo;

    @Test
    public void createUserTest() {
        Users newUsers = new Users();
        newUsers.setUsername("newuser");

        when(usersRepo.save(newUsers)).thenReturn(newUsers);

        Users createdUsers = usersService.createUser(newUsers);

        assertNotNull(createdUsers);
        assertEquals("newuser", createdUsers.getUsername());
    }

    @Test
    public void getByIdTest() throws Exception {
        Integer id = 1;
        Users users = new Users();
        users.setId(id);
        when(usersRepo.findById(id)).thenReturn(Optional.of(users));

        Optional<Users> result = usersService.getById(id);

        assertTrue(result.isPresent());
        Users foundUsers = result.get();
        assertEquals(id, foundUsers.getId());
    }

    @Test
    public void getByIdNotFoundTest() throws Exception {
        BDDMockito.doReturn(Optional.empty()).when(usersRepo).findById(ArgumentMatchers.any());
        Assertions.assertThrows(MessageException.class, () -> {
            usersService.getById(1);
        });
    }

    @Test
    public void getByUsernameTest() {
        String username = "testuser";
        Users users = new Users();
        users.setUsername(username);
        when(usersRepo.findByUsername(username)).thenReturn(Optional.of(users));

        Optional<Users> result = usersService.getByUsername(username);

        assertTrue(result.isPresent());
        Users foundUsers = result.get();
        assertEquals(username, foundUsers.getUsername());
    }

    @Test
    public void getByUsernameNotFoundTest() {
        String username = "nonexistentuser";
        when(usersRepo.findByUsername(username)).thenReturn(Optional.ofNullable(null));

        Optional<Users> result = usersService.getByUsername(username);

        assertFalse(result.isPresent());
    }

    @Test
    public void updateUserTest() throws Exception {
        Integer id = 1;

        Users existingUsers = new Users();
        existingUsers.setId(id);
        existingUsers.setUsername("existinguser");
        existingUsers.setDisplay_name("Existing User");

        Users updatedUsers = new Users();
        updatedUsers.setUsername("updateduser");
        updatedUsers.setDisplay_name("Updated User");

        when(usersRepo.existsById(id)).thenReturn(true);
        when(usersRepo.save(updatedUsers)).thenReturn(updatedUsers);

        Optional<Users> result = usersService.updateUser(id, updatedUsers);

        assertTrue(result.isPresent());
        Users updated = result.get();
        assertEquals("updateduser", updated.getUsername());
        assertEquals("Updated User", updated.getDisplay_name());
    }

    @Test
    public void deleteUserTest() {
        Integer id = 1;
        when(usersRepo.existsById(id)).thenReturn(true);

        boolean result = usersService.delete(id);

        assertTrue(result);
    }

    @Test
    public void deleteUserNotFoundTest() {
        Integer id = 1;
        when(usersRepo.existsById(id)).thenReturn(false);

        boolean result = usersService.delete(id);

        assertFalse(result);
    }
}
