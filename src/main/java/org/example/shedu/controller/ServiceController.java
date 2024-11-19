package org.example.shedu.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.shedu.payload.ApiResponse;
import org.example.shedu.payload.request.ServiceDto;
import org.example.shedu.service.ServicesService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/service")
@RequiredArgsConstructor
public class ServiceController {
    private final ServicesService servicesService;

//    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_MASTER')")
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addService(@RequestBody @Valid ServiceDto serviceDto){
        ApiResponse apiResponse = servicesService.addService(serviceDto);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<ApiResponse> getOneService(@PathVariable Integer id){
        ApiResponse byId = servicesService.getById(id);
        return ResponseEntity.ok(byId);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllService(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "5") int size){
        ApiResponse apiResponse = servicesService.allServices(page, size);
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateService(@PathVariable Integer id,
                                                     @RequestBody @Valid ServiceDto serviceDto){
        ApiResponse apiResponse = servicesService.updateService(id, serviceDto);
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteService(@PathVariable Integer id){
        ApiResponse apiResponse = servicesService.deletedService(id);
        return ResponseEntity.ok(apiResponse);
    }
}
