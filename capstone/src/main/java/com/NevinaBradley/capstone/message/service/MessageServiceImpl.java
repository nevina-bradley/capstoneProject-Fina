package com.NevinaBradley.capstone.message.service;

import com.NevinaBradley.capstone.message.MessageException;
import com.NevinaBradley.capstone.message.model.Message;
import com.NevinaBradley.capstone.message.repo.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {
    private MessageRepo messageRepo;

    @Autowired
    public MessageServiceImpl(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    @Override
    public Message sendMessage(Message message) {
        return messageRepo.save(message);
    }

    @Override
    public Optional<Message> getById(Integer id) throws MessageException {
        Optional<Message> message = messageRepo.findById(id);
        if(message.isEmpty()){
            throw new MessageException("Could not find message at id " + id);
        }
        return messageRepo.findById(id);
    }

    @Override
    public boolean delete(Integer id) {
        if (messageRepo.existsById(id)) {
            messageRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
