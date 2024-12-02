package org.example.shedu.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.shedu.entity.User;
import org.example.shedu.payload.ApiResponse;
import org.example.shedu.payload.request.MessageDto;
import org.example.shedu.payload.request.MessageRequest;
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
    public ResponseEntity<ApiResponse> chat(@RequestParam Integer receiverId,
                                            @CurrentUser User user) {
        ApiResponse chat = chatService.createChat(receiverId,user);
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

    @PostMapping("/send message")
    public ResponseEntity<ApiResponse> sendMessage(@RequestBody @Valid MessageRequest request){
        ApiResponse apiResponse = chatService.sandMessage(request);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }
    @GetMapping("/getOne message/{id}")
    public ResponseEntity<ApiResponse> getOneMessage(@PathVariable int id) {
        ApiResponse message = chatService.getMessage(id);
        return ResponseEntity.ok(message);
    }
    @GetMapping("/all message")
    public ResponseEntity<ApiResponse> getAllMessage(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "5") int size){
        ApiResponse allChat = chatService.getAllChat(page, size);
        return ResponseEntity.ok(allChat);
    }
    @PutMapping("/update message/{id}")
    public ResponseEntity<ApiResponse> updateMessage(@PathVariable int id, @RequestBody MessageDto dto){
        ApiResponse apiResponse = chatService.updateMessage(id, dto);
        return ResponseEntity.ok(apiResponse);
    }
    @DeleteMapping("/delete message/{id}")
    public ResponseEntity<ApiResponse> deleteMessage(@PathVariable int id){
        ApiResponse apiResponse = chatService.deleteMessage(id);
        return ResponseEntity.ok(apiResponse);
    }
    @PutMapping("/read message/{id}")
    public ResponseEntity<ApiResponse> read(@PathVariable int id) {
        ApiResponse readChat = chatService.readMessage(id);
        return ResponseEntity.ok(readChat);
    }
}
