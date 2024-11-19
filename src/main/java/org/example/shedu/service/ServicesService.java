package org.example.shedu.service;

import lombok.RequiredArgsConstructor;
import org.example.shedu.entity.Barbershop;
import org.example.shedu.entity.Order;
import org.example.shedu.entity.Payment;
import org.example.shedu.entity.Service;
import org.example.shedu.payload.ApiResponse;
import org.example.shedu.payload.Pageable;
import org.example.shedu.payload.request.ServiceDto;
import org.example.shedu.payload.response.ServiceResponse;
import org.example.shedu.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ServicesService {
    private final ServiceRepository serviceRepository;
    private final BarbershopRepository barbershopRepository;
    private final FileRepository fileRepository;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    public ApiResponse addService(ServiceDto serviceDto) {
        Optional<Barbershop> byId = barbershopRepository.findById(serviceDto.getBarbershopId());
        if (byId.isEmpty()) {
            return new ApiResponse("Barbershop does not exist!",404);
        }

        Optional<Service> byServiceName = serviceRepository.findByServiceName(serviceDto.getServiceName());
        if (byServiceName.isPresent()) {
            return new ApiResponse("Service already exists!",400);
        }


        Service service = Service.builder()
                .barbershop(byId.get())
                .serviceName(serviceDto.getServiceName())
                .price(serviceDto.getPrice())
                .serviceTime(serviceDto.getServiceTime())
                .description(serviceDto.getDescription())
                .file(serviceDto.getFileId() != null ? fileRepository.findById(serviceDto.getFileId()).get() : null)
                .build();
        serviceRepository.save(service);
        return new ApiResponse("Service added successfully!",201);
    }

    public ApiResponse getById(Integer id) {
        Optional<Service> byId = serviceRepository.findById(id);
        if (byId.isEmpty()) {
            return new ApiResponse("Service does not exist!",404);
        }
        ServiceResponse response = ServiceResponse.builder()
                .id(byId.get().getId())
                .barbershop(byId.get().getBarbershop().getName())
                .serviceName(byId.get().getServiceName())
                .price(byId.get().getPrice())
                .serviceTime(byId.get().getServiceTime())
                .description(byId.get().getDescription())
                .fileId(byId.get().getFile() != null ? fileRepository.findById(byId.get().getFile().getId()).get().getId() :null)
                .createdAt(byId.get().getCreatedAt())
                .build();
        return new ApiResponse(response);
    }

    public ApiResponse allServices(int page, int size){
        Page<Service> all = serviceRepository.findAllByDeletedFalse(PageRequest.of(page, size));
        List<ServiceResponse> responses = new ArrayList<>();
        for (Service service : all) {
            ServiceResponse response = ServiceResponse.builder()
                    .id(service.getId())
                    .serviceName(service.getServiceName())
                    .price(service.getPrice())
                    .serviceTime(service.getServiceTime())
                    .description(service.getDescription())
                    .barbershop(service.getBarbershop().getName())
                    .fileId(service.getFile() != null ? service.getFile().getId() : null)
                    .createdAt(service.getCreatedAt())
                    .build();
            responses.add(response);
        }
        Pageable pageable = Pageable.builder()
                .page(all.getNumber())
                .size(all.getSize())
                .totalPages(all.getTotalPages())
                .totalElements(all.getTotalElements())
                .body(responses)
                .build();
        return new ApiResponse(pageable);
    }
    public ApiResponse updateService(Integer id, ServiceDto serviceDto) {
        Optional<Service> byId = serviceRepository.findById(id);
        if (byId.isEmpty()) {
            return new ApiResponse("Service does not exist!",404);
        }

        Optional<Barbershop> byId1 = barbershopRepository.findById(serviceDto.getBarbershopId());
        if (byId1.isEmpty()) {
            return new ApiResponse("Barbershop does not exist!",404);
        }

        boolean b = serviceRepository.existsByServiceNameAndIdNot(serviceDto.getServiceName(), id);
        if (b) {
            return new ApiResponse("Service already exists!",404);
        }
        Service service = byId.get();
        service.setServiceName(serviceDto.getServiceName());
        service.setPrice(serviceDto.getPrice());
        service.setServiceTime(serviceDto.getServiceTime());
        service.setDescription(serviceDto.getDescription());
        service.setBarbershop(byId1.get());
        serviceRepository.save(service);
        return new ApiResponse("Service muvaffaqiyatli o'zgartirildi!",200);
    }

    public ApiResponse deletedService(Integer id){
        Optional<Service> byId = serviceRepository.findById(id);
        if (byId.isEmpty()) {
            return new ApiResponse("Service does not exist!",404);
        }

        List<Order> allByService = orderRepository.findAllByServiceIdAndDeletedFalse(id);
        List<Order> deletedOrders = new ArrayList<>();
        for (Order order : allByService) {
            order.setDeleted(true);
            order.setService(null);
            deletedOrders.add(order);
        }

        List<Payment> allService = paymentRepository.findAllByServiceIdAndDeletedFalse(id);
        List<Payment> deletedPayments = new ArrayList<>();
        for (Payment payment : allService) {
            payment.setDeleted(true);
            payment.setService(null);
            deletedPayments.add(payment);
        }

        Service service = byId.get();
        service.setDeleted(true);
        serviceRepository.save(service);
        orderRepository.saveAll(deletedOrders);
        paymentRepository.saveAll(deletedPayments);
        return new ApiResponse("Service muvaffaqiyatli o'chirildi!",200);
    }
}
