package org.example.shedu.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.shedu.payload.ApiResponse;
import org.example.shedu.payload.request.RegionDto;
import org.example.shedu.service.RegionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/region")
@RequiredArgsConstructor
public class RegionController {
    private final RegionService regionService;

    @PostMapping("/add-region")
    public ResponseEntity<ApiResponse> add(@RequestBody @Valid RegionDto regionDto) {
        ApiResponse apiResponse = regionService.addRegion(regionDto);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<ApiResponse> getOne(@PathVariable Integer id) {
        ApiResponse byId = regionService.getById(id);
        return ResponseEntity.ok(byId);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAll(@RequestParam (defaultValue = "0")int page,
                                              @RequestParam (defaultValue = "5")int size) {
        ApiResponse all = regionService.getAll(page, size);
        return ResponseEntity.ok(all);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Integer id, @RequestBody RegionDto regionDto) {
        ApiResponse apiResponse = regionService.updateRegion(regionDto, id);
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Integer id) {
        ApiResponse apiResponse = regionService.deleteRegion(id);
        return ResponseEntity.ok(apiResponse);
    }
}
