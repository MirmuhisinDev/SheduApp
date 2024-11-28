package org.example.shedu.controller;

import lombok.RequiredArgsConstructor;
import org.example.shedu.entity.User;
import org.example.shedu.payload.ApiResponse;
import org.example.shedu.security.CurrentUser;
import org.example.shedu.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ntf")
public class NotificationController {
    private final NotificationService service;

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse> getId(@PathVariable Integer id
                                , @CurrentUser User user ){
        ApiResponse apiResponse=service.getById(id,user);
        return ResponseEntity.ok(apiResponse);
    }
    @GetMapping("get/all")
    public ResponseEntity<ApiResponse> getAll(
            @CurrentUser User user,
            @RequestParam (defaultValue = "0") int page,
            @RequestParam (defaultValue = "10") int size
    ){
        ApiResponse apiResponse=service.all(user,page,size);
        return ResponseEntity.ok(apiResponse);
    }
}
