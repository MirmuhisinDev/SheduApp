package org.example.shedu.service.authService;

import lombok.RequiredArgsConstructor;
import org.example.shedu.entity.User;
import org.example.shedu.payload.ApiResponse;
import org.example.shedu.payload.Pageable;
import org.example.shedu.payload.auth.UpdateUser;
import org.example.shedu.payload.response.UserResponse;
import org.example.shedu.repository.UserRepository;
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

    public ApiResponse getById(Integer id){
        Optional<User> byId = userRepository.findById(id);
        if(byId.isPresent()){
            UserResponse response = UserResponse.builder()
                    .fullName(byId.get().getFullName())
                    .birthdate(byId.get().getBirthdate())
                    .phoneNumber(byId.get().getPhoneNumber())
                    .email(byId.get().getEmail())
                    .password(byId.get().getPassword())
                    .build();
            return new ApiResponse(response);
        }
        return new ApiResponse("Sizning ma'lumotlaringiz topilmadi.",404);
    }

    public ApiResponse getAll(int page, int size){
        PageRequest request = PageRequest.of(page, size);
        Page<User> users = userRepository.findAll(request);
        List<UserResponse> responses = new ArrayList<>();
        for (User user : users) {
            UserResponse response = UserResponse.builder()
                    .id(user.getId())
                    .fullName(user.getFullName())
                    .birthdate(user.getBirthdate())
                    .phoneNumber(user.getPhoneNumber())
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .build();
            responses.add(response);
        }
        Pageable pageable = Pageable.builder()
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
        if(byId.isPresent()){
            User user = byId.get();
            user.setDeleted(true);
            userRepository.save(user);
            return new ApiResponse("Sizning ma'lumotlaringiz muvaffaqiyatli o'chirildi.", 200);
        }
        return new ApiResponse("Sizning ma'lumotlaringiz topilmadi.",404);
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
                    .password(user.getPassword())
                    .build();
            responses.add(response);
            return new ApiResponse(responses);
        }
        return new ApiResponse("Sizning ma'lumotlaringiz topilmadi.",404);
    }
}
