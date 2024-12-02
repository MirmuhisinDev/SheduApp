package org.example.shedu.payload.request;

import lombok.*;
import org.example.shedu.entity.Order;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {
    private int id;
    private String fullName;
    private List<Ordered> orders;
}
