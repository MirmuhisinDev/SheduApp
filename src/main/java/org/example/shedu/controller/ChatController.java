package org.example.shedu.controller;

import lombok.RequiredArgsConstructor;
import org.example.shedu.payload.ApiResponse;
import org.example.shedu.payload.request.ChatDto;
import org.example.shedu.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> chat(@RequestBody ChatDto chatDto,
                                            @RequestParam String title) {
        ApiResponse chat = chatService.createChat(chatDto, title);
        return ResponseEntity.status(chat.getStatus()).body(chat);
    }
}
