package org.example.shedu.service;

import lombok.RequiredArgsConstructor;
import org.example.shedu.entity.Message;
import org.example.shedu.payload.ApiResponse;
import org.example.shedu.payload.CustomerPageable;
import org.example.shedu.payload.response.MessageDto;
import org.example.shedu.repository.MessageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor

public class MessageService {

    private final MessageRepository messageRepository;

    public ApiResponse getByIdChat(Integer id) {
        Message byChatId = messageRepository.findByChatIdAndDeletedFalse(id);
        if (byChatId == null) {
            return new ApiResponse("Message not found",404);
        }
        MessageDto response = MessageDto.builder()
                .sender(byChatId.getChat().getSender().getFullName())
                .receiver(byChatId.getChat().getReceiver().getFullName())
                .message(byChatId.getMessage())
                .isRead(byChatId.isRead())
                .build();
        return new ApiResponse("Success",200,response);
    }
    public ApiResponse allChats(int page, int size) {
        Page<Message> all = messageRepository.findAll(PageRequest.of(page, size));
        List<MessageDto> responses = new ArrayList<>();
        for (Message message : all) {
            MessageDto response = MessageDto.builder()
                    .sender(message.getChat().getSender().getFullName())
                    .receiver(message.getChat().getReceiver().getFullName())
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
}
