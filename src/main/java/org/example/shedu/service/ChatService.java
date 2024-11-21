package org.example.shedu.service;

import lombok.RequiredArgsConstructor;
import org.example.shedu.entity.Chat;
import org.example.shedu.entity.Message;
import org.example.shedu.entity.User;
import org.example.shedu.entity.enums.Role;
import org.example.shedu.payload.ApiResponse;
import org.example.shedu.payload.request.ChatDto;
import org.example.shedu.repository.ChatRepository;
import org.example.shedu.repository.MessageRepository;
import org.example.shedu.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    public ApiResponse createChat(ChatDto chatDto, String title) {
        Optional<User> byId = userRepository.findById(chatDto.getSenderId());
        if (byId.isEmpty()) {
            return new ApiResponse("User not found",404);
        }
        Optional<User> byId1 = userRepository.findById(chatDto.getReceiverId());
        if (byId1.isEmpty()) {
            return new ApiResponse("User not found",404);
        }
        Chat chat = Chat.builder()
                .sender(byId.get())
                .receiver(byId1.get())
                .build();
        Chat save = chatRepository.save(chat);

        Message message = Message.builder()
                .chat(save)
                .message(title)
                .isRead(false)
                .build();
        messageRepository.save(message);

        return new ApiResponse("Chat created",200);
    }
}
