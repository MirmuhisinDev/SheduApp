package org.example.shedu.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.shedu.entity.User;
import org.example.shedu.payload.ApiResponse;
import org.example.shedu.payload.request.FeedbackDto;
import org.example.shedu.payload.request.FeedbackRequest;
import org.example.shedu.security.CurrentUser;
import org.example.shedu.service.FeedbackService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackController {
    private final FeedbackService feedbackService;

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> add(@RequestBody @Valid FeedbackRequest request,
                                           @CurrentUser User user) {
        ApiResponse apiResponse = feedbackService.addFeedback(request, user);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @GetMapping("/getOne/{id}")
    public ResponseEntity<ApiResponse> getOne(@PathVariable int id) {
        ApiResponse byIdFeedback = feedbackService.getByIdFeedback(id);
        return ResponseEntity.ok(byIdFeedback);
    }
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAll(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "5") int size) {
        ApiResponse byIdFeedback = feedbackService.allFeedback(page, size);
        return ResponseEntity.ok(byIdFeedback);
    }
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable int id, @RequestBody FeedbackDto request) {
        ApiResponse byIdFeedback = feedbackService.updateFeedback(id, request);
        return ResponseEntity.ok(byIdFeedback);
    }
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable int id) {
        ApiResponse byIdFeedback = feedbackService.deleteFeedback(id);
        return ResponseEntity.ok(byIdFeedback);
    }
}
