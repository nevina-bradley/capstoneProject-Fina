package com.NevinaBradley.capstone.message;

import com.NevinaBradley.capstone.message.controller.MessageController;
import com.NevinaBradley.capstone.message.model.Message;
import com.NevinaBradley.capstone.message.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class MessageControllerTest {
    @Autowired
    private MessageController messageController;

    @MockBean
    private MessageService messageService;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(messageController).build();
    }

    @Test
    public void sendMessageTest() throws Exception {
        Message newMessage = new Message();
        newMessage.setMessage("new message");

        when(messageService.sendMessage(newMessage)).thenReturn(newMessage);

        mockMvc.perform(post("/api/v1/message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newMessage)))
                .andExpect(status().isCreated());
    }

    @Test
    public void getByIdFoundTest() throws Exception {
        Integer id = 1;

        Message mockMessage = new Message();
        mockMessage.setId(1);
        mockMessage.setMessage("test message");

        Optional<Message> mockMessageOptional = Optional.of(mockMessage);
        BDDMockito.when(messageService.getById(mockMessage.getId())).thenReturn(mockMessageOptional);

        mockMvc.perform(get("/api/v1/message/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.message").value("test message"));
    }

    @Test
    public void getByIdNotFoundTest() throws Exception {
        Integer id = 2;

        when(messageService.getById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/message/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteMessageSuccessTest() throws Exception {
        Integer id = 1;

        when(messageService.delete(id)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/message/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteMessageNotFoundTest() throws Exception {
        Integer id = 2;

        when(messageService.delete(id)).thenReturn(false);

        mockMvc.perform(delete("/api/v1/message/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    private String asJsonString(Object obj) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}
