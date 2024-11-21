package org.example.shedu.controller;

import io.swagger.v3.oas.annotations.Operation;
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

   @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_MASTER,')")
   @Operation(summary = "barbershop add",description = "yangi barbershop qushadi")
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addBarbershop(@RequestBody @Valid BarbershopDto barbershopDto,
                                                     @CurrentUser User user){
        ApiResponse apiResponse = barbershopService.addBarbershop(barbershopDto, user);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_MASTER,')")
   @Operation(summary = "barbershop getOne",description = " barbershopni idsi buyicha barbershop qaytaradi  qaytaradi ")
    @GetMapping("/getOne/{id}")
    public ResponseEntity<ApiResponse> getOneBarbershop(@PathVariable Integer id){
        ApiResponse byId = barbershopService.getById(id);
        return ResponseEntity.ok(byId);
    }
     @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_MASTER','ROLE_CUSTOMER')")
   @Operation(summary = "barbershop All",description = " baracha barberlarni kursatadi ")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> allBarbershop(@RequestParam (defaultValue = "0")int page,
                                                     @RequestParam(defaultValue = "5") int size){
        ApiResponse all = barbershopService.getAll(page, size);
        return ResponseEntity.ok(all);
    }
   @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_MASTER,')")
   @Operation(summary = "barbershop update",description = "barbersni Update qiladi ")
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> update(@RequestBody @Valid BarbershopDto dto,
                                              @PathVariable Integer id){
        ApiResponse update = barbershopService.update(id, dto);
        return ResponseEntity.ok(update);
    }
   @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_MASTER,')")
   @Operation(summary = "barbershop delete",description = "barbershopni uchiradi ")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Integer id){
        ApiResponse delete = barbershopService.delete(id);
        return ResponseEntity.ok(delete);
    }
}
