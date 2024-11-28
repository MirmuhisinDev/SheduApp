package org.example.shedu.service;

import lombok.RequiredArgsConstructor;
import org.example.shedu.entity.Chat;
import org.example.shedu.entity.User;
import org.example.shedu.payload.ApiResponse;
import org.example.shedu.payload.CustomerPageable;
import org.example.shedu.payload.request.ChatRequest;
import org.example.shedu.payload.response.ChatResponse;
import org.example.shedu.repository.ChatRepository;
import org.example.shedu.repository.FileRepository;
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

    public ApiResponse createChat(ChatRequest chatDto, User user) {

        Optional<User> byId1 = userRepository.findById(chatDto.getReceiverId());
        if (byId1.isEmpty()) {
            return new ApiResponse("User not found",404);
        }
        Chat chat = Chat.builder()
                .sender(user)
                .receiver(byId1.get())
                .message(chatDto.getMessage())
                .file(chatDto.getFileId() != null ? fileRepository.findById(chatDto.getFileId()).orElse(null) : null)
                .build();
        chatRepository.save(chat);
        emailSenderService.sendEmail(byId1.get().getEmail(), "From "+user.getFullName(),"Message: "+chatDto.getMessage());
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
                .receiver(byId.get().getReceiver().getId())
                .message(byId.get().getMessage())
                .filId(byId.get().getFile().getId())
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
                    .message(chat.getMessage())
                    .filId(chat.getFile().getId())
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
    public ApiResponse updateChat(int id, ChatRequest chatDto){
        Optional<Chat> byId = chatRepository.findById(id);
        if (byId.isEmpty()) {
            return new ApiResponse("Chat not found",404);
        }
        Chat chat = byId.get();
        chat.setReceiver(byId.get().getReceiver());
        chat.setMessage(chatDto.getMessage());
        chat.setFile(byId.get().getFile());
        chatRepository.save(chat);
        return new ApiResponse("Success", 200);
    }
}