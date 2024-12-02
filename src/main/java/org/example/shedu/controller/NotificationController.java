package org.example.shedu.controller;

import lombok.RequiredArgsConstructor;
import org.example.shedu.payload.ApiResponse;
import org.example.shedu.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    @GetMapping("/get one Notification/{id}")
    public ResponseEntity<ApiResponse> getOneNotification(@PathVariable Integer id) {
        ApiResponse oneNotification = notificationService.getOneNotification(id);
        return ResponseEntity.ok(oneNotification);
    }
    @GetMapping("/all Notifications")
    public ResponseEntity<ApiResponse> getAllNotifications(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "5") int size) {
        ApiResponse apiResponse = notificationService.allNotifications(page, size);
        return ResponseEntity.ok(apiResponse);
    }
    @PutMapping("/read/{id}")
    public ResponseEntity<ApiResponse> readNotification(@PathVariable Integer id) {
        ApiResponse read = notificationService.isRead(id);
        return ResponseEntity.ok(read);
    }
}
