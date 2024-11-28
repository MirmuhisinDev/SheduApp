package org.example.shedu.service;

import lombok.RequiredArgsConstructor;

import org.example.shedu.entity.Notification;
import org.example.shedu.entity.User;
import org.example.shedu.payload.ApiResponse;
import org.example.shedu.payload.CustomerPageable;
import org.example.shedu.payload.response.NotificationResponse;
import org.example.shedu.repository.NotificationRepository;
import org.example.shedu.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;


import java.util.List;


@Component
@RequiredArgsConstructor

public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public void addNotification(User user , String title, String message) {
        Notification notification = Notification.builder()
                .title(title)
                .message(message)
                .user(user)
                .isRead(false)
                .build();
        notificationRepository.save(notification);
    }
    public ApiResponse getById(Integer id,User user) {
       Notification byId = notificationRepository.findByIdAndUserId(id,user.getId());

        if (byId==null) {
            return new ApiResponse("Notification not found",404);
        }

      isRead(byId.getId());

        NotificationResponse response = NotificationResponse.builder()
                .id(byId.getId())
                .title(byId.getTitle())
                .message(byId.getMessage())
                .createdAt(byId.getCreatedAt())
                .build();
        return new ApiResponse(response);
    }
    public ApiResponse all(User user,int page, int size) {
        Page<Notification> notifications = notificationRepository.findByUserId(user.getId(),PageRequest.of(page, size));
        List<NotificationResponse> responses = notifications.map(this::toResponse).stream().toList();
        CustomerPageable pageable = CustomerPageable.builder()
                .page(notifications.getNumber())
                .size(notifications.getSize())
                .totalPages(notifications.getTotalPages())
                .totalElements(notifications.getTotalElements())
                .body(responses)
                .build();
        return new ApiResponse(pageable);
    }
    private NotificationResponse toResponse(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .title(notification.getTitle())
                .message(notification.getMessage())
                .createdAt(notification.getCreatedAt())
                .build();
    }

    public void isRead(Integer id) {
       Notification notification = notificationRepository.findById(id).orElse(null);
        assert notification != null;
        notification.setRead(true);
        notificationRepository.save(notification);
    }
}
