package org.example.shedu.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
    private String message;
    private int status;
    private Object body;

    public ApiResponse(Object body) {
        this.body = body;
    }

    public ApiResponse(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
