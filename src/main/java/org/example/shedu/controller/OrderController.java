package org.example.shedu.controller;

import lombok.RequiredArgsConstructor;
import org.example.shedu.entity.User;
import org.example.shedu.payload.ApiResponse;
import org.example.shedu.payload.request.OrderDto;
import org.example.shedu.payload.request.OrderRequest;
import org.example.shedu.security.CurrentUser;
import org.example.shedu.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addOrder(@RequestBody OrderRequest orderDto,
                                                @CurrentUser User user) {
        ApiResponse order = orderService.createOrder(orderDto, user);
        return ResponseEntity.status(order.getStatus()).body(order);
    }
}
