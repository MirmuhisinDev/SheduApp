package org.example.shedu.service.authService;

import lombok.RequiredArgsConstructor;
import org.example.shedu.entity.*;
import org.example.shedu.payload.ApiResponse;
import org.example.shedu.payload.CustomerPageable;
import org.example.shedu.payload.auth.UpdateUser;
import org.example.shedu.payload.response.UserResponse;
import org.example.shedu.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor

public class UserService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final FavouriteRepository favouriteRepository;
    private final FeedbackRepository feedbackRepository;
    private final ChatRepository chatRepository;

    public ApiResponse getById(Integer id){
        Optional<User> byId = userRepository.findById(id);
        if(byId.isPresent()){
            UserResponse response = UserResponse.builder()
                    .fullName(byId.get().getFullName())
                    .birthdate(byId.get().getBirthdate())
                    .phoneNumber(byId.get().getPhoneNumber())
                    .email(byId.get().getEmail())
                    .role(byId.get().getRole())
                    .build();
            return new ApiResponse(response);
        }
        return new ApiResponse("Sizning ma'lumotlaringiz topilmadi.",404);
    }

    public ApiResponse getAll(int page, int size){
        Page<User> users = userRepository.findAllByDeletedFalse(PageRequest.of(page, size));
        List<UserResponse> responses = new ArrayList<>();
        for (User user : users) {
            UserResponse response = UserResponse.builder()
                    .id(user.getId())
                    .fullName(user.getFullName())
                    .birthdate(user.getBirthdate())
                    .phoneNumber(user.getPhoneNumber())
                    .email(user.getEmail())
                    .build();
            responses.add(response);
        }
        CustomerPageable pageable = CustomerPageable.builder()
                .page(users.getNumber())
                .size(users.getSize())
                .totalPages(users.getTotalPages())
                .totalElements(users.getTotalElements())
                .body(responses)
                .build();
        return new ApiResponse(pageable);
    }

    public ApiResponse update(UpdateUser updateUser, Integer id){
        Optional<User> byId = userRepository.findById(id);
        if(byId.isPresent()){
            User user = byId.get();
            user.setFullName(updateUser.getFullName());
            user.setPhoneNumber(updateUser.getPhoneNumber());
            user.setBirthdate(updateUser.getBirthdate());
            user.setEmail(updateUser.getEmail());
            userRepository.save(user);
            return new ApiResponse("Sizning ma'lumotlaringiz muvaffaqiyatli o'zgartirildi.", 200);
        }
        return new ApiResponse("Sizning ma'lumotlaringiz topilmadi.",404);
    }

    public ApiResponse delete(Integer id){
        Optional<User> byId = userRepository.findById(id);
        if(byId.isEmpty()){
            return new ApiResponse("User not found.", 404);
        }
        List<Order> all = orderRepository.findAllByUserIdAndDeletedFalse(id);
        List<Order> orders = new ArrayList<>();
        for (Order order : all) {
            order.setUser(null);
            order.setDeleted(true);
            orders.add(order);
        }
        List<Favourite> byUserId = favouriteRepository.findAllByUserIdAndDeletedFalse(id);
        List<Favourite> favourites = new ArrayList<>();
        for (Favourite favourite : byUserId) {
            favourite.setDeleted(true);
            favourite.setUser(null);
            favourites.add(favourite);
        }
        List<Feedback> allByUserId = feedbackRepository.findAllByUserIdAndDeletedFalse(id);
        List<Feedback> feedbacks = new ArrayList<>();
        for (Feedback feedback : allByUserId) {
            feedback.setDeleted(true);
            feedback.setUser(null);
            feedbacks.add(feedback);
        }
        List<Chat> allByDeletedFalse = chatRepository.findAllByReceiverIdAndSenderIdAndDeletedFalse(id, id);
        List<Chat> chats = new ArrayList<>();
        for (Chat chat : allByDeletedFalse) {
            chat.setDeleted(true);
            chat.setSender(null);
            chat.setReceiver(null);
            chats.add(chat);
        }
        User user = byId.get();
        user.setDeleted(false);
        userRepository.save(user);
        orderRepository.saveAll(orders);
        favouriteRepository.saveAll(favourites);
        feedbackRepository.saveAll(feedbacks);
        chatRepository.saveAll(chats);
        return new ApiResponse("Success.",200);
    }

    public ApiResponse getMe(User user){
        UserResponse response = mapperUser(user);
        return new ApiResponse(response);
    }

    public ApiResponse searchByFullName(String fullName){
        List<User> byFullName = userRepository.findByFullName(fullName);
        List<UserResponse> responses = new ArrayList<>();
        for (User user : byFullName) {
            UserResponse response = UserResponse.builder()
                    .fullName(user.getFullName())
                    .birthdate(user.getBirthdate())
                    .phoneNumber(user.getPhoneNumber())
                    .email(user.getEmail())
                    .role(user.getRole())
                    .build();
            responses.add(response);
            return new ApiResponse(responses);
        }
        return new ApiResponse("Sizning ma'lumotlaringiz topilmadi.",404);
    }

    public UserResponse mapperUser(User user){
        return UserResponse.builder()
                .fullName(user.getFullName())
                .birthdate(user.getBirthdate())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .email(user.getEmail())
                .build();
    }
}
