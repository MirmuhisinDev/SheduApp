package org.example.shedu.service;

import lombok.RequiredArgsConstructor;
import org.example.shedu.entity.Barbershop;
import org.example.shedu.entity.Order;
import org.example.shedu.entity.Service;
import org.example.shedu.entity.User;
import org.example.shedu.payload.ApiResponse;
import org.example.shedu.payload.CustomerPageable;
import org.example.shedu.payload.request.OrderDto;
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
    private final NotificationService service;

    public ApiResponse createOrder(OrderDto orderDto, User user) {
        Optional<Barbershop> byId = barbershopRepository.findById(orderDto.getBarbershopId());
        if (byId.isEmpty()) {
            return new ApiResponse("Barbershop not found", 400);
        }

        Service byId2 = serviceRepository.findById(orderDto.getServiceId(), orderDto.getBarbershopId());
        if (byId2 == null) {
            return new ApiResponse("Service not found", 400);
        }


        LocalDate date= orderDto.getOrderDate();
        LocalTime start= orderDto.getStartTime();
        LocalTime end= orderDto.getEndTime();

        Order byOrder = orderRepository.findByOrderDayAndStartTimeAndEndTime(date, start, end).orElse(null);

        if(byOrder != null) {
            return new ApiResponse("Order not found", 400);
        }
        Order order = Order.builder()
                .user(user)
                .service(byId2)
                .barbershop(byId.get())
                .startTime(start)
                .endTime(end)
                .orderDay(date)
                .build();
        orderRepository.save(order);
        service.addNotification(
               user,
               "Order create !",
                user.getFullName()+": Order create "+LocalTime.now()
        );
        return new ApiResponse("Order created", 201);

    }

    public ApiResponse getById(Integer id){
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
}
