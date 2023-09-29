package com.NevinaBradley.capstone.users.controller;

import com.NevinaBradley.capstone.users.model.Users;
import com.NevinaBradley.capstone.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class UsersController {
    private UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping
    public ResponseEntity<Users> createUser(@RequestBody Users users) {
        Users createdUsers = usersService.createUser(users);
        return ResponseEntity.status(HttpStatus.CREATED).body(users);
    }

    @GetMapping("/search/{username}")
    public ResponseEntity<Users> getByUsername(@PathVariable("username") String username) {
        try {
            Optional<Users> users = usersService.getByUsername(username);

            if (users.isPresent()) {
                return ResponseEntity.ok(users.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getById(@PathVariable("id") Integer id) {
        try {
            Optional<Users> users = usersService.getById(id);

            if (users.isPresent()) {
                return ResponseEntity.ok(users.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable Integer id, @RequestBody Users updatedUsers) throws Exception {
        Optional<Users> updatedUser = usersService.updateUser(id, updatedUsers);
        if (updatedUser.isPresent()) {
            return ResponseEntity.ok(updatedUser.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        boolean deleted = usersService.delete(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
