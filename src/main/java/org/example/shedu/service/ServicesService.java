package org.example.shedu.service;

import lombok.RequiredArgsConstructor;
import org.example.shedu.entity.*;
import org.example.shedu.payload.ApiResponse;
import org.example.shedu.payload.CustomerPageable;
import org.example.shedu.payload.request.ServiceRequest;
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

    public ApiResponse addService(ServiceRequest serviceDto) {
        Barbershop byId = barbershopRepository.findById(serviceDto.getBarbershopId()).orElse(null);
        if (byId == null) {
            return new ApiResponse("Barbershop does not exist!",404);
        }

        if (serviceRepository.existsByServiceName(serviceDto.getServiceName())) {
            return new ApiResponse("Service already exists!",400);
        }
        File file = fileRepository.findById(serviceDto.getFileId()).orElse(null);


        Service service = Service.builder()
                .barbershop(byId)
                .serviceName(serviceDto.getServiceName())
                .serviceTime(serviceDto.getServiceTime())
                .price(serviceDto.getPrice())
                .description(serviceDto.getDescription())
                .deleted(false)
                .file(file)
                .build();
        serviceRepository.save(service);
        return new ApiResponse("Service added successfully!",201);
    }

    public ApiResponse getById(Integer id) {
        Optional<Service> byId = serviceRepository.findById(id);
        if (byId.isEmpty()) {
            return new ApiResponse("Service does not exist!",404);
        }

        File file = fileRepository.findById(byId.get().getFile().getId()).orElse(null);
        assert file != null;
        ServiceResponse response = ServiceResponse.builder()
                .id(byId.get().getId())
                .barbershop(byId.get().getBarbershop().getName())
                .serviceTime(byId.get().getServiceTime())
                .serviceName(byId.get().getServiceName())
                .price(byId.get().getPrice())
                .description(byId.get().getDescription())
                .fileId(byId.get().getFile() != null ? byId.get().getFile().getId() : null)
                .createdAt(byId.get().getCreatedAt())
                .build();
        return new ApiResponse(response);
    }

    public ApiResponse allServices(int page, int size){
        Page<Service> all = serviceRepository.findAll(PageRequest.of(page, size));
        List<ServiceResponse> responses = new ArrayList<>();
        for (Service service : all) {
            ServiceResponse response = ServiceResponse.builder()
                    .id(service.getId())
                    .serviceName(service.getServiceName())
                    .serviceTime(service.getServiceTime())
                    .price(service.getPrice())
                    .description(service.getDescription())
                    .barbershop(service.getBarbershop().getName())
                    .fileId(service.getFile() != null ? service.getFile().getId() : null)
                    .createdAt(service.getCreatedAt())
                    .build();
            responses.add(response);
        }
        CustomerPageable pageable = CustomerPageable.builder()
                .page(all.getNumber())
                .size(all.getSize())
                .totalPages(all.getTotalPages())
                .totalElements(all.getTotalElements())
                .body(responses)
                .build();
        return new ApiResponse(pageable);
    }
    public ApiResponse updateService(Integer id, ServiceRequest serviceDto) {
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
        service.setServiceTime(serviceDto.getServiceTime());
        service.setPrice(serviceDto.getPrice());
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
