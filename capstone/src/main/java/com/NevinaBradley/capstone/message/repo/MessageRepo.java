package com.NevinaBradley.capstone.message.repo;

import com.NevinaBradley.capstone.message.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MessageRepo extends JpaRepository<Message, Integer> {
    Optional<Message> findById(Integer id);

    Message save(Message message);
}
