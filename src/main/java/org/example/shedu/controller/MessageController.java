package org.example.shedu.controller;

import lombok.RequiredArgsConstructor;
import org.example.shedu.payload.ApiResponse;
import org.example.shedu.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @GetMapping("/getOne/{id}")
    public ResponseEntity<ApiResponse> getOne(@PathVariable Integer id) {
        ApiResponse byIdChat = messageService.getByIdChat(id);
        return ResponseEntity.ok(byIdChat);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAll(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "5") int size) {
        ApiResponse apiResponse = messageService.allChats(page, size);
        return ResponseEntity.ok(apiResponse);
    }
}
