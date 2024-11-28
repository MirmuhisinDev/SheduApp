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
import org.example.shedu.payload.response.OrderResponse;
import org.example.shedu.repository.BarbershopRepository;
import org.example.shedu.repository.OrderRepository;
import org.example.shedu.repository.ServiceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor

public class OrderService {
    private final OrderRepository orderRepository;
    private final BarbershopRepository barbershopRepository;
    private final ServiceRepository serviceRepository;
    private final NotificationService notificationService;

    public ApiResponse createOrder(OrderRequest orderDto, User user) {
        Barbershop byId = barbershopRepository.findById(orderDto.getBarbershopId()).orElse(null);
        if (byId==null) {
            return new ApiResponse("Barbershop not found", 400);
        }

        Service byId2 = serviceRepository.findByIdAndBarbershopIdAndDeletedFalse(orderDto.getServiceId(), orderDto.getBarbershopId());
        if (byId2 == null) {
            return new ApiResponse("Service not found", 400);
        }
        LocalDate OrderDate = orderDto.getOrderDate();
        LocalTime start= orderDto.getStartTime();
        LocalTime end= orderDto.getEndTime();

        Order byOrder = orderRepository.findByOrderDayAndStartTimeAndEndTime(OrderDate, start, end).orElse(null);

        if(byOrder != null) {
            return new ApiResponse("Order already exists", 400);
        }
        Order order = Order.builder()
                .user(user)
                .service(byId2)
                .barbershop(byId)
                .startTime(start)
                .endTime(end)
                .orderDay(OrderDate)
                .status(Status.PENDING)
                .build();
        orderRepository.save(order);
        notificationService.addNotification(
                user,
                "Successfully ordered!",
                " sizning buyurtmangiz muvafaqqiyatli qabul qilindi!"
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
    public ApiResponse deleteOrder(Integer id){
        Optional<Order> byId = orderRepository.findById(id);
        if (byId.isEmpty()) {
            return new ApiResponse("Order not found", 404);
        }
        Order order = byId.get();
        order.setDeleted(true);
        order.setStatus(Status.CANCELLED);
        orderRepository.save(order);
        return new ApiResponse("Order deleted", 204);
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

        LocalTime orderStart = orderDto.getStartTime();
        LocalTime orderEnd = orderDto.getEndTime();
        LocalDate OrderDate = orderDto.getOrderDate();
        Optional<Order> byOrder = orderRepository.findByOrderDayAndStartTimeAndEndTime(OrderDate, orderStart, orderEnd);
        if (byOrder.isPresent()) {
            return new ApiResponse("Order already exists", 404);
        }
        Order order = byId.get();
        order.setStartTime(orderStart);
        order.setEndTime(orderEnd);
        order.setOrderDay(OrderDate);
        orderRepository.save(order);
        return new ApiResponse("Order updated", 200);
    }
}