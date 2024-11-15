package org.example.shedu.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.shedu.entity.User;
import org.example.shedu.payload.ApiResponse;
import org.example.shedu.payload.request.BarbershopDto;
import org.example.shedu.security.CurrentUser;
import org.example.shedu.service.BarbershopService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/barbershop")
@RequiredArgsConstructor
public class BarbershopController {
    private final BarbershopService barbershopService;

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addBarbershop(@RequestBody @Valid BarbershopDto barbershopDto,
                                                     @CurrentUser User user){
        ApiResponse apiResponse = barbershopService.addBarbershop(barbershopDto, user);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<ApiResponse> getOneBarbershop(@PathVariable Integer id){
        ApiResponse byId = barbershopService.getById(id);
        return ResponseEntity.ok(byId);
    }
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> allBarbershop(@RequestParam (defaultValue = "0")int page,
                                                     @RequestParam(defaultValue = "5") int size){
        ApiResponse all = barbershopService.getAll(page, size);
        return ResponseEntity.ok(all);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> update(@RequestBody @Valid BarbershopDto dto,
                                              @PathVariable Integer id){
        ApiResponse update = barbershopService.update(id, dto);
        return ResponseEntity.ok(update);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Integer id){
        ApiResponse delete = barbershopService.delete(id);
        return ResponseEntity.ok(delete);
    }
}
