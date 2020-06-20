package org.aykhan.springdataprovidermongo.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ErrorResponseDTO {
    private String message;
    private int status;
}
