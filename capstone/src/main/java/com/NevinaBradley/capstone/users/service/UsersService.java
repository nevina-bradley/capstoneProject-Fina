package com.NevinaBradley.capstone.users.service;

import com.NevinaBradley.capstone.message.MessageException;
import com.NevinaBradley.capstone.users.model.Users;

import java.util.Optional;

public interface UsersService {

    Users createUser(Users users);

    Optional<Users> getById(Integer id) throws MessageException;

    Optional<Users> getByUsername(String username);

    Optional<Users> updateUser(Integer id, Users users) throws Exception;

    boolean delete(Integer id);
}
