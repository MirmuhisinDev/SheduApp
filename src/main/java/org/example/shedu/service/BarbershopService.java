package org.example.shedu.service;

import lombok.RequiredArgsConstructor;
import org.example.shedu.entity.*;
import org.example.shedu.payload.ApiResponse;
import org.example.shedu.payload.Pageable;
import org.example.shedu.payload.request.BarbershopDto;
import org.example.shedu.payload.response.BarbershopResponse;
import org.example.shedu.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor

public class BarbershopService {
    private final BarbershopRepository barbershopRepository;
    private final FileRepository fileRepository;
    private final FeedbackRepository feedbackRepository;
    private final ServiceRepository serviceRepository;
    private final OrderRepository orderRepository;
    private final FavouriteRepository favouriteRepository;
    private final WorkDaysRepository workDaysRepository;
    private final DistrictRepository districtRepository;

    public ApiResponse addBarbershop(BarbershopDto barbershopDto, User user) {
        Optional<Barbershop> byName = barbershopRepository.findByName(barbershopDto.getName());
        if (byName.isPresent()) {
            return new ApiResponse("Barbershop with name " + barbershopDto.getName() + " already exists",400);
        }

        Optional<District> byId = districtRepository.findById(barbershopDto.getDistrictId());
        if (byId.isEmpty()) {
            return new ApiResponse("District with id " + barbershopDto.getDistrictId() + " does not exist",404);
        }

        Barbershop barbershop = Barbershop.builder()
                .name(barbershopDto.getName())
                .info(barbershopDto.getInfo())
                .email(barbershopDto.getEmail())
                .district(byId.get())
                .file(barbershopDto.getFileId() != null ? fileRepository.findById(barbershopDto.getFileId()).get() : null)
                .createdBy(user)
                .build();
        barbershopRepository.save(barbershop);
        return new ApiResponse("Barbershop with name " + barbershopDto.getName() + " added successfully",200);
    }

    public ApiResponse getById(Integer id){
        Optional<Barbershop> byId = barbershopRepository.findById(id);
        if (byId.isEmpty()) {
            return new ApiResponse("Barbershop with id " + id + " does not exist",404);
        }
        BarbershopResponse response = BarbershopResponse.builder()
                .id(byId.get().getId())
                .name(byId.get().getName())
                .info(byId.get().getInfo())
                .email(byId.get().getEmail())
                .district(byId.get().getDistrict().getName())
                .fileId(byId.get().getFile().getId() != null ? fileRepository.findById(byId.get().getFile().getId()).get().getId() : null)
                .createdAt(byId.get().getCreatedAt())
                .build();
        return new ApiResponse(response);
    }

    public ApiResponse getAll(int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Barbershop> all = barbershopRepository.findAllByDeletedFalse(pageRequest);
        List<BarbershopResponse> responses = new ArrayList<>();

        for (Barbershop barbershop : all) {
            BarbershopResponse response = BarbershopResponse.builder()
                    .id(barbershop.getId())
                    .name(barbershop.getName())
                    .info(barbershop.getInfo())
                    .email(barbershop.getEmail())
                    .district(barbershop.getDistrict().getName())
                    .ownerFullName(barbershop.getCreatedBy() != null ? barbershop.getCreatedBy().getFullName() : null)
                    .fileId(barbershop.getFile() != null ? barbershop.getFile().getId() : null)
                    .createdAt(barbershop.getCreatedAt())
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

    public ApiResponse update(Integer id, BarbershopDto barbershopDto) {
        Optional<Barbershop> byId = barbershopRepository.findById(id);
        if (byId.isEmpty()) {
            return new ApiResponse("Barbershop with id " + id + " does not exist",404);
        }

        Optional<District> byId1 = districtRepository.findById(barbershopDto.getDistrictId());
        if (byId1.isEmpty()) {
            return new ApiResponse("District with id " + id + " does not exist",404);
        }

        boolean b = barbershopRepository.existsByNameAndIdNot(barbershopDto.getName(), id);
        if (b) {
            return new ApiResponse("This name already exists.",400);
        }

        Barbershop barbershop = byId.get();
        barbershop.setName(barbershopDto.getName());
        barbershop.setInfo(barbershopDto.getInfo());
        barbershop.setEmail(barbershopDto.getEmail());
        barbershop.setDistrict(byId1.get());
        barbershop.setFile(byId.get().getFile());
        return new ApiResponse("Barbershop updated successfully",200);
    }

    public ApiResponse delete(Integer id) {
        Optional<Barbershop> byId = barbershopRepository.findById(id);
        if (byId.isEmpty()) {
            return new ApiResponse("Barbershop with id " + id + " does not exist",404);
        }

        List<Feedback> byId1 = feedbackRepository.findAllByBarbershopIdAndDeletedFalse(byId.get().getId());
        List<Feedback> feedbacks = new ArrayList<>();
        for (Feedback feedback : byId1) {
            feedback.setBarbershop(null);
            feedback.setDeleted(true);
            feedbacks.add(feedback);
        }

        List<Service> allByBarbershopId = serviceRepository.findAllByBarbershopIdAndDeletedFalse(byId.get().getId());
        List<Service> services = new ArrayList<>();
        for (Service service : allByBarbershopId) {
            service.setBarbershop(null);
            service.setDeleted(true);
            services.add(service);
        }

        List<Order> allByBarbershop = orderRepository.findAllByBarbershopIdAndDeletedFalse(byId.get().getId());
        List<Order> orders = new ArrayList<>();
        for (Order order : allByBarbershop) {
            order.setBarbershop(null);
            order.setDeleted(true);
            orders.add(order);
        }
        List<Favourite> byBarbershopId = favouriteRepository.findAllByBarbershopIdAndDeletedFalse(byId.get().getId());
        List<Favourite> favourites = new ArrayList<>();
        for (Favourite favourite : byBarbershopId) {
            favourite.setBarbershop(null);
            favourite.setDeleted(true);
            favourites.add(favourite);
        }

        List<WorkDays> allBarbershopId = workDaysRepository.findAllByBarbershopIdAndDeletedFalse(byId.get().getId());
        List<WorkDays> workDays = new ArrayList<>();
        for (WorkDays workDay : allBarbershopId) {
            workDay.setBarbershop(null);
            workDay.setDeleted(true);
            workDays.add(workDay);
        }
        Barbershop barbershop = byId.get();
        barbershop.setDeleted(true);
        barbershopRepository.save(barbershop);
        feedbackRepository.saveAll(feedbacks);
        orderRepository.saveAll(orders);
        favouriteRepository.saveAll(favourites);
        workDaysRepository.saveAll(workDays);
        serviceRepository.saveAll(services);

        return new ApiResponse("Barbershop deleted successfully",200);
    }
}