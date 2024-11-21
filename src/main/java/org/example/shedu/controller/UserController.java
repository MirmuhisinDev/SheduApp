package org.example.shedu.controller;

import lombok.RequiredArgsConstructor;
import org.example.shedu.entity.User;
import org.example.shedu.payload.ApiResponse;
import org.example.shedu.payload.auth.UpdateUser;
import org.example.shedu.security.CurrentUser;
import org.example.shedu.service.authService.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/getOne/{id}")
    public ResponseEntity<ApiResponse> getOne(@PathVariable Integer id) {
        ApiResponse byId = userService.getById(id);
        return ResponseEntity.ok(byId);
    }

    @GetMapping("/getMe")
    public ResponseEntity<ApiResponse> getMe(@CurrentUser User user) {
        ApiResponse byId = userService.getMe(user);
        return ResponseEntity.ok(byId);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAll(@RequestParam (defaultValue = "0") int page,
                                              @RequestParam (defaultValue = "5") int size) {
        ApiResponse all = userService.getAll(page, size);
        return ResponseEntity.ok(all);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable Integer id, @RequestBody UpdateUser user) {
        ApiResponse update = userService.update(user, id);
        return ResponseEntity.ok(update);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer id) {
        ApiResponse delete = userService.delete(id);
        return ResponseEntity.ok(delete);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchByFirstName(@RequestParam ("fullName") String fullName) {
        ApiResponse apiResponse = userService.searchByFullName(fullName);
        return ResponseEntity.ok(apiResponse);
    }
}
