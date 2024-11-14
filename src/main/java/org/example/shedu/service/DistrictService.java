package org.example.shedu.service;

import lombok.RequiredArgsConstructor;
import org.example.shedu.entity.District;
import org.example.shedu.entity.Region;
import org.example.shedu.payload.ApiResponse;
import org.example.shedu.payload.request.DistrictDto;
import org.example.shedu.payload.response.DistrictResponse;
import org.example.shedu.repository.DistrictRepository;
import org.example.shedu.repository.RegionRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor

public class DistrictService {
    private final DistrictRepository districtRepository;
    private final RegionRepository regionRepository;

    public ApiResponse addDistrict(DistrictDto districtDto) {
        boolean b = districtRepository.existsByNameAndDeletedIsFalse(districtDto.getDistrictName());
        if (b) {
            return new ApiResponse("District already exists",400);
        }

        Optional<Region> byId = regionRepository.findById(districtDto.getRegionId());
        if (byId.isEmpty()) {
            return new ApiResponse("Region topilmadi.",404);
        }

        District district = District.builder()
                .name(districtDto.getDistrictName())
                .region(byId.get())
                .build();
        districtRepository.save(district);
        return new ApiResponse("District muvaffaqiyatli qo'shildi.",200);
    }

    public ApiResponse getById(int id){
        Optional<District> byId = districtRepository.findById(id);
        if (byId.isEmpty()) {
            return new ApiResponse("District topilmadi.",404);
        }

        DistrictResponse response = DistrictResponse.builder()
                .district(byId.get().getName())
                .regionId(byId.get().getRegion().getId())
                .createdAt(byId.get().getCreatedAt())
                .build();
        return new ApiResponse(response);
    }
}
