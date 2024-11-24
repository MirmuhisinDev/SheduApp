package org.example.shedu.service;

import lombok.RequiredArgsConstructor;
import org.example.shedu.entity.Barbershop;
import org.example.shedu.entity.Favourite;
import org.example.shedu.entity.User;
import org.example.shedu.payload.ApiResponse;
import org.example.shedu.payload.CustomerPageable;
import org.example.shedu.payload.request.FavouriteRequest;
import org.example.shedu.payload.response.FavouriteResponse;
import org.example.shedu.repository.BarbershopRepository;
import org.example.shedu.repository.FavouriteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FavouriteService {
    private final FavouriteRepository favouriteRepository;
    private final BarbershopRepository barbershopRepository;
    public ApiResponse addFavourite(FavouriteRequest favouriteRequest, User user) {
        Optional<Barbershop> byId = barbershopRepository.findByIdAndDeletedFalse(favouriteRequest.getBarbershopId());
        if (byId.isEmpty()) {
            return new ApiResponse("Barbershop not found",404);
        }
        Favourite favourite = Favourite.builder()
                .user(user)
                .barbershop(byId.get())
                .deleted(false)
                .build();
        favouriteRepository.save(favourite);
        return new ApiResponse("Favourite added",200);
    }

    public ApiResponse getOne(int id) {
        Optional<Favourite> byId = favouriteRepository.findById(id);
        if (byId.isEmpty()) {
            return new ApiResponse("Favourite not found",404);
        }
        FavouriteResponse response = FavouriteResponse.builder()
                .id(byId.get().getId())
                .who(byId.get().getUser().getFullName())
                .barbershop(byId.get().getBarbershop().getName())
                .createdAt(byId.get().getCreatedAt())
                .build();
        return new ApiResponse("Success",200,response);
    }
    public ApiResponse deleteFavourite(int id) {
        Optional<Favourite> byId = favouriteRepository.findById(id);
        if (byId.isEmpty()) {
            return new ApiResponse("Favourite not found",404);
        }
        Favourite favourite = byId.get();
        favourite.setDeleted(true);
        favouriteRepository.save(favourite);
        return new ApiResponse("Favourite deleted",200);
    }
    public ApiResponse getAll(int page, int size) {
        Page<Favourite> all = favouriteRepository.findAll(PageRequest.of(page, size));
        List<FavouriteResponse> responses = new ArrayList<>();
        for (Favourite favourite : all) {
            FavouriteResponse response = FavouriteResponse.builder()
                    .id(favourite.getId())
                    .who(favourite.getUser().getFullName())
                    .barbershop(favourite.getBarbershop().getName())
                    .createdAt(favourite.getCreatedAt())
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
        return new ApiResponse("Success",200,pageable);
    }
}
