package com.NevinaBradley.capstone.users.service;

import com.NevinaBradley.capstone.message.MessageException;
import com.NevinaBradley.capstone.users.model.Users;
import com.NevinaBradley.capstone.users.repo.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {
    private UsersRepo usersRepo;

    @Autowired
    public UsersServiceImpl(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }

    @Override
    public Users createUser(Users users) {
        return usersRepo.save(users);
    }

    @Override
    public Optional<Users> getById(Integer id) throws MessageException {
        Optional<Users> users = usersRepo.findById(id);
        if(users.isEmpty()){
            throw new MessageException("Could not find user at id " + id);
        }
        return usersRepo.findById(id);
    }

    @Override
    public Optional<Users> getByUsername(String username) {
        return usersRepo.findByUsername(username);
    }

    @Override
    public Optional<Users> updateUser(Integer id, Users users) throws Exception {
        if (!usersRepo.existsById(id)) {
            throw new Exception("User not found with ID: " + id);
        }

        users.setId(id);

        Users updatedUsers = usersRepo.save(users);

        return Optional.of(updatedUsers);
    }

    @Override
    public boolean delete(Integer id) {
        if (usersRepo.existsById(id)) {
            usersRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
