package org.example.shedu.payload.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserResponse {
    private Integer id;
    private String fullName;
    private String phoneNumber;
    private Integer birthdate;
    private String email;
    private String password;
}
