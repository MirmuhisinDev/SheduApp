package org.example.shedu.payload;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class Pageable {
    private int page;
    private int size;
    private int totalPages;
    private long totalElements;
    private Object body;
}
