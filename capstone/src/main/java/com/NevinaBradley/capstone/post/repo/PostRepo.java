package com.NevinaBradley.capstone.post.repo;

import com.NevinaBradley.capstone.post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepo extends JpaRepository<Post, Integer> {
    Optional<Post> findById(Integer id);

    Post save(Post post);

    @Modifying
    @Query("UPDATE Post u SET u.users_id = :users_id, u.media = :media, u.caption = :caption WHERE u.id = :id")
    void updateById(
            @Param("id") Integer id,
            @Param("users_id") Integer users_id,
            @Param("media") String media,
            @Param("caption") String caption
    );
}
