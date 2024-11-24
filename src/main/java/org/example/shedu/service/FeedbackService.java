package org.example.shedu.service;

import lombok.RequiredArgsConstructor;
import org.example.shedu.entity.Barbershop;
import org.example.shedu.entity.Feedback;
import org.example.shedu.entity.User;
import org.example.shedu.payload.ApiResponse;
import org.example.shedu.payload.CustomerPageable;
import org.example.shedu.payload.request.FeedbackDto;
import org.example.shedu.payload.request.FeedbackRequest;
import org.example.shedu.payload.response.FeedbackResponse;
import org.example.shedu.repository.BarbershopRepository;
import org.example.shedu.repository.FeedbackRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor

public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final BarbershopRepository barbershopRepository;

    public ApiResponse addFeedback(FeedbackRequest feedback, User user) {
        Optional<Barbershop> byId = barbershopRepository.findByIdAndDeletedFalse(feedback.getBarbershopID());
        if (byId.isEmpty()) {
            return new ApiResponse("Barbershop not found",404);
        }
        Feedback feedbackEntity = Feedback.builder()
                .user(user)
                .barbershop(byId.get())
                .comment(feedback.getFeedback())
                .rating(feedback.getRating())
                .build();
        feedbackRepository.save(feedbackEntity);
        return new ApiResponse("Thank you for Feedback!",200);
    }
    public ApiResponse getByIdFeedback(int id){
        Optional<Feedback> byId = feedbackRepository.findById(id);
        if (byId.isEmpty()) {
            return new ApiResponse("Feedback not found",404);
        }
        FeedbackResponse response = FeedbackResponse.builder()
                .feedbackId(byId.get().getId())
                .fromUser(byId.get().getUser().getFullName())
                .barbershop(byId.get().getBarbershop().getName())
                .feedback(byId.get().getComment())
                .status(byId.get().getRating())
                .createdAt(byId.get().getCreatedAt())
                .build();
        return new ApiResponse("Success!",200,response);
    }

    public ApiResponse allFeedback(int page, int size){
        Page<Feedback> all = feedbackRepository.findAllByDeletedFalse(PageRequest.of(page, size));
        List<FeedbackResponse> responses = new ArrayList<>();
        for (Feedback feedback : all) {
            FeedbackResponse response = FeedbackResponse.builder()
                    .feedbackId(feedback.getId())
                    .fromUser(feedback.getUser().getFullName())
                    .barbershop(feedback.getBarbershop().getName())
                    .feedback(feedback.getComment())
                    .status(feedback.getRating())
                    .createdAt(feedback.getCreatedAt())
                    .build();
            responses.add(response);
        }
        CustomerPageable pageable = CustomerPageable.builder()
                .page(all.getNumber())
                .size(all.getSize())
                .totalPages(all.getTotalPages())
                .totalElements(all.getTotalElements())
                .body(responses)
                .build();
        return new ApiResponse("Success!",200,pageable);
    }

    public ApiResponse updateFeedback(int id, FeedbackDto feedbackDto){
        Optional<Feedback> byId = feedbackRepository.findById(id);
        if (byId.isEmpty()) {
            return new ApiResponse("Feedback not found",404);
        }
        Feedback feedback = byId.get();
        feedback.setComment(feedbackDto.getFeedback());
        feedback.setRating(feedbackDto.getRating());
        feedbackRepository.save(feedback);
        return new ApiResponse("Success!",200);
    }
    public ApiResponse deleteFeedback(int id){
        Optional<Feedback> byId = feedbackRepository.findById(id);
        if (byId.isEmpty()) {
            return new ApiResponse("Feedback not found",404);
        }
        Feedback feedback = byId.get();
        feedback.setDeleted(true);
        feedbackRepository.save(feedback);
        return new ApiResponse("Success!",200);
    }
}
