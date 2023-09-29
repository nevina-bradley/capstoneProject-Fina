package com.NevinaBradley.capstone.users.model;

import com.NevinaBradley.capstone.message.model.Message;
import com.NevinaBradley.capstone.post.model.Post;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    private String username;

    @NonNull
    private String password;

    @NonNull
    private String display_name;

    private String bio;

    private String profile_picture;

    @Override
    public String toString() {
        return String.format("%d %s %s %s %s %s", id, username, password, display_name, bio, profile_picture);
    }

    private String asJsonString(Object obj) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    @OneToMany
    @JoinColumn(name = "users_id")
    private Set<Post> posts;

    @OneToMany
    @JoinColumn(name = "users_id")
    private Set<Message> messages;
}
