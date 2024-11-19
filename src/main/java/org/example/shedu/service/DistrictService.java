package org.example.shedu.service;

import lombok.RequiredArgsConstructor;
import org.example.shedu.entity.Barbershop;
import org.example.shedu.entity.District;
import org.example.shedu.entity.Region;
import org.example.shedu.payload.ApiResponse;
import org.example.shedu.payload.Pageable;
import org.example.shedu.payload.request.DistrictDto;
import org.example.shedu.payload.response.DistrictResponse;
import org.example.shedu.repository.BarbershopRepository;
import org.example.shedu.repository.DistrictRepository;
import org.example.shedu.repository.RegionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor

public class DistrictService {
    private final DistrictRepository districtRepository;
    private final RegionRepository regionRepository;
    private final BarbershopRepository barbershopRepository;

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
                .regionName(byId.get().getRegion().getName())
                .createdAt(byId.get().getCreatedAt())
                .build();
        return new ApiResponse(response);
    }

    public ApiResponse getAll(int page , int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<District> all = districtRepository.findAllByDeletedFalse(pageRequest);
        List<DistrictResponse> responses = new ArrayList<>();

        for (District district : all) {
            DistrictResponse response = DistrictResponse.builder()
                    .regionName(district.getRegion().getName())
                    .district(district.getName())
                    .createdAt(district.getCreatedAt())
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

    public ApiResponse updateDistrict(Integer id, DistrictDto districtDto) {
        Optional<District> byId = districtRepository.findById(id);
        if (byId.isEmpty()) {
            return new ApiResponse("District topilmadi.",404);
        }

        Optional<Region> byId1 = regionRepository.findById(districtDto.getRegionId());
        if (byId1.isEmpty()) {
            return new ApiResponse("Region topilmadi.",404);
        }

        boolean b = districtRepository.existsByNameAndIdNot(districtDto.getDistrictName(), id);
        if (b) {
            return new ApiResponse("District already exists",400);
        }
        District district = byId.get();
        district.setName(districtDto.getDistrictName());
        district.setRegion(byId1.get());
        districtRepository.save(district);
        return new ApiResponse("District muvaffaqiyatli o'zgartirildi.",200);
    }
    public ApiResponse deleteDistrict(Integer id) {
        Optional<District> byId = districtRepository.findById(id);
        if (byId.isEmpty()) {
            return new ApiResponse("District topilmadi.",404);
        }

        List<Barbershop> allByDistrict = barbershopRepository.findAllByDistrictIdAndDeletedFalse(id);
        List<Barbershop> barbershopList = new ArrayList<>();
        for (Barbershop barbershop : allByDistrict) {
            barbershop.setDistrict(null);
            barbershopList.add(barbershop);
        }

        District district = byId.get();
        district.setDeleted(true);
        districtRepository.save(district);
        barbershopRepository.saveAll(barbershopList);
        return new ApiResponse("District muvaffaqiyatli o'chirildi.",200);
    }
}
