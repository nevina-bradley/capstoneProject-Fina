package com.NevinaBradley.capstone.message;

import com.NevinaBradley.capstone.message.model.Message;
import com.NevinaBradley.capstone.message.repo.MessageRepo;
import com.NevinaBradley.capstone.message.service.MessageServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MessageServiceImplTest {
    @Autowired
    private MessageServiceImpl messageService;

    @MockBean
    private MessageRepo messageRepo;

    @Test
    public void sendMessageTest() {
        Message newMessage = new Message();
        newMessage.setMessage("message");

        when(messageRepo.save(newMessage)).thenReturn(newMessage);

        Message sentMessage = messageService.sendMessage(newMessage);

        assertNotNull(sentMessage);
        assertEquals("message", sentMessage.getMessage());
    }

    @Test
    public void getByIdTest() throws Exception {
        Integer id = 1;
        Message message = new Message();
        message.setId(id);
        when(messageRepo.findById(id)).thenReturn(Optional.of(message));

        Optional<Message> result = messageService.getById(id);

        assertTrue(result.isPresent());
        Message foundMessage = result.get();
        assertEquals(id, foundMessage.getId());
    }

    @Test
    public void getByIdNotFoundTest() throws Exception {
        BDDMockito.doReturn(Optional.empty()).when(messageRepo).findById(ArgumentMatchers.any());
        Assertions.assertThrows(MessageException.class, () -> {
            messageService.getById(1);
        });
    }

    @Test
    public void deleteMessageTest() {
        Integer id = 1;
        when(messageRepo.existsById(id)).thenReturn(true);

        boolean result = messageService.delete(id);

        assertTrue(result);
    }

    @Test
    public void deleteMessageNotFoundTest() {
        Integer id = 1;
        when(messageRepo.existsById(id)).thenReturn(false);

        boolean result = messageService.delete(id);

        assertFalse(result);
    }
}
