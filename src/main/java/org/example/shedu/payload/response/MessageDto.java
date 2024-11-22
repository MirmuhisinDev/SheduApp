package org.example.shedu.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageDto {
    private String sender;
    private String receiver;
    private String message;
    private boolean isRead;

}
