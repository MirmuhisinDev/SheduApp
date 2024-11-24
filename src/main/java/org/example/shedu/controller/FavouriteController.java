package org.example.shedu.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.shedu.entity.User;
import org.example.shedu.payload.ApiResponse;
import org.example.shedu.payload.request.FavouriteRequest;
import org.example.shedu.security.CurrentUser;
import org.example.shedu.service.FavouriteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favourite")
@RequiredArgsConstructor
public class FavouriteController {
    private final FavouriteService favouriteService;

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> add(@RequestBody FavouriteRequest request,
                                           @CurrentUser User user) {
        ApiResponse apiResponse = favouriteService.addFavourite(request, user);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @GetMapping("/getOne/{id}")
    public ResponseEntity<ApiResponse> getOne(@PathVariable int id) {
        ApiResponse one = favouriteService.getOne(id);
        return ResponseEntity.ok(one);
    }
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAll(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam (defaultValue = "5") int size) {
        ApiResponse all = favouriteService.getAll(page, size);
        return ResponseEntity.ok(all);
    }
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable int id) {
        ApiResponse apiResponse = favouriteService.deleteFavourite(id);
        return ResponseEntity.ok(apiResponse);
    }
}
