package org.example.shedu.controller;

import io.swagger.annotations.Api;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.shedu.payload.ApiResponse;
import org.example.shedu.payload.request.DistrictDto;
import org.example.shedu.service.DistrictService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/district")
@RequiredArgsConstructor
public class DistrictController {
    private final DistrictService districtService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addDistrict(@RequestBody @Valid DistrictDto districtDto){
        ApiResponse apiResponse = districtService.addDistrict(districtDto);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<ApiResponse> getOneDistrict(@PathVariable int id){
        ApiResponse byId = districtService.getById(id);
        return ResponseEntity.ok(byId);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllDistrict(@RequestParam(defaultValue = "0")int page,
                                                      @RequestParam(defaultValue = "5")int size){
        ApiResponse all = districtService.getAll(page, size);
        return ResponseEntity.ok(all);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateDistrict(@PathVariable Integer id, @RequestBody @Valid DistrictDto districtDto){
        ApiResponse apiResponse = districtService.updateDistrict(id, districtDto);
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteDistrict(@PathVariable Integer id){
        ApiResponse apiResponse = districtService.deleteDistrict(id);
        return ResponseEntity.ok(apiResponse);
    }
}
