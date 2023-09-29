package com.NevinaBradley.capstone.message.controller;

import com.NevinaBradley.capstone.message.model.Message;
import com.NevinaBradley.capstone.message.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/message")
public class MessageController {
    private MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) { this.messageService = messageService; }

    @PostMapping
    public ResponseEntity<Message> sendMessage(@RequestBody Message message) {
        Message sentMessage = messageService.sendMessage(message);
        return ResponseEntity.status(HttpStatus.CREATED).body(sentMessage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getById(@PathVariable("id") Integer id) {
        try {
            Optional<Message> message = messageService.getById(id);

            if (message.isPresent()) {
                return ResponseEntity.ok(message.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        boolean deleted = messageService.delete(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
