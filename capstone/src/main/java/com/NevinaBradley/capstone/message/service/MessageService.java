package com.NevinaBradley.capstone.message.service;

import com.NevinaBradley.capstone.message.MessageException;
import com.NevinaBradley.capstone.message.model.Message;

import java.util.Optional;

public interface MessageService {
    Message sendMessage(Message message);

    Optional<Message> getById(Integer id) throws Exception, MessageException;

    boolean delete(Integer id);
}
