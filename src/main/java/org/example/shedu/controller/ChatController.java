package org.example.shedu.controller;

import lombok.RequiredArgsConstructor;
import org.example.shedu.entity.User;
import org.example.shedu.payload.ApiResponse;
import org.example.shedu.payload.request.ChatRequest;
import org.example.shedu.security.CurrentUser;
import org.example.shedu.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> chat(@RequestBody ChatRequest chatDto,
                                            @CurrentUser User user) {
        ApiResponse chat = chatService.createChat(chatDto,user);
        return ResponseEntity.status(chat.getStatus()).body(chat);
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<ApiResponse> getOne(@PathVariable int id) {
        ApiResponse byIdChat = chatService.getByIdChat(id);
        return ResponseEntity.ok(byIdChat);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAll(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "5") int size) {
        ApiResponse allChat = chatService.getAllChat(page, size);
        return ResponseEntity.ok(allChat);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable int id) {
        ApiResponse deleteChat = chatService.deleteChat(id);
        return ResponseEntity.ok(deleteChat);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable int id, @RequestBody ChatRequest chatDto) {
        ApiResponse updateChat = chatService.updateChat(id, chatDto);
        return ResponseEntity.ok(updateChat);
    }
}
