package org.example.shedu.service;

import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.example.shedu.entity.Notification;
import org.example.shedu.entity.Order;
import org.example.shedu.entity.User;
import org.example.shedu.payload.ApiResponse;
import org.example.shedu.payload.CustomerPageable;
import org.example.shedu.payload.response.NotificationResponse;
import org.example.shedu.repository.NotificationRepository;
import org.example.shedu.repository.OrderRepository;
import org.example.shedu.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor

public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public void addNotification(User userFullName, String title, String message) {
        Notification notification = Notification.builder()
                .title(title)
                .message(message)
                .user(userFullName)
                .isRead(false)
                .build();
        notificationRepository.save(notification);
//        return new ApiResponse("Notification added successfully",200);
    }
    @Scheduled(fixedRate = 60000) // Har bir daqiqada tekshiriladi
    public ApiResponse sendNotifications() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime notificationTime = now.plusMinutes(30);

        // 30 daqiqa ichidagi buyurtmalarni topish
        List<Order> orders = orderRepository.findByCreateAtBetweenAndSentNotificationFalse(now, notificationTime);

        for (Order order : orders) {
            // Adminga yoki foydalanuvchiga bildirishnoma yuboring
            System.out.println("Notification sent for order ID: " + order.getBarbershop().getId()+
                    "Sizning 30 daqiqadan ketin buyurtmangiz bor.");


            order.setSentNotification(true);
            orderRepository.save(order);
        }
        return new ApiResponse("Successfully sent notifications");
    }
    public ApiResponse getOneNotification(Integer id) {
        Optional<Notification> byId = notificationRepository.findById(id);
        if (byId.isEmpty()) {
            return new ApiResponse("Notification not found",404);
        }
        NotificationResponse response = NotificationResponse.builder()
                .id(byId.get().getId())
                .title(byId.get().getTitle())
                .message(byId.get().getMessage())
                .userFullName(byId.get().getUser().getFullName())
                .createdAt(byId.get().getCreatedAt())
                .build();
        return new ApiResponse(response);
    }

    public ApiResponse allNotifications(int page, int size) {
        Page<Notification> notifications = notificationRepository.findAll(PageRequest.of(page, size));
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
                .userFullName(notification.getUser().getFullName())
                .createdAt(notification.getCreatedAt())
                .build();
    }

    public ApiResponse isRead(Integer id) {
        Optional<Notification> byId = notificationRepository.findById(id);
        if (byId.isEmpty()) {
            return new ApiResponse("Notification not found",404);
        }
        Notification notification = byId.get();
        notification.setRead(true);
        notificationRepository.save(notification);
        return new ApiResponse("Notification read successfully",200);
    }

}