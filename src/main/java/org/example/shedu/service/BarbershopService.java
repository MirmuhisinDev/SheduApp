package org.example.shedu.service;

import lombok.RequiredArgsConstructor;
import org.example.shedu.entity.*;
import org.example.shedu.exception.GlobalExceptionHandle;
import org.example.shedu.payload.ApiResponse;
import org.example.shedu.payload.CustomerPageable;
import org.example.shedu.payload.request.BarbershopRequest;
import org.example.shedu.payload.response.BarbershopResponse;
import org.example.shedu.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    private final DaysRepository daysRepository;
    private final WorkDaysService service;

    public ApiResponse addBarbershop(BarbershopRequest barbershopDto, User user) {
        if (barbershopRepository.existsByName(barbershopDto.getName())) {
            return new ApiResponse("Barbershop with name " + barbershopDto.getName() + " already exists", 400);
        }

        District districtOpt = districtRepository.findById(barbershopDto.getDistrictId()).orElse(null);
        if (districtOpt== null) {
            return new ApiResponse("District with id " + barbershopDto.getDistrictId() + " does not exist", 404);
        }

        Barbershop barbershop = Barbershop.builder()
                .name(barbershopDto.getName())
                .info(barbershopDto.getInfo())
                .email(user.getEmail())
                .district(districtOpt)
                .file(barbershopDto.getFileId() != null ? fileRepository.findById(barbershopDto.getFileId()).orElse(null) : null)
                .owner(user)
                .build();

        barbershopRepository.save(barbershop);
        service.add(barbershopDto.getStartTime(), barbershopDto.getEndTime(), barbershopDto.getDays(), barbershop);
        return new ApiResponse("Barbershop with name " + barbershopDto.getName() + " added successfully", 200);
    }

    public ApiResponse getById(Integer id) {
        Optional<Barbershop> barbershopOpt = barbershopRepository.findById(id);
        if (barbershopOpt.isEmpty()) {
            return new ApiResponse("Barbershop with id " + id + " does not exist", 404);
        }

        Barbershop barbershop = barbershopOpt.get();

        WorkDays w = workDaysRepository.findByBarbershopIdAndDeletedFalse(barbershop.getId()).orElse(null);

        assert w != null;

        return new ApiResponse(toResponse(barbershop, w.getDays(), w.getCloseTime(), w.getOpenTime()));
    }

    private BarbershopResponse toResponse(Barbershop barbershop, List<Days> days, LocalTime start, LocalTime end) {
        return BarbershopResponse.builder()
                .id(barbershop.getId())
                .name(barbershop.getName())
                .info(barbershop.getInfo())
                .email(barbershop.getEmail())
                .district(barbershop.getDistrict().getName())
                .fileId(barbershop.getFile() != null ? barbershop.getFile().getId() : null)
                .createdAt(barbershop.getCreatedAt())
                .days(days)
                .startTime(start)
                .endTime(end)
                .build();
    }

    public ApiResponse getAll(int page, int size) {
        Page<Barbershop> all = barbershopRepository.findAllByDeletedFalse(PageRequest.of(page, size));

        List<BarbershopResponse> responses = new ArrayList<>();
        for (Barbershop barbershop : all) {
            WorkDays w=workDaysRepository.findByBarbershopIdAndDeletedFalse(barbershop.getId()).orElse(null);
            List<Days> days=new ArrayList<>();
            for (Days day : Objects.requireNonNull(w).getDays()) {
                Days d=daysRepository.findById(day.getDay()).orElse(null);
                days.add(d);
            }
            BarbershopResponse b = BarbershopResponse.builder()
                    .id(barbershop.getId())
                    .name(barbershop.getName())
                    .info(barbershop.getInfo())
                    .email(barbershop.getEmail())
                    .district(barbershop.getDistrict().getName())
                    .fileId(barbershop.getFile() != null ? barbershop.getFile().getId() : null)
                    .createdAt(barbershop.getCreatedAt())
                    .days(days)
                    .ownerFullName(barbershop.getOwner().getFullName())
                    .endTime(w.getCloseTime())
                    .startTime(w.getOpenTime())
                    .build();

            responses.add(b);
        }
       CustomerPageable pageable = CustomerPageable.builder()
                .body(responses)
                .page(all.getNumber())
                .size(all.getSize())
                .totalElements(all.getTotalElements())
                .totalPages(all.getTotalPages())
                .build();
        return new ApiResponse(pageable);
    }

public ApiResponse update(Integer id, BarbershopRequest barbershopDto) {
    Optional<Barbershop> byId = barbershopRepository.findById(id);
    if (byId.isEmpty()) {
        return new ApiResponse("Barbershop with id " + id + " does not exist", 404);
    }

    Optional<District> byId1 = districtRepository.findById(barbershopDto.getDistrictId());
    if (byId1.isEmpty()) {
        return new ApiResponse("District with id " + id + " does not exist", 404);
    }

    boolean b = barbershopRepository.existsByNameAndIdNot(barbershopDto.getName(), id);
    if (b) {
        return new ApiResponse("This name already exists.", 400);
    }

    Barbershop barbershop = byId.get();
    //
    barbershop.setName(barbershopDto.getName());
    barbershop.setInfo(barbershopDto.getInfo());
    barbershop.setEmail(barbershop.getOwner().getEmail());
    barbershop.setDistrict(byId1.get());
    barbershop.setFile(byId.get().getFile());

    service.update(barbershopDto.getStartTime(), barbershopDto.getEndTime(), barbershopDto.getDays(), barbershop.getId());
    return new ApiResponse("Barbershop updated successfully", 200);
}

public ApiResponse delete(Integer id) {
    Optional<Barbershop> byId = barbershopRepository.findById(id);
    if (byId.isEmpty()) {
        return new ApiResponse("Barbershop with id " + id + " does not exist", 404);
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

    Barbershop barbershop = byId.get();
    barbershop.setDeleted(true);
    barbershopRepository.save(barbershop);
    feedbackRepository.saveAll(feedbacks);
    orderRepository.saveAll(orders);
    favouriteRepository.saveAll(favourites);
    serviceRepository.saveAll(services);
    service.delete(barbershop.getId());
    return new ApiResponse("Barbershop deleted successfully", 200);
}
}