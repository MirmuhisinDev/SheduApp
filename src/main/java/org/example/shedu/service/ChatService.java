package org.example.shedu.service;

import lombok.RequiredArgsConstructor;
import org.example.shedu.entity.Chat;
import org.example.shedu.entity.Message;
import org.example.shedu.entity.User;
import org.example.shedu.payload.ApiResponse;
import org.example.shedu.payload.CustomerPageable;
import org.example.shedu.payload.request.MessageDto;
import org.example.shedu.payload.request.MessageRequest;
import org.example.shedu.payload.response.ChatResponse;
import org.example.shedu.payload.response.MessageResponse;
import org.example.shedu.repository.ChatRepository;
import org.example.shedu.repository.FileRepository;
import org.example.shedu.repository.MessageRepository;
import org.example.shedu.repository.UserRepository;
import org.example.shedu.service.authService.EmailSenderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final FileRepository fileRepository;
    private final EmailSenderService emailSenderService;
    private final MessageRepository messageRepository;

    public ApiResponse createChat(Integer receiverId, User user) {

        Optional<User> byId1 = userRepository.findById(receiverId);
        if (byId1.isEmpty()) {
            return new ApiResponse("User not found",404);
        }
        Chat chat = Chat.builder()
                .sender(user)
                .receiver(byId1.get())
                .build();
        chatRepository.save(chat);
        return new ApiResponse("Chat created",200);
    }

    public ApiResponse getByIdChat(int id){
        Optional<Chat> byId = chatRepository.findById(id);
        if (byId.isEmpty()) {
            return new ApiResponse("Chat not found",404);
        }
        ChatResponse response = ChatResponse.builder()
                .id(byId.get().getId())
                .sender(byId.get().getSender().getId())
                .build();
        return new ApiResponse("Success",200,response);
    }

    public ApiResponse getAllChat(int page, int size){
        Page<Chat> all = chatRepository.findAll(PageRequest.of(page, size));
        List<ChatResponse> responses = new ArrayList<>();
        for (Chat chat : all) {
            ChatResponse response = ChatResponse.builder()
                    .id(chat.getId())
                    .sender(chat.getSender().getId())
                    .receiver(chat.getReceiver().getId())
                    .build();
            responses.add(response);
        }
        CustomerPageable pageable = CustomerPageable.builder()
                .page(all.getNumber())
                .size(all.getSize())
                .totalPages(all.getTotalPages())
                .totalElements(all.getTotalElements())
                .body(responses)
                .build();
        return new ApiResponse("Success",200,pageable);
    }
    public ApiResponse deleteChat(int id){
        Optional<Chat> byId = chatRepository.findById(id);
        if (byId.isEmpty()) {
            return new ApiResponse("Chat not found",404);
        }
        Chat chat = byId.get();
        chat.setDeleted(true);
        chatRepository.save(chat);
        return new ApiResponse("Success", 200);
    }

    public ApiResponse sandMessage(MessageRequest request){
        Optional<Chat> byId = chatRepository.findById(request.getChatId());
        if (byId.isEmpty()) {
            return new ApiResponse("Chat not found",404);
        }
        Message message = Message.builder()
                .chat(byId.get())
                .message(request.getMessage())
                .read(false)
                .build();
        messageRepository.save(message);
        return new ApiResponse("Success", 200);
    }
    public ApiResponse getMessage(int id){
        Optional<Message> byId = messageRepository.findById(id);
        if (byId.isEmpty()) {
            return new ApiResponse("Chat not found",404);
        }
        MessageResponse response = MessageResponse.builder()
                .id(byId.get().getId())
                .message(byId.get().getMessage())
                .isRead(byId.get().isRead())
                .build();
        return new ApiResponse("Success",200,response);
    }
    public ApiResponse listMessage(int page, int size){
        Page<Message> all = messageRepository.findAll(PageRequest.of(page, size));
        List<MessageResponse> responses = new ArrayList<>();
        for (Message message : all) {
            MessageResponse response = MessageResponse.builder()
                    .id(message.getId())
                    .message(message.getMessage())
                    .isRead(message.isRead())
                    .build();
            responses.add(response);
        }
        CustomerPageable pageable = CustomerPageable.builder()
                .page(all.getNumber())
                .size(all.getSize())
                .totalPages(all.getTotalPages())
                .totalElements(all.getTotalElements())
                .body(responses)
                .build();
        return new ApiResponse("Success",200,pageable);
    }
    public ApiResponse updateMessage(int id, MessageDto request){
        Optional<Message> byId = messageRepository.findById(id);
        if (byId.isEmpty()) {
            return new ApiResponse("Chat not found",404);
        }
        Message message = byId.get();
        message.setMessage(request.getMessage());
        messageRepository.save(message);
        return new ApiResponse("Success",200);
    }
    public ApiResponse deleteMessage(int id){
        Optional<Message> byId = messageRepository.findById(id);
        if (byId.isEmpty()) {
            return new ApiResponse("Chat not found",404);
        }
        Message message = byId.get();
        message.setDeleted(true);
        messageRepository.save(message);
        return new ApiResponse("Success", 200);
    }
    public ApiResponse readMessage(int id){
        Optional<Message> byId = messageRepository.findById(id);
        if (byId.isEmpty()) {
            return new ApiResponse("Chat not found",404);
        }
        Message message = byId.get();
        message.setRead(true);
        messageRepository.save(message);
        return new ApiResponse("Success", 200);
    }
}