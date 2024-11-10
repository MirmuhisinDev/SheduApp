package org.example.shedu.service;

import lombok.RequiredArgsConstructor;
import org.example.shedu.entity.User;
import org.example.shedu.entity.enums.Role;
import org.example.shedu.payload.ApiResponse;
import org.example.shedu.payload.RequestLogin;
import org.example.shedu.payload.RequestUser;
import org.example.shedu.repository.FileRepository;
import org.example.shedu.repository.UserRepository;
import org.example.shedu.security.JwtProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Random;

@Component
@RequiredArgsConstructor

public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailSenderService emailSenderService;
    private JwtProvider jwtProvider;
    private final FileRepository fileRepository;

    public ApiResponse register(RequestUser requestUser){
        Optional<User> byEmail = userRepository.findByEmail(requestUser.getEmail());
        if(byEmail.isPresent()){
            return new ApiResponse("Email already in use",400);
        }
        int randomNumber = randomNumber();
        User user = User.builder()
                .fullName(requestUser.getFullName())
                .phoneNumber(requestUser.getPhoneNumber())
                .birthdate(requestUser.getBirthdate())
                .email(requestUser.getEmail())
                .password(passwordEncoder.encode(requestUser.getPassword()))
                .activationCode(randomNumber)
                .role(Role.ROLE_CUSTOMER)
                .file(requestUser.getFileId() != null ? fileRepository.findById(requestUser.getFileId()).get() : null)
                .enabled(false)
                .build();
        userRepository.save(user);
        emailSenderService.send(user.getEmail(), "VERIFY EMAIL", "Your activation code is: " + randomNumber);
        return new ApiResponse("Successfully registered",201);
    }
    public int randomNumber(){
        Random random = new Random();
        return random.nextInt(90000)+10000;
    }

    public ApiResponse login(RequestLogin requestLogin){
        Optional<User> byEmail = userRepository.findByEmail(requestLogin.getEmail());
        if(byEmail.isEmpty()){
            return new ApiResponse("Emailingiz topilmadi",404);
        }
        User user = byEmail.get();
        if (!user.isEnabled()) return new ApiResponse("User not enabled",400);
        boolean matches = passwordEncoder.matches(requestLogin.getPassword(), user.getPassword());
        if(matches){
            String s = jwtProvider.generateToken(user.getEmail());
            return new ApiResponse(s+" : : "+user.getRole(), 200);
        }
        return new ApiResponse("Parolingiz mos kelmadi",400);
    }

    public ApiResponse activationCode(Integer code){
        Optional<User> byActivationCode = userRepository.findByActivationCode(code);
        if(byActivationCode.isPresent()){
            User user = byActivationCode.get();
            user.setEnabled(true);
            user.setActivationCode(null);
            userRepository.save(user);
            return new ApiResponse("Sizning accauntingiz muvaffaqiyatli faollashtirildi",200);
        }
        return new ApiResponse("Faollashtirish kodi topilmadi",404);
    }

    public ApiResponse saveAdmin(RequestUser requestUser){
        Optional<User> byEmail = userRepository.findByEmail(requestUser.getEmail());
        if(byEmail.isEmpty()){
            return new ApiResponse("Emailingiz topilmadi",404);
        }
        User user = User.builder()
                .fullName(requestUser.getFullName())
                .phoneNumber(requestUser.getPhoneNumber())
                .birthdate(requestUser.getBirthdate())
                .email(requestUser.getEmail())
                .password(passwordEncoder.encode(requestUser.getPassword()))
                .role(Role.ROLE_MASTER)
                .enabled(false)
                .build();
        userRepository.save(user);
        return new ApiResponse("Master muvoffaqiyatli saqlandi",201);
    }
}
