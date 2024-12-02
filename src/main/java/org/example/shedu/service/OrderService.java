package org.example.shedu.service;

import lombok.RequiredArgsConstructor;
import org.example.shedu.entity.Barbershop;
import org.example.shedu.entity.Order;
import org.example.shedu.entity.Service;
import org.example.shedu.entity.User;
import org.example.shedu.entity.enums.Status;
import org.example.shedu.payload.ApiResponse;
import org.example.shedu.payload.CustomerPageable;
import org.example.shedu.payload.request.OrderDto;
import org.example.shedu.payload.request.OrderRequest;
import org.example.shedu.payload.request.Ordered;
import org.example.shedu.payload.request.UserDto;
import org.example.shedu.payload.response.OrderResponse;
import org.example.shedu.repository.BarbershopRepository;
import org.example.shedu.repository.OrderRepository;
import org.example.shedu.repository.ServiceRepository;
import org.example.shedu.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor

public class OrderService {
    private final OrderRepository orderRepository;
    private final BarbershopRepository barbershopRepository;
    private final ServiceRepository serviceRepository;
    private final NotificationService notificationService;
    private final UserRepository userRepository;

    public ApiResponse createOrder(OrderRequest orderDto, User user) {
        Barbershop byId = barbershopRepository.findById(orderDto.getBarbershopId()).orElse(null);
        if (byId == null) {
            return new ApiResponse("Barbershop not found", 400);
        }

        Service byId2 = serviceRepository.findByIdAndBarbershopIdAndDeletedFalse(orderDto.getServiceId(), orderDto.getBarbershopId());
        if (byId2 == null) {
            return new ApiResponse("Service not found", 400);
        }

        LocalDate orderDate = orderDto.getOrderDate();
        LocalTime start = LocalTime.of(orderDto.getHour(), orderDto.getMinute());
        LocalTime end = LocalTime.of(orderDto.getEndHour(), orderDto.getEndMinute());

        if (start.isAfter(end) || end.isBefore(start) || orderDate.isBefore(LocalDate.now())) {
            return new ApiResponse("Invalid input!", 400);
        }

        Optional<Order> overlappingOrder = orderRepository.findByOrderDayAndTimeOverlap(orderDate, start, end);
        if (overlappingOrder.isPresent()) {
            return new ApiResponse("An overlapping order already exists", 400);
        }

        Order order = Order.builder()
                .user(user)
                .service(byId2)
                .barbershop(byId)
                .startTime(start)
                .endTime(end)
                .orderDay(orderDate)
                .status(Status.PENDING)
                .build();

        orderRepository.save(order);
        notificationService.addNotification(
                user,
                "Successfully ordered!",
                "Sizning buyurtmangiz muvaffaqiyatli qabul qilindi!"
        );notificationService.addNotification(
                byId.getOwner(),
                "You have new order!",
                "Sizga " + orderDate + " da soat " + start + " dan " + end + " gacha yangi buyurtma bor! STATUS: " + order.getStatus().toString()
        );
        return new ApiResponse("Order created", 201);
    }

    public ApiResponse getByIdOrder(Integer id){
        Optional<Order> byId = orderRepository.findById(id);
        if (byId.isEmpty()) {
            return new ApiResponse("Order not found", 404);
        }

        OrderResponse response = OrderResponse.builder()
                .id(byId.get().getId())
                .serviceName(byId.get().getService().getServiceName())
                .servicePrice(byId.get().getService().getPrice())
                .BarbershopName(byId.get().getBarbershop().getName())
                .userFullName(byId.get().getUser().getFullName())
                .orderDate(byId.get().getOrderDay())
                .orderTime(byId.get().getStartTime())
                .build();
        return new ApiResponse(response);
    }

    public ApiResponse allOrders(int page, int size){
        Page<Order> orders = orderRepository.findAll(PageRequest.of(page, size));
        List<OrderResponse> responses = orders.map(this::toResponse).stream().toList();

        CustomerPageable pageable= CustomerPageable.builder()
                .body(responses)
                .totalPages(orders.getTotalPages())
                .totalElements(orders.getTotalElements())
                .page(orders.getNumber())
                .size(orders.getSize())
                .build();

        return new ApiResponse(pageable);
    }

    private OrderResponse toResponse(Order order) {
        return OrderResponse.builder()
                .orderDate(order.getOrderDay())
                .servicePrice(order.getService().getPrice())
                .userFullName(order.getUser().getFullName())
                .orderTime(order.getStartTime())
                .serviceName(order.getService().getServiceName())
                .BarbershopName(order.getBarbershop().getName())
                .id(order.getId())
                .build();
    }

    public ApiResponse doneOrder(Integer id){
        Optional<Order> byId = orderRepository.findById(id);
        if (byId.isEmpty()) {
            return new ApiResponse("Order not found", 404);
        }
        Order order = byId.get();
        order.setStatus(Status.COMPLETED);
        orderRepository.save(order);
        return new ApiResponse("Success", 200);
    }
    public ApiResponse updateOrderTimeAndDate(Integer id, OrderDto orderDto){
        Optional<Order> byId = orderRepository.findByIdAndDeletedFalse(id);
        if (byId.isEmpty()) {
            return new ApiResponse("Order not found", 404);
        }

        LocalDate dateOrder = orderDto.getOrderDate();
        LocalTime start = LocalTime.of(orderDto.getHour(), orderDto.getMinute());
        LocalTime end = LocalTime.of(orderDto.getEndHour(), orderDto.getEndMinute());

        if (start.isAfter(end) || end.isBefore(start) || dateOrder.isBefore(LocalDate.now())) {
            return new ApiResponse("Invalid input!", 400);
        }

        Optional<Order> overlappingOrder = orderRepository.findByOrderDayAndTimeOverlap(dateOrder, start, end);
        if (overlappingOrder.isPresent()) {
            return new ApiResponse("An overlapping order already exists", 400);
        }
        Order order = byId.get();
        order.setStartTime(start);
        order.setEndTime(end);
        order.setOrderDay(dateOrder);
        orderRepository.save(order);
        return new ApiResponse("Order updated", 200);
    }
    public ApiResponse currentUserOrders(User user){
        Optional<User> byId = userRepository.findByIdAndDeletedFalse(user.getId());
        if (byId.isEmpty()) {
            return new ApiResponse("User not found", 404);
        }
        List<Order> all = orderRepository.findAllByUserIdAndDeletedFalse(user.getId());
        List<Ordered> orders = new ArrayList<>();
        for (Order order : all) {
            Ordered ordered = orderDto(order);
            orders.add(ordered);
        }
        UserDto userDto = UserDto.builder()
                .id(byId.get().getId())
                .fullName(byId.get().getFullName())
                .orders(orders)
                .build();
        return new ApiResponse("User's orders", 200, userDto);
    }
    public Ordered orderDto(Order order){
        return Ordered.builder()
                .serviceId(order.getService().getId())
                .barbershopId(order.getBarbershop().getId())
                .startTime(order.getStartTime())
                .endTime(order.getEndTime())
                .orderDate(order.getOrderDay())
                .status(order.getStatus())
                .build();
    }
}