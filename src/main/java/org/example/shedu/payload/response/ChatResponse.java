package org.example.shedu.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatResponse {
    private int id;
    private int sender;
    private int receiver;
    private String message;
    private Long filId;

}
