package org.example.shedu.service;

import lombok.RequiredArgsConstructor;
import org.example.shedu.entity.Region;
import org.example.shedu.payload.ApiResponse;
import org.example.shedu.payload.Pageable;
import org.example.shedu.payload.request.RegionDto;
import org.example.shedu.payload.response.RegionResponse;
import org.example.shedu.repository.RegionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RegionService {
    private final RegionRepository regionRepository;

    public ApiResponse addRegion(RegionDto regionDto) {
        boolean b = regionRepository.existsByNameAndDeletedIsFalse(regionDto.getRegionName());
        if (b) {
            return new ApiResponse("Region already exists",400);
        }

        Region region = Region.builder()
                .id(regionDto.getId())
                .name(regionDto.getRegionName())
                .build();
        regionRepository.save(region);
        return new ApiResponse("Region muvaffaqiyatli qo'shildi",201);
    }

    public ApiResponse getById(int id){
        Optional<Region> byId = regionRepository.findById(id);
        if (byId.isEmpty()) {
            return new ApiResponse("Region topilmadi",404);
        }
        RegionResponse response = RegionResponse.builder()
                .id(byId.get().getId())
                .regionName(byId.get().getName())
                .build();
        return new ApiResponse(response);
    }

    public ApiResponse getAll(int page, int size){
        PageRequest request = PageRequest.of(page, size);
        Page<Region> regions = regionRepository.findAllByDeletedIsFalse(request);
        List<RegionResponse> responses = new ArrayList<>();
        for (Region region : regions) {
            RegionResponse response = RegionResponse.builder()
                    .id(region.getId())
                    .regionName(region.getName())
                    .build();
            responses.add(response);
        }
        Pageable pageable = Pageable.builder()
                .page(request.getPageNumber())
                .size(request.getPageSize())
                .totalPages(regions.getTotalPages())
                .totalElements(regions.getTotalElements())
                .body(responses)
                .build();
        return new ApiResponse(pageable);
    }

    public ApiResponse updateRegion(RegionDto regionDto, Integer id) {
        Optional<Region> byId = regionRepository.findById(id);
        if (byId.isEmpty()) {
            return new ApiResponse("Region topilmadi",404);
        }

        boolean b = regionRepository.existsByNameAndIdNot(regionDto.getRegionName(), id);
        if (b) {
            return new ApiResponse("Region already exists",400);
        }
        Region region = byId.get();
        region.setName(regionDto.getRegionName());
        regionRepository.save(region);
        return new ApiResponse("Region muvaffaquyatli o'zgartirildi.",200);
    }

    public ApiResponse deleteRegion(int id) {
        Optional<Region> byId = regionRepository.findById(id);
        if (byId.isEmpty()) {
            return new ApiResponse("Region topilmadi",404);
        }
        Region region = byId.get();
        region.setDeleted(true);
        regionRepository.save(region);
        return new ApiResponse("Region muvaffaqiyatli o'chirildi.",200);
    }
}