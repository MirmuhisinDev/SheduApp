package org.example.shedu.payload.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class RequestUser {
    @NotBlank
    @Size(min = 2, max = 20, message = "Uzunligi kamida 2 ta bolishi kerak")
    private String fullName;
    @NotBlank
    private String phoneNumber;
    private int birthdate;
    private Long fileId;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
