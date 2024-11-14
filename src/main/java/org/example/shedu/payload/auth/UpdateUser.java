package org.example.shedu.payload.auth;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class UpdateUser {
    private String fullName;
    private String phoneNumber;
    private Integer birthdate;
    private String email;
}
