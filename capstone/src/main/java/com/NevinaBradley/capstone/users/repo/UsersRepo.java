package com.NevinaBradley.capstone.users.repo;

import com.NevinaBradley.capstone.users.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsersRepo extends JpaRepository<Users, Integer> {
    Optional<Users> findByUsername(String username);

    Optional<Users> findById(Integer id);

    Users save(Users users);

    @Modifying
    @Query("UPDATE Users u SET u.username = :username, u.password = :password, u.display_name = :display_name, u.bio = :bio, u.profile_picture = :profile_picture WHERE u.id = :id")
    void updateById(
            @Param("id") Integer id,
            @Param("username") String username,
            @Param("password") String password,
            @Param("display_name") String display_name,
            @Param("bio") String bio,
            @Param("profile_picture") String profile_picture
    );
}
